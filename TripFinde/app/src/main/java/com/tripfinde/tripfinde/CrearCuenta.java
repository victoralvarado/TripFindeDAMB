package com.tripfinde.tripfinde;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.Gravity;
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
import java.util.regex.Pattern;

public class CrearCuenta extends AppCompatActivity {
    TextInputEditText txtNombre, txtApellido, txtCorreoCC, txtClaveCC, txtConfirmarClave;
    Button btnCrearCuentaCC;
    TextView btnBackCC;
    String url = GlobalInfo.URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreoCC = findViewById(R.id.txtCorreoCC);
        txtClaveCC = findViewById(R.id.txtClaveCC);
        txtConfirmarClave = findViewById(R.id.txtConfirmarClave);
        btnCrearCuentaCC = findViewById(R.id.btnModificarCuenta);
        btnCrearCuentaCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString().trim();
                String apellido = txtApellido.getText().toString().trim();
                String correo = txtCorreoCC.getText().toString().trim();
                String clave = txtClaveCC.getText().toString().trim();
                String confirmacionclave = txtConfirmarClave.getText().toString().trim();

                if (nombre.isEmpty()) {
                    txtNombre.setError("Ingrese un Nombre");
                } else if (apellido.isEmpty()) {
                    txtApellido.setError("Ingrese un Apellido");
                } else if (correo.isEmpty()) {
                    txtCorreoCC.setError("Ingrese un Correo");
                } else if (!validarEmail(correo)) {
                    txtCorreoCC.setError("El Correo no es Válido");
                } else if (clave.isEmpty()) {
                    txtClaveCC.setError("Ingrese una Clave");
                } else if (!clave.equals(confirmacionclave)) {
                    txtConfirmarClave.setError("Las Claves no Coinciden");
                } else {
                    verificarEmail();
                }
            }
        });
        btnBackCC = findViewById(R.id.btnBackCC);
        btnBackCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void verificarEmail() {
        StringRequest Srequest = new StringRequest(Request.Method.POST, url + "/ProyectoAPI/webservice/listarUsuarioEmail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.isEmpty()) {
                            txtCorreoCC.setError("El Correo ya se encuentra registrado");
                        } else {
                            crearCuenta();
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
                Map<String, String> param = new HashMap<String, String>();
                param.put("txtCorreo", txtCorreoCC.getText().toString());
                return param;
            }
        };
        RequestQueue requestQ = Volley.newRequestQueue(this);
        requestQ.add(Srequest);
    }

    private void crearCuenta() {
        ProgressDialog progressDialog = new ProgressDialog(CrearCuenta.this);
        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url + "/ProyectoAPI/webservice/postUsuario.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Registro insertado correctamente")) {
                            Toast toast = Toast.makeText(
                                    getApplicationContext(),
                                    "Cuenta Creada Correctamente",
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            progressDialog.dismiss();
                            txtNombre.setText("");
                            txtApellido.setText("");
                            txtCorreoCC.setText("");
                            txtClaveCC.setText("");
                            txtConfirmarClave.setText("");
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(CrearCuenta.this, IniciarSesion.class);
                                    startActivity(intent);
                                }
                            }, 1000);
                        } else {
                            Toast toast = Toast.makeText(
                                    CrearCuenta.this,
                                    "No Se Creó la Cuenta\nIntente mas Tarde",
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(
                        CrearCuenta.this,
                        "No Se Creó la Cuenta\nIntente mas Tarde",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Cargar datos referenciados desde la base de datos
                Map<String, String> params = new HashMap<String, String>();
                params.put("txtNombre", txtNombre.getText().toString());
                params.put("txtApellido", txtApellido.getText().toString());
                params.put("txtCorreo", txtCorreoCC.getText().toString());
                params.put("txtClave", txtClaveCC.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CrearCuenta.this);
        requestQueue.add(request);
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}