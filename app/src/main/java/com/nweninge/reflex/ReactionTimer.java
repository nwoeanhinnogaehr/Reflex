package com.nweninge.reflex;

import java.util.Date;
import java.util.Random;

/**
 * Created by nweninge on 9/30/15.
 */
public class ReactionTimer {
    private Random random = new Random();
    private long delay;
    private long startTime;
    private long reactTime;
    private boolean hasReacted;

    public ReactionTimer() {
        reset();
    }

    public long getDelay() {
        return delay;
    }

    public void start() {
        startTime = new Date().getTime() + delay;
    }

    public void react() {
        reactTime = new Date().getTime();
        hasReacted = true;
    }

    public void reset() {
        delay = random.nextInt(1990) + 10;
        hasReacted = false;
    }

    public boolean hasReacted() {
        return hasReacted;
    }

    public SingleUserRecord getRecord() {
        return new SingleUserRecord(startTime, reactTime);
    }
}
