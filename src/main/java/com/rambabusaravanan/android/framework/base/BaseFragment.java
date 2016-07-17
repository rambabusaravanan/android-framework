package com.rambabusaravanan.android.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rambabusaravanan.android.framework.R;
import com.rambabusaravanan.android.framework.utils.Utils;

/**
 * Created by Andro Babu on Oct 01, 2015.
 */
public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //    protected ProgressDialog networkProgress;
    protected Context appContext, context;
    protected View rootView;
    OnBackPressedListener onBackPressedListener = new OnBackPressedListener() {
        @Override
        public boolean listenBackPressed() {
            Utils.log("BaseFragment : listenBackPressed");
            return onBack();
        }
    };

    // ANDROID FRAGMENT
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appContext = getActivity().getApplicationContext();
        context = getActivity();
        ((BaseActivity)context).setOnBackPressedListener(onBackPressedListener);
        rootView = inflater.inflate(getLayoutId(), container, false);
//        networkProgress = new ProgressDialog(context);
//        networkProgress.setMessage("Loading ..");
        return rootView;
    }

    // Handle Back Press

    protected abstract int getLayoutId();

    public boolean onBack() {
        Utils.log("BaseFragment : onBack - return false");
        return false;
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
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(resId);
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
*/
}
