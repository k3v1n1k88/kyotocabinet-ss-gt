/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.option;

import kyotocabinet.DB;

/**
 *
 * @author k3v1n1k88
 */
public enum KcMergeMode {

    MADD(DB.MADD), //merge mode: keep the existing value
    MAPPEND(DB.MAPPEND), //merge mode: append the new value
    MREPLACE(DB.MREPLACE), //merge mode: modify the existing record only
    MSET(DB.MSET); //merge mode: overwrite the existing value

    private int mode;

    KcMergeMode(int mode) {
        this.mode = mode;
    }

    public int getValue() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

}
