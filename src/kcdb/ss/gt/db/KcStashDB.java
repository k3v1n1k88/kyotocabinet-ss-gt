/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import kcdb.ss.gt.option.KcDBType;
import kcdb.ss.gt.option.KcModeConnection;

/**
 *
 * @author k3v1n1k88
 */
public class KcStashDB extends KcBasicDB{
    
    public static class Args extends KcBasicDBArgs<Args>{
        
        public Args(String dir, String dbName) {
            super(dir); 
            this.dbFileName(KcDBType.STASH.getValue().concat(dbName));
        }
        
    }
    
    public KcStashDB(Args args) {
        super(args);
    }

    public KcStashDB(String dir, String dbName, KcModeConnection modeConnection, long bnum) {
        super(dir, KcDBType.STASH.getValue().concat(dbName), modeConnection, "bnum=".concat(String.valueOf(bnum)).concat("#"));
    }
    
    public KcStashDB(String dir, String dbName, KcModeConnection modeConnection) {
        this(dir, KcDBType.STASH.getValue().concat(dbName), modeConnection, DEFBNUM);
    }
    
    public KcStashDB(String dir, String dbName) {
        this(dir, KcDBType.STASH.getValue().concat(dbName), new KcModeConnection(), DEFBNUM);
    }
    
}
