

package com.notify.app.mobile.core;

/**
 * Bootstrap constants
 */
public final class Constants {
    private Constants() {}

    public static final class Auth {
        private Auth() {}

        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "com.notify.app.mobile";

        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "ConnectaTech";

        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "com.notify.app.mobile.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;

        /**
         * Continue as Guest Credentials *Needs additional security implemented.*
         */
        public static final String GUEST_USERNAME = "demo@connectatech.org";
        public static final String GUEST_PASSWORD = "abc12345";
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static final class Http {
        private Http() {}


        /**
         * Base URL for all requests
         */
        public static final String URL_BASE = "https://api.parse.com";


        public static final String URL_IMAGE_FRAG = "/1/ImageUpload";
        public static final String URL_IMAGE = URL_BASE + URL_IMAGE_FRAG;

        /**
         * Authentication URL
         */
        public static final String URL_AUTH_FRAG = "/1/login";
        public static final String URL_AUTH = URL_BASE + URL_AUTH_FRAG;

        /**
         * List Users URL
         */
        public static final String URL_USERS_FRAG =  "/1/users";
        public static final String URL_USERS = URL_BASE + URL_USERS_FRAG;


        /**
         * List News URL
         */
        public static final String URL_NEWS_FRAG = "/1/classes/News";
        public static final String URL_NEWS = URL_BASE + URL_NEWS_FRAG;

        /**
         * List News URL
         */
        public static final String EXAMPLE_NEWS_FRAG = "/1/classes/Home";
        public static final String EXAMPLE_NEWS = URL_BASE + EXAMPLE_NEWS_FRAG;

        /**
         * List TechService URL
         */
        public static final String URL_TECHSERVICE_FRAG = "/1/classes/TechService";
        public static final String URL_TECHSERVICE = URL_BASE + URL_TECHSERVICE_FRAG;

        /**
         * List Checkin's URL
         */
        public static final String URL_CHECKINS_FRAG = "/1/classes/Locations";
        public static final String URL_CHECKINS = URL_BASE + URL_CHECKINS_FRAG;

        /**
         * PARAMS for auth
         */
        public static final String PARAM_USERNAME = "username";
        public static final String PARAM_PASSWORD = "password";


        public static final String PARSE_APP_ID = "ZoLfhGYjXZyhZsMzCOUiKojKEmNpVHOtommTCMgD";
        public static final String PARSE_REST_API_KEY = "YNjADqZU6FjCMtvvRLoXlkZMyz7TgijvuDoorNiT";
        public static final String PARSE_CLIENT_KEY = "Ekt2HIFo6KhnE5DfOWycWUphwo1p8mOYl8Z9hr5B";
        public static final String HEADER_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
        public static final String HEADER_PARSE_APP_ID = "X-Parse-Application-Id";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";


    }


    public static final class Extra {
        private Extra() {}

        public static final String NEWS_ITEM = "news_item";

        public static final String USER = "user";

        public static final String EXAMPLE_ITEM = "news_item";

        public static final String TECHSERVICE_ITEM = "techservice_item";

        public static final String RATING ="image";

    }

    public static final class Intent {
        private Intent() {}

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "com.notify.app.mobile.";

    }

    public static class Notification {
        private Notification() {
        }

        public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)
    }

}


