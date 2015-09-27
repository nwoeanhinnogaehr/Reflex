package com.nweninge.reflex;

/**
 * Created by nweninge on 9/26/15.
 */
public class MultiUserRecord {
    private int numUsers;
    private int fastestUser;

    public MultiUserRecord(int numUsers, int fastestUser) {
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
