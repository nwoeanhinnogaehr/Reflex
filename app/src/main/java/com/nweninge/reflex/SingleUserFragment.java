package com.nweninge.reflex;


import android.app.AlertDialog;
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

import java.util.Date;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleUserFragment extends Fragment {
    private static final String ARG_DB = "database";
    private static final int TRIGGER_EVENT = 1;

    private RecordDatabase recordDb;
    private Random random;
    private Handler handler;
    private long triggerTime;
    private long tapTime;
    private boolean tapped;

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
        random = new Random();
        // http://stackoverflow.com/questions/1877417/how-to-set-a-timer-in-android
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
        tapped = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recordDb = (RecordDatabase)getArguments().getSerializable(ARG_DB);
        }
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

    private void showHelp() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Help")
                .setMessage("Tap when the screen turns green.")
                .setNeutralButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startTimer();
                    }
                }).show();
    }

    private void startTimer() {
        tapped = false;
        long delay = getRandomDelay();
        setTriggerDelay(delay);
    }

    private void setTriggerDelay(long delay) {
        triggerTime = new Date().getTime() + delay;
        Message msg = handler.obtainMessage(TRIGGER_EVENT);
        handler.sendMessageDelayed(msg, delay);
    }

    private void trigger() {
        if (!tapped) {
            setColor(Color.GREEN);
        }
    }

    private void react() {
        tapTime = new Date().getTime();
        tapped = true;
        Record record = new SingleUserRecord(triggerTime, tapTime);
        recordDb.addRecord(record);
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        if (!record.isOk()) {
            setColor(Color.RED);
            ad.setTitle("Too quick!");
        } else {
            setColor(Color.BLACK);
            ad.setTitle("COOL!");
        }
        ad.setMessage("Your score was " + record.getScore() + ".");
        ad.setNeutralButton("Try again", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startTimer();
            }
        });
        ad.show();
    }

    private void setColor(int color) {
        getActivity().findViewById(R.id.button).setBackgroundColor(color);
    }

    private long getRandomDelay() {
        return random.nextInt(1990) + 10;
    }
}
