package com.example.movies.data.remote;

import android.graphics.Bitmap;

import java.net.URL;

import cafsoft.foundation.Data;
import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLSession;
import cafsoft.foundation.URLSessionDataTask;

public class RemoteDataSource {

    public URLSessionDataTask createDataTask(URL url, URLSession session, DataCompletionHandler dataCompletionHandler){

        //create a network task for the GET request
        var task = session.dataTask(url, (data, response, error) ->{
            if(error != null){
                dataCompletionHandler.run(-1, null);
                return;
            }
            if(response instanceof HTTPURLResponse){
                var httpResposen = (HTTPURLResponse) response;
                dataCompletionHandler.run(httpResposen.getStatusCode(), data);
            }
        });
        return  task;


    }

    public interface DataCompletionHandler{
        void run (int errorCode, Data data);
    }
    public interface TextCompletionHandler{
        void run (String text);
    }

    public interface ImageCompletionHandler{
        void run (int errorCode, Bitmap bitmap);
    }

    public interface ErrorCodeCompletionHandler{
        void run (int errorCode);
    }
}
