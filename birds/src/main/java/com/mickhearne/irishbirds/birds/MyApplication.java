package com.mickhearne.irishbirds.birds;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by michaelhearne on 10/04/2014.
 */
public class MyApplication extends Application {


    private static Context context;


    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }


    public static Context getAppContext() {
        return MyApplication.context;
    }


    /**
     *
     * GOOGLE ANALYTICS TRACKER
     *
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }


    static HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();


    private static final String PROPERTY_ID = "UA-50547496-1";


    public static Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(MyApplication.getAppContext());

            // Set the log level to verbose.
            GoogleAnalytics.getInstance(MyApplication.getAppContext()).getLogger().setLogLevel(Logger.LogLevel.VERBOSE);

            // When dry run is set, hits will not be dispatched, but will still be logged as
            // though they were dispatched.
//            analytics.setDryRun(true);

            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
}
