
package com.notify.app.mobile.bootstrapOrigin.core;

import com.notify.app.mobile.core.Example;
import com.notify.app.mobile.core.ExampleService;
import com.notify.app.mobile.core.Request;
import com.notify.app.mobile.core.RequestService;
import com.notify.app.mobile.core.TechServService;
import com.notify.app.mobile.core.TechService;
import com.parse.ParseUser;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Bootstrap API service
 */
public class BootstrapService {

    private static String curUserServConstraint;

//    private String curUserServConstraint = "{\"title\":\"test1\"}";

//    private String curUserServConstraint = "{__type: \"Pointer\",className: \"_User\",objectId: "+ ParseUser.getCurrentUser().getObjectId() +"}";

    // private String curUserServConstraint = "{\"createdBy\":"+ String.valueOf(ParseUser.getCurrentUser()) +"}";

    //    private String curUserServConstraint = "{\"createdBy\":{__type:\"Pointer\",className:\"_User\",objectId:\"bCIxz54gFI\"}}";
    private static String curUserReqConstraint;
    private RestAdapter restAdapter;

    /**
     * Create bootstrap service
     * Default CTOR
     */
    public BootstrapService() {
    }

    /**
     * Create bootstrap service
     *
     * @param restAdapter The RestAdapter that allows HTTP Communication.
     */


    public BootstrapService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public static void setServConstraint(String constraint) {

        curUserServConstraint = constraint;

    }

    public static void setReqConstraint(String constraint) {

        curUserReqConstraint = constraint;

    }

    private UserService getUserService() {
        return getRestAdapter().create(UserService.class);
    }

    private NewsService getNewsService() {
        return getRestAdapter().create(NewsService.class);
    }

    private TechServService getTechServService() {
        return getRestAdapter().create(TechServService.class);
    }

    private ExampleService getExampleService() {
        return getRestAdapter().create(ExampleService.class);
    }

    private RequestService getRequestService() {
        return getRestAdapter().create(RequestService.class);
    }

    private CheckInService getCheckInService() {
        return getRestAdapter().create(CheckInService.class);
    }

    private RestAdapter getRestAdapter() {
        return restAdapter;
    }

    /**
     * Get all bootstrap News that exists on Parse.com
     */
    public List<News> getNews() {
        return getNewsService().getNews().getResults();
    }

    /**
     * Get Tech Services which meet the constraint applied.
     */
    public List<TechService> getTechService() {

        if (ParseUser.getCurrentUser().getBoolean("isProvider") == true) {
            setServConstraint("{\"createdBy\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"" + ParseUser.getCurrentUser().getObjectId() + "\"}}");
        }

        return getTechServService().getTechService(curUserServConstraint).getResults();

    }

    /**
     * Get all bootstrap Users that exist on Parse.com
     */
    public List<User> getUsers() {
        return getUserService().getUsers().getResults();
    }

    /**
     * Get all bootstrap Checkins that exists on Parse.com
     */
    public List<CheckIn> getCheckIns() {
        return getCheckInService().getCheckIns().getResults();
    }

    /**
     * Get all bootstrap Home Data that exists on Parse.com
     */
    public List<Example> getExample() {
        return getExampleService().getExample().getResults();
    }

    /**
     * Get all bootstrap Home Data that exists on Parse.com
     */
    public List<Request> getRequest() {

        if (ParseUser.getCurrentUser().getBoolean("isProvider") == true) {
            setReqConstraint("{\"provider\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"" + ParseUser.getCurrentUser().getObjectId() + "\"}}");
        }

        return getRequestService().getRequest(curUserReqConstraint).getResults();
    }

    public User authenticate(String email, String password) {
        return getUserService().authenticate(email, password);
    }
}