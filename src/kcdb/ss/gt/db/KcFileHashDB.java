/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import com.google.common.base.Strings;
import kcdb.ss.gt.option.KcCompressorType;
import kcdb.ss.gt.option.KcDBType;
import static kcdb.ss.gt.db.KcFileDB.DEFAPOW;
import static kcdb.ss.gt.db.KcFileDB.DEFFPOW;
import static kcdb.ss.gt.db.KcFileDB.DEFMSIZ;
import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.option.KcOptionStoring;
import static kcdb.ss.gt.db.KcFileDB.DEFDFUNIT;
import kcdb.ss.gt.exception.InvalidParamsException;

/**
 *
 * @author k3v1n1k88
 */
public class KcFileHashDB extends KcFileDB {
    
    public static class Args extends KcFileDBArgs<Args>{
        
        public Args(String dir, String dbName) {
            super(dir);
            this.dbFileName(dbName.concat(KcDBType.HASH.getValue()));
        }
        
    }

    public KcFileHashDB(Args args) throws InvalidParamsException {
        super(args);
    }

    protected KcFileHashDB(String dir, String dbName, KcModeConnection modeConnection, String extOptions, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int alignmentPower, int unitStep, int freeBlockPoolPower, long memorySize) throws InvalidParamsException {
        super(dir, dbName.concat(KcDBType.HASH.getValue()), modeConnection, extOptions, bucketNumber, optStoring, compressorType, cipherKey, alignmentPower, unitStep, freeBlockPoolPower, memorySize);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int alignmentPower, int unitStep, int freeBlockPoolPower, long memorySize) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, bucketNumber, optStoring, compressorType, cipherKey, alignmentPower, unitStep, freeBlockPoolPower, memorySize);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int alignmentPower, int unitStep, int freeBlockPoolPower) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, bucketNumber, optStoring, compressorType, cipherKey, alignmentPower, unitStep, freeBlockPoolPower, DEFMSIZ);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int alignmentPower, int unitStep) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, bucketNumber, optStoring, compressorType, cipherKey, alignmentPower, unitStep, DEFFPOW, DEFMSIZ);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int alignmentPower) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, bucketNumber, optStoring, compressorType, cipherKey, alignmentPower, DEFDFUNIT, DEFFPOW, DEFMSIZ);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, bucketNumber, optStoring, compressorType, cipherKey, DEFAPOW, DEFDFUNIT, DEFFPOW, DEFMSIZ);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, bucketNumber, optStoring, compressorType, DEFCIPHERKEY, DEFAPOW, DEFDFUNIT, DEFFPOW, DEFMSIZ);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, bucketNumber, optStoring, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFAPOW, DEFDFUNIT, DEFFPOW, DEFMSIZ);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, bucketNumber, KcOptionStoring.DEFAULT, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFAPOW, DEFDFUNIT, DEFFPOW, DEFMSIZ);
    }
    
    protected KcFileHashDB(String dir, String dbFileName, KcModeConnection modeConnection) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, DEFBNUM, KcOptionStoring.DEFAULT, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFAPOW, DEFDFUNIT, DEFFPOW, DEFMSIZ);
    }
    
    protected KcFileHashDB(String dir, String dbFileName) throws InvalidParamsException {
        super(dir, dbFileName, new KcModeConnection(), DEFBNUM, KcOptionStoring.DEFAULT, KcCompressorType.DEFAULT, DEFCIPHERKEY, DEFAPOW, DEFDFUNIT, DEFFPOW, DEFMSIZ);
    }
}
