package com.nweninge.reflex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages records and statistics for the app, and provides functions for saving/restoring data
 * from disk, calculating specific statistics, adding and clearing data.
 */
public class RecordDatabase implements Serializable {
    public static final String FILENAME = "data.json";

    /**
     * Records from single user mode
     */
    private List<SingleUserRecord> suRecords;
    /**
     * Records from multi user mode
     */
    private List<MultiUserRecord> muRecords;

    public RecordDatabase() {
        suRecords = new ArrayList<SingleUserRecord>();
        muRecords = new ArrayList<MultiUserRecord>();
    }

    /**
     * Adds a new single user/reaction time record to the database.
     */
    public void addRecord(SingleUserRecord record) {
        suRecords.add(record);
    }
    /**
     * Adds a new multi user/gameshow buzzer record to the database.
     */
    public void addRecord(MultiUserRecord record) {
        muRecords.add(record);
    }

    /**
     * Completely clears all records from the database
     */
    public void clear() {
        suRecords.clear();
        muRecords.clear();
    }

    /**
     * Saves records to disk
     * @param activity the activity to use for file IO.
     */
    public void saveRecords(Activity activity) {
        try {
            FileOutputStream fos = activity.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(this);
            fos.write(json.getBytes());
            fos.close();
        } catch (Exception e) {
            new AlertDialog.Builder(activity)
                    .setTitle("Error saving statistics!!")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    /**
     * Loads records from the disk, replacing any currently in the database.
     * @param activity the activity to use for file IO.
     */
    public void loadRecords(Activity activity) {
        try {
            FileInputStream fis = activity.openFileInput(FILENAME);
            Gson gson = new Gson();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            RecordDatabase db = gson.fromJson(br, RecordDatabase.class);
            this.suRecords = db.suRecords;
            this.muRecords = db.muRecords;
            fis.close();
        } catch (Exception e) {
            // if we couldn't load them, it probably means they didn't exist.
        }
    }

    /**
     * Calculate the minimum reaction time score of the last n entries.
     * @param n The number of entries to include. -1 includes all entries
     * @return The minimum score seen.
     */
    public long minLastN(int n) {
        if (n == -1) {
            n = suRecords.size();
        }
        n = Math.min(n, suRecords.size());
        if (n == 0) return 0;
        long min = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int idx = suRecords.size() - i - 1;
            min = Math.min(suRecords.get(idx).getScore(), min);
        }
        return min;
    }

    /**
     * Calculate the maximum reaction time score of the last n entries.
     * @param n The number of entries to include. -1 includes all entries
     * @return The maximum score seen.
     */
    public long maxLastN(int n) {
        if (n == -1) {
            n = suRecords.size();
        }
        n = Math.min(n, suRecords.size());
        if (n == 0) return 0;
        long max = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int idx = suRecords.size() - i - 1;
            max = Math.max(suRecords.get(idx).getScore(), max);
        }
        return max;
    }

    /**
     * Calculate the average reaction time score of the last n entries.
     * @param n The number of entries to include. -1 includes all entries
     * @return The average score.
     */
    public long avgLastN(int n) {
        if (n == -1) {
            n = suRecords.size();
        }
        n = Math.min(n, suRecords.size());
        if (n == 0) return 0;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            int idx = suRecords.size() - i - 1;
            sum += suRecords.get(idx).getScore();
        }
        return sum/n;
    }

    /**
     * Calculate the median reaction time score of the last n entries.
     * @param n The number of entries to include. -1 includes all entries
     * @return The median score.
     */
    public long medLastN(int n) {
        if (n == -1) {
            n = suRecords.size();
        }
        n = Math.min(n, suRecords.size());
        if (n == 0) return 0;
        if (n == 1) return suRecords.get(0).getScore();
        List<SingleUserRecord> sublist = suRecords.subList(suRecords.size() - n, suRecords.size());
        Collections.sort(sublist);
        int midpoint = sublist.size()/2;
        long median;
        if (sublist.size() % 2 == 1) {
            median = (sublist.get(midpoint - 1).getScore() + sublist.get(midpoint).getScore())/2;
        } else {
            median = sublist.get(midpoint).getScore();
        }
        return median;
    }

    /**
     * Calculate the number of times a given player pressed the button first, with a given
     * number of players.
     * @param numPlayers the number of players in the game
     * @param player the fastest player in the game
     * @return the number of presses with the given constraints
     */
    public int getBuzzerPresses(int numPlayers, int player) {
        int count = 0;
        for (int i = 0; i < muRecords.size(); i++) {
            MultiUserRecord record = muRecords.get(i);
            if (record.getNumUsers() == numPlayers && record.getFastestUser() == player) {
                count++;
            }
        }
        return count;
    }
}
