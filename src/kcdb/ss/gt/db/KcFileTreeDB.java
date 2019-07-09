/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import com.google.common.base.Strings;
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
public class KcFileTreeDB extends KcFileDB {

    private long pageCache;
    private long pageSize;
    private KcComparatorType comparatorType;

    public static class Agrs extends KcFileDBArgs<Agrs> {
        
        long psiz_;
        long pccap_;
        KcComparatorType rcomp_;
        
        public Agrs(String dir, String dbName) {
            super(dir);
            this.dbFileName(dbName.concat(KcDBType.TREE.getValue()));
        }

        public Agrs pageSize(long psiz) throws InvalidParamsException {
            checkValidPageSize(psiz);
            this.extensionOpts_ = this.extensionOpts_
                    .concat("psiz=").concat(String.valueOf(psiz))
                    .concat("#");
            return this;
        }

        public Agrs comparatorType(KcComparatorType rcomp) {
            this.extensionOpts_ = this.extensionOpts_
                    .concat("rcomp=").concat(rcomp.getValue())
                    .concat("#");
            return this;
        }

        public Agrs pageCache(long pccap) throws InvalidParamsException {
            checkValidPageCache(pccap);
            this.extensionOpts_ = this.extensionOpts_
                    .concat("pccap=").concat(String.valueOf(pccap))
                    .concat("#");
            return this;
        }

        public long getPsiz() {
            return psiz_;
        }

        public long getPccap() {
            return pccap_;
        }

        public KcComparatorType getRcomp() {
            return rcomp_;
        }

    }

    public KcFileTreeDB(Agrs args) throws InvalidParamsException {
        super(args);
    }

    public KcFileTreeDB(String dir, String dbName, KcModeConnection modeConnection, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int apow, int dfunit, int fpow, long msiz, long pccap, long psiz, KcComparatorType rcomp) throws InvalidParamsException {
        this(dir, dbName.concat(KcDBType.TREE.getValue()), modeConnection, "", bucketNumber, optStoring, compressorType, cipherKey, apow, dfunit, fpow, msiz, pccap, psiz, rcomp);
        
    }

    protected KcFileTreeDB(String dir, String dbFileName, KcModeConnection modeConnection, String extOptions, long bucketNumber, KcOptionStoring optStoring, KcCompressorType compressorType, String cipherKey, int apow, int dfunit, int fpow, long msiz, long pccap, long psiz, KcComparatorType rcomp) throws InvalidParamsException {
        super(dir, dbFileName, modeConnection,
                extOptions.concat("pccap=").concat(String.valueOf(pccap)).concat("#")
                        .concat("psiz=").concat(String.valueOf(psiz)).concat("#")
                        .concat("rcomp=").concat(rcomp.getValue()).concat("#"), bucketNumber, optStoring, compressorType, cipherKey, apow, dfunit, fpow, msiz);
    }

    protected static final void checkValidPageCache(long pccap) throws InvalidParamsException {
        if (pccap < 0) {
            throw new InvalidParamsException("page cache have to be a number greater than or equals 0");
        }
    }

    protected static final void checkValidPageSize(long psiz) throws InvalidParamsException {
        if (psiz < 0) {
            throw new InvalidParamsException("page size have to be a number greater than or equals 0");
        }
    }

}
