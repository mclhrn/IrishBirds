package com.mickhearne.irishbirds.birds.fragments;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mickhearne.irishbirds.birds.HomeActivity;
import com.mickhearne.irishbirds.birds.R;
import com.mickhearne.irishbirds.birds.db.BirdsDataSource;
import com.mickhearne.irishbirds.birds.model.BirdsSeenModel;
import com.mickhearne.irishbirds.birds.utilities.MyToast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MapViewFragment extends Fragment {


    private View v;

    private BirdsDataSource datasource;

    private List<BirdsSeenModel> birdsSeen;

    private LatLng myMarker;

    private static final LatLng CURRENT_LOC = new LatLng(HomeActivity.LAT, HomeActivity.LNG);

    private GoogleMap map;


    public MapViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map, container, false);

        datasource = new BirdsDataSource(getActivity());

        datasource.open();

        birdsSeen = datasource.findBirdsForMap();

        getShaKey();

        try {
            // Loading map
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }




    private void getShaKey() {

        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.mickhearne.irishbirds.birds",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v("birds", "KeyHash--------------------------:" + Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

    }





    private void initilizeMap() {
        if (map == null) {
            map = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // check if map is created successfully or not
            if (map == null) {
                MyToast.showToast("Sorry! unable to create maps");
            } else {
                addMarkers();
            }
        }
    }

    private void addMarkers() {
        for (int i = 0; i < birdsSeen.size(); i++) {

            map.addMarker(new MarkerOptions().position(myMarker = new LatLng(birdsSeen.get(i)
                    .getLatitude(), birdsSeen.get(i)
                    .getLongitude()))
                    .title(birdsSeen.get(i).getName()));
        }

        map.addMarker(new MarkerOptions().position(CURRENT_LOC).title("You are here"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENT_LOC, 17));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

    }


    @Override
    public void onResume() {
        super.onResume();
        datasource.open();
        initilizeMap();
    }


    @Override
    public void onPause() {
        super.onPause();
        datasource.close();
    }
}
