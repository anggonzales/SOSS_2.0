package com.example.soss.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soss.Adapters.EmpresaAdapter;
import com.example.soss.Adapters.ServicioAdapter;
import com.example.soss.Model.ClsEmpresa;
import com.example.soss.Model.ClsServicio;
import com.example.soss.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiciosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ServicioAdapter AdaptadorServicio;
    private ArrayList<ClsServicio> ListaServicios;
    private static final String PATH_SERVICIOS = "Servicio";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase database;
    DatabaseReference reference;
    Query consulta;
    String IdEmpresa = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServiciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FlightsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiciosFragment newInstance(String param1, String param2) {
        ServiciosFragment fragment = new ServiciosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicios, container, false);
        recyclerView = view.findViewById(R.id.rcvListaServicioEmpresa);

        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIOS);
        ListaServicios = new ArrayList<>();
        AdaptadorServicio = new ServicioAdapter(getActivity(), ListaServicios);
        recyclerView.setAdapter(AdaptadorServicio);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Bundle extras = getActivity().getIntent().getExtras();
        IdEmpresa =  extras.getString("IdEmpresa");


        consulta = FirebaseDatabase.getInstance().getReference("Servicio")
                .orderByChild("IdEmpresa")
                .equalTo(IdEmpresa);

        ListarServicios();
        return view;
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