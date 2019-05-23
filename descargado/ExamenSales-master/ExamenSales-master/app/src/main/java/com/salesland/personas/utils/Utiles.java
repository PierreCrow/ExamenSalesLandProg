package com.salesland.personas.utils;


import android.Manifest;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.salesland.personas.datasource.AppDataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utiles {


    private Context ctx;

    public Utiles(Context ctx){
        this.ctx = ctx;
    }

    public static final String DATABASE_NAME = "DataBase_db";


    public static AppDataBase Inicia_BBDD_LOCAL(Context ctx)
    {
        AppDataBase APPDATABASE;

        APPDATABASE = Room.databaseBuilder(ctx,
                AppDataBase.class, Utiles.DATABASE_NAME).fallbackToDestructiveMigration().build();

        return APPDATABASE;
    }


    public static void loadImageFromStorage(String Path_Foto,ImageView iv)
    {
        try {
            File f=new File(Path_Foto);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            // ImageView img=(ImageView)findViewById(R.id.imgPicker);
            iv.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED && camera != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    //devuelve la ruta de la imagen guardada
    public static String saveImageToStorage(Context ctx,Bitmap bitmapImage,String nombrefoto){


        File Dir1=null;

            Dir1= ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES );
            if (!Dir1.exists()) {
                Dir1.mkdir();
            }
            // Create imageDir
            File mypath=new File(Dir1,nombrefoto.replace(" ","")+".jpg");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return mypath.getAbsolutePath();

        }


    }






