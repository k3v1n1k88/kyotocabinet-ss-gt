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
public class KcDirectoryTreeDB extends KcDirectoryHashDB{
    
    
    private long pageSize;
    private KcComparatorType comparatorType;
    private long pageCache;
    
    public static class Args extends KcDirectoryHashDB.Args<Args>{
        
        public Args(String dir, String dbName) {
            super(dir);
            this.dbFileName(dbName.concat(KcDBType.DIRECTORY_HASH.getValue()));
        }
        
        public Args pageSize(long psiz) throws InvalidParamsException{
            checkValidPageSize(psiz);
            this.extensionOpts(this.extensionOpts_.concat("psiz=").concat(String.valueOf(psiz)).concat("#"));
            return this;
        }
        
        public Args comparatorType(KcComparatorType rcomp){
            this.extensionOpts(this.extensionOpts_.concat("rcomp=").concat(rcomp.getValue()).concat("#"));
            return this;
        }
        
        public Args pagaCache(long pccap) throws InvalidParamsException{
            checkValidPageCache(pccap);
            this.extensionOpts(this.extensionOpts_.concat("pccap=").concat(String.valueOf(pccap)).concat("#"));
            return this;
        }
        
    }

    public KcDirectoryTreeDB(KcBasicDBArgs args) {
        super(args);
    }

    public KcDirectoryTreeDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long pageSize, KcComparatorType comparatorType, long pageCache ) {
        this(dir, dbName.concat(KcDBType.DIRECTORY_TREE.getValue()), modeConnection, "", optStoring, compressorType, cipherKey, pageSize, comparatorType, pageCache);
    }
    
    protected KcDirectoryTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, String extOpts, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long pageSize, KcComparatorType comparatorType, long pageCache ) {
        super(dir, dbFileName, modeConnection, extOpts.concat("psize=").concat(String.valueOf(pageSize)).concat("#")
                .concat("rcomp=").concat(comparatorType.getValue()).concat("#")
                .concat("pccap=").concat(String.valueOf(pageCache).concat("#")), optStoring, compressorType, cipherKey);
    }
    
    public KcDirectoryTreeDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long pageSize, KcComparatorType comparatorType) {
        this(dir, dbName.concat(KcDBType.DIRECTORY_TREE.getValue()), modeConnection, "", optStoring, compressorType, cipherKey, pageSize, comparatorType, DEFPCCAP);
    }
    
    public KcDirectoryTreeDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, long pageSize, long pageCache) {
        this(dir, dbName.concat(KcDBType.DIRECTORY_TREE.getValue()), modeConnection, "", optStoring, compressorType, cipherKey, pageSize, KcComparatorType.DEFAULT, pageCache);
    }
    
    public KcDirectoryTreeDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey ) {
        this(dir, dbName.concat(KcDBType.DIRECTORY_TREE.getValue()), modeConnection, "", optStoring, compressorType, cipherKey, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcDirectoryTreeDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring, KcCompressorType compressorType) {
        this(dir, dbName.concat(KcDBType.DIRECTORY_TREE.getValue()), modeConnection, "", optStoring, compressorType, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcDirectoryTreeDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring) {
        this(dir, dbName.concat(KcDBType.DIRECTORY_TREE.getValue()), modeConnection, "", optStoring, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcDirectoryTreeDB(String dir, String dbName, KcModeConnection modeConnection) {
        this(dir, dbName.concat(KcDBType.DIRECTORY_TREE.getValue()), modeConnection, "", KcOptionStoring.DEFAULT, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }
    
    public KcDirectoryTreeDB(String dir, String dbName) {
        this(dir, dbName.concat(KcDBType.DIRECTORY_TREE.getValue()), new KcModeConnection(), "", KcOptionStoring.DEFAULT, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFPSIZ, KcComparatorType.DEFAULT, DEFPCCAP);
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public KcComparatorType getComparatorType() {
        return comparatorType;
    }

    public void setComparatorType(KcComparatorType comparatorType) {
        this.comparatorType = comparatorType;
    }

    public long getPageCache() {
        return pageCache;
    }

    public void setPageCache(long pageCache) {
        this.pageCache = pageCache;
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
    
}
