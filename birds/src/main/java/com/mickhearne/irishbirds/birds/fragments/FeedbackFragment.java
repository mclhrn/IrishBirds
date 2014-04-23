package com.mickhearne.irishbirds.birds.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mickhearne.irishbirds.birds.R;


/**
 * Created by michaelhearne on 22/04/2014.
 */
public class FeedbackFragment extends DialogFragment {

    /**
     * Create a new instance of FeedbackFragment, providing "num"
     * as an argument.
     */


    public static FeedbackFragment newInstance() {
        FeedbackFragment f = new FeedbackFragment();
        return f;
    }


    public interface DialogClickListener {
        void onPositiveClick();
        void onNegativeClick();
    }


    DialogClickListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_text)
                .setPositiveButton(R.string.dialog_positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onPositiveClick();
                            }
                        })
                .setNegativeButton(R.string.dialog_negative,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onNegativeClick();
                            }
                        }
                ).create();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogClickListener");
        }
    }
}