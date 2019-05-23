package com.salesland.personas.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.salesland.personas.R;
import com.salesland.personas.datasource.AppDataBase;
import com.salesland.personas.entities.Persona;
import com.salesland.personas.utils.Utiles;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.salesland.personas.utils.Utiles.verifyStoragePermissions;

public class AgregarPersonaActivity extends AppCompatActivity
      {

    EditText et_nombre, et_apellidos,et_direccion;
    ImageView imagen;
    Button btn_agregar,photoButton,galeryButton;
    TextView fecha;
    Button btn_fecha;
    AppDataBase BBDD;
    String  AgregaActualiza;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE =1;
    Bitmap bmp_reducido_GALERIA,bmp_reducido_CAMARA,bmp_FINAL;


    String mNombre, mApellido,mDireccion,mRutaFoto;
    Integer mIdPersona;

    Persona nuevoPersona;


    void Viewsreference()
    {
        et_nombre= findViewById(R.id.et_nombre_AgregarPersona);
        et_apellidos = findViewById(R.id.et_apellidos_AgregarPersona);
        et_direccion= findViewById(R.id.et_direccion_AgregarPersona);
        btn_agregar= findViewById(R.id.btn_agregar_persona);
        photoButton= findViewById(R.id.btn_camara_AgregarPersona);
        galeryButton= findViewById(R.id.btn_galeria_AgregarPersona);
        imagen= findViewById(R.id.iv_foto_AgregarPersona);



        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{
                    Intent cameraIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    if(cameraIntent.resolveActivity(getPackageManager()) !=null){

                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    }

                }
                catch (Exception ex)
                {

                }



            }
        });

        galeryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    openGallery();
                }
                catch (Exception ex)
                {
                }

            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNombre=et_nombre.getText().toString();
                mApellido = et_apellidos.getText().toString();
                mDireccion=et_direccion.getText().toString();
                mRutaFoto=Utiles.saveImageToStorage(getApplicationContext(),bmp_FINAL,mNombre+mApellido);

                if(bmp_FINAL==null)
                {
                    imagen.buildDrawingCache();
                    bmp_FINAL=imagen.getDrawingCache();
                }

                if(mNombre.trim().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Ingrese nombre", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(mApellido.trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Ingrese apellidos", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        if(mDireccion.trim().equals(""))
                        {
                            Toast.makeText(getApplicationContext(), "Ingrese direccion", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            if(AgregaActualiza.equals("ACTUALIZA"))
                            {
                               Actualiza_persona tasko= new Actualiza_persona();
                               tasko.execute();

                            }
                            else
                            {
                                nuevoPersona = new Persona(mNombre, mApellido,mDireccion,mRutaFoto);

                                Inserta_persona taski= new Inserta_persona();
                                taski.execute();
                            }






                        }

                    }
                }




            }
        });



    }



          void seteaObjetos()
          {


              SharedPreferences prefe=getSharedPreferences("PersonaSeleccionada", Context.MODE_PRIVATE);
              mIdPersona =Integer.parseInt(prefe.getString("id_persona",""));
              mNombre=(prefe.getString("nombre",""));
              mApellido=(prefe.getString("apellidos",""));
              mDireccion=(prefe.getString("direccion",""));
              mRutaFoto=prefe.getString("rutaFoto","");

              et_nombre.setText(mNombre);
              et_apellidos.setText(mApellido);
              et_direccion.setText(mDireccion);

              Utiles.loadImageFromStorage(mRutaFoto,imagen);


          }


          private void openGallery(){
              Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
              startActivityForResult(gallery, PICK_IMAGE);
          }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregarpersona);

        BBDD= Utiles.Inicia_BBDD_LOCAL(getApplicationContext());

        SharedPreferences prefe=getSharedPreferences("Actualiza_Agrega", Context.MODE_PRIVATE);
         AgregaActualiza=(prefe.getString("valor",""));

        Viewsreference();

        Toolbar  mitoolbar = findViewById(R.id.toolbar_agregarpersona);
        setSupportActionBar(mitoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        verifyStoragePermissions(AgregarPersonaActivity.this);

        if(AgregaActualiza.equals("ACTUALIZA"))
        {
            mitoolbar.setTitle("Actualizar Persona");
            btn_agregar.setText("ACTUALIZAR");
            seteaObjetos();

        }


    }


          private class Actualiza_persona extends AsyncTask<Void, Void, Void> {

              @Override
              protected void onPreExecute() {
              }

              @Override
              protected Void doInBackground(Void... params) {


                  try {

                      BBDD.personasDAO().UpdateTarea(mIdPersona,mNombre,mApellido,mDireccion,mRutaFoto);

                  }
                  catch (Exception e) {
                      String msg=e.getMessage();
                  }

                  return null;
              }

              @Override
              protected void onPostExecute(Void result) {
                  super.onPostExecute(result);

                  Intent intent = new Intent(getApplicationContext(), PersonasActivity.class);
                  startActivity(intent);

              }
          }




          private class Inserta_persona extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {



            try {

                BBDD.personasDAO().InsertOnlyOne(nuevoPersona);

            }
            catch (Exception e) {
                String msg=e.getMessage();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);



            Intent intent = new Intent(getApplicationContext(), PersonasActivity.class);
            startActivity(intent);

        }
    }



    //resultado de la accion:  escoger imagen de galeria / camara
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            //capturo la imagen que es un uri y se o igualo a una variable Uri
            imageUri = data.getData();
            try {
                //estas lineas son para que pueda obtener el uri y asignarlo al bitmap
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //capturo el uri en un bitmap local
                Bitmap bmpp = BitmapFactory.decodeStream(imageStream);

                //reduzo el bitmap local y se lo doy a una bariable de bitmap reducido
                bmp_reducido_GALERIA=getResizedBitmap(bmpp,300);

                //asigno el bitmap reducido a los imagesview
                imagen.setImageBitmap(bmp_reducido_GALERIA);
                //   imagenOculta.setImageBitmap(bmp_reducido_GALERIA);

                //igualo el bitmap reducido a una variable global de la clase
                bmp_FINAL=bmp_reducido_GALERIA;

            }
            catch (Exception e)
            {
                // mensajito=e.getMessage().toString();
            }
        }

        if(resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){

            //capturo la imagen tomada
            Bundle extras =data.getExtras();
            Bitmap imageBitmap=(Bitmap) extras.get("data");

            //ingreso el bitmap capturado a una variable bitmap con el tamaño reducido
            bmp_reducido_CAMARA=getResizedBitmap(imageBitmap,300);

            //asigno ese bitmap reducido a los imagesview (la imagen oculta es para guardar la imagen entera en el servidor , el photoviewer es para solo mostrarlo en circulo)
            imagen.setImageBitmap(bmp_reducido_CAMARA);
            //  imagenOculta.setImageBitmap(bmp_reducido_CAMARA);

            //igualo el bitmap reducido a una variable global de la clase
            bmp_FINAL=bmp_reducido_CAMARA;


        }
    }


    //funcion que cambia el tamaño de un bitmap y por ende le reduce el peso
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {


        int width = image.getWidth();
        int height = image.getHeight();

        //a partir de aca lo comentado escala una imagen y la reduce el tamaño y el peso
/*
        float bitmapRatio = (float)width / (float) height;
        //si es mayor a uno quiere decir que es una imagen horizontal

        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        }
        //de lo contrario si es una imagen vertical
        else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
*/

        //para esta aplicacion le dare un tamaño fijo (osea que no escale)
        width=maxSize;
        height=maxSize-100;

        return Bitmap.createScaledBitmap(image, width, height, true);
    }





}
