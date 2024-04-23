//package kcdb.ss.gt.mapreduce;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//
//import kcdb.ss.gt.option.KcModeConnection;
//import kcdb.ss.gt.option.KcOptionStoring;
//import kcdb.ss.gt.db.KcBasicDB;
//import kcdb.ss.gt.db.KcFileHashDB;
//import kcdb.ss.gt.exception.InvalidParamsException;
//import kcdb.ss.gt.exception.KcDBException;
//import kcdb.ss.gt.exception.PermissonException;
//import kcdb.ss.gt.mapreduce.AbstractMapReduce.MidProcess;
//import java.io.File;
//import java.io.IOException;
//import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;
//import org.bouncycastle.util.Strings;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author k3v1n1k88
// */
//public class QuickMapReduceTest {
//    
//    private static final Logger logger = Logger.getLogger(QuickMapReduceTest.class);
//    private static final long MAXIMUM_NUMS_RECORDS = 2000000L;
//    
//    private static final String dir = "./tmp/kc_test";
//    private static final String dbName = "db_test";
//    
//    private static long count = 0;
//    
//    private static KcFileHashDB.Args args;
//
//    private static KcBasicDB instance = null;
//    
//    private QuickMapReduce mrh = null;
//
//    public QuickMapReduceTest() {
//        
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//        try {
//            
////            if (new File(dir).exists()) {
////                throw new IOException("setUpClass - a error happened when delete folder dir:" + dir);
////            }
//            if(!new File(dir).exists()){
//                if (!new File(dir).mkdirs()) {
//                    throw new IOException("setUpClass - a error happened when create folder directory:" + dir);
//                }
//                logger.info("setupClass - created directory :" + dir);
//            }
//            
//            else{
//                logger.info("setupClass - existed directory :" + dir);
//            }
//
//            long bnum = 33780214;
//            int fpow = 10;
//            long msiz = 1000;
//            int dfunit = 6;
//            int apow = 12;
//            String zkey = "admin";
//
//            args = new KcFileHashDB.Args(dir, dbName)
//                    .bucketNumber(bnum)
//                    .memorySize(msiz)
//                    .unitStep(dfunit)
//                    .alignmentPower(apow)
//                    .cipherKey(zkey)
//                    .freeBlockPoolPower(fpow)
//                    .optionStoring(KcOptionStoring.TSMALL)
//                    .acceptClearDB(true)
//                    .enableAllThrowException()
//                    .modeConnection(new KcModeConnection().createIfNotExist(true));
//            
//            instance = new KcFileHashDB(args);
//
//            boolean ret = instance.start();
//
//            assertTrue("Expected true but got false", ret);
//            if(instance.count()<2000000)
//                generateRecordsIntoDB();
//
//        } catch (IOException ex) {
//            fail("Unexpected exception IOException here when setup class.Detail:" + ex.getMessage());
//        } catch (InvalidParamsException ex) {
//            fail("Unexpected exception InvalidParamsException here when setup class.Detail:" + ex.getMessage());
//        } catch (KcDBException ex) {
//            fail("Unexpected exception KcDBException here when setup class.Detail:" + ex.getMessage());
//        } catch (PermissonException ex) {
//            fail("Unexpected exception PermissonException here when setup class.Detail:" + ex.getMessage());
//        }
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//        try {
//            
//            if(instance.isOpen()){
//                boolean ret = false;
////                boolean ret = instance.clear();
////                assertEquals(true, ret);
//                ret = instance.close();
//                assertEquals(true, ret);
//                
//            }
//            
////            FileUtils.deleteDirectory(new File(dir));
////
////            if (new File(dir).exists()) {
////                throw new IOException("tearDownClass - a error happened when delete folder nonExistedDir:" + dir);
////            }
////
////            logger.info("tearDownClass - deleted directory " + dir + ":" + !new File(dir).exists());
//
//        }catch (Exception ex){
//        
//        }
////        catch (IOException ex) {
////            logger.error("cannot delete directories after test");
////        }
//    }
//    
//    private static synchronized void increaseCount(){
//        count++;
//    }
//    
//    private static void generateRecordsIntoDB(){
//        
//        for(int i = 0; i<MAXIMUM_NUMS_RECORDS; i++){
//            instance.set("key "+i, String.valueOf(i));
//        }
//        
//        assertEquals(MAXIMUM_NUMS_RECORDS, instance.count());
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of setFilter method, of class MapReduceHelper.
//     */
//    @Test
//    public void testSetFilter() {
//        long start = System.nanoTime();
//        mrh = new QuickMapReduce() {
//            @Override
//            public boolean acceptRecord(QuickMapReduce.Record record) {
////                return true;
////                System.out.println("acceptRecord");
//                
//                long val = 0;
//                try {
//                    String strVal = Strings.fromByteArray(record.getValue());
//                    val = Long.valueOf(strVal);
//                    return val < 10000;
//                } catch (Exception ex) {
//                    return false;
//                }
//            }
//
//            @Override
//            public QuickMapReduce.Record produceEmittedRecordFromAcceptedRecord(QuickMapReduce.Record record) {
////                System.out.println("produceEmittedRecordFromAcceptedRecord");
////                this.log("sas", "produceEmittedRecordFromAcceptedRecord");
//                return record;
//            }
//
//            @Override
//            public boolean accessFinalRecord(QuickMapReduce.FinalRecord finalRecord) {
////                System.out.println("accessFinalRecord");
//                while(finalRecord.getIteratorValue().next()!= null){
//                    increaseCount();
//                }
//                
//                return true;
//            }
//        };
//        mrh.execOnMemory().execDefault().setProcessLog((QuickMapReduce.LogMapReduce log) -> {
//            System.out.println("process - " + log.name+":"+log.message);
//            return false;
//        }).execute(instance.getDb(), "./tmp", ExtendMapReduce.OptionExcecute.XNOLOCKNOCOMP.getValue());
//        
//        long end = System.nanoTime();
//        System.out.println("execute-time:"+(end-start));
//        assertEquals(10000, count);
//        
//    }
////
////    /**
////     * Test of setAccess method, of class MapReduceHelper.
////     */
////    @Test
////    public void testSetAccess() {
////        System.out.println("setAccess");
////        MapReduceHelper.Access access = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.setAccess(access);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setPreProc method, of class MapReduceHelper.
////     */
////    @Test
////    public void testSetPreProc() {
////        System.out.println("setPreProc");
////        MapReduceHelper.PreProcess preProc = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        instance.setPreProc(preProc);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setMidProc method, of class MapReduceHelper.
////     */
////    @Test
////    public void testSetMidProc() {
////        System.out.println("setMidProc");
////        MapReduceHelper.MidProcess midProc = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        instance.setMidProc(midProc);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setPostProc method, of class MapReduceHelper.
////     */
////    @Test
////    public void testSetPostProc() {
////        System.out.println("setPostProc");
////        MapReduceHelper.PostProcess postProc = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        instance.setPostProc(postProc);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setProcessLog method, of class MapReduceHelper.
////     */
////    @Test
////    public void testSetProcessLog() {
////        System.out.println("setProcessLog");
////        MapReduceHelper.ProcessLog processLog = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        instance.setProcessLog(processLog);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of map method, of class MapReduceHelper.
////     */
////    @Test
////    public void testMap() {
////        System.out.println("map");
////        byte[] key = null;
////        byte[] value = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        boolean expResult = false;
////        boolean result = instance.map(key, value);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of reduce method, of class MapReduceHelper.
////     */
////    @Test
////    public void testReduce() {
////        System.out.println("reduce");
////        byte[] key = null;
////        ValueIterator vi = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        boolean expResult = false;
////        boolean result = instance.reduce(key, vi);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execParallel method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecParallel() {
////        System.out.println("execParallel");
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.execParallel();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execDefault method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecDefault() {
////        System.out.println("execDefault");
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.execDefault();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execNoCompressNoLock method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecNoCompressNoLock() {
////        System.out.println("execNoCompressNoLock");
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.execNoCompressNoLock();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execNoLock method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecNoLock() {
////        System.out.println("execNoLock");
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.execNoLock();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execNoCompress method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecNoCompress() {
////        System.out.println("execNoCompress");
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.execNoCompress();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execFullOptions method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecFullOptions() {
////        System.out.println("execFullOptions");
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.execFullOptions();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execOnMemory method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecOnMemory() {
////        System.out.println("execOnMemory");
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.execOnMemory();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execOnFile method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecOnFile() {
////        System.out.println("execOnFile");
////        String tmpPath = "";
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper expResult = null;
////        MapReduceHelper result = instance.execOnFile(tmpPath);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of execute method, of class MapReduceHelper.
////     */
////    @Test
////    public void testExecute() {
////        System.out.println("execute");
////        DB db = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        boolean expResult = false;
////        boolean result = instance.execute(db);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of acceptRecord method, of class MapReduceHelper.
////     */
////    @Test
////    public void testAcceptRecord() {
////        System.out.println("acceptRecord");
////        MapReduceHelper.Record record = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        boolean expResult = false;
////        boolean result = instance.acceptRecord(record);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of produceEmittedRecordFromAcceptedRecord method, of class
////     * MapReduceHelper.
////     */
////    @Test
////    public void testProduceEmittedRecordFromAcceptedRecord() {
////        System.out.println("produceEmittedRecordFromAcceptedRecord");
////        MapReduceHelper.Record record = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        MapReduceHelper.Record expResult = null;
////        MapReduceHelper.Record result = instance.produceEmittedRecordFromAcceptedRecord(record);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of accessFinalRecord method, of class MapReduceHelper.
////     */
////    @Test
////    public void testAccessFinalRecord() {
////        System.out.println("accessFinalRecord");
////        MapReduceHelper.FinalRecord finalRecord = null;
////        MapReduceHelper instance = new MapReduceHelperImpl();
////        boolean expResult = false;
////        boolean result = instance.accessFinalRecord(finalRecord);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    public class MapReduceHelperImpl extends MapReduceHelper {
////
////        public boolean acceptRecord(Record record) {
////            return false;
////        }
////
////        public Record produceEmittedRecordFromAcceptedRecord(Record record) {
////            return null;
////        }
////
////        public boolean accessFinalRecord(FinalRecord finalRecord) {
////            return false;
////        }
////    }
//
//}
