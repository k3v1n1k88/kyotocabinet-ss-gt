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
public enum KcLogType {
    
    DEFAULT(""),
    NONE(""),
    FILE("kcdb.log"),
    STD_OUT("-"),
    STD_ERR("+");

    private String value;

    KcLogType(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }
    
    public KcLogType setValue(String input){
        
        return this;
    }

}
