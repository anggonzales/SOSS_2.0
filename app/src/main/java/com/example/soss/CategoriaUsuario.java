package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.soss.Adapters.CategoriaAdapter;
import com.example.soss.Model.ClsCategoria;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategoriaUsuario extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoriaAdapter AdaptadorCategoria;
    private ArrayList<ClsCategoria> ListaCategorias;
    public ArrayList<ClsCategoria> lista = new ArrayList<>();
    private static final String PATH_CATEGORIA = "Categoria";
    DatabaseReference reference;
    private Button btnGuardar;
    private String nombre;
    StringBuilder sb = null;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String IdUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_usuario);
        recyclerView = findViewById(R.id.rcvListaCategorias);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference(PATH_CATEGORIA);
        ListaCategorias = new ArrayList<>();
        AdaptadorCategoria = new CategoriaAdapter(this, ListaCategorias);
        recyclerView.setAdapter(AdaptadorCategoria);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        IdUser = mAuth.getCurrentUser().getUid();


        ListarCategorias();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = new StringBuilder();

                int i = 0;
                do {
                    CategoriaAdapter Myadapter;
                    ClsCategoria objcategoria = CategoriaAdapter.ListaCategoriaChecked.get(i);
                    nombre = objcategoria.getNombre();
                    lista.add(objcategoria);
                    ClsCategoria categoriaguardar = new ClsCategoria(nombre);
                    reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(IdUser);
                    reference.child("Categoria").setValue(lista);
                    sb.append(nombre);

                    if(i != CategoriaAdapter.ListaCategoriaChecked.size() -1){
                        sb.append("\n");
                    }
                    i++;

                } while (i < CategoriaAdapter.ListaCategoriaChecked.size());


                if(CategoriaAdapter.ListaCategoriaChecked.size() > 0){
                    Toast.makeText(CategoriaUsuario.this, sb.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CategoriaUsuario.this, "Por favor seleccione uno o mas", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void ListarCategorias() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsCategoria categoria = dataSnapshot.getValue(ClsCategoria.class);
                categoria.setId(dataSnapshot.getKey());
                if (!ListaCategorias.contains(categoria)) {
                    ListaCategorias.add(categoria);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsCategoria categoria = dataSnapshot.getValue(ClsCategoria.class);
                categoria.setId(dataSnapshot.getKey());
                int index = -1;
                for (ClsCategoria objcategoria : ListaCategorias) {
                    Log.i("iteracion", objcategoria.getId() + " = " + categoria.getId());
                    index++;
                    if (objcategoria.getId().equals(categoria.getId())) {
                        ListaCategorias.set(index, categoria);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsCategoria categoria = dataSnapshot.getValue(ClsCategoria.class);
                categoria.setId(dataSnapshot.getKey());
                int index = -1;
                for (ClsCategoria objcategoria : ListaCategorias) {
                    Log.i("iteracion", objcategoria.getId() + " = " + categoria.getId());
                    index++;
                    if (objcategoria.getId().equals(categoria.getId())) {
                        ListaCategorias.set(index, categoria);
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

    public void ViewPrincipal(View view) {
        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
        finish();
    }
}
