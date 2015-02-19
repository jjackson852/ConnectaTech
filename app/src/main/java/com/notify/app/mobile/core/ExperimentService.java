package com.notify.app.mobile.core;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Martinez on 2/18/2015.
 */
public interface ExperimentService {

    @GET(Constants.Http.URL_USERS_FRAG)
    ExperimentWrapper getExperiment();

    /**
     * The {@link retrofit.http.Query} values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The users email
     * @param password The users password
     * @return A login response.
     */
    @GET(Constants.Http.URL_AUTH_FRAG)
    Experiment authenticate(@Query(Constants.Http.PARAM_USERNAME) String email,
                      @Query(Constants.Http.PARAM_PASSWORD) String password);
}
