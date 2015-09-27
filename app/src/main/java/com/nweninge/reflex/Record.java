package com.nweninge.reflex;

import java.io.Serializable;

/**
 * Created by nweninge on 9/26/15.
 */
public abstract class Record implements Serializable {
    private long delayTime;
    private long pressTime;

    public Record(long delayTime, long pressTime) {
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
