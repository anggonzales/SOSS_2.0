package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.soss.Adapters.ServicioAdapter;
import com.example.soss.Model.ClsServicio;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class DetalleServicio extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ServicioAdapter AdaptadorServicio;
    private ArrayList<ClsServicio> ListaServicios;
    private static final String PATH_SERVICIOS = "Servicio";
    FirebaseDatabase database;
    DatabaseReference reference;
    Query consulta;
    String IdEmpresa = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_servicio);

        recyclerView = findViewById(R.id.rcvListaServicioEmpresa);

        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIOS);
        ListaServicios = new ArrayList<>();
        AdaptadorServicio = new ServicioAdapter(this, ListaServicios);
        recyclerView.setAdapter(AdaptadorServicio);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Bundle extras = getIntent().getExtras();
        IdEmpresa =  extras.getString("IdEmpresa");


        consulta = FirebaseDatabase.getInstance().getReference("Servicio")
                .orderByChild("IdEmpresa")
                .equalTo(IdEmpresa);

        ListarServicios();
    }

    void ListarServicios(){
        consulta.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsServicio servicio = dataSnapshot.getValue(ClsServicio.class);
                servicio.setIdServicio(dataSnapshot.getKey());
                if (!ListaServicios.contains(servicio)) {
                    ListaServicios.add(servicio);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsServicio servicio = dataSnapshot.getValue(ClsServicio.class);
                servicio.setIdServicio(dataSnapshot.getKey());
                int index = -1;
                for (ClsServicio objservicio : ListaServicios) {
                    Log.i("iteracion", objservicio.getIdServicio() + " = " + servicio.getIdServicio());
                    index++;
                    if (objservicio.getIdServicio().equals(servicio.getIdServicio())) {
                        ListaServicios.set(index, servicio);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsServicio servicio = dataSnapshot.getValue(ClsServicio.class);
                servicio.setIdServicio(dataSnapshot.getKey());
                int index = -1;
                for (ClsServicio objservicio : ListaServicios) {
                    Log.i("iteracion", objservicio.getIdServicio() + " = " + servicio.getIdServicio());
                    index++;
                    if (objservicio.getIdServicio().equals(servicio.getIdServicio())) {
                        ListaServicios.remove(index);
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
