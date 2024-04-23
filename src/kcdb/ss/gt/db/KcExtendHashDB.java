/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import kcdb.ss.gt.option.KcComparatorType;
import kcdb.ss.gt.option.KcCompressorType;
import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.option.KcOptionStoring;
import kcdb.ss.gt.exception.InvalidParamsException;

/**
 *
 * @author k3v1n1k88
 */
public abstract class KcExtendHashDB extends KcHashDB{
    
    private long bucketNumber = DEFBNUM;
    
    public static abstract class KcExtendHashDBArgs<T extends KcExtendHashDBArgs<T>> extends KcHashDBArgs<T>{
        
        long bnum_;
        
        protected KcExtendHashDBArgs(String dir) {
            super(dir);
        }
        
        public T bucketNumber(long bnum) throws InvalidParamsException{      
            checkValidBuckerNumber(bnum);
            this.extensionOpts(this.extensionOpts_.concat("bnum=").concat(String.valueOf(bnum)).concat("#"));
            this.bnum_ = bnum;
            return (T) this;
        }

        public long getBnum() {
            return bnum_;
        }
        
    }

    public KcExtendHashDB(KcBasicDBArgs args) {
        super(args);
    }
    
    protected KcExtendHashDB(String dir, String dbFileName, KcModeConnection modeConnection, String extOptions, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, extOptions.concat("bnum=").concat(String.valueOf(bucketNumber)).concat("#"), optStoring, compressorType, cipherKey);
        this.bucketNumber = bucketNumber;
        checkValidBuckerNumber(bucketNumber);
    }

    public KcExtendHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey);
    }
    
    protected static final void checkValidBuckerNumber(long bnum) throws InvalidParamsException{
       if(bnum < 0){
           throw new InvalidParamsException("bnum have to be a number greater than 0");
       }
    }
    
}
