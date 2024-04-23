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

/**
 *
 * @author k3v1n1k88
 */
public class KcDirectoryHashDB extends KcHashDB{
    
    public static class Args<T extends Args<T>> extends KcHashDBArgs<T>{
        
        protected Args(String dir){
            super(dir);
        }
        
        public Args(String dir, String dbName) {
            super(dir);
            this.dbFileName(dbName.concat(KcDBType.DIRECTORY_HASH.getValue()));
        }
        
    }

    public KcDirectoryHashDB(KcBasicDBArgs args) {
        super(args);
    }
    
    protected KcDirectoryHashDB(String dir, String dbFileName, KcModeConnection modeConnection,String extOptions,  KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey) {
        super(dir, dbFileName, modeConnection, extOptions , optStoring, compressorType, cipherKey);
    }

    public KcDirectoryHashDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey) {
        super(dir, dbName.concat(KcDBType.DIRECTORY_HASH.getValue()), modeConnection, optStoring, compressorType, cipherKey);
    }
    
    public KcDirectoryHashDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring, KcCompressorType compressorType) {
        super(dir, dbName.concat(KcDBType.DIRECTORY_HASH.getValue()), modeConnection, optStoring, compressorType, "");
    }
    
    public KcDirectoryHashDB(String dir, String dbName, KcModeConnection modeConnection, KcOptionStoring optStoring) {
        super(dir, dbName.concat(KcDBType.DIRECTORY_HASH.getValue()), modeConnection, optStoring, KcCompressorType.DEFAULT, "");
    }
    
    public KcDirectoryHashDB(String dir, String dbName, KcModeConnection modeConnection) {
        super(dir, dbName.concat(KcDBType.DIRECTORY_HASH.getValue()), modeConnection, KcOptionStoring.TLINEAR, KcCompressorType.DEFAULT, "");
    }
    
    public KcDirectoryHashDB(String dir, String dbName) {
        super(dir, dbName.concat(KcDBType.DIRECTORY_HASH.getValue()), new KcModeConnection(), KcOptionStoring.TLINEAR, KcCompressorType.DEFAULT, "");
    }

}
