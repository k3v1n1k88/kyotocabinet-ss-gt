package kcdb.ss.gt.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import kcdb.ss.gt.option.KcModeConnection;
import kcdb.ss.gt.option.KcOptionStoring;
import kcdb.ss.gt.db.task.KcTransactionTask;
import kcdb.ss.gt.exception.InvalidParamsException;
import kcdb.ss.gt.exception.KcDBException;
import kcdb.ss.gt.exception.PermissonException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kyotocabinet.Cursor;
import kyotocabinet.DB;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.util.Strings;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author k3v1n1k88
 */
public class KcBasicDBTest {

    private static final Logger logger = Logger.getLogger(KcBasicDBTest.class);

    private static final String dir = "./tmp/kc_test";
    private static final String nonExistedDir = "./tmp/kc_non_existed_test";
    private static final String dbName = "db_test";
    
    private static KcFileHashDB.Args args;

    private static KcBasicDB instance = null;

    public KcBasicDBTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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

            long bnum = 33780;
            int fpow = 10;
            long msiz = 1000;
            int dfunit = 6;
            int apow = 12;
            String zkey = "admin";

            args = new KcFileHashDB.Args(dir, dbName)
                    .bucketNumber(bnum)
                    .memorySize(msiz)
                    .unitStep(dfunit)
                    .alignmentPower(apow)
                    .cipherKey(zkey)
                    .freeBlockPoolPower(fpow)
                    .optionStoring(KcOptionStoring.TSMALL)
                    .acceptClearDB(true)
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
            instance = new KcFileHashDB(args);

            boolean ret = instance.start();

