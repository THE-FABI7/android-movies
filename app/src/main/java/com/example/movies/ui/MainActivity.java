package com.example.movies.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movies.R;
import com.example.movies.data.remote.OMDbAPI;
import com.example.movies.domain.model.Root;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.widget.Toast;


import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLComponents;
import cafsoft.foundation.URLQueryItem;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLSession;

public class MainActivity extends AppCompatActivity {

    private TextView labTittle = null;
    private Button btnSearch = null;
    private ImageView imgPoster = null;

    private EditText editTextMovieName;
    private OMDbAPI omDbAPI = new OMDbAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    public void initViews() {
        btnSearch = findViewById(R.id.btnSearch);
        labTittle = findViewById(R.id.labTittle);
        imgPoster = findViewById(R.id.imgPoster);
        editTextMovieName = findViewById(R.id.editTextMovieName);
    }

    public void initEvents() {
        btnSearch.setOnClickListener(view -> {
            requestMovieInfo();
        });
    }

    public void requestMovieInfo() {
        String movieName = editTextMovieName.getText().toString();
        if (movieName.isEmpty()) {
            // Mostrar mensaje de error si el campo está vacío
            Toast.makeText(MainActivity.this, "Por favor ingrese el nombre de la película", Toast.LENGTH_SHORT).show();
            return;
        }
        omDbAPI.requestMovieInfo(movieName, text -> {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setFieldNamingStrategy(field -> {
                var fieldName = field.getName();
                if (fieldName.equals("dvd")) {
                    fieldName = "DVD";
                } else if (!fieldName.startsWith("imdb"))
                    fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                return fieldName;
            });
            var root = gsonBuilder.create().fromJson(text, Root.class);
            if (root.getResponse().equalsIgnoreCase("True")) {

                Log.d("data", root.getTitle());
                requestImage(root);
            } else if(root.getResponse().equalsIgnoreCase("False")) {
                // Mostrar mensaje de error
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Película no encontrada", Toast.LENGTH_SHORT).show();
                });
            }

        }, errorCode -> {
            Log.d("error", String.valueOf(errorCode));
        });
    }

    public void showMovieInfo(Root info, Bitmap image) {
        labTittle.setText(info.getTitle());
        imgPoster.setImageBitmap(image);
    }

    public void requestImage(Root info) {
        omDbAPI.requestImage(info.getPoster(), image -> {
            runOnUiThread(() -> showMovieInfo(info, image));
        }, errorCode -> {
            Log.d("error", String.valueOf(errorCode));
        });
    }
}
