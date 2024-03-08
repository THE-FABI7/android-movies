package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLComponents;
import cafsoft.foundation.URLQueryItem;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLSession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPeliculas();
    }

    public void getPeliculas() {
        // Initialize URLComponents
        var components = new URLComponents();
        components.setScheme("https");
        components.setHost("www.omdbapi.com");
        components.setQueryItems(new URLQueryItem[]{
                new URLQueryItem("t", "hobbit"),
                new URLQueryItem("apikey", "b16f245")
        });

    // Generate the URL from the components
        var url = components.getURL();

    // Create URLRequest (GET)
        var request = new URLRequest(url);

    // Get Default URLSession
        var session = URLSession.getShared();

    // Create a URLSessionTask (network task) for the request
        var task = session.dataTask(request, (data, response, error) -> {

            // Handle general errors
            if (error != null) {
                // Network error
                return;
            }

            // Verify that the response is an HTTPURLResponse object
            if (response instanceof HTTPURLResponse) {
                var httpResponse = (HTTPURLResponse) response;

                // Check the status code
                if (httpResponse.getStatusCode() == 200) {
                    // Process the received data
                    if (data != null) {
                        Log.d("data", data.toText());
                    }
                } else {
                    // Status code is not equals to 200

                }
            }
        });

      // Start the task
        task.resume();
    }


}