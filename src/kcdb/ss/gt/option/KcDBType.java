/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.option;

/**
 *
 * @author k3v1n1k88
 */
public enum KcDBType {

    BASIC("-"),
    CACHE_HASH("*"),
    CACHE_TREE("%"),
    DIRECTORY_HASH(".kcd"),
    HASH(".kch"),
    PROTO_HASH("-"),
    PROTO_TREE("+"),
    STASH(":"),
    PLAIN_TEXT(".kcx"),
    TREE(".kct"),
    DIRECTORY_TREE(".kcf");

    private String value;

    KcDBType(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }

    public void setType(String value) {
        this.value = value;
    }

}
