package com.example.soss.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soss.Model.ClsCategoria;
import com.example.soss.R;

import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private LayoutInflater inflador;
    ArrayList<ClsCategoria> ListaCategoria;
    public static ArrayList<ClsCategoria> ListaCategoriaChecked = new ArrayList<>();
    Context micontext;

    public CategoriaAdapter(Context context, ArrayList<ClsCategoria> datos) {
        this.ListaCategoria = datos;
        micontext = context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_categoria_usuario, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        //holder.Categorias.setText(ListaCategoria.get(i).getNombre());
        //holder.NombreCategoria.setChecked(ListaCategoria.get(i).isSelected());
        holder.Categorias.setText(ListaCategoria.get(i).getNombre());
        holder.NombreCategoria.setChecked(ListaCategoria.get(i).isSelected());


        holder.setItemClickListener(new ViewHolder.ItemClickListener(){
            public void onItemClick(View v, int pos){
                CheckBox NombreCategoria = (CheckBox) v;
                ClsCategoria current = ListaCategoria.get(i);

                if(NombreCategoria.isChecked()){
                    current.setSelected(true);
                    ListaCategoriaChecked.add(current);

                } else if (!NombreCategoria.isChecked()) {
                    current.setSelected(false);
                    ListaCategoriaChecked.remove(current);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListaCategoria.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox NombreCategoria;
        public TextView Categorias;
        ItemClickListener itemClickListener;

        ViewHolder(View itemView) {
            super(itemView);
            NombreCategoria = (CheckBox) itemView.findViewById(R.id.chkCategoria);
            Categorias = (TextView) itemView.findViewById(R.id.txtNombreCategoria);
            NombreCategoria.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        interface ItemClickListener {
            void onItemClick(View v, int pos);
        }
    }
}
