package com.nweninge.reflex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nweninge on 9/26/15.
 */
public class RecordDatabase implements Serializable {
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

    public long minLastN(int n) {
        if (n == -1) {
            n = suRecords.size();
        }
        n = Math.min(n, suRecords.size());
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
}
