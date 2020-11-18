package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    public static String EXTRA_TEXT = "com.example.movies.EXTRA_TEXT";

    TextView title, year, id, command;
    EditText inputsearch;
    Button btnSearch;
    ImageView imageView;

    RequestQueue requestQueue;

    String url, searchval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataParsing();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetail();
            }
        });



    }









    private void toDetail() {
        String searchval = inputsearch.getText().toString().trim();
        String url = "https://www.omdbapi.com/?t="+searchval+"&apikey=84b437ad";

        Intent intent = new Intent(MainActivity.this, details.class);
        intent.putExtra(EXTRA_TEXT, url);

        startActivity(intent);
    }


    private void dataParsing() {
        String searchval = inputsearch.getText().toString().trim();

        String url = "https://www.omdbapi.com/?t="+searchval+"&apikey=84b437ad";

        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String judul = object.getString("Title");
                    String tahun = object.getString("Year");
                    String gambar = object.getString("Poster");

                    Glide.with(MainActivity.this).load(gambar).into(imageView);
                    title.setText("Title: "+judul);
                    year.setText("Year: "+tahun);
                    command.setText("Tap the picture for details");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }

    private void init(){
        title = findViewById(R.id.tv_title);
        year = findViewById(R.id.tv_year);
        id = findViewById(R.id.tv_id);

        inputsearch = findViewById(R.id.ed_search);
        btnSearch = findViewById(R.id.btn_search);
        imageView = findViewById(R.id.image);
        command = findViewById(R.id.command);
    }
}