package com.example.soss.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soss.Config.Config;
import com.example.soss.DetalleServicio;
import com.example.soss.Model.ClsServicio;
import com.example.soss.PagoServicio;
import com.example.soss.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ViewHolder> {

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    private LayoutInflater inflador;
    ArrayList<ClsServicio> ListaServicios;
    Context micontext;

    public ServicioAdapter(Context context, ArrayList<ClsServicio> datos) {
        this.ListaServicios = datos;
        micontext = context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_detalle_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        holder.NombreServicio.setText(ListaServicios.get(i).getNombre());
        holder.Precio.setText("Costo : S/ " + ListaServicios.get(i).getPrecio());

        holder.Pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monto = (String) ListaServicios.get(i).getPrecio();
                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(monto)), "USD", "",
                PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(micontext, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                //micontext.startActivityForResult(intent, PAYPAL_REQUEST_CODE);
                intent.putExtra("Precio", ListaServicios.get(i).getPrecio());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(micontext, PagoServicio.class);
                intent.putExtra("Precio", ListaServicios.get(i).getPrecio());
                micontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListaServicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView NombreServicio;
        public TextView Precio;
        public Button   Pagar;

        ViewHolder(View itemView) {
            super(itemView);
            NombreServicio = (TextView) itemView.findViewById(R.id.txtNombre);
            Precio = (TextView) itemView.findViewById(R.id.txtPrecio);
            Pagar = (Button) itemView.findViewById(R.id.btnPagar);
        }
    }
}
