package com.devastadores.hackhaton.transitodelestado;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by Alejandro on 03/05/2017.
 */

public class reporte extends AppCompatActivity {
    private String num;
    private String placa;
    private TextView tipoevento;
    private TextView fecha;
    private TextView localizacion;
    private ImageView imagevento;
    private String calle="";
    private String latitud="";
    private String longitud="";
    private String event;
    private Button enviarreporte;
    private int tipo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportes);
        imagevento=(ImageView)findViewById(R.id.imtipo);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        tipoevento=(TextView)findViewById(R.id.tipoevento);
        fecha=(TextView)findViewById(R.id.fechaevento);
        localizacion=(TextView)findViewById(R.id.localizacionevento);
        Intent in=getIntent();
        Bundle extras=in.getExtras();
        calle=extras.getString("calle");
        longitud=extras.getString("longitud");
        latitud=extras.getString("latitud");
        enviarreporte=(Button)findViewById(R.id.envioreport);
        enviarreporte.setOnClickListener(clickenvio);
        switch (extras.getInt("tipo"))
        {
            case 0: imagevento.setBackgroundResource(R.drawable.sem);
                event="Semaforo";
                tipoevento.setText("TIPO DE EVENTO:\n"+"Semaforo Descompuesto");
                break;
            case 1: imagevento.setBackgroundResource(R.drawable.acc);
                event="Choque";

                tipoevento.setText("TIPO DE EVENTO:\n"+"Accidente");
                break;
            case 2: imagevento.setBackgroundResource(R.drawable.bloc);
                event="Bloqueo";

                tipoevento.setText("TIPO DE EVENTO:\n"+"Bloqueo Vial");
                break;
            case 3: imagevento.setBackgroundResource(R.drawable.extor);
            event="Extorsion";

            tipoevento.setText("TIPO DE EVENTO:\n"+"Extorsion Vial");
            break;
        }
        localizacion.setText("LOCALIZACION DEL EVENTO:\n"+calle);
        fecha.setText("FECHA:\n");

    }
    private View.OnClickListener clickenvio=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            envio(latitud,longitud,event,calle);
        }
    };
private void envio(String a,String b,String c,String d)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://192.168.18.129/trafico/php/insert_suc_apk.php");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
        nameValuePair.add(new BasicNameValuePair("lat", a));
        nameValuePair.add(new BasicNameValuePair("long", b));

        nameValuePair.add(new BasicNameValuePair("ubicacion", eliminarAcentos(d)));
        nameValuePair.add(new BasicNameValuePair("suceso", c));


        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));


        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }



        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log

            Toast.makeText(getApplicationContext(),"Reporte Enviado",Toast.LENGTH_SHORT).show();
            finish();
        } catch (ClientProtocolException e) {
            // Log exception
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public static String eliminarAcentos(String str) {

        final String ORIGINAL = "ÁáÉéÍíÓóÚúÑñÜü";
        final String REEMPLAZO = "AaEeIiOoUuNnUu";

        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int indice = 0; indice < array.length; indice++) {
            int pos = ORIGINAL.indexOf(array[indice]);
            if (pos > -1) {
                array[indice] = REEMPLAZO.charAt(pos);
            }
        }
        return new String(array);
    }
    }

