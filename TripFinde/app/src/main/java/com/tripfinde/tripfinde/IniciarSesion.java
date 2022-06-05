package com.tripfinde.tripfinde;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class IniciarSesion extends AppCompatActivity {
    TextView btnBackIS, btnCrearCuentaIS;
    TextInputEditText txtCorreoIS, txtClaveIS;
    Button btnIniciarSesion;
    String url = GlobalInfo.URL;
    String correo, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        btnBackIS = findViewById(R.id.btnBackCC);
        btnCrearCuentaIS = findViewById(R.id.btnCrearCuentaIS);
        txtCorreoIS = findViewById(R.id.txtCorreoIS);
        txtClaveIS = findViewById(R.id.txtClaveIS);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnBackIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCrearCuentaIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesion.this, CrearCuenta.class);
                startActivity(intent);
            }
        });
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtCorreoIS.getText().toString().trim().isEmpty()) {
                    txtCorreoIS.setError("Ingrese su correo");
                } else if (txtClaveIS.getText().toString().trim().isEmpty()) {
                    txtClaveIS.setError("Ingrese su contraseña");
                } else {
                    correo = txtCorreoIS.getText().toString();
                    clave = txtClaveIS.getText().toString();
                    validarUsuario(url + "/ProyectoAPI/webservice/iniciarSesion.php");
                }
            }
        });
    }

    private void validarUsuario(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    guardarPreferencias();
                    Intent intent = new Intent(IniciarSesion.this, App.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT);
                toast.show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("txtCorreo", correo);
                params.put("txtClave", clave);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void guardarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo", correo);
        editor.putString("clave", clave);
        editor.putBoolean("sesion", true);
        editor.commit();
    }
}