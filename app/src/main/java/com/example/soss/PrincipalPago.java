package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.soss.Adapters.PagoAdapter;
import com.example.soss.Model.ClsPagoServicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class PrincipalPago extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PagoAdapter AdaptadorPago;
    private ArrayList<ClsPagoServicio> ListaPagoServicio = new ArrayList<>();
    private static final String PATH_PAGO = "Transferencia";
    DatabaseReference reference;
    FirebaseAuth mAuth;
    Query consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_pago);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rcvListaPagos);

        ListaPagoServicio = new ArrayList<>();
        AdaptadorPago = new PagoAdapter(this, ListaPagoServicio);
        recyclerView.setAdapter(AdaptadorPago);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String UID = mAuth.getCurrentUser().getUid();

        consulta = FirebaseDatabase.getInstance().getReference("Transferencia")
                .orderByChild("idUsuario")
                .equalTo(UID);


        ListarPagos();
    }

    void ListarPagos() {
        consulta.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsPagoServicio pago = dataSnapshot.getValue(ClsPagoServicio.class);
                pago.setId(dataSnapshot.getKey());
                if (!ListaPagoServicio.contains(pago)) {
                    ListaPagoServicio.add(pago);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsPagoServicio pago = dataSnapshot.getValue(ClsPagoServicio.class);
                pago.setId(dataSnapshot.getKey());
                int index = -1;
                for (ClsPagoServicio objpago : ListaPagoServicio) {
                    Log.i("iteracion", objpago.getId() + " = " + pago.getId());
                    index++;
                    if (objpago.getId().equals(pago.getId())) {
                        ListaPagoServicio.set(index, pago);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsPagoServicio pago = dataSnapshot.getValue(ClsPagoServicio.class);
                pago.setId(dataSnapshot.getKey());
                int index = -1;
                for (ClsPagoServicio objpago : ListaPagoServicio) {
                    Log.i("iteracion", objpago.getId() + " = " + pago.getId());
                    index++;
                    if (objpago.getId().equals(pago.getId())) {
                        ListaPagoServicio.remove(index);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}