package com.mickhearne.irishbirds.birds;

import android.app.Activity;
import android.os.Bundle;

import com.mickhearne.irishbirds.birds.fragments.BirdProfileFragment;
import com.mickhearne.irishbirds.birds.model.Bird;


public class ProfileActivty extends Activity implements
        BirdProfileFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle b = getIntent().getExtras();
        Bird bird = b.getParcelable("com.mickhearne.irishbirds.birds.model.Bird");
        int bgColor = b.getInt("bgColor");

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, BirdProfileFragment.newInstance(bird, bgColor))
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(String screen) {

    }
}
