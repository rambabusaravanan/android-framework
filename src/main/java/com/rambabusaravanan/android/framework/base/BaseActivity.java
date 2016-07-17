package com.rambabusaravanan.android.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.rambabusaravanan.android.framework.R;
import com.rambabusaravanan.android.framework.utils.Utils;

/**
 * Created by Andro Babu on Oct 01, 2015.
 */
public abstract class BaseActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    //    protected ProgressDialog networkProgress;
    protected Context appContext, context;
    private SwipeRefreshLayout swipeRefreshLayout;

    // ANDROID ACTIVITY

    private OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = getApplicationContext();
        context = this;

//        networkProgress = new ProgressDialog(context);
//        networkProgress.setMessage("Loading ..");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Handle Back Press

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
            } else {
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
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int stringResId) {
        Toast.makeText(appContext, getString(stringResId), Toast.LENGTH_SHORT).show();
    }

    // PULL TO REFRESH

    protected void setSwipeRefreshLayout(int resId) {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(resId);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeResources(R.color.holo_red_light,
                    R.color.holo_blue_bright,
                    R.color.holo_orange_light,
                    R.color.holo_green_light);
        }

    }

    protected void stopRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    protected boolean getRefreshingStatus() {
        return swipeRefreshLayout.isRefreshing();
    }

    /*
     * This should be overridden
     */
    @Override
    public void onRefresh() {
        stopRefresh();
    }




    // NETWORK

/*
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
    */
}
