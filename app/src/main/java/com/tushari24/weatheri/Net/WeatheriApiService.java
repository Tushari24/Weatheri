package com.tushari24.weatheri.Net;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.tushari24.weatheri.Utils.GlobalFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

    public WeatheriApiService (Context context, CallbackListener listner, String url, String jsonObject) {
        this.context = context;
        this.url = url;
        this.finalUrl = url + jsonObject;
        this.listner = listner;
        this.jsonObject =jsonObject;
        global = new GlobalFunctions(context);
        System.out.println("\nUrl : " + url + "   \n\nRequest Object :\n" + jsonObject.toString());
    }


    @Override
    protected String doInBackground(String...params) {

        RequestBody body = RequestBody.create(JSON,jsonObject.toString() );

        Request.Builder builder = new Request.Builder();
        builder.url(finalUrl);
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
        String responceCode,message,count,listArray,mainArray,windAraay,sysArray,rain,snow,cloudsArray,weatherArray;
        JSONObject idObj=null,nameObj=null,dtObj =null,rainObj=null,snowObj=null;
        JSONArray mainJsonArray=null;
        if (!responseString.isEmpty()) {
            try {
                JSONObject weatherJasonObject = new JSONObject(responseString);
                message = weatherJasonObject.getString("message");
                responceCode = weatherJasonObject.getString("cod");
                count = weatherJasonObject.getString("count");
                JSONArray listArrayObj = weatherJasonObject.getJSONArray("list");
                for (int i=0; i<=listArrayObj.length();i++) {
                     idObj = listArrayObj.getJSONObject(i);
                     nameObj = listArrayObj.getJSONObject(i);
                    JSONArray coord = listArrayObj.getJSONArray(i);
                     mainJsonArray = listArrayObj.getJSONArray(i);
                    dtObj = listArrayObj.getJSONObject(i);
                    JSONArray windJsonArray = listArrayObj.getJSONArray(i);
                    JSONArray sysJsonArray = listArrayObj.getJSONArray(i);
                    rainObj = listArrayObj.getJSONObject(i);
                    snowObj = listArrayObj.getJSONObject(i);
                    JSONArray cloudsJsonArray = listArrayObj.getJSONArray(i);
                    JSONArray weatherJsonArray = listArrayObj.getJSONArray(i);
                }
                String id = idObj.getString("id");
                String name = nameObj.getString("name");
                String dt = dtObj.getString("name");
                rain = rainObj.getString("name");
                snow = snowObj.getString("name");





            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else
        {
            System.out.println("NULL Responce");
        }
    }
}
