package com.example.soss.Fragments;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soss.DetalleEmpresa;
import com.example.soss.Model.ClsRating;
import com.example.soss.R;
import com.example.soss.UbicacionServicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmpresaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpresaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String latitudservicio;
    String longitudservicio;
    String nombreempresa;
    double ratingtotal;
    String idEmpresa,NombreEmpresa,DescripcionEmpresa;
    TextView txtPrueba,txtTituloEmpresa;
    Button btnUbicacion, btnCalificacion;
    RatingBar ratingBarCalificacion ;
   // private DatabaseReference reference;
    FirebaseAuth mAuth;
   // FirebaseUser user;
    android.app.AlertDialog alertDialog;
    List<Rating> Ratings;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmpresaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmpresaFragment newInstance(String param1, String param2) {
        EmpresaFragment fragment = new EmpresaFragment();
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
        View view =  inflater.inflate(R.layout.fragment_empresa, container, false);
        btnUbicacion = (Button)view.findViewById(R.id.btnUbicacion);
        btnCalificacion = (Button)view.findViewById(R.id.btnCalificacion);
       // txtPrueba = (TextView)view.findViewById(R.id.txtPrueba);
        txtTituloEmpresa = (TextView)view.findViewById(R.id.txtTituloEmpresa) ;
        mAuth = FirebaseAuth.getInstance();
        String IdUser = mAuth.getCurrentUser().getUid();
       // txtPrueba.setText(IdUser);
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(getActivity()).build();

        ratingBarCalificacion = (RatingBar)view.findViewById(R.id.rbrEmpresa);

        Bundle extras = getActivity().getIntent().getExtras();
        latitudservicio =  extras.getString("Latitud");
        longitudservicio = extras.getString("Longitud");
        nombreempresa = extras.getString("Nombre");
        idEmpresa = extras.getString("IdEmpresa");
        ratingtotal = extras.getDouble("Calificacion");
        ratingBarCalificacion.setRating((float) ratingtotal);
        txtTituloEmpresa.setText(nombreempresa);

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UbicacionServicio.class);
                //intent.putExtra("id", datos.get(i).getId());
                intent.putExtra("Latitud", latitudservicio);
                intent.putExtra("Longitud", longitudservicio );
                startActivity(intent);
            }
        });

        btnCalificacion.setOnClickListener(v -> {
            MostrarDialogRating();
        });

        return view;
    }

    private void MostrarDialogRating(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Rating de Empresa");
        builder.setMessage("Descripcion");

        View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_rating,null);

        RatingBar ratingBar = (RatingBar)itemView.findViewById(R.id.rating_bar);
        EditText edtComentario = (EditText)itemView.findViewById(R.id.edtComentario);

        builder.setView(itemView);
        builder.setNegativeButton("Cancelar",(dialogInterface,i) -> {
            dialogInterface.dismiss();
        });
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            ClsRating ratingModel = new ClsRating();
            ratingModel.setUid(mAuth.getCurrentUser().getUid());
            ratingModel.setComentario(edtComentario.getText().toString());
            ratingModel.setValorRating(ratingBar.getRating());
            Map<String,Object> serverTimeStamp = new HashMap<>();
            serverTimeStamp.put("timeStamp", ServerValue.TIMESTAMP);
            ratingModel.setComanetarioTimeStamp(serverTimeStamp);
            GuardarRating(mAuth.getCurrentUser().getUid(),edtComentario.getText().toString(),ratingBar.getRating());

        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void GuardarRating(String Uid, String Comentario, float Rating) {
        FirebaseDatabase.getInstance()
                .getReference("Empresa")
                .child(idEmpresa)
                .child("Rating")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int ayu =1;
                        ClsRating ModeloRating = new ClsRating();

                        //VERIFICAR SI EXISTE ALGUN RATING DESDE 1

                        //VERIFICAR QUE EXISTA ALGUN RATING HECHO POR EL USUARIO

                        //SI EL USUARIO YA CALIFICO O COMENTO LA EMPRESA ENTONCES ACTUALIZAR

                        //SINO AGREGAR
                        ModeloRating.setUid(Uid);
                        ModeloRating.setComentario(Comentario);
                        ModeloRating.setValorRating(Rating);

                        DatabaseReference RatingReference = FirebaseDatabase.getInstance().getReference("Empresa").child(idEmpresa).child("Rating").child(String.valueOf(dataSnapshot.getChildrenCount()+1));
                        RatingReference.setValue(ModeloRating);

                        //OBETENER EMPRESA SELECCIONADA

                        FirebaseDatabase.getInstance()
                                .getReference("Empresa")
                                .child(idEmpresa).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //AUMENTAR EL CONTEO DE RATING
                                String actual = dataSnapshot.child("RatingConteo").getValue().toString();

                                long contarRating = Long.parseLong(actual)  + 1;

                                //MANDAR DATOS EMPRESA
                                DatabaseReference EmpresaReference = FirebaseDatabase.getInstance().getReference("Empresa").child(idEmpresa);
                                Map<String,Object> hopperUpdates = new HashMap<>();
                                hopperUpdates.put("RatingConteo", contarRating);


                                //TODOS POR DEFECTO EL CONTEO
                                //CALCULAR EL RATING TOTAL
                                double sumaRating = (Double.valueOf(dataSnapshot.child("RatingTotal").getValue().toString()) + Rating)*contarRating;
                                double resultado = sumaRating/contarRating;
                                if (contarRating == 1){
                                    hopperUpdates.put("RatingTotal", resultado);
                                }else{
                                    hopperUpdates.put("RatingTotal", resultado/2);
                                }


                                EmpresaReference.updateChildren(hopperUpdates);
                                Toast.makeText(getActivity(),  "rating" + String.valueOf(Rating) + "SUMA" +sumaRating + "RatingTotal" + dataSnapshot.child("RatingTotal").getValue().toString() + "RatinConteo " +contarRating, Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}