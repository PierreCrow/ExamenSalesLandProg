package com.salesland.personas.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.salesland.personas.DialogFragments.BuscarPersona;
import com.salesland.personas.DialogFragments.DetallePersona;
import com.salesland.personas.R;
import com.salesland.personas.adapter.ListaPersonas;
import com.salesland.personas.datasource.AppDataBase;
import com.salesland.personas.entities.Persona;
import com.salesland.personas.utils.Utiles;

import java.util.ArrayList;
import java.util.List;


public class PersonasActivity extends AppCompatActivity implements ListaPersonas.OnItemClickListener
        ,BuscarPersona.CierraDialog_BuscarPersona
{



    private ListaPersonas adapter;
    private  ListaPersonas.OnItemClickListener  mlistener;
    RecyclerView recyclerviewpersonas;

    FloatingActionButton btn_agregarPersona, btn_buscarpersona;
    Button filtross;

    ArrayList<Persona> misPersonas, misPersonasFiltradas;

    AppDataBase BBDD;





    public ProgressDialog dialog;

    @Override
    public void onItemClicked(View v) {

        try{
            DetallePersona df= new DetallePersona();
            df.show(getSupportFragmentManager(), "Dialog");

        }
        catch (Exception ex)
        {

        }
    }

    @Override
    public void onClose_BuscaPersona(DialogInterface dialog, String nombreapellido, Boolean busco) {

        filtross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtross.setVisibility(View.GONE);
                adapter= new ListaPersonas(mlistener,getApplicationContext(), misPersonas);
                recyclerviewpersonas.setAdapter(adapter);
            }
        });

        if(busco==true)
        {
            if(misPersonas ==null || misPersonas.size()==0)
            {
                Toast.makeText(getApplicationContext(), "No hay personas que buscar", Toast.LENGTH_SHORT).show();
                filtross.setVisibility(View.GONE);
            }
            else
                {
                    misPersonasFiltradas= new ArrayList<>();


                    for(int i = 0; i< misPersonas.size(); i++)
                    {
                        String nombreCompleto=misPersonas.get(i).getNombre()+" "+misPersonas.get(i).getApellidos();

                        if(misPersonas.get(i).getNombre().toLowerCase().contains(nombreapellido.toLowerCase()) ||
                                nombreapellido.toLowerCase().equals(misPersonas.get(i).getNombre().toLowerCase()) ||
                                nombreapellido.toLowerCase().equals(misPersonas.get(i).getApellidos().toLowerCase()) ||
                                misPersonas.get(i).getApellidos().toLowerCase().contains(nombreapellido.toLowerCase()) ||
                                nombreCompleto.toLowerCase().contains(nombreapellido.toLowerCase()) )
                        {
                            misPersonasFiltradas.add(misPersonas.get(i));
                        }


                    }

                    filtross.setVisibility(View.VISIBLE);
                    filtross.setText(nombreapellido);
                    adapter= new ListaPersonas(mlistener,getApplicationContext(), misPersonasFiltradas);
                    recyclerviewpersonas.setAdapter(adapter);
                }

            }

    }



    private class cargaPersonas extends AsyncTask<Void, Void, ArrayList<Persona>> {

        @Override
        protected void onPreExecute() {
      //      dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //    dialog.setCanceledOnTouchOutside(false);
          //  dialog.setMessage("Cargamdo Personas");
            //dialog.show();
        }

        @Override
        protected ArrayList<Persona> doInBackground(Void... params) {

            List<Persona> personasas;
            ArrayList<Persona> listapersonas=null;
            try {

                personasas =BBDD.personasDAO().GetAll();

                if(personasas ==null || personasas.size()==0)
                {
                }
                else
                {
                    listapersonas=new ArrayList<>();

                    for(Integer i = 0; i< personasas.size(); i++)
                    {
                        listapersonas.add(personasas.get(i));
                    }
                }






            }
            catch (Exception e) {
            }

            return listapersonas;
        }

        @Override
        protected void onPostExecute(ArrayList<Persona> result) {
            super.onPostExecute(result);

           // if (dialog.isShowing()) {
           //     dialog.dismiss(); }


            if(result==null || result.size()==0)
            {

            }
            else
            {
                misPersonas =result;

                adapter= new ListaPersonas(mlistener,getApplicationContext(), misPersonas);
                recyclerviewpersonas.setAdapter(adapter);
            }

        }
    }




    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.personas);

        BBDD= Utiles.Inicia_BBDD_LOCAL(getApplicationContext());

        Toolbar mitoolbar = findViewById(R.id.toolbar_ttt);
        setSupportActionBar(mitoolbar);

        SharedPreferences preferencias=getSharedPreferences("Actualiza_Agrega", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("valor", "ACTUALIZA" );
        editor.commit();


        mlistener=this;


        filtross= findViewById(R.id.tv_filtros_personas);
        btn_agregarPersona = findViewById(R.id.btn_Agregar_persona);
        btn_buscarpersona = findViewById(R.id.btn_Buscar_persona);
        recyclerviewpersonas = findViewById(R.id.rv_personas);
        recyclerviewpersonas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

       // dialog = new ProgressDialog(getApplicationContext());

        cargaPersonas task= new cargaPersonas();
        task.execute();




        btn_buscarpersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    BuscarPersona df= new BuscarPersona();
                    df.show(getSupportFragmentManager(), "Dialog");

                }
                catch (Exception ex)
                {

                }


            }
        });

        btn_agregarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    SharedPreferences preferencias=view.getContext().getSharedPreferences("Actualiza_Agrega", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferencias.edit();
                    editor.putString("valor", "AGREGA" );
                    editor.commit();

                    Intent intento = new Intent(getApplicationContext(), AgregarPersonaActivity.class);
                    startActivity(intento);
                }
                catch (Exception ex)
                {

                }



            }
        });




    }






}



