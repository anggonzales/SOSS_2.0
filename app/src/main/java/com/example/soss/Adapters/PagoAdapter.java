package com.example.soss.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soss.Model.ClsPagoServicio;
import com.example.soss.R;

import java.util.ArrayList;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolder> {

    private LayoutInflater inflador;
    ArrayList<ClsPagoServicio> ListaPagoServicio;
    Context micontext;
    ViewHolder viewHolder;

    public PagoAdapter(Context context, ArrayList<ClsPagoServicio> datos) {
        this.ListaPagoServicio = datos;
        micontext = context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_pago_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {

        holder.txtId.setText(ListaPagoServicio.get(i).getId());
        holder.txtPrecio.setText(Double.toString(ListaPagoServicio.get(i).getPrecio()));
        holder.txtEstado.setText(ListaPagoServicio.get(i).getEstado());
        holder.txtFecha.setText(String.valueOf(ListaPagoServicio.get(i).getFecha()));
    }

    @Override
    public int getItemCount() {
        return ListaPagoServicio.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId;
        public TextView txtPrecio;
        public TextView txtEstado;
        public TextView txtFecha;

        ViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.txtId);
            txtPrecio = (TextView) itemView.findViewById(R.id.txtPrecio);
            txtEstado = (TextView) itemView.findViewById(R.id.txtEstado);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
        }
    }
}
