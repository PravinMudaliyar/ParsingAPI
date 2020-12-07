package com.example.parsingapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

   // private RequestQueue requestQueue;              ( Before singleton usage )


    RequestQueue queue;




   /*  https://jsonplaceholder.typicode.com/todos/1      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
       // queue = MySingleton.getInstance(this).getRequestQueue();        (Also works).



        //  requestQueue = Volley.newRequestQueue(this);   // Also can use : MainActivity.this       ( Before singleton usage )

        //We have used requestQueue bcoz we can't directly access anything from internet as we wish. Any thing can happen while using it.
        // Hence we are collecting everything in queue to avoid lag,traffic jam,etc.


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/todos/1",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("JSON", "onResponse: " + response.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // JsonArrayRequest

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/todos", null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i=0 ; i<response.length() ; i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("JsonArray", "onResponse: " + jsonObject.getString("title"));
                        boolean d = jsonObject.getBoolean("completed");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    //    requestQueue.add(jsonObjectRequest);          .... This is for JsonObject           ( Before singleton usage )
    //      requestQueue.add(jsonArrayRequest);                                                ( Before singleton usage )

        //Important to make changes in manifest file (Internet permission) to be able to fetch the data perfectly.


        queue.add(jsonArrayRequest);

    }
}