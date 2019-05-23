package com.salesland.personas.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesland.personas.R;
import com.salesland.personas.entities.Persona;
import com.salesland.personas.utils.Utiles;

import java.util.ArrayList;
import java.util.List;


//ADAPTADOR PARA LA LISTA USANDO RECYCLERVIEW
public class ListaPersonas extends RecyclerView.Adapter<ListaPersonas.ListaAsistenciasViewHolder> {


    private List<Persona> items =new ArrayList<>();



    public OnItemClickListener mlistener;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClicked(View v);
    }

    public void add(Persona item){
        items.add(item);
        notifyItemInserted(items.size()-1);
    }



    @Override
    public ListaAsistenciasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistapersonas, parent, false);
        ListaAsistenciasViewHolder rvMainAdapterViewHolder = new ListaAsistenciasViewHolder(view);

        return  rvMainAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(final ListaAsistenciasViewHolder holder, int position) {
        Persona personasss = items.get(position);

        holder.nombre.setText(personasss.getNombre());
        holder.id_persona.setText(personasss.getId_persona().toString());
        holder.apellidos.setText(personasss.getApellidos().toString());
        holder.direccion.setText(personasss.getDireccion().toString());
        holder.rutaFoto.setText(personasss.getRutaFoto().toString());


        Utiles.loadImageFromStorage(personasss.getRutaFoto(),holder.foto);




    }

    @Override
    public int getItemCount() {

        return items.size();
    }

      class ListaAsistenciasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombre, id_persona, apellidos, direccion, rutaFoto;
        ImageView foto;


        public ListaAsistenciasViewHolder(View v){
            super(v);
            nombre = v.findViewById(R.id.tv_nombre_ItemPersona);
            id_persona = v.findViewById(R.id.tv_idpersona_ItemPersona);
            apellidos = v.findViewById(R.id.tv_apellidos_ItemPersona);
            direccion = v.findViewById(R.id.tv_direccion_ItemPersona);
            rutaFoto = v.findViewById(R.id.tv_rutaFoto_ItemPersona);
            foto= v.findViewById(R.id.iv_fotopersona_ItemPersona);


            v.setOnClickListener(this);
        }


         @Override
         public void onClick(View v) {


             SharedPreferences preferencias=v.getContext().getSharedPreferences("PersonaSeleccionada",Context.MODE_PRIVATE);
             SharedPreferences.Editor editor=preferencias.edit();
             editor.putString("id_persona", id_persona.getText().toString() );
             editor.putString("apellidos", apellidos.getText().toString() );
             editor.putString("direccion", direccion.getText().toString() );
             editor.putString("nombre", nombre.getText().toString() );
             editor.putString("rutaFoto", rutaFoto.getText().toString() );
             editor.commit();

             mlistener.onItemClicked(v);


         }

    }


    public ListaPersonas(OnItemClickListener listener, Context context, ArrayList<Persona> item){
        this.items = item;
        this.mlistener = listener;
        this.mContext=context;

    }



}


