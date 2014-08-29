package com.mickhearne.irishbirds.birds.utilities;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by michaelhearne on 11/07/2014.
 */
public class Agent {

//    public static void init() {
//
//        checkData();
//    }
//
//
//    private static void checkData() {
//
//        String url = Const.DATA_URL;
//        Firebase dataRef = new Firebase(url);
//        dataRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                for(int i = 0;i < snapshot.getChildrenCount(); i++)  {
//
//                    // TODO Database insert stuff
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//                Log.i("mick", "Firebase Error: " + firebaseError.getMessage());
//            }
//        });
//    }
}