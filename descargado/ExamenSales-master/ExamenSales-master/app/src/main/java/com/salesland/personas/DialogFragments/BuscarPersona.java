package com.salesland.personas.DialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.salesland.personas.R;
import com.salesland.personas.datasource.AppDataBase;
import com.salesland.personas.utils.Utiles;


public class BuscarPersona extends DialogFragment
   {




    EditText et_nombreapellido;


    AppDataBase BBDD;

    Boolean busco;
    Button btn_buscar;






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





    //evento cuando el dialog se cierra
    @Override
    public void onDismiss(DialogInterface dialog) {



        Activity aaa=getActivity();



            if(aaa instanceof CierraDialog_BuscarPersona)
            {  ((CierraDialog_BuscarPersona)aaa).onClose_BuscaPersona(dialog,et_nombreapellido.getText().toString(),busco);}




    }


    @Override
    public void onCancel (DialogInterface dialog)
    {
        dismiss();
    }










    //evento cuando se crea el dialog
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {

      View view = getActivity().getLayoutInflater().inflate(R.layout.buscarpersona, new LinearLayout(getActivity()), false);


        BBDD= Utiles.Inicia_BBDD_LOCAL(getActivity());


        et_nombreapellido = view.findViewById(R.id.et_nombre_BuscarPersona);
        btn_buscar= view.findViewById(R.id.btn_buscar_BuscarPersona);



        busco=false;


        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   nroDoc=et_rucCliente.getText().toString();


                if(et_nombreapellido.getText().toString().trim().equals(""))
                {
                    dismiss();
                }
                else
                {
                    dismiss();
                    busco=true;
                }



            }
        });



        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);



       builder.setContentView(view);
        return  builder;

    }

    public interface CierraDialog_BuscarPersona
    {
        void onClose_BuscaPersona(DialogInterface dialog, String nombreapellido, Boolean busco);
    }







}

