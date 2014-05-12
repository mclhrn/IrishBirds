package com.mickhearne.irishbirds.birds.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mickhearne.irishbirds.birds.HomeActivity;
import com.mickhearne.irishbirds.birds.R;
import com.mickhearne.irishbirds.birds.db.BirdsDataSource;
import com.mickhearne.irishbirds.birds.model.Bird;
import com.mickhearne.irishbirds.birds.utilities.MyToast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BirdProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BirdProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BirdProfileFragment extends android.app.Fragment {

    private View v;

    private Bird bird;

    private BirdsDataSource datasource;

    boolean isBirdsSeen, isWishlist;

    private OnFragmentInteractionListener mListener;

    private int bgColor;

    private LinearLayout ll;


    public static BirdProfileFragment newInstance() {
        BirdProfileFragment fragment = new BirdProfileFragment();
        return fragment;
    }


    public static BirdProfileFragment newInstance(Bird bird, int bgColor) {
        BirdProfileFragment fragment = new BirdProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("bird", bird);
        args.putInt("bgColor", bgColor);
        fragment.setArguments(args);
        return fragment;
    }


    public BirdProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bird = getArguments().getParcelable("bird");
            bgColor = getArguments().getInt("bgColor");

            datasource = new BirdsDataSource(getActivity());
            datasource.open();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_bird_profile, container, false);
        setHasOptionsMenu(true);
        checkLists();
        refreshDisplay();
        return v;
    }


    public void updateContent(Bird mBird, int bgColor) {
        this.bird = mBird;
        this.bgColor = bgColor;
        refreshDisplay();
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(bird.getName());
    }


    private void refreshDisplay() {

        // Get handles to UI objects
        TextView tv = (TextView) v.findViewById(R.id.name);
        TextView tv2 = (TextView) v.findViewById(R.id.latin);
        TextView tv3 = (TextView) v.findViewById(R.id.status);
        TextView tv4 = (TextView) v.findViewById(R.id.identification);
        TextView tv5 = (TextView) v.findViewById(R.id.diet);
        TextView tv6 = (TextView) v.findViewById(R.id.breeding);
        TextView tv7 = (TextView) v.findViewById(R.id.wintering_habits);
        TextView tv8 = (TextView) v.findViewById(R.id.where_to_see);
        TextView tv9 = (TextView) v.findViewById(R.id.conservation);
        ImageView iv = (ImageView) v.findViewById(R.id.main_profile_image);

        TextView divBar1 = (TextView) v.findViewById(R.id.div_bar_1);
        TextView divBar2 = (TextView) v.findViewById(R.id.div_bar_2);
        TextView divBar3 = (TextView) v.findViewById(R.id.div_bar_3);
        TextView divBar4 = (TextView) v.findViewById(R.id.div_bar_4);

        divBar1.setBackgroundResource(bgColor);
        divBar2.setBackgroundResource(bgColor);
        divBar3.setBackgroundResource(bgColor);
        divBar4.setBackgroundResource(bgColor);

        tv.setText(bird.getName());
        tv2.setText(bird.getLatinName());
        tv3.setText(bird.getStatus());
        tv4.setText(bird.getIdentification());
        tv5.setText(bird.getDiet());
        tv6.setText(bird.getBreeding());
        tv7.setText(bird.getWinteringHabits());
        tv8.setText(bird.getWhereToSee());
        tv9.setText(bird.getConservation());

        int imageResource = getResources().getIdentifier(bird.getImageLarge(),
                "drawable", getActivity().getPackageName());
        if (imageResource != 0) {
            iv.setImageResource(imageResource);
        }
    }


    private void checkLists() {
        isBirdsSeen = datasource.checkSeenlist(bird.getId());
        isWishlist = datasource.checkWishlist(bird.getId());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile, menu);


        // Show delete menu item if we came from Birds Seen or Wish
        menu.findItem(R.id.delete_from_seen).setVisible(isBirdsSeen);
        menu.findItem(R.id.delete_from_wish).setVisible(isWishlist);

        // Show add menu item if we came from main reference guide
        menu.findItem(R.id.add_to_seen).setVisible(!isBirdsSeen);
        menu.findItem(R.id.add_to_wishlist).setVisible(!isWishlist);

        // Share Sighting
        menu.findItem(R.id.share_sighting).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_seen:
                if (datasource.addToBirdsSeen(bird, HomeActivity.LAT, HomeActivity.LNG)) {
                    checkLists();
                    getActivity().invalidateOptionsMenu();
                    notifyUser(" added to Birds Seen List");
                } else {
                    notifyUser(" not added to Birds Seen List");
                }
                break;
            case R.id.add_to_wishlist:
                if (datasource.addToWishList(bird)) {
                    checkLists();
                    getActivity().invalidateOptionsMenu();
                    notifyUser(" added to Wishlist");
                } else {
                    notifyUser(" not added to Wishlist");
                }
                break;
            case R.id.share_sighting:
                shareSighting();
                break;
            case R.id.delete_from_seen:
                if (isBirdsSeen) {
                    if (datasource.removeFromBirdsSeen(bird)) {
                        checkLists();
                        getActivity().invalidateOptionsMenu();
                        notifyUser(" removed from Birds Seen List");
                    } else {
                        notifyUser(" not removed from Birds Seen list");
                    }
                }
                break;
            case R.id.delete_from_wish:
                if (isWishlist) {
                    if (datasource.removeFromWishList(bird)) {
                        checkLists();
                        getActivity().invalidateOptionsMenu();
                        notifyUser(" removed from Wishlist");
                    } else {
                        notifyUser(" not removed from Wishlist");
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void notifyUser(String mList) {
        MyToast.showToast(bird.getName() + mList);
    }


    private void shareSighting() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "I just saw a " + bird.getName()
                + " using the Irish Birds App. ";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Irish Birds Sighting");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String screen);
    }
}