package com.tripfinde.tripfinde;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
 * Use the {@link DepartamentosFragment} factory method to
 * create an instance of this fragment.
 */
public class DepartamentosFragment extends Fragment {
    View view;
    private RequestQueue requestQueue, requestQueue2;
    private List<ModeloDepartamento> lsDepartamentos;
    private RecyclerView recyclerView, recyclerView2;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    TextView txtDepartamentoF;
    private List<ModeloLugar> lsLugares;
    String url = GlobalInfo.URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_departamentos, container, false);
        lsDepartamentos = new ArrayList<>();
        lsDepartamentos.clear();
        recyclerView = view.findViewById(R.id.rcvDepartamentosCards);
        txtDepartamentoF = view.findViewById(R.id.txtDepartamentoF);
        lsLugares = new ArrayList<>();
        recyclerView2 = view.findViewById(R.id.rcvLugaresDepartamento);
        if(txtDepartamentoF.getText().toString().equals("TextView")){
            lsLugares.clear();
            txtDepartamentoF.setText("Ahuachapán");
            cargarSitios("Ahuachapán");
        }

        cargarDepartamentos();

        return view;
    }

    private void cargarSitios(String buscar) {
        String URL = url +"/ProyectoAPI/webservice/getLugarD.php?departamento=" + buscar;
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
                    setDataRecyclerAdapter2(lsLugares);
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
        requestQueue2 = Volley.newRequestQueue(getActivity());
        requestQueue2.add(stringRequest);
    }

    private void setDataRecyclerAdapter2(List<ModeloLugar> lsLugares) {
        AdapterLugares myadapter = new AdapterLugares(getActivity(), lsLugares);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setAdapter(myadapter);
    }

    private void cargarDepartamentos() {
        String URL = url+"/ProyectoAPI/webservice/getDepartamento.php";
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
                        ModeloDepartamento mDepartamento = new ModeloDepartamento();
                        mDepartamento.setNombre(jObject.getString("nombre"));
                        mDepartamento.setPortada(jObject.getString("portada"));
                        lsDepartamentos.add(mDepartamento);
                    }
                    setDataRecyclerAdapter(lsDepartamentos);
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

    private void setDataRecyclerAdapter(List<ModeloDepartamento> lsDepartamentos) {
        AdapterDepartamentos myadapter = new AdapterDepartamentos(getActivity(), lsDepartamentos, new AdapterDepartamentos.ItemClickListener() {
            @Override
            public void onItemClick(ModeloDepartamento item) {
                lsLugares.clear();
                txtDepartamentoF.setText(item.getNombre());
                cargarSitios(item.getNombre());
                Toast toast = Toast.makeText(getActivity(), item.getNombre(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(myadapter);
    }
}