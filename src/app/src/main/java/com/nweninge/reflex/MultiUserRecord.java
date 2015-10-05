package com.nweninge.reflex;

import java.io.Serializable;

/**
 * Stores a record of a single round played in the multi user/gameshow buzzer mode.
 */
public class MultiUserRecord implements Serializable {
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
