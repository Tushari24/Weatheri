package com.tushari24.weatheri.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tushari24.weatheri.Activities.WeatheriActivity;
import com.tushari24.weatheri.Net.CallbackListener;
import com.tushari24.weatheri.Net.WeatheriApiService;

/**
 * Created by Tushari24 on 16-11-2017.
 */

public class GlobalFunctions implements CallbackListener {

    private Context context;
    private ProgressDialog progressDialog;

    public GlobalFunctions(Context context) {
        this.context = context;
    }



    /**
     * Check whether Internet connection is available.
     */
    public boolean isConnectingToInternet() {
        boolean returnvalue = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
               // Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                returnvalue = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
               // Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                returnvalue = true;
            }
        } else {
            // not connected to the internet
            returnvalue = false;
        }

        return returnvalue;
    }

    /**
     * Show alert dialog for current activity.
     */
    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);

        //alertDialog.setIcon(R.drawable.error);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(Constants.ALERT_SUBMIT, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void showWebView(WebView mywebview, String myUrl) {
        // TODO Auto-generated method stub
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        mywebview.loadUrl(myUrl);
        progressDialog = ProgressDialog.show(context,"" , "Loading....");//where context = YourActivity.this;
        mywebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap bitmap) {

                super.onPageStarted(view, url, bitmap);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
                Toast.makeText(context, "Something Went Wrong"+errorCode +" :"+description, Toast.LENGTH_SHORT).show();
            }

        });

    }

    /** swipe down to Refresh*/
    public void SwipeDownRefresh(final SwipeRefreshLayout swipe, final WebView mywebview) {
        // TODO Auto-generated method stub
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipe.setRefreshing(true);
                ( new Handler()).postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        swipe.setRefreshing(false);
                        String weburl = mywebview.getUrl();
                        showWebView(mywebview, weburl); // calling Webview
                    }
                }, 1000);
            }
        });
    }




    public void callAPI(String url, String jsonObject) {
        if (isConnectingToInternet()) {
            WeatheriApiService apiService = new WeatheriApiService(context, GlobalFunctions.this, url, jsonObject);
            apiService.execute();
        }else {
            showAlertDialog(context, Constants.ALERT_TITLE, ErrorMessages.NETWORKAVAILABILITY,
                    false);
        }
    }

    @Override
    public void weatherResponceTaskCompleted(String responseString) {
        // TODO Auto-generated method stub
        if (context.getClass().equals(WeatheriActivity.class)) {
            if(responseString != null)
            {
                WeatheriActivity activity = (WeatheriActivity) context;
                activity.updateWeatherUi(responseString);
            }
        }

    }


}
