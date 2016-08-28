package com.rambabusaravanan.android.framework.network.core;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.rambabusaravanan.android.framework.utils.BasePreference;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Andro Babu
 */
public class ApiRequest<T> extends Request<T> {

    private static final String SET_COOKIE = "Set-Cookie";
    private static final String COOKIE = "Cookie";

    private final Map<String, String> params;
    private final String requestBody;
    private final Class<T> clazz;
    private final Context context;
    private HttpEntity multipartEntity;
    private Response.Listener<T> listener;
    private HashMap<String, String> headers;

    public ApiRequest(Context context, int method, String url, Map<String, String> params, String requestBody, MultipartEntityBuilder entityBuilder, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener eListener) {
        super(method, url, eListener);
        this.context = context;
        this.params = params;
        this.requestBody = requestBody;
        if (entityBuilder != null)
            this.multipartEntity = entityBuilder.build();
        this.clazz = clazz;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        if (this.headers != null)
            headers.putAll(this.headers);
        String cookie = BasePreference.getInstance(context).get(SET_COOKIE);
        if (cookie != null) {
            headers.put(COOKIE, cookie);
        }
        return headers;
    }

    public ApiRequest setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

//    @Override
//    protected void onFinish() {
//        super.onFinish();
//        this.listener = null;
//    }

    @Override
    public String getBodyContentType() {
        if (multipartEntity != null) {
            return multipartEntity.getContentType().getValue();
        } else if (requestBody != null)
            return ContentType.APPLICATION_JSON.toString();
        else
            return super.getBodyContentType();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (multipartEntity != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                multipartEntity.writeTo(bos);
            } catch (IOException e) {
                VolleyLog.wtf("IOException writing MultiPart HttpEntity to ByteArrayOutputStream @ " + getUrl());
                e.printStackTrace();
            }
            return bos.toByteArray();
        } else if (requestBody != null) {
            try {
                return requestBody.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                VolleyLog.wtf("Unsupported Encoding while getBody() trying to get the bytes using 'utf-8' @ " + getUrl() + " for %s", requestBody);
                e.printStackTrace();
                return null;
            }
        } else {
            return super.getBody();
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Map headers = response.headers;
        if (headers != null && headers.containsKey(SET_COOKIE)) {
            BasePreference.getInstance(context).put(SET_COOKIE, (String) headers.get(SET_COOKIE));
        }
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new Gson().fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            VolleyLog.wtf("Unsupported Encoding while parseNetworkResponse() trying to parse the response bytes using '%s' @ " + getUrl() + " for %s",HttpHeaderParser.parseCharset(response.headers), requestBody);
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
