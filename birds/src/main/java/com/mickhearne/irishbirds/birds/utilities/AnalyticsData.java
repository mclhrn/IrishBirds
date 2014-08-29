package com.mickhearne.irishbirds.birds.utilities;

import com.mickhearne.irishbirds.birds.MyApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by michaelhearne on 30/05/2014.
 */
public class AnalyticsData {

    public static void sendWithScreenName(String screenName) {

        // Get tracker
        Tracker t = MyApplication.getTracker(MyApplication.TrackerName.APP_TRACKER);

        // Set screen name
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }
}
