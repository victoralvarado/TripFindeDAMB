package com.tripfinde.tripfinde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class IniciarSesion extends AppCompatActivity {
TextView btnBackIS, btnCrearCuentaIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        btnBackIS = findViewById(R.id.btnBackCC);
        btnCrearCuentaIS =  findViewById(R.id.btnCrearCuentaIS);

        btnBackIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCrearCuentaIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesion.this,CrearCuenta.class);
                startActivity(intent);
            }
        });
    }
}