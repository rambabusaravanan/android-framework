package com.rambabusaravanan.android.framework.network;

import com.android.volley.Request;

/**
 * Created by Andro Babu
 */
public enum RequestMethod {
    GET(Request.Method.GET),
    POST(Request.Method.POST),
    DELETE(Request.Method.DELETE),
    PUT(Request.Method.PUT),
    PATCH(Request.Method.PATCH);

    private int code;

    RequestMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
