package com.devastadores.hackhaton.transitodelestado;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Alejandro on 03/05/2017.
 */

public class login extends AppCompatActivity {
    private EditText num;
    private EditText placa;
    private Button login;
    private String Numero="";
    private String NPlaca="";
    String fichero = "usuario.txt";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        num=(EditText) findViewById(R.id.idclave);
        placa=(EditText) findViewById(R.id.placa);
        login=(Button)findViewById(R.id.btnlogin);
        comprobacion();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Numero=num.getText()+"\n";
                NPlaca=placa.getText()+"\n";

                almacenmientousuario();
                comprobacion();
            }
        });
    }
    private void comprobacion()
    {
        if(comprobacionusuario())
        {
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Inicie Sesion", Toast.LENGTH_SHORT).show();
        }
    }
    private void almacenmientousuario()
    {
        FileOutputStream fos;
        try {
            fos = openFileOutput(fichero, Context.MODE_PRIVATE);
            fos.write(Numero.getBytes());
            fos.write(NPlaca.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
        }
    }
    public boolean comprobacionusuario()
    {
        String linea;
        try {
            FileInputStream f = getApplicationContext().openFileInput(fichero);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(f));
            int n = 0;
            Numero = entrada.readLine();
            Toast.makeText(getApplicationContext(),"Numero de Permiso: "+Numero,Toast.LENGTH_SHORT).show();
            NPlaca = entrada.readLine();
            Toast.makeText(getApplicationContext(),"Placa: "+NPlaca,Toast.LENGTH_SHORT).show();
            f.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Usuario no Iniciado",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
