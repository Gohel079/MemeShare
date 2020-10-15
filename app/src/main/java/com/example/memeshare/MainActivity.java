package com.example.memeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView memeimg;
    ProgressBar progressBar;
   String  currentimg=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memeimg = (ImageView)findViewById(R.id.memeimageView);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        loadmeme();
    }

    private void loadmeme()
    {
        progressBar.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://meme-api.herokuapp.com/gimme";

// Request a string response from the provided URL.
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            currentimg = response.getString("url");
                            Glide.with(getApplicationContext()).load(currentimg).into(memeimg);
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Sorry ! Can't Work", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);


    }
    public void sharememe(View view) {
//
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//        sharingIntent.setType("image/*");
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, currentimg);
//        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT ,"Hey you are Using , Madhavesh Gohel MemeShare App "+currentimg);
        Intent chooser = Intent.createChooser(intent,"Madhavesh Gohel MemeShareApp...");
        startActivity(chooser);
    }

    public void nextmeme(View view) {
        loadmeme();
    }
}