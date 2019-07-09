/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import com.google.common.base.Strings;
import kcdb.ss.gt.option.KcErrorType;
import kcdb.ss.gt.option.KcLogKind;
import kcdb.ss.gt.option.KcLogType;
import kcdb.ss.gt.exception.KcDBException;
import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.db.task.KcTransactionTask;
import kcdb.ss.gt.exception.PermissonException;
import java.io.File;
import java.util.List;
import java.util.Map;
import kcdb.ss.gt.option.KcMergeMode;
import kyotocabinet.Cursor;
import kyotocabinet.DB;
import kyotocabinet.FileProcessor;
import kyotocabinet.Visitor;
import static kyotocabinet.Visitor.NOP;
import org.apache.log4j.Logger;

/**
 *
 * @author k3v1n1k88
 */
public class KcBasicDB implements KcDB {

    protected static final long DEFBNUM = Long.MAX_VALUE;

    protected static final Logger logger = Logger.getLogger(KcDB.class);
    protected boolean acceptClearDB = false;
    protected boolean acceptCreateFolder = false;

    protected DB db;
    protected String dbFileName;

    protected String dir;
    protected boolean enableAllThrowException = false;

    protected String extensionOpts;
    protected boolean isOpen = false;
    protected String logDir = "";
    protected KcLogKind logKind = KcLogKind.ERROR;
    protected String logPrefix = "";
    protected KcLogType logType = KcLogType.NONE;
    protected KcModeConnection modeConnection;
    protected int retryCloseDB = 3;

    protected KcBasicDB(KcBasicDBArgs args) {
        this.acceptClearDB = args.acceptClearDB_;
        this.dir = args.dir_;
        this.dbFileName = args.dbFileName_;
        this.logKind = args.logKind_;
        this.logType = args.logType_;
        this.modeConnection = args.modeConnection_;
        this.logPrefix = args.logPrefix_;
        this.extensionOpts = args.extensionOpts_;
        this.acceptCreateFolder = args.acceptCreateFolder_;
        this.logDir = args.logDir_;
        this.enableAllThrowException = args.enableAllThrowException_;
    }

    protected KcBasicDB(String dir, String dbFileName, KcModeConnection modeConnection, String extOpts) {
        this.dir = dir;
        this.dbFileName = dbFileName;
        this.extensionOpts = extOpts;
        this.modeConnection = modeConnection;
    }

    @Override
    public boolean accept(byte[] key, Visitor visitor, boolean writable) {
        checkDBIsOpen();
        return this.db.accept(key, visitor, isOpen);
    }

    @Override
    public boolean accept_bulk(byte[][] keys, Visitor visitor, boolean writable) {
        checkDBIsOpen();
        return this.db.accept_bulk(keys, visitor, isOpen);
    }

    @Override
    public boolean add(String key, String value) {
        checkDBIsOpen();
        return this.db.add(key, value);
    }

    @Override
    public boolean add(byte[] key, byte[] value) {
        checkDBIsOpen();
        return this.db.add(key, value);
    }

    @Override
    public boolean append(String key, String value) {
        checkDBIsOpen();
        return this.db.append(key, value);
    }

    @Override
    public boolean append(byte[] key, byte[] value) {
        checkDBIsOpen();
        return this.db.append(key, value);
    }

    @Override
    public boolean begin_transaction(boolean commit) {
        checkDBIsOpen();
        return this.db.begin_transaction(commit);
    }

    @Override
    public boolean cas(byte[] key, byte[] oval, byte[] nval) {
        checkDBIsOpen();
        return this.db.cas(key, oval, nval);
    }

    @Override
    public boolean cas(String key, String oval, String nval) {
        checkDBIsOpen();
        return this.db.cas(key, oval, nval);
    }

    @Override
    public long check(byte[] key) {
        checkDBIsOpen();
        return this.db.check(key);
    }

    @Override
    public long check(String key) {
        checkDBIsOpen();
        return this.db.check(key);
    }

    private void checkDBIsOpen() {
        if (!this.isOpen) {
            throw new IllegalStateException("DB is not open. Ensure you call start method before!");
        }
    }

    @Override
    public boolean clear() {
        checkDBIsOpen();
        if (!this.acceptClearDB) {
            return false;
        } else {
            return this.db.clear();
        }
    }

    @Override
    public boolean close() {
        if (this.isOpen == false) {
            return true;
        }

        boolean ret = this.db.close();

        if (ret == true) {
            this.isOpen = false;
        }

        return ret;
    }

