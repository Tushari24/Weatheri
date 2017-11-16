package com.tushari24.weatheri.Net;

import android.content.Context;
import android.os.AsyncTask;

import com.tushari24.weatheri.Utils.GlobalFunctions;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tushari24 on 16-11-2017.
 */

public class WeatheriApiService  extends AsyncTask<String, Void, String>  {

    Context context;
    String url;
    String finalUrl, functionType;
    String jsonObject;
    String responseString = null;
    CallbackListener listner;
    GlobalFunctions global;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String

    public WeatheriApiService (Context context, CallbackListener listner, String url, String jsonObject) {
        this.context = context;
        this.url = url;
        this.finalUrl = url;
        this.listner = listner;
        this.jsonObject =jsonObject;
        global = new GlobalFunctions(context);
        System.out.println("\nUrl : " + url + "   \n\nRequest Object :\n" + jsonObject.toString());
    }


    @Override
    protected String doInBackground(String...params) {

        RequestBody body = RequestBody.create(JSON,jsonObject.toString() );

        Request.Builder builder = new Request.Builder();
        builder.url(url);
       // builder.post(body);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            responseString = response.body().string();
            return responseString ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String responseString) {
        super.onPostExecute(responseString);
        System.out.println(responseString);
    }
}
