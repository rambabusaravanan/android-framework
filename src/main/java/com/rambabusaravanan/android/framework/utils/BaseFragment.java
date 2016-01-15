package com.rambabusaravanan.android.framework.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public abstract class BaseFragment extends Fragment {

    protected ProgressDialog networkProgress;
    protected Context app_context, context;

    // ANDROID FRAGMENT


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        app_context = getActivity().getApplicationContext();
        context = getActivity();
        ((BaseActivity)context).setOnBackPressedListener(onBackPressedListener);

        networkProgress = new ProgressDialog(context);
        networkProgress.setMessage("Loading ..");
        return onCreateBaseView(inflater, container, savedInstanceState);
    }

    public View onCreateBaseView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    OnBackPressedListener onBackPressedListener = new OnBackPressedListener() {
        @Override
        public boolean listenBackPressed() {
            Utils.log("BaseFragment : listenBackPressed");
            return onBack();
        }
    };

    public boolean onBack() {
        Utils.log("BaseFragment : onBack - return false");
        return false;
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
        } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
        return "";
    }

    public void sendRequest(final String url) {
        sendRequest(url, null);
    }

    public void sendRequest(final String url, JSONObject jsonObject) {
        networkProgress.show();

        Utils.log("Request => "+url + "\nparams:" + jsonObject);
        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                networkProgress.dismiss();
                Utils.log("Response <= "+url + "\ndata:" + response);
                handleResponse(url, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkProgress.dismiss();
                Utils.log("Response <= "+url + "\nerror:" + error);
                handleError(url, error);
            }
        });
        addRequest(request);
    }

    private void addRequest(Request request) {
        Network.getInstance().request(request);
    }

    protected void handleResponse(String url, JSONObject response) { }
    protected void handleError(String url, Exception exception) { }
}
