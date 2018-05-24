package com.example.gabriel.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gabriel.myapplication.modelo.Locais;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private final String TAG="ListActivity";
    private static List list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("List", "test");
        b(savedInstanceState);
        //Bundle bundle = getIntent().getExtras();
        //try {

//            Log.i(TAG,)
           // Log.i(TAG, String.valueOf(list.size()));
//        }catch(Exception e){
//
//        }
    }
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListActivity.this, MapsActivity.class);
        startActivity(intent);
    }
    public void b(Bundle bundle){
        Log.i("List", "test");
        //Bundle bundle = getIntent().getExtras();
        //try {
        String json = bundle.getString("json");
        Gson g = new Gson();
        list = stringToArray(json, Locais[].class);
        Log.i(TAG,json);
        Log.i(TAG,"Lista");
//            Log.i(TAG,)
        Log.i(TAG, String.valueOf(list.size()));
    }
}
