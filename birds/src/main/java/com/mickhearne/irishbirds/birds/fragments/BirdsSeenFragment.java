package com.mickhearne.irishbirds.birds.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by michaelhearne on 13/04/2014.
 */
public class BirdsSeenFragment extends ListFragment implements TextWatcher {

    private static final int BIRD_DETAIL_ACTIVITY = 1001;

    private List<Bird> birds;

    private boolean seenAtoZ = false;

    private BirdsDataSource datasource;

    private ArrayAdapter<Bird> adapter;

    private OnBirdSeenSelectedListener mCallback;

    private View v;

    public static BirdsSeenFragment newInstance() {
        BirdsSeenFragment f = new BirdsSeenFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_birds_seen, container, false);

        initScreen();

        return v;
    }


    private void initScreen() {
        datasource = new BirdsDataSource(getActivity());
        datasource.open();

        birds = datasource.findBirdsSeen();

        if (birds.isEmpty()) {
            MyToast.showToast("You have not added any birds to this list");
        }


        aToZToggle();

        initUI();

        refreshDisplay();
    }


    private void initUI() {

        // Get handles to UI objects
        ListView lv = (ListView) v.findViewById(android.R.id.list);
        EditText inputSearch = (EditText) v.findViewById(R.id.seen_input_search);

        // Enable filtering on list
        lv.setTextFilterEnabled(true);
        inputSearch.addTextChangedListener(this);
    }


    private void aToZToggle() {
        // Toggle display a to z or z to a
        ImageButton atoz = (ImageButton) v.findViewById(R.id.seen_atoz);
        atoz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!seenAtoZ) {
                    birds = datasource.seenAtoZ();
                    refreshDisplay();
                    seenAtoZ = true;
                }
                else {
                    birds = datasource.findBirdsSeen();
                    refreshDisplay();
                    seenAtoZ = false;
                }
            }
        });
    }


    public void refreshDisplay() {
        adapter = new ListViewAdapter(getActivity(), birds);
        setListAdapter(adapter);
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


    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {

        Bird bird = adapter.getItem(position);

        mCallback.OnBirdSeenSelected(bird);
    }


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
            mCallback = (OnBirdSeenSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBirdSeenSelectedListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    public interface OnBirdSeenSelectedListener {
        public void OnBirdSeenSelected(Bird selection);
    }
}
