package com.nweninge.reflex;

/**
 * Created by nweninge on 9/26/15.
 */
public class MultiUserRecord extends Record {
    private int numUsers;
    private int fastestUser;

    public MultiUserRecord(long delayTime, long pressTime, int numUsers, int fastestUser) {
        super(delayTime, pressTime);
        this.numUsers = numUsers;
        this.fastestUser = fastestUser;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public int getFastestUser() {
        return fastestUser;
    }

}
