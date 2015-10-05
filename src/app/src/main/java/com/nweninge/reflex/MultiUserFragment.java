package com.nweninge.reflex;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * This fragment is responsible for the UI in the multi user gameshow buzzer mode.
 */
public class MultiUserFragment extends Fragment {
    private static final String ARG_DB = "database";

    /**
     * A reference to the global database in MainActivity, for convenience.
     */
    private RecordDatabase recordDb;

    /**
     * The number of players in the current game
     */
    private int numPlayers;

    /**
     * If a player touches the screen, we set this to true to immediately lock the screen
     * and prevent other players from touching it afterwards.
     */
    private boolean lockScreen;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recordDb The record database to store results in.
     * @return A new instance of fragment SingleUserFragment.
     */
    public static MultiUserFragment newInstance(RecordDatabase recordDb) {
        MultiUserFragment fragment = new MultiUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DB, recordDb);
        fragment.setArguments(args);
        return fragment;
    }

    public MultiUserFragment() {
        // Required empty public constructor
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
        // determine the number of players and reset the game
        getActivity().findViewById(R.id.tablelayout).setVisibility(View.INVISIBLE);
        askNumPlayers();
        reset();
        setReactListeners();
    }

    /**
     * Ask the user how many players should be in this game.
     */
    private void askNumPlayers() {
        new AlertDialog.Builder(getActivity())
                .setTitle("How many players?")
                .setItems(new CharSequence[]{"2", "3", "4"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setNumPlayers(which+2);
                        getActivity().findViewById(R.id.tablelayout).setVisibility(View.VISIBLE);
                        showHelp();
                    }
                })
                .show();
    }

    /**
     * Reset the state of all the buttons.
     */
    private void reset() {
        for (int i = 1; i <= 4; i++) {
            getPlayerButton(i).setBackgroundColor(Color.BLACK);
        }
        lockScreen = false;
    }

    /**
     * Called when a player touches a button
     * @param player the player that touched their button
     */
    private void react(int player) {
        // if another player was faster in this round, do nothing.
        if (lockScreen) {
            return;
        }
        lockScreen = true;

        // indicate the button was pressed and save a record of it.
        getPlayerButton(player).setBackgroundColor(Color.GREEN);
        MultiUserRecord record = new MultiUserRecord(numPlayers, player);
        recordDb.addRecord(record);
        recordDb.saveRecords(this.getActivity());
        new AlertDialog.Builder(getActivity())
                .setTitle("Player " + player + " wins.")
                .setNeutralButton("Next round", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        reset();
                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        reset();
                    }
                }).show();
    }

    /**
     * Hooks up the react method to each button
     */
    private void setReactListeners() {
        for (int i = 1; i <= 4; i++) {
            final int j = i;
            getPlayerButton(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    react(j);
                    return true;
                }
            });
        }
    }

    /**
     * Gets the button corresponding to a given player
     */
    private View getPlayerButton(int player) {
        switch (player) {
            case 1:
                return getActivity().findViewById(R.id.button1);
            case 2:
                return getActivity().findViewById(R.id.button2);
            case 3:
                return getActivity().findViewById(R.id.button3);
            case 4:
                return getActivity().findViewById(R.id.button4);
        }
        return null;
    }

    /**
     * Shows a message describing how to play the game
     */
    private void showHelp() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Help")
                .setMessage("Whoever presses their button first wins.")
                .setNeutralButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * Changes the visibility of buttons to reflect a given number of players
     */
    private void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
        getActivity().findViewById(R.id.button4).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.tablerow2).setVisibility(View.VISIBLE);
        if (numPlayers == 3) {
            getActivity().findViewById(R.id.button4).setVisibility(View.GONE);
        } else if (numPlayers == 2) {
            getActivity().findViewById(R.id.tablerow2).setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_user, container, false);
    }
}
