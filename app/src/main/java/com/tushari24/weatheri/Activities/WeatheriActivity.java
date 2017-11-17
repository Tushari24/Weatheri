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

public class WeatheriActivity extends Activity {

    Context context;
    private TextView exText;
    String apiObject;
    GlobalFunctions global = new GlobalFunctions(WeatheriActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatheri);
        initControls();
        onClicklisteners();
        SetUi();

    }

    private void initControls() {

        exText = (TextView) findViewById(R.id.exText);

    }

    private  void SetUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT); //for Inherit the background color
        }
    }

    private void onClicklisteners() {

    }





    @Override
    protected void onStart() {
        super.onStart();
        apiObject = "find?q=pune&units=metric&type=accurate&APPID=9716de31a746bae9af222745c5b7ad7f";
        global.callAPI(ServiceUrl.BASE_URL,apiObject);

    }
}
