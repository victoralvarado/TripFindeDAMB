package com.tripfinde.tripfinde;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLugares extends RecyclerView.Adapter<AdapterLugares.MyViewHolder> {
    private Context context;
    private List<ModeloLugar> mdata;
    String url = GlobalInfo.URL;
    public AdapterLugares(Context context, List<ModeloLugar> mdata) {
        this.context = context;
        this.mdata = mdata;
    }


    @NonNull
    @Override
    public AdapterLugares.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.lugares, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLugares.MyViewHolder myViewHolder, final int i) {
        final String idLugar = mdata.get(i).getIdLugar();
        final String nombre = mdata.get(i).getNombre();
        final String descripcion = mdata.get(i).getDescripcion();
        final String portada = mdata.get(i).getPortada();
        Picasso.get().load(url +"/ProyectoAPI/imagenes/portadaLugar/" + portada).into(myViewHolder.portada);
        myViewHolder.titulo.setText(nombre);
        myViewHolder.descripcion.setText(descripcion);
        myViewHolder.vermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(context, Lugar.class);
                        break;
                    default:
                        intent = new Intent(context, Lugar.class);
                        break;
                }
                intent.putExtra("idLugar", idLugar);
                intent.putExtra("nombre", nombre);
                intent.putExtra("descripcion", descripcion);
                intent.putExtra("portada", portada);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView portada;
        TextView titulo, descripcion;
        Button vermas;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            portada =itemView.findViewById(R.id.imgImagenL);
            portada.setClipToOutline(true);
            titulo = itemView.findViewById(R.id.txtTituloLugar);
            descripcion = itemView.findViewById(R.id.txtDescripcion);
            vermas =itemView.findViewById(R.id.btnVerMas);
        }
    }
}
