/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db.task;

import kyotocabinet.DB;

/**
 *
 * @author k3v1n1k88
 */
public abstract class KcTransactionTask {

    /**
     * KcTransactionTask is a task implemented support for transaction task. It's ensure that all executing success. 
     * 
     * @param instanceDB instance db execute transaction
     * @return true if success, otherwise return false
     * @throws Exception exception if error happen. You should throw exception when implement this method. 
     * Because a transaction will be failed when catch error 
     */
    public abstract boolean executeTransactionTaskWith(DB instanceDB) throws Exception;
}