    @Override
    public boolean copy(String dest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count() {
        checkDBIsOpen();
        return this.db.count();
    }

    protected void createDirectory(String dir) throws PermissonException {

        File file = new File(dir);

        if (!file.exists() || !file.isDirectory()) {

            if (this.acceptCreateFolder) {

                boolean created = new File(dir).mkdir();

                if (!created) {
                    throw new PermissonException("error happen when try to create folder in path:" + dir + ". Check your permision and try again!");
                }
            } else {
                throw new PermissonException("cannot create folder because config acceptCreateFolder is false, turn on it if you accept creating folder");
            }
        }

        if (!file.canRead() || !file.canWrite()) {
            throw new PermissonException("cannot read or write on folder:" + dir + ". Check your permision and try again!");
        }

    }

    private String createPath() {
        StringBuilder sb = new StringBuilder().append(dir)
                .append("/").append(dbFileName)
                .append("#").append(extensionOpts);
        if (!logType.equals(KcLogType.NONE)) {
            sb.append("log=").append(!logType.equals(KcLogType.FILE) ? logType.getValue() : this.logDir.concat("/").concat(logType.getValue()))
                    .append("#logkinds=").append(logKind.getValue());
            if (!Strings.isNullOrEmpty(logPrefix)) {
                sb.append("#logpx=").append(logPrefix);
            }
        }
        return sb.toString();
    }

    @Override
    public Cursor cursor() {
        checkDBIsOpen();
        return this.db.cursor();
    }

    @Override
    public boolean dump_snapshot(String dest) {
        checkDBIsOpen();
        return this.db.dump_snapshot(dest);
    }

    @Override
    public boolean end_transaction(boolean commit) {
        checkDBIsOpen();
        return this.db.end_transaction(commit);
    }

    @Override
    public kyotocabinet.Error error() {
        checkDBIsOpen();
        return this.db.error();
    }

    @Override
    public String get(String key) {
        checkDBIsOpen();
        return this.db.get(key);
    }

    @Override
    public byte[] get(byte[] key) {
        checkDBIsOpen();
        return this.db.get(key);
    }

    public DB getDb() {
        checkDBIsOpen();
        return db;
    }

    public void setEnableAllThrowException(boolean enableAllThrowException) {
        this.enableAllThrowException = enableAllThrowException;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public void setLogKind(KcLogKind logKind) {
        this.logKind = logKind;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public void setLogType(KcLogType logType) {
        this.logType = logType;
    }

    public void setModeConnection(KcModeConnection modeConnection) {
        this.modeConnection = modeConnection;
    }

    public int getRetryCloseDB() {
        return retryCloseDB;
    }

    public void setRetryCloseDB(int timesRetry) {
        this.retryCloseDB = timesRetry;
    }

    @Override
    public byte[][] get_bulk(byte[][] keys, boolean atomic) {
        checkDBIsOpen();
        return this.db.get_bulk(keys, atomic);
    }

    @Override
    public Map<String, String> get_bulk(List<String> keys, boolean atomic) {
        checkDBIsOpen();
        return this.db.get_bulk(keys, atomic);
    }

    @Override
    public long increment(byte[] key, long num, long orig) throws kyotocabinet.Error {
        checkDBIsOpen();
        return this.db.increment(key, num, orig);
    }

    @Override
    public long increment(String key, long num, long orig) {
        checkDBIsOpen();
        return this.db.increment(key, num, orig);
    }

    @Override
    public double increment_double(byte[] key, double num, double orig) {
        checkDBIsOpen();
        return this.db.increment_double(key, num, orig);
    }

    @Override
    public double increment_double(String key, double num, double orig) {
        checkDBIsOpen();
        return this.db.increment_double(key, num, orig);
    }

    public String info() {
        return new StringBuilder().
                append("KcBasicDB{").
                append(",dir=").append(dir).
                append(",dbFileName=").append(dbFileName).
                append(",extensionOpts=").append(extensionOpts).
                append(",modeConnection=").append(modeConnection).
                append(",logType=").append(logType).
                append(",logKind=").append(logKind).
                append(",logPrefix=").append(logPrefix).
                append(",logDir=").append(logDir).
                append(",acceptClearDB=").append(acceptClearDB).
                append(",acceptCreateFolder=").append(acceptCreateFolder).
                append(",retryCloseDB=").append(retryCloseDB).
                append(",enableAllThrowException=").append(enableAllThrowException).
                append(",isOpen=").append(isOpen).
                append('}').toString();
    }

    public boolean isAcceptClearDB() {
        return acceptClearDB;
    }

    public void setAcceptClearDB(boolean acceptClearDB) {
        this.acceptClearDB = acceptClearDB;
    }

    public boolean isAcceptCreateFolder() {
        return acceptCreateFolder;
    }

    public void setAcceptCreateFolder(boolean acceptCreateFolder) {
        this.acceptCreateFolder = acceptCreateFolder;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public boolean iterate(Visitor visitor, boolean writable) {
        checkDBIsOpen();
        return this.db.iterate(visitor, writable);
    }

    @Override
    public boolean load_snapshot(String src) {
        checkDBIsOpen();
        return this.db.load_snapshot(src);
    }

    @Override
    public List<String> match_prefix(String prefix, long max) {
        checkDBIsOpen();
        return this.db.match_prefix(prefix, max);
    }

    @Override
    public List<String> match_regex(String regex, long max) {
        checkDBIsOpen();
        return this.db.match_regex(regex, max);
    }

    @Override
    public List<String> match_similar(String origin,
                                      long range,
                                      boolean utf,
                                      long max) {
        checkDBIsOpen();
        return this.db.match_similar(origin, range, utf, max);
    }

    @Override
    public boolean merge(DB[] srcary, int mode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean occupy(boolean writable, FileProcessor proc) {
        checkDBIsOpen();
        return this.db.occupy(writable, proc);
    }

    protected boolean open() throws PermissonException {
        boolean ret = false;
        try {
            this.db = new DB();
            String path = this.createPath();
            int mode = modeConnection.toMode();

            if ((mode & DB.OTRUNCATE) != 0 && !this.acceptClearDB) {
                throw new PermissonException("Flag forceCreate enable, but flag acceptClearDB is not enable (default it's false, for safety purposes). Enable flag acceptClearDB if you sure you know to doing");
            }

            ret = db.open(path, mode);
            logger.info("Try to open db: path=" + path + "; mode=" + mode);
            return ret;

        } catch (PermissonException ex) {
            //logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public String path() {
        return this.db.path();
    }

    protected void prepareBeforeOpenDB() throws PermissonException {
        if (this.acceptCreateFolder) {
            createDirectory(this.logDir);
            createDirectory(this.dir);
        }

    }

    @Override
    public boolean remove(byte[] key) {
        checkDBIsOpen();
        return this.db.remove(key);
    }

    @Override
    public boolean remove(String key) {
        checkDBIsOpen();
        return this.db.remove(key);
    }

    @Override
    public long remove_bulk(byte[][] recs, boolean atomic) {
        checkDBIsOpen();
        return this.db.remove_bulk(recs, atomic);
    }

    @Override
    public long remove_bulk(List<String> recs, boolean atomic) {
        checkDBIsOpen();
        return this.db.remove_bulk(recs, atomic);
    }

    @Override
    public boolean replace(String key, String value) {
        checkDBIsOpen();
        return this.db.replace(key, value);
    }

    @Override
    public boolean replace(byte[] key, byte[] value) {
        checkDBIsOpen();
        return this.db.replace(key, value);
    }

    @Override
    public byte[] seize(byte[] key) {
        checkDBIsOpen();
        return this.db.seize(key);
    }

    @Override
    public String seize(String key) {
        checkDBIsOpen();
        return this.db.seize(key);
    }

    @Override
    public boolean set(String key, String value) {
        checkDBIsOpen();
        return this.db.set(key, value);
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        checkDBIsOpen();
        return this.db.set(key, value);
    }

    @Override
    public long set_bulk(byte[][] recs, boolean atomic) {
        checkDBIsOpen();
        return this.db.set_bulk(recs, atomic);
    }

    @Override
    public long set_bulk(Map<String, String> recs, boolean atomic) {
        checkDBIsOpen();
        return this.db.set_bulk(recs, atomic);
    }

    @Override
    public long size() {
        checkDBIsOpen();
        return this.db.size();
    }

    @Override
    public boolean start() throws KcDBException, PermissonException {

        this.prepareBeforeOpenDB();

        boolean ret = this.open();

        if (enableAllThrowException) {
            this.tune_exception_rule(new int[]{kyotocabinet.Error.BROKEN,
                                               kyotocabinet.Error.DUPREC,
                                               kyotocabinet.Error.INVALID,
                                               kyotocabinet.Error.LOGIC,
                                               kyotocabinet.Error.MISC,
                                               kyotocabinet.Error.NOIMPL,
                                               kyotocabinet.Error.NOPERM,
                                               kyotocabinet.Error.NOREC,
                                               kyotocabinet.Error.NOREPOS,
                                               kyotocabinet.Error.SUCCESS,
                                               kyotocabinet.Error.SYSTEM
            });
        }

        if (ret == false) {
            String internalMsg = KcErrorType.getErrorTypeByCode(this.db.error().code()).toString();
            throw new KcDBException("cannot start DB, check your config again and try it later, ensure db is existed or you open it with writing mode. Config:" + this.info() + "\nInternal messgae:" + internalMsg);
        }

        this.isOpen = ret;
        logger.info("Started db, database is already...");
        logger.info("DB status: " + this.status());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            synchronized (KcBasicDB.class) {
                logger.info("Stopping ... Try to close database: " + this.info());
                boolean retClose = false;
                int tryTimes = this.retryCloseDB;

                if (!this.isOpen()) {
                    retClose = true;
                }

                while (!retClose && tryTimes > 0) {

                    retClose = this.db.close();

                    if (retClose == false && tryTimes > 0) {
                        logger.error("Cannot close db, try close db again... " + tryTimes);
                        synchronized (this) {
                            try {
                                this.wait(1000);
                            } catch (InterruptedException ex) {

                            }
                        }
                    }

                    tryTimes--;
                }

                if (retClose == false) {
                    logger.error("Cannot close db after retry " + this.retryCloseDB + " times.");
                } else {
                    logger.info("Stopped database: " + this.createPath());
                }
            }

        }));

        return ret;
    }

    @Override
    public Map<String, String> status() {
        return this.db.status();
    }

    @Override
    public boolean synchronize(boolean hard, FileProcessor proc) {
        checkDBIsOpen();
        return this.db.synchronize(hard, proc);
    }

    @Override
    public boolean tune_encoding(String string) {
        return db.tune_encoding(string);
    }

    @Override
    public boolean tune_exception_rule(int[] ints) {
        return db.tune_exception_rule(ints);
    }
    
    /**
     * Merge records from other databases.
     *
     * @param srcary an array of the source database objects.
     * @param mode   mode merging
     * @see KcMergeMode
     * @return true on success, or false on failure.
     */
    public boolean merge(DB[] srcary, KcMergeMode mode) {
        return this.merge(srcary, mode.getValue());
    }
    
     /**
     *
     * @param task transaction task implements list TODO
     * @param hard true for physical synchronization with the device, or false
     *             for logical
     *             synchronization with the file system.
     *
     * @throws Exception exception happen when  TODO list have error. That case, task will not commit to database. So, if you want not commit if error happen,
     * throw exception when implements KcTransactionTask
     */
    public void execTransTask(KcTransactionTask task, boolean hard) throws Exception {

        this.db.begin_transaction(hard);
        try {
            task.executeTransactionTaskWith(db);
        } catch (Exception ex) {
            this.db.end_transaction(false);
            throw ex;
        }
        this.db.end_transaction(true);

    }


    public static abstract class KcBasicDBArgs<T extends KcBasicDBArgs> {

        String dir_ = ".";
        String extensionOpts_ = "";

        KcLogType logType_ = KcLogType.NONE;
        KcLogKind logKind_ = KcLogKind.ERROR;
        String logPrefix_ = "";
        String logDir_ = "";
        KcModeConnection modeConnection_ = new KcModeConnection();

        String dbFileName_;

        boolean acceptClearDB_ = false;
        boolean acceptCreateFolder_ = false;
        boolean enableAllThrowException_ = false;

        protected KcBasicDBArgs(String dir) {
            this.dir_ = dir;
        }

        public T logType(KcLogType logType) {
            this.logType_ = logType;
            return (T) this;
        }

        public T logKind(KcLogKind logKind) {
            this.logKind_ = logKind;
            return (T) this;
        }

        public T logPrefix(String prefixLog) {
            this.logPrefix_ = prefixLog;
            return (T) this;
        }

        public T modeConnection(KcModeConnection modeConnection) {
            this.modeConnection_ = modeConnection;
            return (T) this;
        }

        public T acceptClearDB(boolean acceptClearDB) {
            this.acceptClearDB_ = acceptClearDB;
            return (T) this;
        }

        protected T dbFileName(String dbFileName) {
            this.dbFileName_ = dbFileName;
            return (T) this;
        }

        protected T extensionOpts(String extensionOpts) {
            this.extensionOpts_ = extensionOpts;
            return (T) this;
        }

        public T acceptCreateFolder(boolean bool) {
            this.acceptCreateFolder_ = bool;
            return (T) this;
        }

        public T acceptCreateFolder() {
            this.acceptCreateFolder_ = true;
            return (T) this;
        }

        public T logDir(String dir) {
            this.logDir_ = dir;
            this.logType_ = KcLogType.FILE;
            return (T) this;
        }

        public T enableAllThrowException() {
            this.enableAllThrowException_ = true;
            return (T) this;
        }

        public String getDir() {
            return dir_;
        }

        public String getExtensionOpts() {
            return extensionOpts_;
        }

        public KcLogType getLogType() {
            return logType_;
        }

        public KcLogKind getLogKind() {
            return logKind_;
        }

        public String getLogPrefix() {
            return logPrefix_;
        }

        public String getLogDir() {
            return logDir_;
        }

        public KcModeConnection getModeConnection() {
            return modeConnection_;
        }

        public String getDbFileName() {
            return dbFileName_;
        }

        public boolean isAcceptClearDB() {
            return acceptClearDB_;
        }

        public boolean isAcceptCreateFolder() {
            return acceptCreateFolder_;
        }

        public boolean isEnableAllThrowException() {
            return enableAllThrowException_;
        }

    }
    
    

}
