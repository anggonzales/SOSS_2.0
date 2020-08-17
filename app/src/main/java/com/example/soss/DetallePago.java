package com.example.soss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.soss.Model.ClsPagoServicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetallePago extends AppCompatActivity {

    TextView txtId;
    TextView txtMonto;
    TextView txtEstado;
    private DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    public static String IdUser = "";
    private static final String PATH_PAGO = "Transferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pago);

        mAuth = FirebaseAuth.getInstance();
        txtId = (TextView) findViewById(R.id.txtId);
        txtMonto = (TextView) findViewById(R.id.txtMonto);
        txtEstado = (TextView) findViewById(R.id.txtEstado);

        IdUser = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference(PATH_PAGO);

        //Get intent
        Intent intent = getIntent();
        try
        {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            MostrarDetalles(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void MostrarDetalles(JSONObject response, String paymentAmount){
        try{
            Date objFecha = new Date();
            String strDateFormat = "dd-MM-aaaa hh: mm: ss";
            SimpleDateFormat objFormato = new SimpleDateFormat(strDateFormat);
            txtId.setText(response.getString("id"));
            txtEstado.setText(response.getString("state"));
            txtMonto.setText(paymentAmount);
            ClsPagoServicio pagoservicio = new ClsPagoServicio(IdUser, Double.valueOf(txtMonto.getText().toString()), objFecha.toString(), txtEstado.getText().toString());
            reference.push().setValue(pagoservicio);

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void Regresar(View view) {
        startActivity(new Intent(this, Principal.class));
    }
}