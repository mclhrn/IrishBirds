package com.mickhearne.irishbirds.birds;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.support.v4.widget.DrawerLayout;

//import com.mickhearne.irishbirds.birds.fragments.BirdsSeenFragment;
//import com.mickhearne.irishbirds.birds.fragments.BirdsWishFragment;
//import com.mickhearne.irishbirds.birds.fragments.MapsActivity;
import com.mickhearne.irishbirds.birds.fragments.BirdProfileFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsSeenFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsWishlistFragment;
import com.mickhearne.irishbirds.birds.fragments.MapViewFragment;
import com.mickhearne.irishbirds.birds.fragments.NavigationDrawerFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsFragment;
import com.mickhearne.irishbirds.birds.model.Bird;
import com.mickhearne.irishbirds.birds.utilities.MyToast;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                    BirdsFragment.OnBirdSelectedListener,
                    BirdsSeenFragment.OnBirdSeenSelectedListener,
                    BirdsWishlistFragment.OnBirdWishSelectedListener,
                    BirdProfileFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static final int BIRD_DETAIL_ACTIVITY = 1001;

    private static Context context = MyApplication.getAppContext();
    private static Intent instance = null;

    private int currentFrag;


    public static Intent getInstance(){
        if (instance == null){
            instance = new Intent(context,MainActivity.class);
            instance.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        return instance;
    }


    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putInt("fragmentNumber", currentFrag);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null != savedInstanceState) {
            currentFrag = savedInstanceState.getInt("fragmentNumber");
        }

        Intent mIntent = getIntent();
        if (null != mIntent) {
            currentFrag = mIntent.getIntExtra("fragmentNumber", 0);
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        onNavigationDrawerItemSelected(currentFrag);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = BirdsFragment.newInstance();
                break;
            case 1:
                fragment = BirdsSeenFragment.newInstance();
                break;
            case 2:
                fragment = BirdsWishlistFragment.newInstance();
                break;
            case 3:
                try {
                    fragment = (com.google.android.gms.maps.MapFragment) this.getFragmentManager().findFragmentById(R.id.map);
                    if (fragment != null) {
                        getFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    fragment = new MapViewFragment();
                } catch (IllegalStateException e) {
                    Log.i("DAA", "Fail destroying Map Fragment");
                }
                break;

            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null).commit();
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public void onBirdSelected(Bird selection) {

        Fragment fragment = BirdProfileFragment.newInstance(selection);

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null).commit();
        }
    }


    @Override
    public void onBirdWishSelected(Bird selection) {
        onBirdSelected(selection);
    }


    @Override
    public void OnBirdSeenSelected(Bird selection) {
        onBirdSelected(selection);
    }


    @Override
    public void onFragmentInteraction(Uri selection) {
    }


}
