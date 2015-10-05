package com.nweninge.reflex;

import java.io.Serializable;

/**
 * Stores a record of a single reaction time in single user mode.
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

    /**
     * @return true iff the user pressed the button after the delay
     */
    public boolean isOk() {
        return pressTime >= delayTime;
    }

    /**
     * The amount of delay between the trigger, and the user pressing the button, in milliseconds
     */
    public long getScore() {
        return pressTime - delayTime;
    }
}