            assertTrue("Expected true but got false", ret);

        } catch (IOException ex) {
            fail("Unexpected exception IOException here when setup class.Detail:" + ex.getMessage());
        } catch (InvalidParamsException ex) {
            fail("Unexpected exception InvalidParamsException here when setup class.Detail:" + ex.getMessage());
        } catch (KcDBException ex) {
            fail("Unexpected exception KcDBException here when setup class.Detail:" + ex.getMessage());
        } catch (PermissonException ex) {
            fail("Unexpected exception PermissonException here when setup class.Detail:" + ex.getMessage());
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
    public void setUp() throws Exception {
        try {
            if (!instance.isOpen()) {
                instance.start();
            }
            boolean ret = instance.clear();
            Assert.assertEquals(ret, true);
            Assert.assertEquals(0L, instance.count());
            //logger.info("setup - "+ret);
        } catch (KcDBException ex) {
            fail("Unexpected KcDBException here:" + ex.getMessage());
        } catch (PermissonException ex) {
            fail("Unexpected PermissonException here:" + ex.getMessage());
        }
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
     * Test of countAllRecords method, of class KcBasicDB.
     */
    @Test
    public void testCount() {
        try {

            String key1 = "test1";
            String val1 = "test1";
            String key2 = "test2";
            String val2 = "test2";

            long currentNumsRecord = instance.count();

            boolean ret = false;
            long addedRecords = 0;

            ret = instance.add(key1, val1);
            if (ret == true) {
                addedRecords++;
            }

            ret = instance.add(key2, val2);
            if (ret == true) {
                addedRecords++;
            }

            long afterCountRecords = addedRecords + currentNumsRecord;
            long expectedCountRecords = 2 + currentNumsRecord;

            assertTrue("Expected " + (2 + currentNumsRecord) + " but got " + (addedRecords + currentNumsRecord), afterCountRecords == expectedCountRecords);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of check method, of class KcBasicDB.
     */
    @Test
    public void testCheck_byteArr() {
        try {

            byte[] key = Strings.toByteArray("test");
            byte[] val = Strings.toByteArray("test");

            boolean ret = instance.set(key, val);

            assertTrue("Expected true when set but got false", ret == true);

            long nums = instance.check(key);

            assertTrue("Expected true but got false", nums > 0);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of check method, of class KcBasicDB.
     */
    @Test
    public void testCheck_String() {
        try {

            String key = "test";
            String val = "test";

            boolean ret = instance.set(key, val);

            assertTrue("Expected true when set but got false", ret == true);

            long nums = instance.check(key);

            assertTrue("Expected true but got false", nums > 0);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of cas method, of class KcBasicDB.
     */
    @Test
    public void testCas_3args_byte() {

        try {

            byte[] key = Strings.toByteArray("test");
            byte[] oval = Strings.toByteArray("test");
            byte[] nval = Strings.toByteArray("test2");

            boolean ret = instance.set(key, oval);

            assertTrue("Expected true when set but got false", ret == true);

            ret = instance.cas(key, oval, nval);

            assertTrue("Expected true when cas but got false", ret == true);

            byte[] val = instance.get(key);

            Assert.assertArrayEquals(val, nval);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of cas method, of class KcBasicDB.
     */
    @Test
    public void testCas_3args_2() {
        try {

            String key = "test";
            String oval = "test";
            String nval = "test2";

            boolean ret = instance.set(key, oval);

            assertTrue("Expected true when set but got false", ret == true);

            ret = instance.cas(key, oval, nval);

            assertTrue("Expected true when cas but got false", ret == true);

            String val = instance.get(key);

            Assert.assertEquals(val, nval);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of add method, of class KcBasicDB.
     */
    @Test
    public void testAddStringNonExistedRecords() {
        try {

            String key = "test";
            String val = "test";

            boolean ret = instance.add(key, val);

            assertTrue("Expected true when set but got false", ret == true);

            String v = instance.get(key);

            assertEquals(v, val);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of add method, of class KcBasicDB.
     */
    @Test
    public void testAddStringExistedRecords() {
        try {

            String key = "test1";
            String val1 = "test1";

            String val2 = "test2";

            boolean ret = false;

            //add records
            instance.add(key, val1);
            ret = instance.add(key, val2);

            assertEquals(ret, false);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of add method, of class KcBasicDB.
     */
    @Test
    public void testAddByteNonExistedRecord() {
        try {

            byte[] key = Strings.toByteArray("test");
            byte[] val = Strings.toByteArray("test");

            boolean ret;

            //add records
            ret = instance.add(key, val);

            assertEquals(ret, true);

            //get record 
            byte[] v = instance.get(key);

            Assert.assertArrayEquals(v, val);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of append method, of class KcBasicDB.
     */
    @Test
    public void testAddByteExistedRecord() {
        try {

            byte[] key = Strings.toByteArray("test");
            byte[] val1 = Strings.toByteArray("test1");

            byte[] val2 = Strings.toByteArray("test2");

            boolean ret;

            //add records
            instance.add(key, val1);

            ret = instance.add(key, val2);
            assertEquals(ret, false);

            //get record             
            byte[] v = instance.get(key);

            Assert.assertArrayEquals(v, val1);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of append method, of class KcBasicDB.
     */
    @Test
    public void testAppendByte() {
        try {

            byte[] key = Strings.toByteArray("test");
            byte[] val1 = Strings.toByteArray("test1");
            byte[] val2 = Strings.toByteArray("test2");

            boolean ret;

            //add records
            ret = instance.append(key, val1);
            assertEquals(ret, true);

            ret = instance.append(key, val2);
            assertEquals(ret, true);

            //get record             
            byte[] v = instance.get(key);

            Assert.assertArrayEquals(v, Bytes.concat(val1, val2));

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of append method, of class KcBasicDB.
     */
    @Test
    public void testAppendString() {
        try {

            String key = "test";
            String val1 = "test1";
            String val2 = "test2";

            boolean ret;

            //add records
            ret = instance.append(key, val1);
            assertEquals(ret, true);

            ret = instance.append(key, val2);
            assertEquals(ret, true);

            //get record             
            String v = instance.get(key);

            assertEquals(v, val1.concat(val2));

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of get method, of class KcBasicDB.
     */
    @Test
    public void testGet_String() {
        try {

            String key = "test";
            String val = "test";

            boolean ret;

            //add records
            ret = instance.append(key, val);
            assertEquals(ret, true);

            //get record             
            String v = instance.get(key);

            assertEquals(v, val);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of get method, of class KcBasicDB.
     */
    @Test
    public void testGetByteExistedRecord() {
        try {

            byte[] key = Strings.toByteArray("test");
            byte[] val = Strings.toByteArray("test");

            boolean ret;

            //add records
            ret = instance.append(key, val);
            assertEquals(ret, true);

            //get record             
            byte[] v = instance.get(key);

            Assert.assertArrayEquals(v, val);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of get method, of class KcBasicDB.
     */
    @Test
    public void testGetByteNonExistedRecord() {
        try {

            byte[] key = Strings.toByteArray("test");

            boolean ret;

            //get record             
            byte[] v = instance.get(key);

            Assert.assertArrayEquals(v, null);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of set method, of class KcBasicDB.
     */
    @Test
    public void testSetString() {
        try {

            String key = "test";
            String val = "test";

            boolean ret;

            //add records
            ret = instance.set(key, val);
            assertEquals(ret, true);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of set method, of class KcBasicDB.
     */
    @Test
    public void testSetByte() {
        try {

            byte[] key = Strings.toByteArray("test");
            byte[] val = Strings.toByteArray("test");

            boolean ret;

            //add records
            ret = instance.set(key, val);
            assertEquals(ret, true);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of clear method, of class KcBasicDB.
     */
    @Test
    public void testClear() {
        try {

            byte[] key1 = Strings.toByteArray("test1");
            byte[] val1 = Strings.toByteArray("test");

            byte[] key2 = Strings.toByteArray("test2");
            byte[] val2 = Strings.toByteArray("test2");

            byte[] key3 = Strings.toByteArray("test3");
            byte[] val3 = Strings.toByteArray("test3");

            byte[] key4 = Strings.toByteArray("test4");
            byte[] val4 = Strings.toByteArray("test4");

            boolean ret;

            //add records
            ret = instance.set(key1, val1);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key2, val2);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key3, val3);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key4, val4);
            assertEquals(ret, true);

            long numsRec = instance.count();
            assertEquals(numsRec, 4);

            ret = instance.clear();
            assertEquals(ret, true);

            numsRec = instance.count();
            assertEquals(numsRec, 0);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of close method, of class KcBasicDB.
     */
    @Test
    public void testClose() {
        try {

            boolean ret;

            ret = instance.close();

            assertEquals(ret, true);
            assertEquals(instance.isOpen(), false);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of cursor method, of class KcBasicDB.
     */
    @Test
    public void testCursor() {
        try {

            String key1 = "test1";
            String val1 = "test1";

            String key2 = "test2";
            String val2 = "test2";

            String key3 = "test3";
            String val3 = "test3";

            String key4 = "test4";
            String val4 = "test4";

            boolean ret;

            //add records
            ret = instance.set(key1, val1);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key2, val2);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key3, val3);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key4, val4);
            assertEquals(ret, true);

            long numsRec = instance.count();
            assertEquals(4, numsRec);

            Cursor c = instance.cursor();
            c.jump();

            int count = 0;
            do {

                count++;

            } while (c.step());

            assertEquals(4, count);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of dumpSnapshot method, of class KcBasicDB.
     */
    @Test
    public void testDumpSnapshot() {

        String fileSnapshot = "./tmp/dump.kch";
        boolean existed = false;

        try {

            String key1 = "test1";
            String val1 = "test1";

            String key2 = "test2";
            String val2 = "test2";

            String key3 = "test3";
            String val3 = "test3";

            String key4 = "test4";
            String val4 = "test4";

            boolean ret;

            //add records
            ret = instance.set(key1, val1);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key2, val2);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key3, val3);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key4, val4);
            assertEquals(ret, true);

            ret = instance.dump_snapshot(fileSnapshot);

            System.out.println("existed dir:" + new File(fileSnapshot).exists());

            System.out.println("err:" + instance.error());

            assertEquals(true, ret);

            existed = new File(fileSnapshot).exists();

            assertEquals(true, existed);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        } finally {
            new File(fileSnapshot).deleteOnExit();
        }
    }

    /**
     * Test of beginTransaction method, of class KcBasicDB.
     */
    @Test
    public void testTransactionSuccess() {

        try {

            instance.begin_transaction(true);

            String key1 = "test1";
            String val1 = "test1";

            String key2 = "test2";
            String val2 = "test2";

            String key3 = "test3";
            String val3 = "test3";

            String key4 = "test4";
            String val4 = "test4";

            boolean ret;

            //add records
            ret = instance.set(key1, val1);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key2, val2);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key3, val3);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key4, val4);
            assertEquals(ret, true);

            instance.end_transaction(true);

            long numsRec = instance.count();
            assertEquals(4, numsRec);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of beginTransaction method, of class KcBasicDB.
     */
    @Test
    public void testTransactionFail() {

        try {

            instance.begin_transaction(true);

            String key1 = "test1";
            String val1 = "test1";

            String key2 = "test1";
            String val2 = "test1";

            String key3 = "test3";
            String val3 = "test3";

            String key4 = "test4";
            String val4 = "test4";

            boolean ret;

            //add records
            ret = instance.set(key1, val1);
            assertEquals(ret, true);

            instance.close();

            ret = instance.set(key1, val1);
            assertEquals(ret, false);

            fail("Expected exception here");

        } catch (Exception ex) {
            //logger.error(ex.getMessage(), ex);
        } finally {
            try {
                instance.start();
                long numsRec = instance.count();
                assertEquals(0, numsRec);
            } catch (KcDBException ex) {
                fail("Unexpected KcDBException exception here:" + ex.getMessage());
            } catch (PermissonException ex) {
                fail("Unexpected PermissonException exception here:" + ex.getMessage());
            } catch (Exception ex) {
                fail("testTransactionFail - Unexpected exception here when try to start db to count:" + ex.getMessage());
            }
        }
    }

    /**
     * Test of getBulk method, of class KcBasicDB.
     */
    @Test
    public void testGetBulk_byteArrArr_boolean() {

        try {

            byte[] key1 = Strings.toByteArray("test1");
            byte[] val1 = Strings.toByteArray("test1");

            byte[] key2 = Strings.toByteArray("test2");
            byte[] val2 = Strings.toByteArray("test2");

            byte[] key3 = Strings.toByteArray("test3");
            byte[] val3 = Strings.toByteArray("test3");

            byte[] key4 = Strings.toByteArray("test4");
            byte[] val4 = Strings.toByteArray("test4");

            byte[][] testKeys = {key1, key2, key3, key4};
            byte[][] testValues = {val1, val2, val3, val4};

            byte[][] expectedResult = {key1, val1, key2, val2, key3, val3, key4, val4};

            boolean ret;

            //add records
            ret = instance.set(testKeys[0], testValues[0]);
            assertEquals(ret, true);

            //add records
            ret = instance.set(testKeys[1], testValues[1]);
            assertEquals(ret, true);

            //add records
            ret = instance.set(testKeys[2], testValues[2]);
            assertEquals(ret, true);

            //add records
            ret = instance.set(testKeys[3], testValues[3]);
            assertEquals(ret, true);

            long numsRec = instance.count();
            assertEquals(4, numsRec);

            byte[][] bulk = instance.get_bulk(testKeys, true);

            Assert.assertArrayEquals(expectedResult, bulk);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of getBulk method, of class KcBasicDB.
     */
    @Test
    public void testGetBulk_List_boolean() {
        try {

            String key1 = "test1";
            String val1 = "test1";

            String key2 = "test2";
            String val2 = "test2";

            String key3 = "test3";
            String val3 = "test3";

            String key4 = "test4";
            String val4 = "test4";

            List<String> listKeys = Arrays.asList(key1, key2, key3, key4);
            List<String> listValues = Arrays.asList(val1, val2, val3, val4);

            Map<String, String> expectedResult = IntStream.range(0, listKeys.size())
                    .boxed()
                    .collect(Collectors.toMap(i -> listKeys.get(i), i -> listValues.get(i)));

            boolean ret;

            //add records
            ret = instance.set(key1, val1);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key2, val2);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key3, val3);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key4, val4);
            assertEquals(ret, true);

            long numsRec = instance.count();
            assertEquals(4, numsRec);

            Map<String, String> bulk = instance.get_bulk(listKeys, false);

            Assert.assertEquals(expectedResult, bulk);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of incrementDouble method, of class KcBasicDB.
     */
    @Test
    public void testIncrementDouble_3args_1() {
        try {

            String key = "key1";
            Double orig = 2.;
            Double addition = 1.5;
            Double delta = 0.000001;

            Double ret;

            //add record
            ret = instance.increment_double(key, addition, orig);
            assertEquals(ret, orig + addition, delta);

            //get record
            assertEquals(ret, orig + addition, delta);

            //add 2rd record
            ret = instance.increment_double(key, addition, orig);
            assertEquals(ret, orig + 2 * addition, delta);

        } catch (NumberFormatException ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of incrementDouble method, of class KcBasicDB.
     */
    @Test
    public void testIncrementDouble_3args_2() {
        try {

            byte[] key = Strings.toByteArray("key");
            Double orig = 0.;
            Double addition = 1.;
            Double delta = 0.000001;

            Double ret;

            //add record
            ret = instance.increment_double(key, addition, orig);
            assertEquals(ret, orig + addition, delta);

            //get record
            assertEquals(ret, orig + addition, delta);

            //add 2rd record
            ret = instance.increment_double(key, addition, orig);
            assertEquals(ret, orig + 2 * addition, delta);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of increment method, of class KcBasicDB.
     */
    @Test
    public void testIncrement_3args_1() {
        try {

            String key = "key1";
            long orig = 2L;
            long addition = -1L;

            long ret;

            //add record
            ret = instance.increment(key, addition, orig);
            assertEquals(ret, orig + addition);

            //2rd increase
            ret = instance.increment(key, addition, orig);
            assertEquals(ret, orig + 2 * addition);

            //get record 
            ret = Longs.fromByteArray(instance.get(Strings.toByteArray(key)));
            assertEquals(ret, orig + 2 * addition);

        } catch (NumberFormatException ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of increment method, of class KcBasicDB.
     */
    @Test
    public void testIncrement_3args_2() {
        try {

            byte[] key = Strings.toByteArray("key1");
            long orig = 2L;
            long addition = 1L;

            long ret;

            //add record
            ret = instance.increment(key, addition, orig);
            assertEquals(ret, orig + addition);

            //2rd increase
            ret = instance.increment(key, addition, orig);
            assertEquals(ret, orig + 2 * addition);

            //get record 
            ret = Longs.fromByteArray(instance.get(key));
            assertEquals(ret, orig + 2 * addition);

        } catch (NumberFormatException ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of iterate method, of class KcBasicDB.
     */
    @Test
    public void testIterate() {

    }

    /**
     * Test of loadSnapshot method, of class KcBasicDB.
     */
    @Test
    public void testLoadSnapshot() {
    }

    /**
     * Test of matchPrefix method, of class KcBasicDB.
     */
    @Test
    public void testMatchPrefix() {
        try {

            String key1 = "test1";
            String val1 = "test1";

            String key2 = "test2";
            String val2 = "test2";

            String key3 = "test3";
            String val3 = "test3";

            String key4 = "test4";
            String val4 = "test4";

            boolean ret;

            //add records
            ret = instance.set(key1, val1);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key2, val2);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key3, val3);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key4, val4);
            assertEquals(ret, true);

            List<String> l = instance.match_prefix("test", -1);
            assertEquals(4, l.size());

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of matchRegex method, of class KcBasicDB.
     */
    @Test
    public void testMatchRegex() {
        try {

            String key1 = "test1";
            String val1 = "test1";

            String key2 = "test2";
            String val2 = "test2";

            String key3 = "test3";
            String val3 = "test3";

            String key4 = "test4";
            String val4 = "test4";

            boolean ret;

            //add records
            ret = instance.set(key1, val1);
            assertEquals(ret, true);

            //add records
            ret = instance.set(key2, val2);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key3, val3);
            assertEquals(ret, true);
            //add records
            ret = instance.set(key4, val4);
            assertEquals(ret, true);

            List<String> l = instance.match_regex("te", -1);
            assertEquals(4, l.size());

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of matchSimilar method, of class KcBasicDB.
     */
    @Test
    public void testMatchSimilar() {
    }

    /**
     * Test of size method, of class KcBasicDB.
     */
    @Test
    public void testSize() {
    }

    /**
     * Test of seize method, of class KcBasicDB.
     */
    @Test
    public void testSeize_byteArr() {
        try {

            byte[] key = Strings.toByteArray("test1");
            byte[] val = Strings.toByteArray("test1");

            boolean ret;

            //add records
            ret = instance.set(key, val);
            assertEquals(true, ret);

            byte[] v = instance.seize(key);
            Assert.assertArrayEquals(val, v);

            v = instance.get(key);
            Assert.assertArrayEquals(null, v);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of seize method, of class KcBasicDB.
     */
    @Test
    public void testSeize_String() {
        try {

            String key = "test1";
            String val = "test1";

            boolean ret;

            //add records
            ret = instance.set(key, val);
            assertEquals(true, ret);

            String v = instance.seize(key);
            Assert.assertEquals(val, v);

            v = instance.get(key);
            Assert.assertEquals(null, v);

        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of getStatus method, of class KcBasicDB.
     */
    @Test
    public void testGetStatus() {
        try {
            Map<String, String> status = instance.status();
            assertNotEquals(null, status);
        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of synchronize method, of class KcBasicDB.
     */
    @Test
    public void testSynchronize() {
    }

    /**
     * Test of tune_exception_rule method, of class KcBasicDB.
     */
    @Test
    public void testTune_exception_rule() {
    }

    /**
     * Test of tune_encoding method, of class KcBasicDB.
     */
    @Test
    public void testTune_encoding() {
    }

    /**
     * Test of isAcceptClearDB method, of class KcBasicDB.
     */
    @Test
    public void testIsAcceptClearDB() {
        try {
            boolean ret = instance.clear();
            assertEquals(true, ret);
        } catch (Exception ex) {
            fail("Unexpected exception here:" + ex.getMessage());
        }
    }

    /**
     * Test of setAcceptClearDB method, of class KcBasicDB.
     */
    @Test
    public void testSetAcceptClearDB() {
    }

    /**
     * Test of setModeConnection method, of class KcBasicDB.
     */
    @Test
    public void testSetModeConnection() {
    }

    /**
     * Test of setLogType method, of class KcBasicDB.
     */
    @Test
    public void testSetLogType() {
    }

    /**
     * Test of setPrefixLog method, of class KcBasicDB.
     */
    @Test
    public void testSetPrefixLog() {
    }

    /**
     * Test of isAcceptCreateFolder method, of class KcBasicDB.
     */
    @Test
    public void testIsAcceptCreateFolder() {
    }

    /**
     * Test of setAcceptCreateFolder method, of class KcBasicDB.
     */
    @Test
    public void testSetAcceptCreateFolder() {
    }

    /**
     * Test of setRetryCloseDB method, of class KcBasicDB.
     */
    @Test
    public void testSetRetryCloseDB() {
    }

    /**
     * Test of getRetryCloseDB method, of class KcBasicDB.
     */
    @Test
    public void testGetRetryCloseDB() {
    }

    /**
     * Test of setLogKind method, of class KcBasicDB.
     */
    @Test
    public void testSetLogKind() {
    }

    /**
     * Test of setLogPrefix method, of class KcBasicDB.
     */
    @Test
    public void testSetLogPrefix() {
    }

    /**
     * Test of setEnableAllThrowException method, of class KcBasicDB.
     */
    @Test
    public void testSetEnableAllThrowException() {
    }

    /**
     * Test of setLogDir method, of class KcBasicDB.
     */
    @Test
    public void testSetLogDir() {
    }

    @Test
    public void testExecTransTaskFail() {
        try {
            instance.execTransTask(new KcTransactionTask() {
                @Override
                public boolean executeTransactionTaskWith(DB instanceDB) {
                    
                    String key1 = "test1";
                    String val1 = "test1";

                    String key2 = "test2";
                    String val2 = "test2";

                    String key3 = "test3";
                    String val3 = "test3";

                    String key4 = "test4";
                    String val4 = "test4";

                    boolean ret;

                    //add records
                    ret = instance.set(key1, val1);
                    assertEquals(ret, true);

                    //add records
                    ret = instance.set(key2, val2);
                    assertEquals(ret, true);

                    //add records
                    ret = instance.set(key3, val3);
                    assertEquals(ret, true);

                    //add records
                    ret = instance.set(key4, val4);
                    assertEquals(ret, true);

                    // error
                    Long.valueOf("sjhd");

                    return true;
                }
            }, true);
            fail("Expected exception here, but can not got it");
        } catch (Exception ex) {
            
        } finally{
            assertEquals(0, instance.count());
        }
    }
    
    
    @Test
    public void testExecTransTaskSuccess() {
        try {
            instance.execTransTask(new KcTransactionTask() {
                @Override
                public boolean executeTransactionTaskWith(DB instanceDB) {
                    
                    String key1 = "test1";
                    String val1 = "test1";

                    String key2 = "test2";
                    String val2 = "test2";

                    String key3 = "test3";
                    String val3 = "test3";

                    String key4 = "test4";
                    String val4 = "test4";

                    boolean ret;

                    //add records
                    ret = instance.set(key1, val1);
                    assertEquals(ret, true);

                    //add records
                    ret = instance.set(key2, val2);
                    assertEquals(ret, true);

                    //add records
                    ret = instance.set(key3, val3);
                    assertEquals(ret, true);

                    //add records
                    ret = instance.set(key4, val4);
                    assertEquals(ret, true);

                    return true;
                }
            }, true);
            
        } catch (Exception ex) {
            fail("Unexpected exception here, but can not got it");
        } finally {
            assertEquals(4, instance.count());
        }
        
        
    }

}
