package com.rambabusaravanan.android.framework.network.core;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Andro Babu on Oct 01, 2015.
 */
public class Network {

    private static Network instance;
    private final Context context;
    private RequestQueue queue;

    Network(Context context) {
        this.context = context;
        this.queue = getRequestQueue();
    }

    public static synchronized Network getInstance(Context context) {
        if (instance == null)
            instance = new Network(context);
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null)
            queue = Volley.newRequestQueue(context.getApplicationContext());
        return queue;
    }

    public void addToRequestQueue(Request request) {
        getRequestQueue().add(request);
    }
}
