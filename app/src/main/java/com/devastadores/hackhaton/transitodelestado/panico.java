package com.devastadores.hackhaton.transitodelestado;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alejandro on 03/05/2017.
 */
public class panico extends Activity {

    private static final String LOG_TAG = "Grabadora";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private static String fichero = Environment.
            getExternalStorageDirectory().getAbsolutePath()+"/audio.3gp";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panico);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toast.makeText(getApplicationContext(),"Iniciando Grabacion",Toast.LENGTH_SHORT);
        grabar();
    }

    public void grabar() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setOutputFile(fichero);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.
                OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.
                AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Fallo en grabación");
        }
        mediaRecorder.start();


    }

    public void detenerGrabacion(View view) {
        try
        {


        mediaRecorder.stop();
        mediaRecorder.release();
        Toast.makeText(getApplicationContext(),"Deteniendo Grabacion",Toast.LENGTH_SHORT);
        }catch (Exception e)
        {

        }
    }

    public void reproducir(View view) {

        try
        {
            mediaRecorder.stop();
            mediaRecorder.release();
            Toast.makeText(getApplicationContext(),"Deteniendo Grabacion",Toast.LENGTH_SHORT);
        }catch (Exception e)
        {

        }
        mediaPlayer = new MediaPlayer();
        Toast.makeText(getApplicationContext(),fichero,Toast.LENGTH_SHORT).show();
        try {
            mediaPlayer.setDataSource(fichero);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Fallo en reproducción");
        }
    }
}