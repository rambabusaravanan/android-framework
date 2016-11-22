package com.rambabusaravanan.android.framework.network.base;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.rambabusaravanan.android.framework.network.RequestMethod;
import com.rambabusaravanan.android.framework.network.ResponseListener;
import com.rambabusaravanan.android.framework.network.core.ApiRequest;
import com.rambabusaravanan.android.framework.network.core.Network;
import com.rambabusaravanan.android.framework.utils.Utils;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Andro Babu
 */
public abstract class BaseRequest<T extends BaseResponse> {
    private static final char SEPARATOR = '/';
    protected Context context;
    protected Map<String, String> postParams;
    protected Map<String, String> getParams;
    protected String baseUrl;
    protected RetryPolicy retryPolicy;
    private ResponseListener defaultListener;
    private RequestMethod method;
    private Class<T> clazz;
    private StringBuilder pathParam;
    private String requestBody;
    private MultipartEntityBuilder entityBuilder;
    private boolean noProgress;
    private ProgressDialog progress;
    private CharSequence labelLoading = "Loading ..";
    private HashMap<String, String> headers;
    private Boolean shouldCache;

    public BaseRequest(Context context) {
        this.context = context;
        this.method = RequestMethod.GET;
        this.pathParam = new StringBuilder();
        this.postParams = new HashMap<>();
        this.getParams = new HashMap<>();
        this.clazz = getResponseClass();
    }

    public BaseRequest setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public BaseRequest setShouldCache(Boolean shouldCache) {
        this.shouldCache = shouldCache;
        return this;
    }

    public BaseRequest setDefaultResponder(ResponseListener listener) {
        this.defaultListener = listener;
        return this;
    }

    public BaseRequest withoutProgress() {
        noProgress = true;
        return this;
    }

    public BaseRequest withProgress(boolean showProgress) {
        noProgress = !showProgress;
        return this;
    }

    public BaseRequest setProgressMessage(CharSequence message) {
        labelLoading = message;
        return this;
    }

    private String getQueryString() {
        StringBuilder sb = new StringBuilder();
        sb.append('?');
        for (Map.Entry<?, ?> entry : getParams.entrySet()) {
            if (sb.length() > 0)
                sb.append('&');
            try {
                sb.append(String.format("%s=%s", URLEncoder.encode(entry.getKey().toString(), "UTF-8"), URLEncoder.encode(entry.getValue().toString(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    protected abstract String getPath();

    // EITHER getResponseClass should be Override OR setResponseClass should be called
    protected Class<T> getResponseClass() {
        return null;
    }

    // EITHER getResponseClass should be Override OR setResponseClass should be called
    protected void setResponseClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    public BaseRequest addPostParam(String key, Object value) {
        if (key != null && value != null)
            this.postParams.put(key, value.toString());
        return this;
    }

    public BaseRequest addGetParam(String key, Object value) {
        if (key != null && value != null)
            this.getParams.put(key, value.toString());
        return this;
    }

    public BaseRequest appendPathParam(String... q) {
        for (String s : q) {
            pathParam.append(SEPARATOR);
            pathParam.append(s);
        }
        return this;
    }

    public BaseRequest setRequestBody(Object requestBody) {
        this.requestBody = requestBody.toString();
        return this;
    }

    public BaseRequest addEntity(String key, Object value) {
        if (entityBuilder == null) {
            entityBuilder = MultipartEntityBuilder.create();
        }

        if (value instanceof File) {
            entityBuilder.addPart(key, new FileBody((File) value));
        } else if (value instanceof String) {
            entityBuilder.addTextBody(key, value.toString());
        }
        return this;
    }

    protected String getUrl() {
        StringBuilder url = new StringBuilder(baseUrl);
        url.append(getPath());
        if (pathParam != null)
            url.append(pathParam);
        url.append(getQueryString());
        return url.toString();
    }

    protected RequestMethod getMethod() {
        return this.method;
    }


    public void send(final ResponseListener listener) {
        if (!noProgress)
            progress = ProgressDialog.show(context, "", labelLoading, true, false);

        Utils.log(getMethod(), ' ', this.getClass().getSimpleName(), " >>> ", getUrl(), postParams);
        ApiRequest request = new ApiRequest<T>(context, getMethod().getCode(), getUrl(), postParams, requestBody, entityBuilder, clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                Utils.log(getMethod(), ' ', BaseRequest.this.getClass().getSimpleName(), " <<< S <<< ", response);
                if (progress != null && progress.isShowing())
                    progress.dismiss();
                if (defaultListener != null)
                    defaultListener.onSuccess(response);
                if (listener != null)
                    listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                T response = null;
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        String json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                        response = Response.success(new Gson().fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(error.networkResponse)).result;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Utils.log(getMethod(), ' ', BaseRequest.this.getClass().getSimpleName(), " <<< E <<< ", error);
                Utils.log(getMethod(), ' ', BaseRequest.this.getClass().getSimpleName(), " <<< E <<< ", response);
                if (progress != null && progress.isShowing())
                    progress.dismiss();
                if (defaultListener != null)
                    defaultListener.onError(response, error);
                if (listener != null)
                    listener.onError(response, error);
            }
        });
        if (retryPolicy != null)
            request.setRetryPolicy(retryPolicy);
        if (headers != null)
            request.setHeaders(headers);
        if (shouldCache != null)
            request.setShouldCache(shouldCache);
        Network.getInstance(context).addToRequestQueue(request);
    }

    public void send() {
        send(null);
    }

    public void get() {
        get(null);
    }

    public void get(ResponseListener listener) {
        this.method = RequestMethod.GET;
        send(listener);
    }

    public void post() {
        post(null);
    }

    public void post(ResponseListener listener) {
        this.method = RequestMethod.POST;
        send(listener);
    }

    public void delete() {
        delete(null);
    }

    public void delete(ResponseListener listener) {
        this.method = RequestMethod.DELETE;
        send(listener);
    }

    public void put() {
        put(null);
    }

    public void put(ResponseListener listener) {
        this.method = RequestMethod.PUT;
        send(listener);
    }

    public void patch() {
        patch(null);
    }

    public void patch(ResponseListener listener) {
        this.method = RequestMethod.PATCH;
        send(listener);
    }


}
