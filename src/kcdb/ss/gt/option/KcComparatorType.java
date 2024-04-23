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
public enum KcComparatorType {

    DEFAULT("lex"), //lexical comparator
    LEXICAL("lex"), //lexical comparator
    DECIMAL("dec"), //decimal comparator
    LEXICAL_DESC("lexdesc"), //lexical descending comparator
    DECIMAL_DESC("decdesc"); //decimal descending comparator

    private String value;

    private KcComparatorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
