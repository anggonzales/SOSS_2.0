package com.example.soss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    EditText edtEmail;
    EditText edtPassword;
    EditText edtNombre;
    Button btnRegistrar;
    private DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Cargar();
        mAuth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                Registrar(nombre, email, password);
            }
        });
    }

    private void Cargar() {
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnRegistrar = (Button) findViewById(R.id.btnRegistro);
    }

    private void Registrar(final String nombre, final String email, String password) {

        if (TextUtils.isEmpty(nombre)) {
            edtNombre.setError("campo requerido");
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.setError("campo requerido");
        } else if (TextUtils.isEmpty(password)) {
            edtPassword.setError("campo requerido");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String IdUser = mAuth.getCurrentUser().getUid();
                        reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(IdUser);
                        reference.child("IdUsuario").setValue(IdUser);
                        reference.child("NombreUsuario").setValue(nombre);
                        reference.child("CorreoUsuario").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                user = mAuth.getCurrentUser();
                                if (user != null) {
                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Registro.this, "Registrado", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Registro.this, Login.class));
                                            finish();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
