/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.mapreduce;

import kcdb.ss.gt.db.KcBasicDB;
import kyotocabinet.DB;
import kyotocabinet.MapReduce;

/**
 *
 * @author k3v1n1k88
 */
public abstract class ExtendMapReduce extends MapReduce{
    
    public static enum OptionExcecute {
        XNOLOCK(1),                    ///< avoid locking against update operations
        XPARAMAP(1 << 1),                   ///< run mappers in parallel
        XPARARED(1 << 2),                   ///< run reducers in parallel
        XPARAFLS(1 << 3),                   ///< run cache flushers in parallel
        XNOCOMP(1 << 8),                     ///< avoid compression of temporary databases
        XPARALLEL(XPARAMAP.opt|XPARARED.opt|XPARAFLS.opt),
        XNOLOCKNOCOMP(XNOLOCK.opt|XNOCOMP.opt),
        FULLOPTS(XPARALLEL.opt|XNOLOCKNOCOMP.opt),
        DEFAULT(0);
        
        int opt;
        
        OptionExcecute(int opt){
            this.opt = opt;
        }

        public int getValue() {
            return opt;
        }

        public void setOpt(int opt) {
            this.opt = opt;
        }
        
    };
    
    public boolean execute(DB db, String tempPath, OptionExcecute options){
        return this.execute(db, tempPath, options.getValue());
    }
    
}
