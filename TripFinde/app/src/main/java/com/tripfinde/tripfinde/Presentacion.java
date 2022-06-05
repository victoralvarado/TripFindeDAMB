package com.tripfinde.tripfinde;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Presentacion extends AppCompatActivity {
    ProgressBar progressBar;
    String url = GlobalInfo.URL;
    String correo, clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                boolean session = prefs.getBoolean("sesion", false);
                if (session) {
                    correo = prefs.getString("correo", "");
                    clave = prefs.getString("clave", "");
                    validarUsuario(url + "/ProyectoAPI/webservice/iniciarSesion.php");

                }else{
                    Intent intent = new Intent(Presentacion.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);
    }

    private void validarUsuario(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Intent intent = new Intent(Presentacion.this, App.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Presentacion.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
}