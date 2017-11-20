package com.tushari24.weatheri.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tushari24.weatheri.Net.ServiceUrl;
import com.tushari24.weatheri.R;
import com.tushari24.weatheri.Utils.GlobalFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatheriActivity extends Activity {

    Context context;
    private TextView city_name ,temp_text , txt_description;
    String apiObject;
    GlobalFunctions global = new GlobalFunctions(WeatheriActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatheri);
        initControls();
        SetUi();

    }

    private void initControls() {

        city_name = (TextView) findViewById(R.id.txt_city_name);
        temp_text = (TextView) findViewById(R.id.txt_temp);
        txt_description = (TextView) findViewById(R.id.txt_description);

    }

    private void SetUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT); //for Inherit the background color
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        apiObject = "find?q=pune&units=metric&type=accurate&APPID=9716de31a746bae9af222745c5b7ad7f";
        global.callAPI(ServiceUrl.BASE_URL, apiObject);

    }

    public void updateWeatherUi(String responseString) {
        System.out.println("responseString in weatheractivity =" + responseString);
        String responceCode = null, message, count, temp = null, pressure, humidity, nameCity = null, rain, snow, description = null, main = null;
        JSONObject idObj = null, nameObj = null, dtObj = null, rainObj = null, snowObj = null;
        //  JSONArray mainJsonArray=null;
        if (!responseString.isEmpty()) {
            try {
                JSONObject weatherJasonObject = new JSONObject(responseString);
                message = weatherJasonObject.getString("message");
                responceCode = weatherJasonObject.getString("cod");
                count = weatherJasonObject.getString("count");
                JSONArray listArrayObj = weatherJasonObject.getJSONArray("list");

                System.out.println("message=" + message + "\n responceCode=" + responceCode + "\ncount=" + count +
                        "\n listArrayObj= " + listArrayObj);

                System.out.println("listArrayObj size=" + listArrayObj.length());
                JSONObject jsonobject = listArrayObj.getJSONObject(0);
                nameCity = jsonobject.getString("name");
                JSONObject mainJsonArray = jsonobject.getJSONObject("main");
                temp = mainJsonArray.getString("temp");
                humidity = mainJsonArray.getString("humidity");
                JSONArray mainJsonArrayweather = jsonobject.getJSONArray("weather");
                JSONObject objectweather = mainJsonArrayweather.getJSONObject(0);
                main = objectweather.getString("main");
                description = objectweather.getString("description");

                System.out.println("nameCity=" + nameCity + "\n temp=" + temp + "\nhumidity=" + humidity +
                        "\n main= " + main + "\n description=" + description);
            }catch (JSONException e){
                e.printStackTrace();
            }

            if(responceCode.equals("200") ){
                if (main.equals("Rain") || main.equals("Clouds")) {
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN); //set Background Color
                }
                temp_text.setText(nameCity);
                city_name.setText("Temp:"+temp + (char) 0x00B0 + "C");
                txt_description.setText(description);

            }

        }
    }
}
