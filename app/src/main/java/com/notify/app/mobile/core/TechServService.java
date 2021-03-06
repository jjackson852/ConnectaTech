package com.notify.app.mobile.core;

import com.notify.app.mobile.bootstrapOrigin.core.Constants;

import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Interface for defining the news service to communicate with Parse.com
 */
public interface TechServService {

    @GET(Constants.Http.URL_TECHSERVICE_FRAG)
    TechServiceWrapper getTechService(@Query("where") String constraint);

}
