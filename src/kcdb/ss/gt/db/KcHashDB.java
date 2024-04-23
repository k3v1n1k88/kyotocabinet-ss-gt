/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import com.google.common.base.Strings;
import kcdb.ss.gt.option.KcCompressorType;
import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.option.KcOptionStoring;
import kcdb.ss.gt.exception.InvalidParamsException;

/**
 *
 * @author k3v1n1k88
 */
public abstract class KcHashDB extends KcBasicDB{
    
    protected static final String DEFCIPHERKEY = "";
    
    /**
     * The default size of page
     */
    protected static final long DEFPSIZ = Long.MAX_VALUE;
    
    /***
     *  The default size of the page cache
     */
    protected static final long DEFPCCAP = Long.MAX_VALUE;
    
    KcOptionStoring optStoring;
    KcCompressorType compressorType;
    String cipherKey = DEFCIPHERKEY;
    
    public static abstract class KcHashDBArgs<T extends KcHashDBArgs<T>> extends KcBasicDBArgs<T>{
        
        KcOptionStoring optStoring_ = KcOptionStoring.DEFAULT;
        KcCompressorType compressorType_ = KcCompressorType.DEFAULT;
        String cipherKey_ = "";
        
        protected KcHashDBArgs(String dir) {
            super(dir);
        }
        
        public T optionStoring(KcOptionStoring optStoring){
            this.extensionOpts(this.extensionOpts_.concat("opts=").concat(optStoring.getValue()).concat("#"));
            this.optStoring_ = optStoring;
            return (T) this;
        }
        
        public T compressorType(KcCompressorType compressorType){
            this.extensionOpts(this.extensionOpts_.concat("zcomp=").concat(compressorType.getValue()).concat("#"));
            this.compressorType_ = compressorType;
            return (T) this;
        }
        
        public T cipherKey(String cipherKey){
            if(Strings.isNullOrEmpty(cipherKey)){
                logger.warn("forward cipher key, because you provided a null or empty strong cipher key");
            }
            else{
                this.extensionOpts(this.extensionOpts_.concat("zkey=").concat(cipherKey).concat("#"));
                this.cipherKey_ = cipherKey;
            }
            return (T) this;
        }

        public KcOptionStoring getOptStoring() {
            return optStoring_;
        }

        public KcCompressorType getCompressorType() {
            return compressorType_;
        }

        public String getCipherKey() {
            return cipherKey_;
        }
        
        
    }
    
    public KcHashDB(KcBasicDBArgs args) {
        super(args);
    }
    
    protected KcHashDB(String dir, String dbFileName, KcModeConnection modeConnection, String extOtps, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey ) {
        super(dir, dbFileName, modeConnection, extOtps
                .concat("opts=").concat(optStoring.getValue()).concat("#")
                .concat("zcomp=").concat(compressorType.getValue()).concat("#")
                .concat("zkey=").concat(cipherKey).concat("#"));
    }
    
    protected KcHashDB(String dir, String dbFileName, KcModeConnection modeConnection, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey ) {
        this(dir, dbFileName, modeConnection, "",  optStoring, compressorType, cipherKey);
    }

    public KcOptionStoring getOptStoring() {
        return optStoring;
    }

    public void setOptStoring(KcOptionStoring optStoring) {
        this.optStoring = optStoring;
    }

    public KcCompressorType getCompressorType() {
        return compressorType;
    }

    public void setCompressorType(KcCompressorType compressorType) {
        this.compressorType = compressorType;
    }

    public String getCipherKey() {
        return cipherKey;
    }

    public void setCipherKey(String cipherKey) {
        this.cipherKey = cipherKey;
    }
    
}
