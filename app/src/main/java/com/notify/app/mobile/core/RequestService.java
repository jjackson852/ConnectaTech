package com.notify.app.mobile.core;

import com.notify.app.mobile.bootstrapOrigin.core.Constants;

import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Interface for defining the news service to communicate with Parse.com
 */
public interface RequestService {

    @GET(Constants.Http.URL_REQUEST_FRAG)
    RequestWrapper getRequest(@Query("where") String constraint);

}
