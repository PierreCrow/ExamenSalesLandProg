package com.salesland.personas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salesland.personas.R;
import com.salesland.personas.datasource.AppDataBase;
import com.salesland.personas.utils.Utiles;

public class LoginActivity extends AppCompatActivity
       {



    EditText et_user, et_pass;
    Button btn_ingresar;
    String mUser,mPass;
    AppDataBase BBDD;

    public static String USER1="usuario@usuario.com";
    public static String USER2="admin@usuario.com";
    public static String PASSUSER1="123456789";
    public static String PASSUSER2="admin123";



    void Viewsreference()
    {
        et_user = findViewById(R.id.et_ingresar_usuario);
        et_pass = findViewById(R.id.et_ingresar_contrasena);
        btn_ingresar = findViewById(R.id.btn_ingresar_Login);



        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mUser= et_user.getText().toString();
                mPass = et_pass.getText().toString();

                if(mUser.trim().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Ingrese usuario", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(mPass.trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Ingrese contraseña", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        if(mUser.trim().equals(USER1) && mPass.trim().equals(PASSUSER1))
                        {
                            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), PersonasActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            if(mUser.trim().equals(USER2) && mPass.trim().equals(PASSUSER2))
                            {
                                Intent intent = new Intent(getApplicationContext(), PersonasActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();

                            }
                        }


                    }
                }




            }
        });


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        BBDD= Utiles.Inicia_BBDD_LOCAL(getApplicationContext());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Viewsreference();


    }



}
