/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.mapreduce;

import com.google.common.base.Strings;
import java.io.File;
import kyotocabinet.DB;
import kyotocabinet.MapReduce;
import kyotocabinet.ValueIterator;

/**
 *
 * @author k3v1n1k88
 */
public abstract class QuickMapReduce extends AbstractMapReduce {

    private OptionExcecute opts = OptionExcecute.DEFAULT;
    private String tempPath = "";

    public Filter filter;
    public Access access;

    public PreProcess preProc = () -> {
    };
    public MidProcess midProc = () -> {
    };
    public PostProcess postProc = () -> {
    };

    public ProcessLog processLog = (LogMapReduce log) -> {
        System.out.println("processLog - "+log.name);
        return true;
    };

    @Override
    public boolean map(byte[] key, byte[] value) {

        try {
            if (filter.accept(new Record(key, value))) {
                Record acceptedRecord = new Record(key, value);
                Record emittedRecord = this.access.produceEmittedRecordFromAcceptedRecordImpl(acceptedRecord);

                if (emittedRecord != null) {
                    return this.emit(emittedRecord.key, emittedRecord.value);
                }
            }

            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    @Override
    public boolean reduce(byte[] key, ValueIterator vi) {
        try {
            return this.access.accessFinalRecordImpl(new FinalRecord(key, vi));
        } catch (Exception ex) {

        }
        return false;
    }

    public QuickMapReduce() {
        this.filter = (Record record) -> {
            return this.acceptRecord(record);
        };
        this.access = new Access() {
            @Override
            public Record produceEmittedRecordFromAcceptedRecordImpl(Record record) {
                return produceEmittedRecordFromAcceptedRecord(record);
            }

            @Override
            public boolean accessFinalRecordImpl(FinalRecord finalRecord) {
                return accessFinalRecord(finalRecord);
            }
        };

    }

    public QuickMapReduce setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public QuickMapReduce setAccess(Access access) {
        this.access = access;
        return this;
    }

    public QuickMapReduce beforePreProcess(PreProcess preProc) {
        this.preProc = preProc;
        return this;
    }

    public QuickMapReduce beforeMidProcess(MidProcess midProc) {
        this.midProc = midProc;
        return this;
    }

    public QuickMapReduce beforePostProcess(PostProcess postProc) {
        this.postProc = postProc;
        return this;
    }

    public QuickMapReduce setProcessLog(ProcessLog processLog) {
        this.processLog = processLog;
        return this;
    }

    public QuickMapReduce execParallel() {
        this.opts = OptionExcecute.XPARALLEL;
        return this;
    }

    public QuickMapReduce execDefault() {

        this.opts = OptionExcecute.DEFAULT;
        return this;
    }

    public QuickMapReduce execNoCompressNoLock() {

        this.opts = OptionExcecute.XNOLOCKNOCOMP;

        return this;
    }

    public QuickMapReduce execNoLock() {

        this.opts = OptionExcecute.XNOLOCK;

        return this;
    }

    public QuickMapReduce execNoCompress() {
        this.opts = OptionExcecute.XNOCOMP;
        return this;
    }

    public QuickMapReduce execFullOptions() {
        opts = OptionExcecute.FULLOPTS;
        return this;
    }

    public QuickMapReduce execOnMemory() {
        this.tempPath = "";
        return this;
    }

    public QuickMapReduce execOnFile(String tmpPath) {
        if (Strings.isNullOrEmpty(tmpPath)||!new File(tmpPath).exists()||new File(tmpPath).isFile()) {
            throw new IllegalArgumentException("invalid tmpPath:"+tmpPath);
        }
        
        
        this.tempPath = tmpPath;
        return this;
    }

    public boolean execute(DB db) {
        return this.execute(db, tempPath, this.opts.getValue());
    }

    @Override
    public boolean log(String name, String msg) {
        return this.processLog.proccess(new LogMapReduce(name, msg));
//        return super.log(name, msg);
    }

    @Override
    public boolean postprocess() {
        this.postProc.prepare();
        return super.postprocess(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean midprocess() {
        this.midProc.prepare();
        return super.midprocess(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean preprocess() {
        this.preProc.prepare();
        return super.preprocess(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Accept a record or not. 
     * MapReduce will filter all records according to this implementing 
     * @param record need check
     * @return true if accept, else false will be return
     */
    public abstract boolean acceptRecord(Record record);

    /**
     * Produce a emitted record from accepted record.
     * Emitted record will commit to temporary database. It's use in mapping method
     * @param acceptedRecord accepted record
     * @return
     */
    public abstract Record produceEmittedRecordFromAcceptedRecord(Record acceptedRecord);

    /**
     * Implementing TODO with final record you will take after mapping finished.
     * It's use in reducing method
     * @param finalRecord final record
     * @see FinalRecord
     * @return true if access successfully, otherwise false 
     */
    public abstract boolean accessFinalRecord(FinalRecord finalRecord);

}
