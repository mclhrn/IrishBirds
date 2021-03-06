package com.mickhearne.irishbirds.birds.fragments;

import android.app.Activity;
import android.app.Fragment;
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

import com.mickhearne.irishbirds.birds.R;
import com.mickhearne.irishbirds.birds.db.BirdsDataSource;
import com.mickhearne.irishbirds.birds.list.ListViewAdapter;
import com.mickhearne.irishbirds.birds.model.Bird;
import com.mickhearne.irishbirds.birds.utilities.AnalyticsData;

import java.util.List;

public class BirdsFragment extends Fragment implements TextWatcher {


    private List<Bird> birds;
    private boolean refAtoZ = false;
    private BirdsDataSource datasource;
    public final static String LOGTAG = null;
    private ArrayAdapter<Bird> adapter;
    private View v;
    private AbsListView lv;
    private int bgColor;
    OnBirdSelectedListener mCallback;
    public static BirdsFragment newInstance() {
        BirdsFragment f = new BirdsFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_birds, container, false);

        openDB();

        initUI();

        getBirds();

        aToZToggle();

        refreshDisplay();

        return v;
    }


    private void initUI() {
        lv = (AbsListView) v.findViewById(R.id.list);
        lv.setTextFilterEnabled(true);
        EditText inputSearch = (EditText) v.findViewById(R.id.ref_input_search);
        inputSearch.addTextChangedListener(this);
        bgColor = R.color.home_button_birds;
    }


    private void openDB() {
        // open connection to db
        datasource = new BirdsDataSource(getActivity());
        datasource.open();
    }


    private void getBirds() {
        // get list of birds from db
        birds = datasource.findAll();
    }


    private void aToZToggle() {
        // Toggle display a to z or z to a
        ImageButton atoz = (ImageButton) v.findViewById(R.id.ref_atoz);
        atoz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!refAtoZ) {
                    birds = datasource.refAtoZ();
                    refreshDisplay();
                    refAtoZ = true;
                }
                else {
                    birds = datasource.findAll();
                    refreshDisplay();
                    refAtoZ = false;
                }
            }
        });
    }


    private void refreshDisplay() {
        adapter = new ListViewAdapter(getActivity(), birds);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Bird bird = adapter.getItem(position);
                mCallback.onBirdSelected(bird, bgColor);
                lv.setItemChecked(position, true);
            }
        });
    }


    public void onResume() {
        super.onResume();

        datasource.open();
    }


    @Override
    public void onStart() {
        super.onStart();

        // Google Analytics
        AnalyticsData.sendWithScreenName("Bird Reference Guide Screen");
    }


    @Override
    public void onPause() {
        super.onPause();

        datasource.close();
    }


    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onTextChanged(CharSequence cs, int start, int before, int count) {
        if (count < before) {
            ((ListViewAdapter) adapter).resetData();
        }
        adapter.getFilter().filter(cs.toString());
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnBirdSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnOptionSelectedListener");
        }
    }


    public interface OnBirdSelectedListener {
        public void onBirdSelected(Bird selection, int bgColor);
    }
}