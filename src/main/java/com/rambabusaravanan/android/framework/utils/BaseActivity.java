package com.rambabusaravanan.android.framework.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rambabusaravanan.android.framework.network.Network;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Andro Babu on Oct 01, 2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressDialog networkProgress;
    protected Context app_context, context;

    // ANDROID ACTIVITY

    private OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_context = getApplicationContext();
        context = this;

        networkProgress = new ProgressDialog(context);
        networkProgress.setMessage("Loading ..");
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        int fragCount = getSupportFragmentManager().getBackStackEntryCount();
        boolean isHandled = false;

        if (onBackPressedListener != null) {
            Utils.log("BaseActivity : onBackPressed (calling listener)");
            isHandled = onBackPressedListener.listenBackPressed();
        }
        if (!isHandled) {
            if (fragCount != 0) {
                Utils.log("BaseActivity : onBackPressed (popBackStack)");
                getSupportFragmentManager().popBackStack();
            }
            else {
                Utils.log("BaseActivity : onBackPressed (default)");
                onBack();
            }
        }
    }

    public void onBack() {
        Utils.log("BaseActivity : onBack");
        super.onBackPressed();
    }

    // ANDROID UTILS

    public void toast(String message) {
        Toast.makeText(app_context, message, Toast.LENGTH_SHORT).show();
    }

    public int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    // NETWORK

    public String encodeUrl(String query) {
        try {
            return URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void sendRequest(final String url) {
        sendRequest(url, null);
    }

    public void sendRequest(final String url, JSONObject jsonObject) {
        networkProgress.show();

        Utils.log("Request => " + url + "\nparams:" + jsonObject);
        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                networkProgress.dismiss();
                Utils.log("Response <= " + url + "\ndata:" + response);
                handleResponse(url, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkProgress.dismiss();
                Utils.log("Response <= " + url + "\nerror:" + error);
                handleError(url, error);
            }
        });
        addRequest(request);
    }

    private void addRequest(Request request) {
        Network.getInstance().request(request);
    }

    protected void handleResponse(String url, JSONObject response) {
    }

    protected void handleError(String url, Exception exception) {
    }
}
