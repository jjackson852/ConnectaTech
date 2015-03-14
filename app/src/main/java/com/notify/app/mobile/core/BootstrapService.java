
package com.notify.app.mobile.core;

import com.parse.ParseUser;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Bootstrap API service
 */
public class BootstrapService {

    private RestAdapter restAdapter;

    private String curUserServConstraint = "{\"title\":\"test1\"}";

   // private String curUserServConstraint = "{\"createdBy\":"+ String.valueOf(ParseUser.getCurrentUser()) +"}";

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

    private CheckInService getCheckInService() {
        return getRestAdapter().create(CheckInService.class);
    }

    private ExperimentService getExperimentService(){
        return getRestAdapter().create(ExperimentService.class);
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
     * Get all bootstrap News that exists on Parse.com
     */
    public List<TechService> getTechService() {
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

    public List<Experiment> getExperiment(){
        return getExperimentService().getExperimentService().getResults();
    }

    public User authenticate(String email, String password) {
        return getUserService().authenticate(email, password);
    }
}