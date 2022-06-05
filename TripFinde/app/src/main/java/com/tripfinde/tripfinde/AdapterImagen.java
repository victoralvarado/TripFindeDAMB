package com.tripfinde.tripfinde;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterImagen extends RecyclerView.Adapter<AdapterImagen.MyViewHolder> {

    private Context context;
    private List<ModeloImagen> mdata;
    String url = GlobalInfo.URL;
    public AdapterImagen(Context context, List<ModeloImagen> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public AdapterImagen.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.imageneslugar, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImagen.MyViewHolder myViewHolder, final int i) {
        final String imagen = mdata.get(i).getImagen();
        Picasso.get().load(url +"/ProyectoAPI/imagenes/imgLugares/" + imagen).into(myViewHolder.imagen);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        public MyViewHolder(@NonNull View itemview){
            super(itemview);
            imagen = itemview.findViewById(R.id.imgImagenL);
            imagen.setClipToOutline(true);
        }
    }
}
