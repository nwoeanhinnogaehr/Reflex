package com.nweninge.reflex;

import java.util.Date;
import java.util.Random;

/**
 * Implements the timing of reactions to a signal, with a random delay.
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

    /**
     * @return the amount of random delay, in milliseconds
     */
    public long getDelay() {
        return delay;
    }

    /**
     * Starts timing
     */
    public void start() {
        startTime = new Date().getTime() + delay;
    }

    /**
     * Called to signal the reaction to the start + random delay.
     */
    public void react() {
        reactTime = new Date().getTime();
        hasReacted = true;
    }

    /**
     * Resets the delay to a new random value
     */
    public void reset() {
        delay = random.nextInt(1990) + 10;
        hasReacted = false;
    }

    /**
     * @return true if the user has reacted to the signal yet
     */
    public boolean hasReacted() {
        return hasReacted;
    }

    /**
     * Returns a new record of the reaction time
     * @return null if the user has not yet reacted, otherwise a new record.
     */
    public SingleUserRecord getRecord() {
        if (!hasReacted) {
            return null;
        } else {
            return new SingleUserRecord(startTime, reactTime);
        }
    }
}
