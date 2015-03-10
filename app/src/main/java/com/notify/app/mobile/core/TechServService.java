package com.notify.app.mobile.core;

import retrofit.http.GET;


/**
 * Interface for defining the news service to communicate with Parse.com
 */
public interface TechServService {

    @GET(Constants.Http.URL_TECHSERVICE_FRAG)
    TechServiceWrapper getTechService();

}
