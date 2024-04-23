/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.option;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kyotocabinet.Error;
/**
 *
 * @author k3v1n1k88
 */
public enum KcErrorType {

    SUCCESS(Error.SUCCESS, "success"), ///< success
    NOIMPL(Error.NOIMPL, "not implements"), ///< not implemented
    INVALID(Error.INVALID, "invalid operation"), ///< invalid operation
    NOREPOS(Error.NOREPOS, "no repository"), ///< no repository
    NOPERM(Error.NOPERM, "no permission"), ///< no permission
    BROKEN(Error.BROKEN, "broken file"), ///< broken file
    DUPREC(Error.DUPREC, "record duplication"), ///< record duplication
    NOREC(Error.NOREC, "no record"), ///< no record
    LOGIC(Error.LOGIC, "logical inconsistency"), ///< logical inconsistency
    SYSTEM(Error.SYSTEM, "system error"), ///< system error
    UNKNOW(11, "unknown message"),
    
    MISC(Error.MISC, "miscellaneous error"); ///< miscellaneous error
    
    
    private static Map<Integer, KcErrorType> holderKcErrorTypeByCode = null;
    
    static{
        Map<Integer, KcErrorType>build = new HashMap<>();
        for(KcErrorType errorType: KcErrorType.values()){
            build.put(errorType.code, errorType);
        }
        holderKcErrorTypeByCode = Collections.unmodifiableMap(build);
    }
    
    int code;
    String msg;
            
    KcErrorType(int code){
        this.code = code;
    }
    KcErrorType(int code, String s){
        this.code = code;
        this.msg = s;
    }
    
    public static KcErrorType getErrorTypeByCode(int code){
        KcErrorType errType = holderKcErrorTypeByCode.get(code);
        if(errType== null){
            errType =  KcErrorType.UNKNOW;
        }
        return errType;
    }

    @Override
    public String toString() {
        return this.name()+'('+"code="+code+",msg=\""+msg+"\")";
    }
}


