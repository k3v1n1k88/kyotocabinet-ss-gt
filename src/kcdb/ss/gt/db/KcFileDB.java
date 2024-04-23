/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import com.google.common.base.Strings;
import kcdb.ss.gt.option.KcComparatorType;
import kcdb.ss.gt.option.KcCompressorType;
import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.option.KcOptionStoring;
import kcdb.ss.gt.exception.InvalidParamsException;

/**
 *
 * @author k3v1n1k88
 */
public abstract class KcFileDB extends KcExtendHashDB {

    /**
     * The default alignment power.
     */
    protected static final int DEFAPOW = 3;

    /**
     * The default free block pool power.
     */
    protected static final int DEFFPOW = 10;
    
    /**
     * The default size of the memory-mapped region.
     */
    protected static final long DEFMSIZ = 64L << 20;
    
    /**
     * The default unit step
     */
    protected static final int DEFDFUNIT = 8;
    
    /**
     * The default page size
     */
    static final long DEFPSIZ = -1;
    
    long alignmentPower;
    int unitStep;
    int freeBlockPoolPower;
    long memorySize;
    
    
    public static abstract class KcFileDBArgs<T extends KcFileDBArgs<T>> extends KcExtendHashDBArgs<T> {
        
        int apow_;
        int dfunit_;
        int fpow_;
        long msiz_;
        
        protected KcFileDBArgs(String dir) {
            super(dir);
        }

        public T alignmentPower(int apow) throws InvalidParamsException {
            checkValidAlignmentPower(apow);
            this.extensionOpts(this.extensionOpts_.concat("apow=").concat(String.valueOf(apow)).concat("#"));
            return (T) this;
        }

        public T unitStep(int dfunit) throws InvalidParamsException {
            checkValidUnitStep(dfunit);
            this.extensionOpts(this.extensionOpts_.concat("dfunit=").concat(String.valueOf(dfunit)).concat("#"));
            return (T) this;
        }

        public T freeBlockPoolPower(int fpow) throws InvalidParamsException {
            checkValidFreeBlockPoolPower(fpow);
            this.extensionOpts(this.extensionOpts_.concat("fpow=").concat(String.valueOf(fpow)).concat("#"));
            return (T) this;
        }

        public T memorySize(long msiz) throws InvalidParamsException {
            checkValidMemorySize(msiz);
            this.extensionOpts(this.extensionOpts_.concat("msiz=").concat(String.valueOf(msiz)).concat("#"));
            return (T) this;
        }

        public int getApow() {
            return apow_;
        }

        public int getDfunit() {
            return dfunit_;
        }

        public int getFpow() {
            return fpow_;
        }

        public long getMsiz() {
            return msiz_;
        }
        
        
        
    }

    public KcFileDB(KcFileDBArgs args) throws InvalidParamsException {
        super(args);
    }

    protected KcFileDB(String dir, String dbFileName, KcModeConnection modeConnection, String extOptions, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int alignmentPower, int unitStep, int freeBlockPoolPower, long memorySize) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection, extOptions.concat("apow=").concat(String.valueOf(alignmentPower)).concat("#")
                .concat("dfunit=").concat(String.valueOf(unitStep)).concat("#")
                .concat("fpow=").concat(String.valueOf(freeBlockPoolPower)).concat("#")
                .concat("msiz=").concat(String.valueOf(memorySize)).concat("#"), 
                bucketNumber, optStoring, compressorType, cipherKey);
        checkValidAlignmentPower(alignmentPower);
        checkValidUnitStep(unitStep);
        checkValidMemorySize(memorySize);
        checkValidFreeBlockPoolPower(freeBlockPoolPower);
    }
    
    protected KcFileDB(String dir, String dbFileName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int alignmentPower, int unitStep, int freeBlockPoolPower, long memorySize) throws InvalidParamsException {
        this(dir, dbFileName, modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, alignmentPower, unitStep, freeBlockPoolPower, memorySize);
    }
    
    protected static final void checkValidAlignmentPower(int apow) throws InvalidParamsException{
        if(apow <= 0 || apow > MAXAPOW){
            throw new InvalidParamsException("apow have to be a integer number between 1 and  "+MAXAPOW);
        }
    }
    
    protected static final void checkValidUnitStep(int dfunit) throws InvalidParamsException{
        if(dfunit <= 0){
            throw new InvalidParamsException("dfunit have to be a integer number greater than 0");
        }
    }
    
    protected static final void checkValidMemorySize(long msiz) throws InvalidParamsException{
        if(msiz < 0){
            throw new InvalidParamsException("msiz have to be a integer number greater than or equals 0");
        }
    }
    
    protected static final void checkValidFreeBlockPoolPower(long fpow) throws InvalidParamsException{
        if(fpow < 0 || fpow > MAXFPOW){
            throw new InvalidParamsException("fpow have to be a integer number between 0 and "+MAXFPOW);
        }
    }
    
}
