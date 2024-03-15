package com.example.movies.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.movies.R;
import com.example.movies.data.remote.OMDbAPI;
import com.example.movies.domain.model.Root;
import com.google.gson.Gson;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLComponents;
import cafsoft.foundation.URLQueryItem;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLSession;

public class MainActivity extends AppCompatActivity {

    private Button btnSearch = null;
    private OMDbAPI omDbAPI = new OMDbAPI();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }
    public void initViews(){
        btnSearch = findViewById(R.id.btnSearch);
    }
    public void initEvents(){
        btnSearch.setOnClickListener(view -> {
            requestMovieInfo();
        });
    }

    public void requestMovieInfo(){
        omDbAPI.requestMovieInfo("superman", text -> {
            Root root = new Gson().fromJson(text, Root.class);
            Log.d("data", root.getTitle());
        }, errorCode -> {
            Log.d("error", String.valueOf(errorCode));
        });
    }
}
