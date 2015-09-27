package com.nweninge.reflex;

/**
 * Created by nweninge on 9/26/15.
 */
public class SingleUserRecord {
    private long delayTime;
    private long pressTime;

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
