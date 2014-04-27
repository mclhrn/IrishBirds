package com.mickhearne.irishbirds.birds;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.mickhearne.irishbirds.birds.fragments.BirdProfileFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsSeenFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsWishlistFragment;
import com.mickhearne.irishbirds.birds.fragments.FeedbackFragment;
import com.mickhearne.irishbirds.birds.fragments.NavigationDrawerFragment;
import com.mickhearne.irishbirds.birds.model.Bird;
import com.mickhearne.irishbirds.birds.utilities.MyToast;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        BirdsFragment.OnBirdSelectedListener,
        BirdsSeenFragment.OnBirdSeenSelectedListener,
        BirdsWishlistFragment.OnBirdWishSelectedListener,
        BirdProfileFragment.OnFragmentInteractionListener,
        FeedbackFragment.DialogClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static Context context = MyApplication.getAppContext();

    private static Intent instance = null;

    private int currentFrag;

    private boolean detailPage;


    public static Intent getInstance() {
        if (instance == null) {
            instance = new Intent(context, MainActivity.class);
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

        Intent mIntent = getIntent();
        if (null != mIntent) {
            currentFrag = mIntent.getIntExtra("fragmentNumber", 0);
        }

        if (null != savedInstanceState) {
            currentFrag = savedInstanceState.getInt("fragmentNumber");
        }

        addDrawer();

        mTitle = getTitle();

        // Determine if layout is landscape or not
        if (findViewById(R.id.displayDetail) != null) {

            // We are in layout mode
            detailPage = true;

        } else {

            // Not in layout mode
            detailPage = false;

        }
    }


    private void addDrawer() {

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), currentFrag);

        onNavigationDrawerItemSelected(currentFrag);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                destroyFragment(fragment);
                fragment = BirdsFragment.newInstance();
                loadFragment(fragment, position);
                break;
            case 1:
                destroyFragment(fragment);
                fragment = BirdsSeenFragment.newInstance();
                loadFragment(fragment, position);
                break;
            case 2:
                destroyFragment(fragment);
                fragment = BirdsWishlistFragment.newInstance();
                loadFragment(fragment, position);
                break;
            case 3:
                Intent mIntent = new Intent(this, MapsActivity.class);
                startActivity(mIntent);
                break;
            case 4:
                showDialog();
                break;
            default:
                break;
        }
    }


    private void destroyFragment(Fragment fragment) {

        try {
            fragment = this.getFragmentManager().findFragmentById(R.id.displayDetail);
            if (fragment != null) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        } catch (IllegalStateException e) {
            Log.i("DAA", "Fail destroying List Fragment");
        }
    }


    public void loadFragment(Fragment fragment, int position) {

        if (fragment != null) {

            currentFrag = position;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }


    public void restoreActionBar() {

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    /**
     * Bird selected from List
     */
    @Override
    public void onBirdSelected(Bird selection, int bgColor) {

        if (detailPage) {

            BirdProfileFragment detailFragment = BirdProfileFragment.newInstance(selection, bgColor);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.displayDetail, detailFragment)
                    .commit();

        } else {

            Intent mIntent = new Intent(this, ProfileActivty.class);
            mIntent.putExtra("com.mickhearne.irishbirds.birds.model.Bird", selection);
            mIntent.putExtra("bgColor", bgColor);
            startActivity(mIntent);
        }
    }


    @Override
    public void onBirdWishSelected(Bird selection, int bgColor) {
        onBirdSelected(selection, bgColor);
    }


    @Override
    public void OnBirdSeenSelected(Bird selection, int bgColor) {
        onBirdSelected(selection, bgColor);
    }


    // Profile screen override - empty for now
    @Override
    public void onFragmentInteraction(String screen) {

    }


    /**
     * Feedback Dialog Fragment
     */
    // Display feedback dialog
    void showDialog() {
        DialogFragment newFragment = FeedbackFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }


    // Positive Click on Feedback Dialog
    @Override
    public void onPositiveClick() {
        launchMarket();
    }


    // Negative Click on Feedback Dialog
    @Override
    public void onNegativeClick() {

        // Do Nothing

    }


    // Positive Action on Feedback Dialog click - Launch Google Play to rate app
    private void launchMarket() {

        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            MyToast.showToast("Problem connecting to Google Play");
        }
    }
}
