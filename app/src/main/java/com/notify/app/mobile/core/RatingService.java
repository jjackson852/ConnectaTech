package com.notify.app.mobile.core;

import com.notify.app.mobile.bootstrapOrigin.core.Constants;

import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Interface for defining the news service to communicate with Parse.com
 */
public interface RatingService {

    @GET(Constants.Http.URL_RATING_FRAG)
    RatingWrapper getRating(@Query("where") String constraint, @Query("order") String orderConstraint);

}
