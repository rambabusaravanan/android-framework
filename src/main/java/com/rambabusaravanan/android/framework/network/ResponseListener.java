package com.rambabusaravanan.android.framework.network;

import com.rambabusaravanan.android.framework.network.base.BaseResponse;

/**
 * Created by Andro Babu
 */
public interface ResponseListener<T extends BaseResponse> {

    void onSuccess(T response);

    void onError(T response, Exception e);
}
