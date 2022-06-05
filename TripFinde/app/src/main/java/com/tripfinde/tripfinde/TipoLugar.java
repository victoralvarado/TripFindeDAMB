package com.tripfinde.tripfinde;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TipoLugar extends AppCompatActivity {
    TextView btnRegresarTipo, txtTipoLugar;
    private RequestQueue requestQueue;
    private List<ModeloLugar> lsLugares;
    RecyclerView rcvTipoLugar;
    String url = GlobalInfo.URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_lugar);
        btnRegresarTipo = findViewById(R.id.btnRegresarTipo);
        txtTipoLugar = findViewById(R.id.txtTipoLugar);
        Bundle bundle = getIntent().getExtras();
        String tipoLugar = bundle.getString("tipoLugar");
        txtTipoLugar.setText(tipoLugar);

        lsLugares = new ArrayList<>();
        lsLugares.clear();
        cargarSitios(tipoLugar);
        rcvTipoLugar = findViewById(R.id.rcvTipoLugar);

        btnRegresarTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void cargarSitios(String tipoLugar) {
        String URL = url +"/ProyectoAPI/webservice/getLugarTL.php?tipo="+tipoLugar;
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
                        ModeloLugar mLugar = new ModeloLugar();
                        mLugar.setIdLugar(jObject.getString("id"));
                        mLugar.setDescripcion(jObject.getString("descripcion"));
                        mLugar.setPortada(jObject.getString("portada"));
                        mLugar.setNombre(jObject.getString("nombre"));
                        lsLugares.add(mLugar);
                    }
                    setDataRecyclerAdapter(lsLugares);
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

    private void setDataRecyclerAdapter(List<ModeloLugar> lsLugares) {
        AdapterLugares myadapter = new AdapterLugares(this, lsLugares);
        rcvTipoLugar.setLayoutManager(new LinearLayoutManager(this));
        rcvTipoLugar.setAdapter(myadapter);
    }
}