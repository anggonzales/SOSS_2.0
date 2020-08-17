package com.example.soss.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soss.DetalleEmpresa;
import com.example.soss.Model.ClsEmpresa;
import com.example.soss.DetalleServicio;
import com.example.soss.R;
import com.example.soss.UbicacionServicio;

import java.util.ArrayList;

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.ViewHolder> {

    private LayoutInflater inflador;
    ArrayList<ClsEmpresa> ListaEmpresas;
    Context micontext;
    ViewHolder viewHolder;

    public EmpresaAdapter(Context context, ArrayList<ClsEmpresa> datos) {
        this.ListaEmpresas = datos;
        micontext = context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_principal_servicio, parent, false);
        return new ViewHolder(v);
    }

    public View getView (final int position, View convertView, ViewGroup parent){

        if(convertView == null) {

            convertView = inflador.inflate(R.layout.item_principal_servicio, parent, false);
            viewHolder.NombreEmpresa = (TextView) convertView.findViewById(R.id.txtNombre);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.NombreEmpresa.setText(ListaEmpresas.get(position).getNombre());
        return convertView;
    }

    public  void filtrar(ArrayList<ClsEmpresa> filtro){
        this.ListaEmpresas = filtro;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {

        holder.NombreEmpresa.setText(ListaEmpresas.get(i).getNombre());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(micontext, DetalleEmpresa.class);
                //intent.putExtra("id", datos.get(i).getId());
                intent.putExtra("IdEmpresa", ListaEmpresas.get(i).getIdEmpresa());
                intent.putExtra("Nombre", ListaEmpresas.get(i).getNombre());
                intent.putExtra("Latitud", ListaEmpresas.get(i).getLatitud());
                intent.putExtra("Longitud", ListaEmpresas.get(i).getLongitud());
                intent.putExtra("Calificacion", ListaEmpresas.get(i).getRatingTotal());

                micontext.startActivity(intent);
            }
        });

        holder.Servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(micontext, DetalleServicio.class);
                intent.putExtra("IdEmpresa", ListaEmpresas.get(i).getIdEmpresa());
                micontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListaEmpresas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView NombreEmpresa;
        public TextView InicialLogo;
        public Button Servicio;

        ViewHolder(View itemView) {
            super(itemView);
            NombreEmpresa = (TextView) itemView.findViewById(R.id.txtNombre);
            InicialLogo = (TextView) itemView.findViewById(R.id.txtLogo);
            Servicio = (Button) itemView.findViewById(R.id.btnServicio);
        }
    }
}
