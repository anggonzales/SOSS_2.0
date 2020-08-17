package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.soss.Adapters.EmpresaAdapter;
import com.example.soss.Model.ClsEmpresa;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PrincipalServicio extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EmpresaAdapter AdaptadorEmpresa;
    private ArrayList<ClsEmpresa> ListaEmpresa;
    private static final String PATH_SERVICIO = "Empresa";
    DatabaseReference reference;
    private EditText etbuscarnombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_servicio);

        recyclerView = findViewById(R.id.rcvListaServicios);
        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIO);
        ListaEmpresa = new ArrayList<>();
        AdaptadorEmpresa = new EmpresaAdapter(this, ListaEmpresa);
        recyclerView.setAdapter(AdaptadorEmpresa);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        etbuscarnombre = (EditText)findViewById(R.id.edtBusqueda);

        etbuscarnombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // searchPeopleProfile(etbuscarnombre.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

        ListarEmpresas();
    }

    private void filtrar(String texto) {
        ArrayList<ClsEmpresa> filtradatos = new ArrayList<>();

        for(ClsEmpresa item : ListaEmpresa){
            if (item.getNombre().contains(texto)){
                filtradatos.add(item);
            }
            AdaptadorEmpresa.filtrar(filtradatos);
        }
    }

    void ListarEmpresas() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                if (!ListaEmpresa.contains(empresa)) {
                    ListaEmpresa.add(empresa);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                int index = -1;
                for (ClsEmpresa objempresa : ListaEmpresa) {
                    Log.i("iteracion", objempresa.getIdEmpresa() + " = " + empresa.getIdEmpresa());
                    index++;
                    if (objempresa.getIdEmpresa().equals(empresa.getIdEmpresa())) {
                        ListaEmpresa.set(index, empresa);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                int index = -1;
                for (ClsEmpresa objempresa : ListaEmpresa) {
                    Log.i("iteracion", objempresa.getIdEmpresa() + " = " + empresa.getIdEmpresa());
                    index++;
                    if (objempresa.getIdEmpresa().equals(empresa.getIdEmpresa())) {
                        ListaEmpresa.remove(index);
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
