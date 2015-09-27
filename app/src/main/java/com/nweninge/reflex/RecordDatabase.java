package com.nweninge.reflex;

import java.io.Serializable;
import java.util.ArrayList;
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
}
