package com.tripfinde.tripfinde;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GestionCuenta extends AppCompatActivity {
    TextInputEditText txtNombreGC, txtApellidoGC, txtCorreoGC, txtClaveGC, txtConfirmarClaveGC;
    TextView btnBackGC;
    Button btnModificarCuenta, btnEliminarCuenta;
    String url = GlobalInfo.URL;
    String codigo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cuenta);
        txtNombreGC = findViewById(R.id.txtNombreGC);
        txtApellidoGC = findViewById(R.id.txtApellidoGC);
        txtCorreoGC = findViewById(R.id.txtCorreoGC);
        txtClaveGC = findViewById(R.id.txtClaveGC);
        txtConfirmarClaveGC = findViewById(R.id.txtConfirmarClaveGC);
        btnBackGC = findViewById(R.id.btnBackGC);
        btnModificarCuenta = findViewById(R.id.btnModificarCuenta);
        btnEliminarCuenta = findViewById(R.id.btnEliminarCuenta);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            txtCorreoGC.setText(bundle.getString("correo"));
            txtClaveGC.setText(bundle.getString("clave"));
            txtConfirmarClaveGC.setText(bundle.getString("clave"));

            buscarUsuario(bundle.getString("correo"));
        }
        btnModificarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombreGC.getText().toString().trim();
                String apellido = txtApellidoGC.getText().toString().trim();
                String correo = txtCorreoGC.getText().toString().trim();
                String clave = txtClaveGC.getText().toString().trim();
                String confirmacionclave = txtConfirmarClaveGC.getText().toString().trim();

                if (nombre.isEmpty()) {
                    txtNombreGC.setError("Ingrese un Nombre");
                } else if (apellido.isEmpty()) {
                    txtApellidoGC.setError("Ingrese un Apellido");
                } else if (correo.isEmpty()) {
                    txtCorreoGC.setError("Ingrese un Correo");
                } else if (!validarEmail(correo)) {
                    txtCorreoGC.setError("El Correo no es Válido");
                } else if (clave.isEmpty()) {
                    txtClaveGC.setError("Ingrese una Clave");
                } else if (!clave.equals(confirmacionclave)) {
                    txtConfirmarClaveGC.setError("Las Claves no Coinciden");
                } else {
                    verificarEmail();
                }
            }
        });

        btnEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GestionCuenta.this);
                builder.setMessage("¿Está seguro que desea eliminar su cuenta?")
                        .setTitle("Eliminar Cuenta")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                eliminarCuenta();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        btnBackGC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void buscarUsuario(String correo) {
        ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Cargando...");
        progressDialog.setMessage("Espere un momento");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/ProyectoAPI/webservice/listarUsuarioEmail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.isEmpty()) {
                            SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                            prefs.edit().clear().commit();

                            Intent intent = new Intent(GestionCuenta.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(GestionCuenta.this, "Datos modificados desde otro dispositivo,\nvuelva a iniciar Sesión", Toast.LENGTH_LONG).show();
                        } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                codigo = jObject.getString("id");
                                txtNombreGC.setText(jObject.getString("pNombre"));
                                txtApellidoGC.setText(jObject.getString("pApellido"));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                    }
                                }, 1500);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("Error en cath", e.toString());
                        }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error en response", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("txtCorreo", correo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GestionCuenta.this);
        requestQueue.add(stringRequest);
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void verificarEmail() {
        StringRequest Srequest = new StringRequest(Request.Method.POST, url + "/ProyectoAPI/webservice/listarUsuarioEmailId.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.isEmpty()) {
                            txtCorreoGC.setError("El Correo ya se encuentra registrado");
                        } else {
                            modificarCuenta();
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
                param.put("txtCorreo", txtCorreoGC.getText().toString());
                param.put("codigo", codigo);
                return param;
            }
        };
        RequestQueue request = Volley.newRequestQueue(this);
        request.add(Srequest);
    }

    public void modificarCuenta() {
        StringRequest request = new StringRequest(Request.Method.POST, url + "/ProyectoAPI/webservice/modificarUsuario.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Datos Modificados", Toast.LENGTH_SHORT);
                        toast.show();
                        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("correo", txtCorreoGC.getText().toString());
                        editor.putString("clave", txtClaveGC.getText().toString());
                        editor.putBoolean("sesion", true);
                        editor.commit();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT);
                toast.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("txtNombre", txtNombreGC.getText().toString());
                param.put("txtApellido", txtApellidoGC.getText().toString());
                param.put("txtCorreo", txtCorreoGC.getText().toString());
                param.put("txtClave", txtClaveGC.getText().toString());
                param.put("codigo", codigo);
                return param;
            }
        };
        RequestQueue requestQ = Volley.newRequestQueue(this);
        requestQ.add(request);
    }

    public void eliminarCuenta() {
        StringRequest request = new StringRequest(Request.Method.POST, url + "/ProyectoAPI/webservice/eliminarUsuario.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Cuenta Eliminada", Toast.LENGTH_SHORT);
                        toast.show();
                        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                        preferences.edit().clear().commit();
                        Intent intent = new Intent(GestionCuenta.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT);
                toast.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("codigo", codigo);
                return param;
            }
        };
        RequestQueue requestQ = Volley.newRequestQueue(this);
        requestQ.add(request);
    }
}