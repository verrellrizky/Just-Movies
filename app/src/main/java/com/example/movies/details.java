package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class details extends AppCompatActivity {

    TextView titleDetail, released, genre, actor, boxoffice, textID;
    ImageView detailImage;
    RequestQueue requestQueue;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();
        detailparser();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(details.this, "Movie saved !!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(details.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void detailparser() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String judul = object.getString("Title");
                    String rilis = object.getString("Released");
                    String mgenre = object.getString("Genre");
                    String pemain = object.getString("Actors");
                    String uang = object.getString("BoxOffice");
                    String identity = object.getString("imdbID");

                    String gambar = object.getString("Poster");


                    Glide.with(details.this).load(gambar).into(detailImage);
                    titleDetail.setText("Title: "+judul);
                    released.setText("Released on: "+rilis);
                    genre.setText("Genre: "+mgenre);
                    actor.setText("Actors: "+pemain);
                    boxoffice.setText("BoxOffice: "+uang);
                    textID.setText("imdb ID: "+identity);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(details.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }

    private void init() {
        titleDetail = findViewById(R.id.titledetail);
        released = findViewById(R.id.detail_released);
        genre = findViewById(R.id.detail_genre);
        actor = findViewById(R.id.actors);
        boxoffice = findViewById(R.id.detail_boxofice);
        textID = findViewById(R.id.id);
        save = findViewById(R.id.save_btn);


        detailImage = findViewById(R.id.detailImage);

    }
}