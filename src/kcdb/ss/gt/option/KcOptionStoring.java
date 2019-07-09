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
public enum KcOptionStoring {
    
    DEFAULT("l"), ///< use linear collision chaining
    TSMALL("s"), ///< use 32-bit addressing
    TLINEAR("l"), ///< use linear collision chaining
    TCOMPRESS("c"); ///< compress each record

    private String value;

    private KcOptionStoring(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
