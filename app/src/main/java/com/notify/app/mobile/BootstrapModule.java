package com.notify.app.mobile;

import android.accounts.AccountManager;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.notify.app.mobile.authenticator.ApiKeyProvider;
import com.notify.app.mobile.authenticator.BootstrapAuthenticatorActivity;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.authenticator.TimerTestFragment;
import com.notify.app.mobile.core.BootstrapService;
import com.notify.app.mobile.core.Constants;
import com.notify.app.mobile.core.PostFromAnyThreadBus;
import com.notify.app.mobile.core.RestAdapterRequestInterceptor;
import com.notify.app.mobile.core.RestErrorHandler;
import com.notify.app.mobile.core.TechService;
import com.notify.app.mobile.core.TimerService;
import com.notify.app.mobile.core.UserAgentProvider;
import com.notify.app.mobile.ui.AddServiceActivity;
import com.notify.app.mobile.ui.BootstrapTimerActivity;
import com.notify.app.mobile.ui.CheckInsListFragment;
import com.notify.app.mobile.ui.ExampleActivity;
import com.notify.app.mobile.ui.ExampleListFragment;
import com.notify.app.mobile.ui.MainActivity;
import com.notify.app.mobile.ui.MealListActivity;
import com.notify.app.mobile.ui.NavigationDrawerFragment;
import com.notify.app.mobile.ui.NewsActivity;
import com.notify.app.mobile.ui.NewsListFragment;
import com.notify.app.mobile.ui.ProviderProfileFragment;
import com.notify.app.mobile.ui.RatingActivity;
import com.notify.app.mobile.ui.TechServiceActivity;
import com.notify.app.mobile.ui.TechServiceListFragment;
import com.notify.app.mobile.ui.TestActivity;
import com.notify.app.mobile.ui.TrialListFragment;
import com.notify.app.mobile.ui.UserActivity;
import com.notify.app.mobile.ui.UserListFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module(
        complete = false,

        injects = {
                BootstrapApplication.class,
                BootstrapAuthenticatorActivity.class,
                MainActivity.class,
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                ExampleActivity.class,
                ExampleListFragment.class,
                RatingActivity.class,
                MealListActivity.class,
                NavigationDrawerFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                TestActivity.class,
                TrialListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                TimerService.class,
                TimerTestFragment.class,
                ProviderProfileFragment.class,
                TechServiceActivity.class,
                TechServiceListFragment.class,
                AddServiceActivity.class
        }
)
public class BootstrapModule {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new PostFromAnyThreadBus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

    @Provides
    BootstrapService provideBootstrapService(RestAdapter restAdapter) {
        return new BootstrapService(restAdapter);
    }

    @Provides
    BootstrapServiceProvider provideBootstrapServiceProvider(RestAdapter restAdapter, ApiKeyProvider apiKeyProvider) {
        return new BootstrapServiceProvider(restAdapter, apiKeyProvider);
    }

    @Provides
    ApiKeyProvider provideApiKeyProvider(AccountManager accountManager) {
        return new ApiKeyProvider(accountManager);
    }

    @Provides
    Gson provideGson() {
        /**
         * GSON instance to use for all request  with date format set up for proper parsing.
         * <p/>
         * You can also configure GSON with different naming policies for your API.
         * Maybe your API is Rails API and all json values are lower case with an underscore,
         * like this "first_name" instead of "firstName".
         * You can configure GSON as such below.
         * <p/>
         *
         * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
         *         .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
         */
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    @Provides
    RestErrorHandler provideRestErrorHandler(Bus bus) {
        return new RestErrorHandler(bus);
    }

    @Provides
    RestAdapterRequestInterceptor provideRestAdapterRequestInterceptor(UserAgentProvider userAgentProvider) {
        return new RestAdapterRequestInterceptor(userAgentProvider);
    }

    @Provides
    RestAdapter provideRestAdapter(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint(Constants.Http.URL_BASE)
                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

}
