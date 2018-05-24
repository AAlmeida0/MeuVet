package com.example.gabriel.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.example.gabriel.myapplication.modelo.localizacao;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Rest extends AsyncTask<String, String, String> {
    private final String TAG = "getTH";
    private String json;
    private localizacao loc;
    public Rest(localizacao loc) {
        this.loc = loc;
        Log.i(TAG, String.valueOf(loc.getLatitude()));
        Log.i(TAG, String.valueOf(loc.getLongitude()));
    }

    @Override
    protected String doInBackground(String... link) {
        URL url = null;
        try {
            url = new URL("http://192.168.42.57:8080/restPAP/webresources/rest");
            Log.i(TAG, "tag1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //URLConnection conn = url.openConnection();
            //conn.set
            Log.i(TAG, "tag2");
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            Gson g = new Gson();
            String cordenadas = g.toJson(loc);
            os.writeBytes(cordenadas);
            os.flush();
            os.close();
            Scanner s = new Scanner(conn.getInputStream());
            Log.i(TAG, "tag3");
            Log.i(TAG, s.toString());
            json = s.useDelimiter("\\A").next();
            Log.i(TAG,json);
            s.close();
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("POST");
//            urlConnection.
            //DefaultHttpClient httpclient = new DefaultHttpClient();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
