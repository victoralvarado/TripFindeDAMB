package com.tripfinde.tripfinde;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDepartamentos extends RecyclerView.Adapter<AdapterDepartamentos.MyViewHolder> {
    public interface ItemClickListener {
        void onItemClick(ModeloDepartamento item);
    }
    String url = GlobalInfo.URL;
    private Context context;
    private List<ModeloDepartamento> mdata;
    private ItemClickListener listener;

    public AdapterDepartamentos(Context context, List<ModeloDepartamento> mdata, ItemClickListener listener) {
        this.context = context;
        this.mdata = mdata;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterDepartamentos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.departamentos, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDepartamentos.MyViewHolder myViewHolder, final int i) {
        final String nombre = mdata.get(i).getNombre();
        final String portada = mdata.get(i).getPortada();
        Picasso.get().load(url +"/ProyectoAPI/imagenes/portadaDepto/" + portada).into(myViewHolder.portada);
        myViewHolder.nombre.setText(nombre);
        myViewHolder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(mdata.get(i));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView portada;
        TextView nombre;
        LinearLayout contenedor;
        TextView nombreSeleccion;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            portada =itemView.findViewById(R.id.imgPortadaD);
            portada.setClipToOutline(true);
            nombre = itemView.findViewById(R.id.txtDepartamento);
            contenedor = itemView.findViewById(R.id.contenedorDepartamento);
            nombreSeleccion = itemView.findViewById(R.id.txtDepartamentoF);
        }
    }
}
