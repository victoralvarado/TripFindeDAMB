package com.tripfinde.tripfinde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button btnIniciarSesion, btnCrearcuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciarSesion = findViewById(R.id.btnModificarCuenta);
        btnCrearcuenta = findViewById(R.id.btnCrearCuenta);
        Bundle bundle = getIntent().getExtras();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,IniciarSesion.class);
                startActivity(intent);
            }
        });

        btnCrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CrearCuenta.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        boolean session = prefs.getBoolean("sesion", false);
        if (session) {
            moveTaskToBack(true);

        }else{
            moveTaskToBack(false);
        }

    }
}