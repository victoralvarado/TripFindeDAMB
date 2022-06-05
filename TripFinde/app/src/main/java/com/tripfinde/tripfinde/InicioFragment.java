package com.tripfinde.tripfinde;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {
    View view;
    private RequestQueue requestQueue;
    private List<ModeloLugar> lsLugares;
    private RecyclerView recyclerView;
    String url = GlobalInfo.URL;
    Button btnMontania, btnPlaya, btnLago, btnHistorico, btnOtros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inicio, container, false);
        btnMontania =  view.findViewById(R.id.btnMontania);
        btnPlaya = view.findViewById(R.id.btnPlaya);
        btnLago = view.findViewById(R.id.btnLago);
        btnHistorico = view.findViewById(R.id.btnHistorico);
        btnOtros = view.findViewById(R.id.btnOtros);

        lsLugares = new ArrayList<>();
        lsLugares.clear();
        recyclerView = view.findViewById(R.id.rcvLugaresF);
        cargarSitios();

        btnMontania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TipoLugar.class);
                intent.putExtra("tipoLugar", "Montaña");
                startActivity(intent);
            }
        });

        btnPlaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TipoLugar.class);
                intent.putExtra("tipoLugar", "Playa");
                startActivity(intent);
            }
        });

        btnLago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TipoLugar.class);
                intent.putExtra("tipoLugar", "Lago");
                startActivity(intent);
            }
        });

        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TipoLugar.class);
                intent.putExtra("tipoLugar", "Historico");
                startActivity(intent);
            }
        });

        btnOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TipoLugar.class);
                intent.putExtra("tipoLugar", "Otros");
                startActivity(intent);
            }
        });
        return view;
    }

    private void cargarSitios() {
        String URL = url +"/ProyectoAPI/webservice/getLugar.php";
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
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void setDataRecyclerAdapter(List<ModeloLugar> lsLugares) {
        AdapterLugares myadapter = new AdapterLugares(getActivity(), lsLugares);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myadapter);
    }
}