/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import kcdb.ss.gt.option.KcCompressorType;
import kcdb.ss.gt.option.KcDBType;
import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.option.KcOptionStoring;
import kcdb.ss.gt.exception.InvalidParamsException;

/**
 *
 * @author k3v1n1k88
 */
public class KcCacheHashDB extends KcExtendHashDB{
    
    private static final long DEFCAPSIZ = Long.MAX_VALUE;
    private static final long DEFCAPCNT = Long.MAX_VALUE;
    
    private long capcnt;
    private long capsiz;
    
    public static class Args extends KcExtendHashDBArgs<Args>{
        
        long capcnt_;
        long capsiz_;
        
        public Args(String dir, String dbName) {
            super(dir);
            this.dbFileName(KcDBType.CACHE_HASH.getValue().concat(dbName));
        }
        
        public Args capacityCount(long capcnt) throws InvalidParamsException{
            checkValidCapacityCount(capcnt);
            this.extensionOpts_ = this.extensionOpts_.concat("capcnt=").concat(String.valueOf(capcnt)).concat("#");
            this.capcnt_ = capcnt;
            return this;
        }
        
        public Args capacitySize(long capsiz) throws InvalidParamsException{
            checkValidCapacitySize(capsiz);
            this.extensionOpts_ = this.extensionOpts_.concat("capsiz=").concat(String.valueOf(capsiz)).concat("#");
            this.capsiz_ = capsiz;
            return this;
        }

        public long getCapcnt() {
            return capcnt_;
        }

        public long getCapsiz() {
            return capsiz_;
        }        
        
    }

    public KcCacheHashDB(Args args) throws InvalidParamsException {
        super(args);
    }

    public KcCacheHashDB(String dir, String dbName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long capacityCount, long capacitySize) throws InvalidParamsException {
        this(dir, KcDBType.CACHE_HASH.getValue().concat(dbName), modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, capacityCount, capacitySize);
    }
    
    public KcCacheHashDB(String dir, String dbName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long capacityCount) throws InvalidParamsException {
        this(dir, KcDBType.CACHE_HASH.getValue().concat(dbName), modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, capacityCount, DEFCAPSIZ);
    }
    
    public KcCacheHashDB(String dir, String dbName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey) throws InvalidParamsException {
        this(dir, KcDBType.CACHE_HASH.getValue().concat(dbName), modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, DEFCAPCNT, DEFCAPSIZ);
    }
    
    protected KcCacheHashDB(String dir, String dbFileName, KcModeConnection modeConnection, String extOptions, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long capacityCount, long capacitySize) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, 
                extOptions.concat("capcnt=").concat(String.valueOf(capacityCount)).concat("#")
                .concat("capsiz=").concat(String.valueOf(capacitySize)).concat("#"), bucketNumber, optStoring, compressorType, cipherKey);
        
        checkValidCapacitySize(capacitySize);
        checkValidCapacityCount(capacityCount);
        
        this.capcnt = capacityCount;
        this.capsiz = capacitySize;
    }

    public long getCapcnt() {
        return capcnt;
    }

    public void setCapcnt(long capcnt) {
        this.capcnt = capcnt;
    }

    public long getCapsiz() {
        return capsiz;
    }

    public void setCapsiz(long capsiz) {
        this.capsiz = capsiz;
    }
    
     protected static final void checkValidCapacityCount(long capcnt) throws InvalidParamsException{
        if(capcnt <= 0){
            throw new InvalidParamsException("capacity count have to be a number greater than 0");
        }
    }
    
    protected static final void checkValidCapacitySize(long capsiz) throws InvalidParamsException{
        if(capsiz <= 0){
            throw new InvalidParamsException("capacity size have to be a number greater than 0");
        }
    }
    
}
