package com.tripfinde.tripfinde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class App extends AppCompatActivity {
    InicioFragment inicioFragment = new InicioFragment();
    DepartamentosFragment departamentosFragment = new DepartamentosFragment();
    BuscarFragment buscarFragment = new BuscarFragment();
    OpcionesFragment opcionesFragment = new OpcionesFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(inicioFragment);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.inicioFragment:
                    loadFragment(inicioFragment);
                    return true;
                case R.id.departamentosFragment:
                    loadFragment(departamentosFragment);
                    return true;
                case R.id.buscarFragment:
                    loadFragment(buscarFragment);
                    return true;
                case R.id.opcionesFragment:
                    loadFragment(opcionesFragment);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.commit();
    }
}