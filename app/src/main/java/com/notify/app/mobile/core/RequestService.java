package com.notify.app.mobile.core;

import retrofit.http.GET;


/**
 * Interface for defining the news service to communicate with Parse.com
 */
public interface RequestService {

    @GET(Constants.Http.URL_REQUEST_FRAG)
    RequestWrapper getRequest();

}
