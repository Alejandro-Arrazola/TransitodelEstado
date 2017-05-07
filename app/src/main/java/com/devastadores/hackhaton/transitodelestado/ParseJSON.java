package com.devastadores.hackhaton.transitodelestado;

import android.util.JsonReader;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alejandro on 28/10/2015.
 */
public class ParseJSON {

    //metodo que recorre todo el JSON
    public ArrayList<Datos> LeerTodoJson(JsonReader jr)
    {
        ArrayList<Datos> resultado= new ArrayList<Datos>();
        try
        {
            //posiciona el apuntador al inicio del arreglo
            jr.beginArray();
            //se realiza el recorrido
            while(jr.hasNext())
            {
                //guardamos un objeto en el array list
                resultado.add(leerUnObjetoJson(jr));
            }
            //posicion el apuntador al final del arreglo
            jr.endArray();
        }catch (Exception e)
        {
            Log.e("Error TodoJson",e.toString()+e.getMessage());
        }
        return resultado;
    }


    //Metodo Que Retorna un Objeto del Json
    public Datos leerUnObjetoJson(JsonReader jr)
    {
         String suceso=null;
         String latitud=null;
         String longitud=null;
        String estado=null;
        String ubicacion=null;
        try {
            //Obtenemos el Inicio del Objeto
            jr.beginObject();
            while(jr.hasNext())
            {
                String propiedad=jr.nextName();
                if(propiedad.equals("lat"))
                {
                    latitud=jr.nextString();
                }else if(propiedad.equals("long"))
                {
                    longitud=jr.nextString();
                }
                else if(propiedad.equals("suceso"))
                {
                    suceso=jr.nextString();
                }else if(propiedad.equals("estado"))
                {
                    estado=jr.nextString();
                }else if(propiedad.equals("ubicacion"))
                {
                    ubicacion=jr.nextString();
                }
                else
                {
                    //en caso de un elemento que no queremos utilizar
                  jr.skipValue();
                }
            }
            //Se indica que termina el Objeto
            jr.endObject();

        } catch (Exception e) {
            Log.e("ERROR EN PARSE",e.toString());
        }
        return new Datos(suceso,latitud,longitud,estado,ubicacion);
    }
}
