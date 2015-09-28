package com.nweninge.reflex;

import java.io.Serializable;

/**
 * Created by nweninge on 9/26/15.
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
