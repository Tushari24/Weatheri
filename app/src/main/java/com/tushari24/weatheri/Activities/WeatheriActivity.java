package com.tushari24.weatheri.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.tushari24.weatheri.Net.ServiceUrl;
import com.tushari24.weatheri.R;
import com.tushari24.weatheri.Utils.GlobalFunctions;

public class WeatheriActivity extends Activity {

    private TextView exText;
    String apiObject;
    GlobalFunctions global = new GlobalFunctions(WeatheriActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatheri);
        initControls();
        onClicklisteners();

    }

    private void initControls() {

        exText = (TextView) findViewById(R.id.exText);

    }

    private void onClicklisteners() {

    }





    @Override
    protected void onStart() {
        super.onStart();
        apiObject = "find?q=pune&units=metric&type=accurate&APPID=5054524f6d447b484ec235de900a6b73";
        global.callAPI(ServiceUrl.BASE_URL,apiObject);

    }
}
