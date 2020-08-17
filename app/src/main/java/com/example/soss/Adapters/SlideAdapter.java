package com.example.soss.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.soss.R;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public int[] lstImages = {
            R.drawable.image_pro,
            R.drawable.image_3,
            R.drawable.image_4,
            R.drawable.image_4,
    };

    public String[] lstTitles = {
            "Bienvenido",
            "Consulta",
            "Encuentra",
            "Comunicate"
    };

    public String[] lstDescripciones = {
            "La app donde puedes encontrar servicios en la ciudad de Tacna",
            "Busca los servicios que quieras",
            "Ubica el negocio",
            "Consulta a los negocios de forma directa con nuestro chat"
    };

    public int[] lstBackgroundColor = {
            Color.rgb(255,255,255),
            Color.rgb(255,255,255),
            Color.rgb(255,255,255),
            Color.rgb(255,255,255)
    };


    public SlideAdapter (Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return lstTitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide, container,false);
        LinearLayout layoutslide = view.findViewById(R.id.lnlSlide);
        ImageView imgSlide = (ImageView) view.findViewById(R.id.imgBienvenida);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitulo);
        TextView txtDescripcion = (TextView) view.findViewById(R.id.txtDescripcion);
        layoutslide.setBackgroundColor(lstBackgroundColor[position]);
        imgSlide.setImageResource(lstImages[position]);
        txtTitle.setText(lstTitles[position]);
        txtDescripcion.setText(lstDescripciones[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
