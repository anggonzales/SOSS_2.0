package com.example.soss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.soss.Config.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PagoServicio extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    TextView txtCambio;
    EditText edtMonto;
    EditText edtMontoCambio;
    Button btnPagar;
    String Precio = "";
    String monto = "";
    double monto2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_servicio);

        Intent intent = new Intent (this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        btnPagar = (Button)findViewById(R.id.btnPagar);
        edtMonto = (EditText) findViewById(R.id.edtMonto);
        txtCambio = (TextView) findViewById(R.id.txtCambio);
        edtMontoCambio = (EditText) findViewById(R.id.edtMontoCambio);

        Bundle extras = getIntent().getExtras();
        Precio = extras.getString("Precio");

        edtMonto.setText(Precio);
        ObtenerCambio();

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pagar();
            }
        });
    }

    public void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void ObtenerCambio() {
        final String fecha2 = "2020-08-13";
        //Date objFecha = new Date();
        //String Formato = "aaaa-MM-dd";
        //SimpleDateFormat objFormatofecha = new SimpleDateFormat(Formato);
        //final String fecha = objFormatofecha.format(objFecha);
        String URL = "https://api.sunat.cloud/cambio/" + fecha2;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject itemfecha = response.getJSONObject(fecha2);
                    Log.e("datos", response.toString());
                    Double precioDolar = itemfecha.getDouble("compra");
                    Log.e("compra", precioDolar.toString());
                    Toast.makeText(PagoServicio.this, precioDolar.toString(), Toast.LENGTH_LONG).show();
                    txtCambio.setText(precioDolar.toString());

                    monto2 = Double.parseDouble(edtMonto.getText().toString());

                    Double cambiodolar = (monto2 / Double.parseDouble(txtCambio.getText().toString()));

                    DecimalFormat twoDForm = new DecimalFormat("#.##");
                    Double conversion = Double.valueOf(twoDForm.format(cambiodolar));
                    edtMontoCambio.setText(conversion.toString());
                            /*SharedPreferences prefe = getSharedPreferences("fecha", getApplicationContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefe.edit();
                            editor.putString("valordolar", precioDolar.toString());
                            editor.commit();

                            if(itemfecha == null){
                                txtCambio.setText(prefe.getString("valordolar", ""));
                            }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PagoServicio.this, "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(objRequest);
    }

    public void Pagar() {
        monto = edtMontoCambio.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(monto)), "USD", "Pagar servicio", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PagoServicio.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, DetallePago.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", monto)
                        );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
