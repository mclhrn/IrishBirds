package com.mickhearne.irishbirds.birds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mickhearne.irishbirds.birds.db.BirdsDataSource;
import com.mickhearne.irishbirds.birds.model.Bird;
import com.mickhearne.irishbirds.birds.utilities.JSONPullParser;
import com.mickhearne.irishbirds.birds.utilities.MyLocation;
import com.mickhearne.irishbirds.birds.utilities.MyToast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by michaelhearne on 10/04/2014.
 */
public class HomeActivity extends Activity implements View.OnClickListener {


    public static double LAT = 0;

    public static double LNG = 0;

    private Typeface font;

    private BirdsDataSource datasource;

    private static SharedPreferences pref;

    private static SharedPreferences.Editor editor;

    private boolean parseData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        openDB();

        pref = MyApplication.getAppContext().getSharedPreferences("Irish Birds Prefs", 0);
        editor = pref.edit();

        parseData = pref.getBoolean("parseData", true);

        if (parseData) {

            GetData task = new GetData();
            task.execute();
        }

        font = Typeface.createFromAsset(this.getApplicationContext().getAssets(),
                "fonts/fontawesome-webfont.ttf");

        initUI();

        setLocation();
    }


    private void openDB() {
        // open connection to db
        datasource = new BirdsDataSource(this);
        datasource.open();
    }


    private void createData() throws IOException, JSONException {
        JSONPullParser parser = new JSONPullParser();
        List<Bird> birds = parser.parseJSON(this);
        for (Bird bird : birds) {
            datasource.create(bird);
        }
        editor.putBoolean("parseData", false);
        editor.commit();
    }


    private void initUI() {

        // Set background to white
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        // Get handles to UI objects
        Button reference_guide = (Button) findViewById(R.id.birds_icon);
        Button birds_seen_btn = (Button) findViewById(R.id.birds_seen_icon);
        Button wishlist_btn = (Button) findViewById(R.id.birds_wish_icon);
        Button map_view_btn = (Button) findViewById(R.id.map_icon);

        // Set click listeners
        reference_guide.setOnClickListener(this);
        birds_seen_btn.setOnClickListener(this);
        wishlist_btn.setOnClickListener(this);
        map_view_btn.setOnClickListener(this);

        // Set Typeface
        reference_guide.setTypeface(font);
        birds_seen_btn.setTypeface(font);
        wishlist_btn.setTypeface(font);
        map_view_btn.setTypeface(font);
    }


    @Override
    public void onClick(View v) {
        Intent mIntent = MainActivity.getInstance();
        switch (v.getId()) {
            case R.id.birds_icon:
                mIntent.putExtra("fragmentNumber", 0);
                break;
            case R.id.birds_seen_icon:
                mIntent.putExtra("fragmentNumber", 1);
                break;
            case R.id.birds_wish_icon:
                mIntent.putExtra("fragmentNumber", 2);
                break;
            case R.id.map_icon:
                mIntent.putExtra("fragmentNumber", 3);
                break;
            default:

                break;
        }
        startActivity(mIntent);
    }


    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                createData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    private void setLocation() {
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                if (null != location) {
                    LAT = location.getLatitude();
                    LNG = location.getLongitude();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.showToast("Problem getting Location");
                        }
                    });
                }
            }
        };

        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
    }
}
