package com.example.movies.data.remote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cafsoft.foundation.URLComponents;
import cafsoft.foundation.URLQueryItem;
import cafsoft.foundation.URLSession;

public class OMDbAPI extends RemoteDataSource {
    private final String BASE_URL = "https://www.omdbapi.com";
    private final String API_KEY = "b16f245";

    public void requestMovieInfo(String title, TextCompletionHandler textCompletionHandler, ErrorCodeCompletionHandler errorCodeCompletionHandler) {
        var comp = new URLComponents(BASE_URL);
        comp.setQueryItems(new URLQueryItem[]{
                new URLQueryItem("apiKey", API_KEY),
                new URLQueryItem("t", title)
        });

        //Generate the URL from the components
        var url = comp.getURL();

        //Get Default URLsession
        var session = URLSession.getShared();


        //create a network task for the GET request
        var task = createDataTask(url, session, (errorCode, data) -> {
            if (errorCode == 200) {
                var text = (String) null;
                if (data != null)
                    text = data.toText();
                textCompletionHandler.run(text);

            } else {
                errorCodeCompletionHandler.run(errorCode);
            }
        });

        task.resume();
    }

    public void requestImage(String stringURL, ImageCompletionHandler imageCompletionHandler, ErrorCodeCompletionHandler errorCodeCompletionHandler) {

        var components = new URLComponents(stringURL);
        components.setScheme("https");

        //Generate the URL from the components
        var url = components.getURL();

        //Get default URLsession
        var session = URLSession.getShared();

        //create a network task for the GET request
        var task = createDataTask(url, session, (errorCode, data) -> {
            if (errorCode == 200) {
                var bitmap = (Bitmap) null;
                if (data != null)
                    bitmap = BitmapFactory.decodeByteArray(data.toBytes(),0, data.length());
                imageCompletionHandler.run(bitmap);
            } else {
                errorCodeCompletionHandler.run(errorCode);
            }
        });
        //start the task
        task.resume();
    }
}
