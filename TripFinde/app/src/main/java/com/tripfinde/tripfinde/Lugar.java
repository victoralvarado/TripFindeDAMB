package com.tripfinde.tripfinde;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lugar extends AppCompatActivity {
    TextView btnRegresarLugar, txtTituloLugar, txtDescripcionLugar;
    ImageView imgPortadaLugar;
    private RequestQueue requestQueue;
    private List<ModeloImagen> lsImagenes;
    private RecyclerView recyclerView;
    String url = GlobalInfo.URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);
        btnRegresarLugar = findViewById(R.id.btnRegresarTipo);
        txtTituloLugar = findViewById(R.id.txtTituloLugar);
        txtDescripcionLugar = findViewById(R.id.txtDescripcionLugar);
        imgPortadaLugar = findViewById(R.id.imgPortadaLugar);
        lsImagenes = new ArrayList<>();
        lsImagenes.clear();
        recyclerView = findViewById(R.id.rcvImagenesLugar);
        imgPortadaLugar.setClipToOutline(true);
        Bundle bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        txtTituloLugar.setText(nombre);
        String idLugar = bundle.getString("idLugar");
        String descripcion = bundle.getString("descripcion");
        txtDescripcionLugar.setText(descripcion);
        String Portada = bundle.getString("portada");
        Picasso.get().load(url +"/ProyectoAPI/imagenes/portadaLugar/" + Portada).into(imgPortadaLugar);



        btnRegresarLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cargarImagenes(Integer.parseInt(idLugar));
    }

    private void cargarImagenes(int lugar) {
        String URL = url +"/ProyectoAPI/webservice/getImagenL.php?lugar="+lugar;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response", response);
                try {
                    Log.i("Error Response", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);
                        ModeloImagen mImagen = new ModeloImagen();
                        mImagen.setImagen(jObject.getString("imagen"));
                        lsImagenes.add(mImagen);
                    }
                    setDataRecyclerAdapter(lsImagenes);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Error Catch", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.toString());
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setDataRecyclerAdapter(List<ModeloImagen> lsImagenes) {
        AdapterImagen myadapter = new AdapterImagen(this, lsImagenes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
    }
}