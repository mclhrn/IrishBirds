package com.mickhearne.irishbirds.birds.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mickhearne.irishbirds.birds.list.ListViewAdapter;
import com.mickhearne.irishbirds.birds.R;
import com.mickhearne.irishbirds.birds.db.BirdsDataSource;
import com.mickhearne.irishbirds.birds.model.Bird;
import com.mickhearne.irishbirds.birds.utilities.MyToast;

import java.util.List;


public class BirdsWishlistFragment extends Fragment implements TextWatcher {


    private List<Bird> birds;

    private boolean wish_atoz = false;

    private BirdsDataSource datasource;

    private ArrayAdapter<Bird> adapter;

    private OnBirdWishSelectedListener mCallback;

    private int bgColor;

    private AbsListView lv;

    private View v;


    public static BirdsWishlistFragment newInstance() {
        BirdsWishlistFragment f = new BirdsWishlistFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_birds_wishlist, container, false);

        initScreen();

        return v;
    }


    private void initScreen() {
        datasource = new BirdsDataSource(getActivity());
        datasource.open();

        birds = datasource.findWishList();

        if (birds.isEmpty()) {
            MyToast.showToast("You have not added any birds to this list");
        }

        initUI();

        aToZToggle();

        refreshDisplay();
    }


    private void initUI() {

        // Get handles to UI objects
        lv = (AbsListView) v.findViewById(R.id.list);
        EditText inputSearch = (EditText) v.findViewById(R.id.wish_input_search);

        // Enable filtering on list
        lv.setTextFilterEnabled(true);
        inputSearch.addTextChangedListener(this);

        bgColor = R.color.wish_list_bg;
    }


    public void refreshDisplay() {
        adapter = new ListViewAdapter(getActivity(), birds);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Bird bird = adapter.getItem(position);

                mCallback.onBirdWishSelected(bird, bgColor);

            }
        });
    }


    private void aToZToggle() {
        // Toggle display a to z or z to a
        ImageButton atoz = (ImageButton) v.findViewById(R.id.wish_atoz);
        atoz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!wish_atoz) {
                    birds = datasource.wishAtoZ();
                    refreshDisplay();
                    wish_atoz = true;
                }
                else {
                    birds = datasource.findWishList();
                    refreshDisplay();
                    wish_atoz = false;
                }
            }
        });
    }


    public void onResume() {
        super.onResume();
        initScreen();
    }


    @Override
    public void onPause() {
        super.onPause();
        datasource.close();
    }


//    @Override
//    public void onListItemClick(ListView lv, View v, int position, long id) {
//
//        Bird bird = adapter.getItem(position);
//
//        mCallback.onBirdWishSelected(bird, bgColor);
//    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count < before) {
            ((ListViewAdapter) adapter).resetData();
        }
        adapter.getFilter().filter(s.toString());
    }


    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnBirdWishSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    public interface OnBirdWishSelectedListener {
        public void onBirdWishSelected(Bird selection, int bgColor);
    }
}
