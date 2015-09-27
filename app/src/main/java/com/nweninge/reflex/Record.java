package com.nweninge.reflex;

import java.io.Serializable;

/**
 * Created by nweninge on 9/26/15.
 */
public abstract class Record implements Serializable {
    private double delayTime;
    private double pressTime;

    public Record(double delayTime, double pressTime) {
        this.delayTime = delayTime;
        this.pressTime = pressTime;
    }

    public double getDelayTime() {
        return delayTime;
    }

    public double getPressTime() {
        return pressTime;
    }

    public boolean isOk() {
        return pressTime >= delayTime;
    }

    // Closer to zero is better
    public double getScore() {
        return pressTime - delayTime;
    }
}
