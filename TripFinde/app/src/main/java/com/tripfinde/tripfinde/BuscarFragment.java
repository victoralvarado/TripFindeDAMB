package com.tripfinde.tripfinde;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
 * Use the {@link BuscarFragment} factory method to
 * create an instance of this fragment.
 */
public class BuscarFragment extends Fragment {
    View view;
    private RequestQueue requestQueue;
    private List<ModeloLugar> lsLugares;
    private RecyclerView recyclerView;
    EditText txtBuscar;
    String url = GlobalInfo.URL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buscar, container, false);
        lsLugares = new ArrayList<>();
        lsLugares.clear();
        recyclerView = view.findViewById(R.id.rcvLugaresF);
        cargarSitios("");
        txtBuscar = view.findViewById(R.id.txtBuscar);
        txtBuscar.setSaveEnabled(false);
        txtBuscar.requestFocus();

        //Mostrar teclado
        new Handler().postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                imm.showSoftInput(txtBuscar, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);

        //Filtrar registros al escribir en buscar
        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtBuscar.getText().toString().length() > 0) {
                    lsLugares.clear();
                    cargarSitios(txtBuscar.getText().toString());
                }
            }
        });
        return view;
    }

    //Cargar los sitios con el parametro a buscar
    private void cargarSitios(String buscar) {
        String URL = url +"/ProyectoAPI/webservice/getLugarF.php?like=" + buscar;
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