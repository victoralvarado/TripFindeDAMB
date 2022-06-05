package com.tripfinde.tripfinde;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpcionesFragment} factory method to
 * create an instance of this fragment.
 */
public class OpcionesFragment extends Fragment {
    TextView btnCerrarSesion, btnGestionCuenta, txtCopyright;
    View view;
    Calendar cal= Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int year= cal.get(Calendar.YEAR);
        view = inflater.inflate(R.layout.fragment_opciones, container, false);
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        btnGestionCuenta = view.findViewById(R.id.btnGestionCuenta);
        txtCopyright = view.findViewById(R.id.txtCopy);
        txtCopyright.setText("Copyright © "+year+" TripFinde");
        btnGestionCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                String correo = prefs.getString("correo", "");
                String clave = prefs.getString("clave", "");
                Intent intent = new Intent(getActivity(), GestionCuenta.class);
                intent.putExtra("correo", correo);
                intent.putExtra("clave", clave);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                prefs.edit().clear().commit();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}