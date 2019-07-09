/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.option;

import kyotocabinet.DB;
import org.apache.log4j.Logger;

/**
 *
 * @author k3v1n1k88
 */
public class KcModeConnection {

    private static final Logger logger = Logger.getLogger(KcModeConnection.class);

    boolean createIfNotExist = false;
    boolean forceCreate = false;
    boolean updateByTransaction = false;
    boolean updateSyncFile = false;
    boolean lockFile = true;
    boolean blockFile = true;
    boolean autoRepair = true;

    Mode modeConnection = Mode.READ_WRITE;

    public static enum Mode {

        ONLY_READ(DB.OREADER),
        READ_WRITE(DB.OWRITER);

        private int mode;

        private Mode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

    }

    public KcModeConnection() {
    }
    
    public KcModeConnection onlyRead() {
        this.modeConnection = Mode.ONLY_READ;
        return this;
    }
    
    public KcModeConnection readAndWrite() {
        this.modeConnection = Mode.READ_WRITE;
        return this;
    }

    public KcModeConnection mode(Mode mode) {
        this.modeConnection = mode;
        return this;
    }

    public KcModeConnection createIfNotExist(boolean bool) {
        this.createIfNotExist = bool;
        return this;
    }

    public KcModeConnection forceCreate(boolean bool) {
        this.forceCreate = bool;
        return this;
    }

    public KcModeConnection updateByTransaction(boolean bool) {
        this.updateByTransaction = bool;
        return this;
    }

    public KcModeConnection updateSyncFile(boolean bool) {
        this.updateSyncFile = bool;
        return this;
    }

    public KcModeConnection lockFile(boolean bool) {
        this.lockFile = bool;
        return this;
    }

    public KcModeConnection blockFile(boolean bool) {
        this.blockFile = bool;
        return this;
    }

    public KcModeConnection autoRepair(boolean bool) {
        this.autoRepair = bool;
        return this;
    }

    @Deprecated
    public static class Builder {
        
        private KcModeConnection modeConnection;
        
        public Builder(){
            modeConnection = new KcModeConnection();
        }
        
        public Builder modeConnection(Mode mode){
            modeConnection.modeConnection = mode;
            return this;
        }
        
        public Builder createIfNotExist(boolean bool){
            modeConnection.createIfNotExist = bool;
            return this;
        } 
        
        public Builder forceCreate(boolean bool){
            modeConnection.forceCreate = bool;
            return this;
        }
        
        public Builder updateByTransaction(boolean bool){
            modeConnection.updateByTransaction = bool;
            return this;
        }
        
        public Builder updateSyncFile(boolean bool){
            modeConnection.updateSyncFile = bool;
            return this;
        }
        
        public Builder lockFile(boolean bool){
            modeConnection.lockFile = bool;
            return this;
        }
        
        public Builder blockFile(boolean bool){
            modeConnection.blockFile = bool;
            return this;
        }
        
        public Builder autoRepair(boolean bool){
            modeConnection.autoRepair = bool;
            return this;
        }
        
        public KcModeConnection build(){
            logger.info("build a mode connection:"+this.modeConnection.toString());
            return this.modeConnection;
        }
    }
    public int toMode() {

        int ret = this.modeConnection.getMode();

        if (!lockFile) {
            ret = ret | DB.ONOLOCK;
        }

        if (!blockFile) {
            ret = ret | DB.OTRYLOCK;
        }

        if (!autoRepair) {
            ret = ret | DB.ONOREPAIR;
        }

        if (this.modeConnection == Mode.ONLY_READ) {
            logger.warn("skip options: \"createIfNotExist\", \"forceCreate\", \"updateByTransaction\", \"updateSyncFile\", because you are at mode only reading.");
        } else {

            if (createIfNotExist) {
                ret = ret | DB.OCREATE;
            }

            if (forceCreate) {
                ret = ret | DB.OTRUNCATE;
            }

            if (updateByTransaction) {
                ret = ret | DB.OAUTOTRAN;
            }

            if (updateSyncFile) {
                ret = ret | DB.OAUTOSYNC;
            }

        }
        return ret;
    }

    @Override
    public String toString() {
        return "Options{" + "createIfNotExist=" + createIfNotExist + ", forceCreate=" + forceCreate + ", updateByTransaction=" + updateByTransaction + ", updateSyncFile=" + updateSyncFile + ", lockFile=" + lockFile + ", blockFile=" + blockFile + ", autoRepair=" + autoRepair + ", modeConnection=" + modeConnection + '}';
    }

}
