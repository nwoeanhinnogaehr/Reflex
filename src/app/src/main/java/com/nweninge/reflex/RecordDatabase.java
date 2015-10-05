package com.nweninge.reflex;

import android.app.Activity;
import android.content.Context;
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
 * Created by nweninge on 9/26/15.
 */
public class RecordDatabase implements Serializable {
    public static final String FILENAME = "data.json";

    private List<SingleUserRecord> suRecords;
    private List<MultiUserRecord> muRecords;

    public RecordDatabase() {
        suRecords = new ArrayList<SingleUserRecord>();
        muRecords = new ArrayList<MultiUserRecord>();
    }

    public void addRecord(SingleUserRecord record) {
        suRecords.add(record);
    }
    public void addRecord(MultiUserRecord record) {
        muRecords.add(record);
    }

    public void clear() {
        suRecords.clear();
        muRecords.clear();
    }

    public void saveRecords(Activity activity) throws IOException {
        FileOutputStream fos = activity.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(this);
        fos.write(json.getBytes());
        fos.close();
    }

    public void loadRecords(Activity activity) throws IOException {
        FileInputStream fis = activity.openFileInput(FILENAME);
        Gson gson = new Gson();
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        RecordDatabase db = gson.fromJson(br, RecordDatabase.class);
        this.suRecords = db.suRecords;
        this.muRecords = db.muRecords;
        fis.close();
    }

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

    public long medLastN(int n) {
        if (n == -1) {
            n = suRecords.size();
        }
        n = Math.min(n, suRecords.size());
        if (n == 0) return 0;
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