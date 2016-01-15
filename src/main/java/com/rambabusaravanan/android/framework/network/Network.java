package com.rambabusaravanan.android.framework.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Andro Babu on Oct 01, 2015.
 */
public class Network {

    private static Network instance;
    private final RequestQueue queue;

    public static void init(Context context) {
        instance = new Network(context);
    }

    public Network(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static Network getInstance() {
        return instance;
    }

    public void request(Request request) {
        queue.add(request);
    }

}
