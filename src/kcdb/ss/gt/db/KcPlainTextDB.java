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
public class KcPlainTextDB extends KcBasicDB{
    
    public static class Args extends KcBasicDBArgs<Args> {
        
        public Args(String dir, String dbName) {
            super(dir);
            this.dbFileName(dbName.concat(KcDBType.PLAIN_TEXT.getValue()));
        }
        
    }
    
    public KcPlainTextDB(KcBasicDBArgs args) {
        super(args);
    }

    public KcPlainTextDB(String dir, String dbName, KcModeConnection modeConnection) {
        super(dir,dbName.concat(KcDBType.PLAIN_TEXT.getValue()) , modeConnection, "");
    }
}
