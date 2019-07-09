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
public enum KcLogKind {

    DEBUG("debug"),
    INFO("info"),
    WARN("warn"),
    ERROR("error");

    private String value;

    private KcLogKind(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
