/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import kcdb.ss.gt.option.KcComparatorType;
import kcdb.ss.gt.option.KcCompressorType;
import kcdb.ss.gt.option.KcDBType;
import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.option.KcOptionStoring;
import kcdb.ss.gt.exception.InvalidParamsException;

/**
 *
 * @author k3v1n1k88
 */
public class KcCacheTreeDB extends KcExtendHashDB{
    
    private long psiz;
    private KcComparatorType rcomp;
    private long pccap;
    
    
    public static class Args extends KcExtendHashDBArgs<Args>{
    
        long psiz_;
        long pccap_;
        
        public Args(String dir, String dbName) {
            super(dir);
            this.dbFileName(KcDBType.CACHE_TREE.getValue().concat(dbName));
        }
        
        public Args pageSize(long psiz) throws InvalidParamsException{
            checkValidPageSize(psiz);
            this.extensionOpts_ = this.extensionOpts_.concat("psiz=").concat(String.valueOf(psiz)).concat("#");
            return this;
        }
        
        public Args pageCache(long pccap) throws InvalidParamsException{
            checkValidPageSize(pccap);
            this.extensionOpts_ = this.extensionOpts_.concat("pccap=").concat(String.valueOf(pccap)).concat("#");
            return this;
        }
        
        public Args comparatorType(KcComparatorType rcomp){
            this.extensionOpts_ = this.extensionOpts_.concat("rcomp=").concat(rcomp.getValue()).concat("#");
            return this;
        }
        
    
    }

    public KcCacheTreeDB(Args args) throws InvalidParamsException {
        super(args);
    }

    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long psiz, KcComparatorType rcomp, long pccap) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, psiz, rcomp, pccap);
    }
    
    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long psiz, KcComparatorType rcomp) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, psiz, rcomp, DEFPCCAP);
    }
    
    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long psiz) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, psiz, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, optStoring, compressorType, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, optStoring, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, KcOptionStoring.DEFAULT, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", DEFBNUM, KcOptionStoring.DEFAULT, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcCacheTreeDB(String dir, String dbFileName) throws InvalidParamsException {
        this(dir, dbFileName, new KcModeConnection(), "", DEFBNUM, KcOptionStoring.DEFAULT, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }

    public KcCacheTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, String extOptions, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long pageSize, KcComparatorType comparatorType, long pageCache) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, 
                extOptions.concat("psiz=").concat(String.valueOf(pageSize)).concat("#")
                .concat("rcomp=").concat(comparatorType.getValue()).concat("#")
                .concat("pccap=").concat(String.valueOf(pageCache)).concat("#"), bucketNumber, optStoring, compressorType, cipherKey);
        
        checkValidPageCache(pageCache);
        checkValidPageSize(pageSize);
        
        this.pccap = pageCache;
        this.psiz = pageSize;
    }
    
    protected static final void checkValidPageCache(long pccap) throws InvalidParamsException{
        if(pccap < 0){
            throw new InvalidParamsException("page cache have to be a number greater than or equals 0");
        }
    }
    
    protected static final void checkValidPageSize(long psiz) throws InvalidParamsException{
        if(psiz < 0){
            throw new InvalidParamsException("page size have to be a number greater than or equals 0");
        }
    }

    public long getPsiz() {
        return psiz;
    }

    public void setPsiz(long psiz) {
        this.psiz = psiz;
    }

    public KcComparatorType getRcomp() {
        return rcomp;
    }

    public void setRcomp(KcComparatorType rcomp) {
        this.rcomp = rcomp;
    }

    public long getPccap() {
        return pccap;
    }

    public void setPccap(long pccap) {
        this.pccap = pccap;
    }
    
    
    
}
