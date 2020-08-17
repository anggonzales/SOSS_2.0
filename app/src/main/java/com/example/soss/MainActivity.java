package com.example.soss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    public void Login(View view) {
        startActivity(new Intent(this, Login.class));
    }

    public void Registro(View view) { startActivity(new Intent(this, Registro.class)); }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!= null){
            startActivity(new Intent(this, Principal.class));
            finish();
        }
    }
}
