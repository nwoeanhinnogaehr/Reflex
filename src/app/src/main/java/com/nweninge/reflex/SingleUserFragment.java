package com.nweninge.reflex;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * A fragment based UI for recording reaction times.
 */
public class SingleUserFragment extends Fragment {
    private static final String ARG_DB = "database";
    /**
     * The event that fires when the user is supposed to react.
     */
    private static final int TRIGGER_EVENT = 1;

    /**
     * A reference to the MainActivity's record database, for convenience.
     */
    private RecordDatabase recordDb;

    /**
     * Implements the logic behind the UI.
     */
    private ReactionTimer timer;

    /**
     * Used as a callback timer.
     */
    private Handler handler;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recordDb The record database to store results in.
     * @return A new instance of fragment SingleUserFragment.
     */
    public static SingleUserFragment newInstance(RecordDatabase recordDb) {
        SingleUserFragment fragment = new SingleUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DB, recordDb);
        fragment.setArguments(args);
        return fragment;
    }

    public SingleUserFragment() {
        // http://stackoverflow.com/questions/1877417/how-to-set-a-timer-in-android
        // set up the handler to called trigger when it fires.
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case TRIGGER_EVENT:
                    trigger();
                    break;
                }
            }
        };
        timer = new ReactionTimer();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recordDb = (RecordDatabase)getArguments().getSerializable(ARG_DB);
        }
    }

    @Override
    public void onPause() {
        handler.removeMessages(TRIGGER_EVENT);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                react();
            }
        });
        showHelp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_user, container, false);
    }

    /**
     * Shows a message describing how to use the timer.
     */
    private void showHelp() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Help")
                .setMessage("Tap when the screen turns green.")
                .setNeutralButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startTimer();
                    }
                }).show();
    }

    /**
     * Starts the timer and sets the trigger to fire after the random delay.
     */
    private void startTimer() {
        timer.reset();
        timer.start();
        setTriggerDelay(timer.getDelay());
    }

    /**
     * Sets the trigger to fire after a given amount of delay.
     */
    private void setTriggerDelay(long delay) {
        Message msg = handler.obtainMessage(TRIGGER_EVENT);
        handler.sendMessageDelayed(msg, delay);
    }

    /**
     * Called by the handler when the user is supposed to react.
     */
    private void trigger() {
        if (!timer.hasReacted()) {
            setColor(Color.GREEN);
        }
    }


    /**
     * Called when the user reacts to the trigger
     */
    private void react() {
        // If this round is already over, we need to reset
        if (timer.hasReacted()) {
            showHelp();
            return;
        }

        // Record the reaction time and show it to the user.
        timer.react();
        SingleUserRecord record = timer.getRecord();
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        if (!record.isOk()) {
            // The user pressed the button before the trigger
            setColor(Color.RED);
            ad.setTitle("Too quick!");
        } else {
            // The user was successful
            recordDb.addRecord(record);
            recordDb.saveRecords(this.getActivity());
            setColor(Color.BLACK);
            ad.setTitle("COOL!");
        }
        ad.setMessage("Your score was " + record.getScore() + ".");
        ad.setNeutralButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startTimer();
            }
        });
        ad.show();
    }

    /**
     * Sets the colour of the screen to a given colour. Used as an indicator of the state of the
     * timer.
     */
    private void setColor(int color) {
        getActivity().findViewById(R.id.button).setBackgroundColor(color);
    }
}
