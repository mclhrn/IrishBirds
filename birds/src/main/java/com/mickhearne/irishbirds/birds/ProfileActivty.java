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

        Fragment fragment = null;
        destroyFragment(fragment);

        fragment = BirdProfileFragment.newInstance(selection, bgColor);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    private void destroyFragment(Fragment fragment) {

        try {
            fragment = this.getFragmentManager().findFragmentById(R.id.container);
            if (fragment != null) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        } catch (IllegalStateException e) {
            Log.i("DAA", "Fail destroying Profile Fragment");
        }
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
