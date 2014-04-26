package com.mickhearne.irishbirds.birds.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
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

public class MapViewFragment extends SupportMapFragment {


    private OnGoogleMapFragmentListener mCallback;

    private static final String SUPPORT_MAP_BUNDLE_KEY = "MapOptions";


    public static interface OnGoogleMapFragmentListener {
        void onMapReady(GoogleMap map);
    }


    public static MapViewFragment newInstance() {
        return new MapViewFragment();
    }


    public static MapViewFragment newInstance(GoogleMapOptions options) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(SUPPORT_MAP_BUNDLE_KEY, options);

        MapViewFragment fragment = new MapViewFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (mCallback != null) {
            mCallback.onMapReady(getMap());
        }
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnGoogleMapFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName() + " must implement OnGoogleMapFragmentListener");
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        if (getMap() != null) {

        }
    }
}
