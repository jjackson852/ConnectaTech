package com.notify.app.mobile.core;

import com.notify.app.mobile.bootstrapOrigin.core.Constants;

import retrofit.http.GET;


/**
 * Interface for defining the news service to communicate with Parse.com
 */
public interface ExampleService {

    @GET(Constants.Http.EXAMPLE_NEWS_FRAG)
    ExampleWrapper getExample();

}
