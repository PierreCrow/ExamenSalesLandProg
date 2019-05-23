package com.salesland.personas.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.salesland.personas.R;
import com.salesland.personas.activities.AgregarPersonaActivity;
import com.salesland.personas.activities.PersonasActivity;
import com.salesland.personas.datasource.AppDataBase;
import com.salesland.personas.utils.Utiles;


public class DetallePersona extends DialogFragment {


    TextView tv_nombre, tv_apellidos, tv_direccion;
    ImageView iv_foto;


    Integer id_Persona;

    Button btnEditar,btnEliminar;



    AppDataBase BBDD;





    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {

            //numero de pixeles que tendra de ancho
            // int width = 700;
            int width=ViewGroup.LayoutParams.MATCH_PARENT;

            //la altura se ajustara al contenido
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;

            //se lo asigno a mi dialogfragment
            dialog.getWindow().setLayout(width, height);

            //con esto hago que sea invicible
            //en este caso no lo hara xq le de un fondo al linearlayout
            // dialog.getWindow().getAttributes().alpha = 0.9f;
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {


    }


    @Override
    public void onCancel (DialogInterface dialog)
    {
        dismiss();
    }



    private class Elimina_Persona extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {



            try {

                BBDD.personasDAO().DeleteById(id_Persona);

            }
            catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            dismiss();

            Intent intent = new Intent(getActivity(), PersonasActivity.class);
            startActivity(intent);

        }
    }



    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {

      View view = getActivity().getLayoutInflater().inflate(R.layout.detallepersona, new LinearLayout(getActivity()), false);

        BBDD= Utiles.Inicia_BBDD_LOCAL(getActivity());

        tv_nombre = view.findViewById(R.id.tv_Nombre_DetallePersona);
        tv_apellidos = view.findViewById(R.id.tv_apellidos_DetallePersona);
        tv_direccion = view.findViewById(R.id.tv_direccion_DetallePersona);
        iv_foto= view.findViewById(R.id.iv_Foto_DetallePersona);
        btnEditar= view.findViewById(R.id.btn_editar_DetallePersona);
        btnEliminar= view.findViewById(R.id.btn_eliminar_DetallePersona);


        SharedPreferences prefe=getActivity().getSharedPreferences("PersonaSeleccionada", Context.MODE_PRIVATE);
        id_Persona =Integer.parseInt(prefe.getString("id_persona",""));
        String nombre=(prefe.getString("nombre",""));
        String apellidoss=(prefe.getString("apellidos",""));
        String direccionn=(prefe.getString("direccion",""));
        String rutafotoo=prefe.getString("rutaFoto","");


        tv_direccion.setText(direccionn);
        tv_nombre.setText(nombre);
        tv_apellidos.setText(apellidoss);

        Utiles.loadImageFromStorage(rutafotoo,iv_foto);



        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
             //   Intent intent = new Intent(getActivity(), LoginActivity.class);
             //   startActivity(intent);


                SharedPreferences preferencias=view.getContext().getSharedPreferences("Actualiza_Agrega", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("valor", "ACTUALIZA" );
                editor.commit();
                Intent intent = new Intent(getActivity(), AgregarPersonaActivity.class);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Muestradialog(getActivity());
            }
        });

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);

       builder.setContentView(view);
        return  builder;

    }

    void Muestradialog(Context ctx)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:




                        Elimina_Persona task= new Elimina_Persona();
                        task.execute();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("¿Esta seguro que desea eliminar esta persona").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }







}

