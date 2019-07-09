package kcdb.ss.gt.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.option.KcOptionStoring;
import kcdb.ss.gt.exception.InvalidParamsException;
import kcdb.ss.gt.exception.KcDBException;
import kcdb.ss.gt.exception.PermissonException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author k3v1n1k88
 */
public class KcHashDBTest {

    private static final Logger logger = Logger.getLogger(KcHashDBTest.class);

    private static final String dir = "./tmp/kc_test";
    private static final String nonExistedDir = "./tmp/kc_non_existed_test";
    private static final String dbName = "db_test";

    public KcHashDBTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        try {
            if (!new File(dir).mkdirs()) {
                throw new IOException("setUpClass - a error happened when create folder directory:" + dir);
            }

            FileUtils.deleteDirectory(new File(nonExistedDir));

            if (new File(nonExistedDir).exists()) {
                throw new IOException("setUpClass - a error happened when delete folder nonExistedDir:" + nonExistedDir);
            }

            logger.info("setupClass - created directory :" + dir);
            logger.info("setUpClass - deleted directory :" + nonExistedDir);

        } catch (IOException ex) {
            fail("Unexpected exception here when setup class.Detail:" + ex.getMessage());
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            FileUtils.deleteDirectory(new File(dir));
            FileUtils.deleteDirectory(new File(nonExistedDir));

            if (new File(dir).exists()) {
                throw new IOException("tearDownClass - a error happened when delete folder nonExistedDir:" + dir);
            }

            if (new File(nonExistedDir).exists()) {
                throw new IOException("tearDownClass - a error happened when delete folder nonExistedDir:" + nonExistedDir);
            }

            logger.info("tearDownClass - deleted directory " + dir + ":" + !new File(dir).exists());
            logger.info("tearDownClass - deleted nonExistedDir " + nonExistedDir + ":" + !new File(nonExistedDir).exists());

        } catch (IOException ex) {
            logger.error("cannot delete directories after test");
        }
    }

    @Before
    public void setUp() {
        new File(dir + "/" + dbName + ".kch").delete();
    }

    @After
    public void tearDown() {
        try {

            FileUtils.deleteDirectory(new File(nonExistedDir));

            if (new File(nonExistedDir).exists()) {
                throw new IOException("tearDown - have error happened when delete folder nonExistedDir:" + nonExistedDir);
            }

        } catch (IOException ex) {
            fail("Unexpected exception IOException here. Detail:" + ex.getMessage());
        }
    }

    /**
     * Test testOpenSuccessExistedDB
     */
    @Test
    public void testOpenSuccessExistedDB() {
        
        KcFileHashDB kcdb = null;
        
        try {
            new File(dir + "/" + dbName + ".kch").createNewFile();

            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName);
            kcdb = new KcFileHashDB(args);

            boolean ret = kcdb.start();

            assertTrue("expected true but got false", ret);
            assertTrue("expected true but got false", kcdb.isOpen());
            
            kcdb.close();

        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
            System.out.println(ex.getMessage() + ":" + ex);
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
            System.out.println(ex.getMessage() + ":" + ex);
        } catch (IOException ex) {
            fail("Cannot set up before test");
        } catch (InvalidParamsException ex) {
            fail("Unexpected exception InvalidParamsException here");
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }

    /**
     * testOpenSuccessNonExistedDB
     */
    @Test
    public void testOpenSuccessNonExistedDB() {
        
        KcFileHashDB kcdb = null;
        
        try {

            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            boolean ret = kcdb.start();

            assertTrue("expected true but got false", ret);
            assertTrue("expected true but got false", kcdb.isOpen());
            
            kcdb.close();

        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            java.util.logging.Logger.getLogger(KcHashDBTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }

    /**
     * testOpenSuccessNonExistedDB
     */
    @Test
    public void testOpenSuccessNonExistedDBFullValidParams() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 33780;
            int fpow = 10;
            long msiz = 1000;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";
            
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            boolean ret = kcdb.start();

            assertTrue("expected true but got false", ret);
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            fail("Unexpected exception InvalidParamsException here");
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    /**
     * test with freeBBlockPoolPower < 0
     */
    @Test
    public void testOpenFailWithInvalidFpowSmallerThanZ() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 33780;
            int fpow = -11;
            long msiz = 1000;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";
            
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            boolean ret = kcdb.start();

            fail("Expected exception InvalidParamsException here");
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    /**
     * test with freeBlockPoolPower over MAXFPOW 
     */
    @Test
    public void testOpenFailWithInvalidFpowOverMAXFPOW() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 33780;
            int fpow = 67;
            long msiz = 1000;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";
            
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            boolean ret = kcdb.start();

            fail("Expected exception InvalidParamsException here");
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    /**
     * test with freeBBlockPoolPower < 0
     */
    @Test
    public void testOpenFailWithInvalidApowSmallerThanZ() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 33780;
            int fpow = -11;
            long msiz = 1000;
            int dfunit = 6;
            int apow = -21;
            String zkey = "admin";
            
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            boolean ret = kcdb.start();

            fail("Expected exception InvalidParamsException here");
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    /**
     * test with freeBlockPoolPower over MAXFPOW 
     */
    @Test
    public void testOpenFailWithInvalidFpowOverMAXAPOW() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 33780;
            int fpow = 12;
            long msiz = 1000;
            int dfunit = 6;
            int apow = 343;
            String zkey = "admin";
            
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            boolean ret = kcdb.start();

            fail("Expected exception InvalidParamsException here");
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    
    /**
     * test with freeBBlockPoolPower < 0
     */
    @Test
    public void testOpenFailWithInvalidDfunitSmallerThanZ() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 33780;
            int fpow = 11;
            long msiz = 1000;
            int dfunit = -6;
            int apow = 21;
            String zkey = "admin";
            
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            kcdb.start();

            fail("Expected exception InvalidParamsException here");
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
   
    
    
    
     /**
     * testOpenSuccessNonExistedDB
     */
    @Test
    public void testOpenFailWithInvalidBnum() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = -33780;
            int fpow = 11;
            long msiz = 1000;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";
            
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            boolean ret = kcdb.start();

            fail("Expected exception InvalidParamsException here");
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    @Test
    public void testOpenFailWithInvalidMsiz() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = -33780;
            int fpow = 11;
            long msiz = -12;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";
            
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            kcdb = new KcFileHashDB(args);

            kcdb.start();

            fail("Expected exception InvalidParamsException here");
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    
    @Test
    public void testWriteSuccessWhenOpenByReadWriteModeConnection() {
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 3780;
            int fpow = 11;
            long msiz = 12;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";
            
            String k = "test";
            String v = "test";
            
            
            // preprocess create db
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true).readAndWrite());
            kcdb = new KcFileHashDB(args);

            kcdb.start();
            
            boolean ret = kcdb.set(k, v);

            assertTrue("Expected true but got false", ret);
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            fail("Unexpected exception InvalidParamsException here");
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    @Test
    public void testWriteFailWhenOpenByReadOnlyModeConnection() {
        
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 3780;
            int fpow = 11;
            long msiz = 12;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";
            
            String k = "test";
            String v = "test";
            
            
            // preprocess create db
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .modeConnection(new KcModeConnection().createIfNotExist(true).readAndWrite());
            kcdb = new KcFileHashDB(args);

            kcdb.start();
            kcdb.close();
            
            // re-open db
            kcdb = new KcFileHashDB(args.modeConnection(new KcModeConnection().onlyRead()));
            kcdb.start();
            
            boolean ret = kcdb.set(k, v);

            assertFalse("Expected false but got true", ret);
            
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here");
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            fail("Unexpected exception InvalidParamsException here");
        } finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    
    @Test
    public void testThrowExceptionWhenSetupThrowAllException() {
        
        KcFileHashDB kcdb = null;
        
        try {

            long bnum = 3780;
            int fpow = 11;
            long msiz = 12;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";
            
            String k = "test";
            String v = "test";
            
            
            // preprocess create db
            KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .enableAllThrowException()
                    .modeConnection(new KcModeConnection().createIfNotExist(true).onlyRead());
            kcdb = new KcFileHashDB(args);

            kcdb.start();
            
            fail("Expected exception kyotocabinet.Error here");
            
        } catch (KcDBException ex) {
            
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here");
        } catch (InvalidParamsException ex) {
            fail("Unexpected exception InvalidParamsException here");
        } catch(kyotocabinet.Error ex){
        
        } 
        finally {
            if(kcdb!=null)
                kcdb.close();
        }
    }
    
    
}
