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
public class KcProtoHashDB extends KcBasicDB{
    
    public static class Args extends KcBasicDB.KcBasicDBArgs<Args> {
        
        public Args(String dir, String dbName) {
            super(dir);
            this.dbFileName(KcDBType.PROTO_HASH.getValue().concat(dbName));
        }
        
    }
    
    public KcProtoHashDB(KcBasicDB.KcBasicDBArgs args) {
        super(args);
    }

    public KcProtoHashDB(String dir, String dbName, KcModeConnection modeConnection) {
        super(dir, KcDBType.PROTO_HASH.getValue().concat(dbName), modeConnection, "");
    }
}
