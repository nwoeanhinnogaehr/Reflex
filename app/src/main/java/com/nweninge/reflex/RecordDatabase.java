package com.nweninge.reflex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nweninge on 9/26/15.
 */
public class RecordDatabase implements Serializable {
    private List<Record> records;

    public RecordDatabase() {
        records = new ArrayList<Record>();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public void clear() {
        records.clear();
    }
}
