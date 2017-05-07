package com.devastadores.hackhaton.transitodelestado;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.id.list;
import static android.R.id.message;

/**
 * Created by Alejandro on 03/05/2017.
 */

public class estados extends AppCompatActivity {
    private ArrayList<Datos> resultado=new ArrayList<Datos>();
    ListView lis;
    private Button sol;
    private String pathestados;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estado);
        sol=(Button)findViewById(R.id.sol);
        sol.setOnClickListener(btnsol);
        lis=(ListView)findViewById(R.id.estadoslist);
        Intent in=getIntent();
        Bundle extras=in.getExtras();
        pathestados=extras.getString("path");

        Toast.makeText(getApplicationContext(),pathestados,Toast.LENGTH_SHORT).show();
        lis.setOnItemClickListener(clicklist);
    }
    private AdapterView.OnItemClickListener clicklist = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Datos item=resultado.get(i);
            Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
            String url = "https://www.google.com/maps/@"+item.getLatitud()+","+item.getLongitud()+",17z";
            Uri uri = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(launchBrowser);
        }
    };
    private View.OnClickListener btnsol = new View.OnClickListener()
    {

        @Override
        public void onClick(View view) {

            try {
                InputStream is=new FileInputStream(pathestados);
                recorreJSon(is);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_list_item_1, resultado);
                lis.setAdapter(arrayAdapter);
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(),pathestados,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }


        }
    };
    private void recorreJSon(InputStream is)
    {
        try {//para recorrer  el jso
            //InputStream is=MainActivity.this.getResources().openRawResource(R.raw.datos);
            JsonReader jsonR=new JsonReader(new InputStreamReader(is,"UTF-8"));

            //se realiza el recorrido del JSon
            ParseJSON pJSON=new ParseJSON();
            resultado=pJSON.LeerTodoJson(jsonR);
            Toast.makeText(getApplicationContext(),resultado.toString(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class HtpJSon extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL direccion =new URL(params[0]);
                HttpURLConnection conec=(HttpURLConnection)direccion.openConnection();
                InputStream is=new BufferedInputStream(conec.getInputStream());
                recorreJSon(is);
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            JSONObject jsonObject = new JSONObject();

        }
    }

}
