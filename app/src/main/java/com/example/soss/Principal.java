package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.soss.Adapters.EmpresaAdapter;
import com.example.soss.Model.ClsEmpresa;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EmpresaAdapter AdaptadorEmpresa;
    private ArrayList<ClsEmpresa> ListaEmpresa;
    private static final String PATH_SERVICIO = "Empresa";
    DatabaseReference reference;
    FirebaseDatabase database;
    Query consulta;
    String IdCategoria = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.drawer_open, R.string. drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(
                R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Listado de servicios sugeridos */
        recyclerView = findViewById(R.id.rcvListaServicios);

        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIO);

        recyclerView = findViewById(R.id.rcvListaServicios);
        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIO);
        ListaEmpresa = new ArrayList<>();
        AdaptadorEmpresa = new EmpresaAdapter(this, ListaEmpresa);
        recyclerView.setAdapter(AdaptadorEmpresa);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_micuenta:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.activity_consulta_servicios:
                startActivity(new Intent(this, PrincipalServicio.class));
                break;
            case R.id.activity_pago:
                startActivity(new Intent(this, PrincipalPago.class));
                break;
            case R.id.activity_categoria:
                startActivity(new Intent(this, CategoriaUsuario.class));
                break;
            case R.id.nav_salir:
                finish();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
}
