package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView =  findViewById(R.id.text);
        final TextView textView2=findViewById(R.id.min);
        final TextView textView3=findViewById(R.id.max);
        final TextView textView4=findViewById(R.id.pressure);
        final TextView textView5=findViewById(R.id.wind);
        final TextView textView6=findViewById(R.id.description);
        final ProgressBar pd=findViewById(R.id.pd);
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.openweathermap.org/data/2.5/weather?q=Egypt&appid=d6d3982d847c2f2440b639860fd4e457";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject parent=new JSONObject(response);
                            JSONArray arr = parent.getJSONArray("weather");
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject each = (JSONObject) arr.get(i);
                               String s= each.getString("description");
                                textView6.setText(s);
                            }

                            Log.d("json",""+parent);
                            JSONObject main=parent.getJSONObject("main");

                            JSONObject wind=parent.getJSONObject("wind");


                            double windSpeed=wind.getDouble("speed");
                            textView5.setText(""+windSpeed);
                            Log.d("json",""+main);
                            double temp=main.getDouble("temp");
                            double max=main.getDouble("temp_max");
                            double min=main.getDouble("temp_min");
                            double pressure=main.getDouble("pressure");
                            Log.d("json","temp");
                            pd.setVisibility(View.INVISIBLE);
                            textView.setText(""+temp+" ");
                            textView2.setText(""+min);
                            textView3.setText(""+max);
                            textView4.setText(""+pressure);




                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                        // Display the first 500 characters of the response string.
                      //  textView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("json",""+error);
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);





    }
}