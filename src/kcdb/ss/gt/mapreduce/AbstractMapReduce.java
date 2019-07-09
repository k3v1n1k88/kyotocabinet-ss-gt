/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.mapreduce;

import kyotocabinet.ValueIterator;

/**
 *
 * @author k3v1n1k88
 */
public abstract class AbstractMapReduce extends ExtendMapReduce{
    
    
    public interface Filter {
        public boolean accept(Record record);
    }

    public interface Access {

        public Record produceEmittedRecordFromAcceptedRecordImpl(Record record);

        public boolean accessFinalRecordImpl(FinalRecord finalRecord);
    }
    
    public interface MidProcess {
        public void prepare();
    }
    
    public interface PreProcess {
        public void prepare();
    }
    
    public interface PostProcess {
        public void prepare();
    }
    
    public interface ProcessLog{
        public boolean proccess(LogMapReduce log);
    }

    public class FinalRecord {

        byte[] key;
        ValueIterator iteratorValue;

        public FinalRecord(byte[] key, ValueIterator iteratorValue) {
            this.key = key;
            this.iteratorValue = iteratorValue;
        }

        public byte[] getKey() {
            return key;
        }

        public void setKey(byte[] key) {
            this.key = key;
        }

        public ValueIterator getIteratorValue() {
            return iteratorValue;
        }

        public void setIteratorValue(ValueIterator iteratorValue) {
            this.iteratorValue = iteratorValue;
        }

    }

    public class Record {

        byte[] key;
        byte[] value;

        public Record(byte[] key, byte[] value) {
            this.key = key;
            this.value = value;
        }

        public byte[] getKey() {
            return key;
        }

        public void setKey(byte[] key) {
            this.key = key;
        }

        public byte[] getValue() {
            return value;
        }

        public void setValue(byte[] value) {
            this.value = value;
        }
    }
    
    public class LogMapReduce{
        
        String name;
        String message;

        public LogMapReduce(String name, String message) {
            this.name = name;
            this.message = message;
        }
        

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }   
    }
    

    
}
