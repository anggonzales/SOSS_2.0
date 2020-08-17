package com.example.soss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.soss.Adapters.SlideAdapter;

public class Slider extends AppCompatActivity {

    private ViewPager VistaPagina;
    private SlideAdapter AdaptadorSlider;
    private LinearLayout dostsLayout;
    private TextView[] NumeroPuntos;
    private Button btnAtras;
    private Button btnSiguiente;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        VistaPagina = (ViewPager) findViewById(R.id.vprSlider);
        dostsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        btnAtras = (Button) findViewById(R.id.btnBack);
        btnSiguiente = (Button) findViewById(R.id.btnNext);

        AdaptadorSlider = new SlideAdapter(this);
        VistaPagina.setAdapter(AdaptadorSlider);
        addDotsIndicator(0);
        VistaPagina.addOnPageChangeListener(viewListener);

        btnAtras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                VistaPagina.setCurrentItem(mCurrentPage - 1);
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int next = getItem(+1);
                if(next < NumeroPuntos.length){
                    VistaPagina.setCurrentItem(next);
                }else {
                    Intent intent = new Intent(Slider.this, CategoriaUsuario.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void addDotsIndicator(int position){
        NumeroPuntos = new TextView[4];
        dostsLayout.removeAllViews();

        for(int i = 0; i< NumeroPuntos.length; i++){
            NumeroPuntos[i] = new TextView(this);
            NumeroPuntos[i].setText(Html.fromHtml("&#8226;"));
            NumeroPuntos[i].setTextSize(35);
            NumeroPuntos[i].setTextColor(getResources().getColor(R.color.colorTransparenteWhite));
            dostsLayout.addView(NumeroPuntos[i]);
        }

        if(NumeroPuntos.length > 0){
            NumeroPuntos[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private int getItem(int i){
        return VistaPagina.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if(i == NumeroPuntos.length - 1){
                btnSiguiente.setEnabled(true);
                btnAtras.setEnabled(true);
                btnAtras.setVisibility(View.VISIBLE);
                btnSiguiente.setText("Finalizar");
                btnAtras.setText("Back");

            } else {
                btnSiguiente.setEnabled(true);
                btnAtras.setEnabled(true);
                btnAtras.setVisibility(View.VISIBLE);
                btnSiguiente.setText("Next");
                btnAtras.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
