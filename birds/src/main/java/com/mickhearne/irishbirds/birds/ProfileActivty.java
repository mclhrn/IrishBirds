package com.mickhearne.irishbirds.birds;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.mickhearne.irishbirds.birds.fragments.BirdProfileFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsSeenFragment;
import com.mickhearne.irishbirds.birds.fragments.BirdsWishlistFragment;
import com.mickhearne.irishbirds.birds.model.Bird;


public class ProfileActivty extends Activity implements
        BirdProfileFragment.OnFragmentInteractionListener,
        BirdsFragment.OnBirdSelectedListener,
        BirdsSeenFragment.OnBirdSeenSelectedListener,
        BirdsWishlistFragment.OnBirdWishSelectedListener {


    private Bird bird;
    private int bgColor;
    private boolean detailPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle b = getIntent().getExtras();
        bird = b.getParcelable("com.mickhearne.irishbirds.birds.model.Bird");
        bgColor = b.getInt("bgColor");

        // Determine if layout is landscape or not
        if (findViewById(R.id.bird_list_container) != null) {

            // We are in layout mode
            detailPage = true;

        } else {

            // Not in layout mode
            detailPage = false;

        }

        Log.i("birds", "will call buildUI: " + detailPage);
        buildUI();
    }


    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
    }


    private void buildUI() {

        getFragmentManager().beginTransaction()
                .add(R.id.container, BirdProfileFragment.newInstance(bird, bgColor))
                .commit();
        Log.i("birds", "in buildUI: " + detailPage);
        if (detailPage) {

            Fragment fragment = null;

            switch (bgColor) {
                case R.color.home_button_birds:
                    fragment = BirdsFragment.newInstance();
                    break;
                case R.color.seen_list_bg:
                    fragment = BirdsSeenFragment.newInstance();
                    break;
                case R.color.wish_list_bg:
                    fragment = BirdsWishlistFragment.newInstance();
                    break;
                default:
                    break;
            }

            Log.i("birds", "in buildUI if detail block: " + fragment + " " + fragment.getClass());

            getFragmentManager().beginTransaction()
                    .add(R.id.bird_list_container, fragment)
                    .commit();
        }
    }


    @Override
    public void onFragmentInteraction(String screen) {

    }


    @Override
    public void onBirdSelected(Bird selection, int bgColor) {

            BirdProfileFragment detailFragment = BirdProfileFragment.newInstance(selection, bgColor);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.displayDetail, detailFragment)
                    .commit();
    }


    @Override
    public void OnBirdSeenSelected(Bird selection, int bgColor) {
        onBirdSelected(selection, bgColor);
    }

    @Override
    public void onBirdWishSelected(Bird selection, int bgColor) {
        onBirdSelected(selection, bgColor);
    }
}