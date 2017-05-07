package com.devastadores.hackhaton.transitodelestado;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.location.SettingInjectorService;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.location.LocationListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private  String pathestados="";
    private ImageButton btnPanic;
    private ImageButton reportsema;
    private ImageButton reportacci;
    private ImageButton reportbloqueo;
    private ImageButton ext;
    private String calle = "";
    private String latitud = "";
    private String longitud = "";
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), login.class);
        startActivity(intent);

        btnPanic = (ImageButton) findViewById(R.id.panicimg);
        ext = (ImageButton) findViewById(R.id.extor);
        reportsema = (ImageButton) findViewById(R.id.traficimg);
        reportacci = (ImageButton) findViewById(R.id.choque);
        reportbloqueo = (ImageButton) findViewById(R.id.bloqueo);
        btnPanic.setOnClickListener(clickbtnpanic);
        reportsema.setOnClickListener(clickbtnsema);
        reportacci.setOnClickListener(clickbtnacci);
        reportbloqueo.setOnClickListener(clickbtnbloque);
        ext.setOnClickListener(clickbtnext);

        ////
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

        } else {
            locationStart();
        }
        ////
    }

    ////
    private void locationStart() {

        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        //mensaje1.setText("Localizacion agregada");
        //mensaje2.setText("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    //mensaje2.setText("Mi direccion es: \n"
                    //      + DirCalle.getAddressLine(0));
                    calle = DirCalle.getAddressLine(0);
                    longitud = String.valueOf(loc.getLongitude());
                    latitud = String.valueOf(loc.getLatitude());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        MainActivity mainActivity;

        public MainActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();

            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            // mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            // mensaje1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

    ////
    private View.OnClickListener clickbtnpanic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Uri numero = Uri.parse("tel:" + "9511812320");
            Intent intent = new Intent(Intent.ACTION_CALL, numero);

            startActivity(intent);


        }
    };
    private View.OnClickListener clickbtnsema = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), reporte.class);
            intent.putExtra("tipo", 0);
            intent.putExtra("calle", calle);
            intent.putExtra("latitud", latitud);
            intent.putExtra("longitud", longitud);
            startActivity(intent);
        }
    };
    private View.OnClickListener clickbtnext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {



            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), panico.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener clickbtnacci = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), reporte.class);
            intent.putExtra("tipo", 1);
            intent.putExtra("calle", calle);
            intent.putExtra("latitud", latitud);
            intent.putExtra("longitud", longitud);
            startActivity(intent);
        }
    };
    private View.OnClickListener clickbtnbloque = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), reporte.class);
            intent.putExtra("tipo", 2);
            intent.putExtra("calle", calle);
            intent.putExtra("latitud", latitud);
            intent.putExtra("longitud", longitud);
            startActivity(intent);
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String doc;
        Uri.parse("android.resource://com.my.package/drawable/icon");
        int id = item.getItemId();

        if /*(id == R.id.reporte) {
            // Handle the camera action
        } else if*/ (id == R.id.estado) {
            dow();
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), estados.class);
            intent.putExtra("path",pathestados);
            startActivity(intent);

        } else if (id == R.id.reglamento) {
            String url = "http://www.congresooaxaca.gob.mx/62/legislacion/leyes/212.pdf";
            Uri uri = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(launchBrowser);


        } else if (id == R.id.form) {
            String url = "https://script.google.com/macros/s/AKfycbzzzsergF5SDrsldXS4jqeEG5C3-5FRnCOw-FEjDt8s6MHWHa4q/exec";
            Uri uri = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(launchBrowser);
        } else if (id == R.id.nav_share) {
            //////////Repositorio/////////////

            String url = "https://script.google.com/macros/s/AKfycbzzzsergF5SDrsldXS4jqeEG5C3-5FRnCOw-FEjDt8s6MHWHa4q/exec";
            Uri uri = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(launchBrowser);
        } else if (id == R.id.nav_ayuda) {



        } else if (id == R.id.cerrar_sesion) {
            getApplicationContext().deleteFile("usuario.txt");
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /////////
    private void dow()
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            //primero especificaremos el origen de nuestro archivo a descargar utilizando
            //la ruta completa
            URL url = new URL("http://192.168.18.129/trafico/php/consu_even.php");

            //establecemos la conexión con el destino
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //establecemos el método jet para nuestra conexión
            //el método setdooutput es necesario para este tipo de conexiones
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //por último establecemos nuestra conexión y cruzamos los dedos <img draggable="false" class="emoji" alt="😛" src="https://s.w.org/images/core/emoji/72x72/1f61b.png">
            urlConnection.connect();

            //vamos a establecer la ruta de destino para nuestra descarga
            //para hacerlo sencillo en este ejemplo he decidido descargar en
            //la raíz de la tarjeta SD
            File SDCardRoot = Environment.getExternalStorageDirectory();

            //vamos a crear un objeto del tipo de fichero
            //donde descargaremos nuestro fichero, debemos darle el nombre que
            //queramos, si quisieramos hacer esto mas completo
            //cogeríamos el nombre del origen
            File file = new File(SDCardRoot,"ejemplo.txt");

            //para escribir el archivo que descargamos en el nuevo
            FileOutputStream fileOutput = new FileOutputStream(file);
            pathestados=file.getAbsolutePath();
            Toast.makeText(getApplicationContext(),pathestados,Toast.LENGTH_SHORT).show();
            //leemos los datos desde la url
            InputStream inputStream = urlConnection.getInputStream();

            //obtendremos el tamaño del archivo y lo asociaremos a una
            //variable de tipo entero
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;

            //creamos un buffer y una variable para ir almacenando el
            //tamaño temporal de este
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            //ahora iremos recorriendo el buffer para escribir el archivo de destino
            //siempre teniendo constancia de la cantidad descargada y el total del tamaño
            //con esto podremos crear una barra de progreso
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {

                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                //podríamos utilizar una función para ir actualizando el progreso de lo
                //descargado


            }
            //cerramos
            fileOutput.close();

//y gestionamos errores
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }}


}

