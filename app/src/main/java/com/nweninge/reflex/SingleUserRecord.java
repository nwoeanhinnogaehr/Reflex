package com.nweninge.reflex;

import java.io.Serializable;

/**
 * Created by nweninge on 9/26/15.
 */
public class SingleUserRecord implements Serializable, Comparable<SingleUserRecord> {
    private long delayTime;
    private long pressTime;

    @Override
    public int compareTo(SingleUserRecord another) {
        return ((Long)getScore()).compareTo(another.getScore());
    }

    public SingleUserRecord(long delayTime, long pressTime) {
        this.delayTime = delayTime;
        this.pressTime = pressTime;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public long getPressTime() {
        return pressTime;
    }

    public boolean isOk() {
        return pressTime >= delayTime;
    }

    // Closer to zero is better
    public long getScore() {
        return pressTime - delayTime;
    }
}
