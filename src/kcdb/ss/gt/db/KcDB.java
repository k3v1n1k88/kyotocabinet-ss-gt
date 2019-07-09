/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcdb.ss.gt.db;

import kcdb.ss.gt.db.task.KcTransactionTask;
import java.util.List;
import java.util.Map;
import kyotocabinet.Cursor;
import kyotocabinet.DB;
import kyotocabinet.FileProcessor;
import kyotocabinet.Visitor;

/**
 *
 * @author k3v1n1k88
 */
public interface KcDB {

    /**
     * The maximum alignment power.
     */
    public static final int MAXAPOW = 15;

    /**
     * The maximum free block pool power.
     */
    public static final int MAXFPOW = 20;

    /**
     * Accept a visitor to a record.
     *
     * @param key      the key.
     * @param visitor  a visitor object which implements the Visitor interface.
     * @param writable true for writable operation, or false for read-only
     *                 operation.
     *
     * @return true on success, or false on failure.
     * @note The operation for each record is performed atomically and other
     * threads accessing the
     * same record are blocked. To avoid deadlock, any explicit database
     * operation must not be
     * performed in this method.
     */
    public boolean accept(byte[] key, Visitor visitor, boolean writable);

    /**
     * Accept a visitor to multiple records at once.
     *
     * @param keys     specifies an array of the keys.
     * @param visitor  a visitor object.
     * @param writable true for writable operation, or false for read-only
     *                 operation.
     *
     * @return true on success, or false on failure.
     * @note The operations for specified records are performed atomically and
     * other threads
     * accessing the same records are blocked. To avoid deadlock, any explicit
     * database operation
     * must not be performed in this method.
     */
    public boolean accept_bulk(byte[][] keys, Visitor visitor, boolean writable);

    /**
     * Add a record.
     *
     * @param key   the key.
     * @param value the value.
     *
     * @return true on success, or false on failure.
     * @note If no record corresponds to the key, a new record is created. If
     * the corresponding record exists, the record is not modified and false is
     * returned.
     */
    boolean add(byte[] key, byte[] value);

    /**
     * Add a record.
     *
     * @note Equal to the original DB.add method except that the parameters are
     * String.
     * @see #add(byte[], byte[])
     */
    boolean add(String key, String value);

    /**
     * Append the value of a record.
     *
     * @note Equal to the original DB.append method except that the parameters
     * are String.
     * @see #append(byte[], byte[])
     */
    boolean append(String key, String value);

    /**
     * Append the value of a record.
     *
     * @param key   the key.
     * @param value the value.
     *
     * @return true on success, or false on failure.
     * @note If no record corresponds to the key, a new record is created. If
     * the corresponding record exists, the given value is appended at the end
     * of the existing value.
     */
    boolean append(byte[] key, byte[] value);

    /**
     * Begin transaction.
     *
     * @param hard true for physical synchronization with the device, or false
     *             for logical synchronization with the file system.
     *
     * @return true on success, or false on failure.
     */
    boolean begin_transaction(boolean hard);

    /**
     * Perform compare-and-swap.
     *
     * @param key      key of record.
     * @param oldValue the old value. null means that no record corresponds.
     * @param newValue the new value. null means that the record is removed.
     *
     * @return true on success, or false on failure.
     */
    boolean cas(byte[] key, byte[] oldValue, byte[] newValue);

    /**
     * Perform compare-and-swap.
     *
     * @note Equal to the original DB.cas method except that the parameters are
     * String.
     * @see #cas(byte[], byte[], byte[])
     */
    boolean cas(String key, String oldValue, String newValue);

    /**
     * Check the existence of a record.
     *
     * @param key of record.
     *
     * @return the size of the value, or -1 on failure.
     */
    long check(byte[] key);

    /**
     * Retrieve the value of a record.
     *
     * @note Equal to the original DB.check method except that the parameter is
     * String.
     * @see #check(byte[])
     */
    long check(String key);

    /**
     * Remove all records.
     *
     * @return true on success, or false on failure.
     */
    boolean clear();

    /**
     * Close the database file.
     *
     * @return true on success, or false on failure.
     */
    boolean close();

    /**
     * Create a copy of the database file.
     *
     * @param dest the path of the destination file.
     *
     * @return true on success, or false on failure.
     */
    boolean copy(String dest);

    /**
     * Get the number of records.
     *
     * @return the number of records, or -1 on failure.
     */
    long count();

    /**
     * Create a cursor object.
     *
     * @return the return value is the created cursor object. Each cursor should
     *         be disabled with the Cursor#disable method when it is no longer in use.
     */
    Cursor cursor();

    /**
     * Dump records into a snapshot file.
     *
     * @param fullPath the name of the destination file and its full path.
     *
     * @return true on success, or false on failure.
     */
    boolean dump_snapshot(String fullPath);

    /**
     * End transaction.
     *
     * @param commit true to commit the transaction, or false to abort the
     *               transaction.
     *
     * @return true on success, or false on failure.
     */
    boolean end_transaction(boolean commit);

    /**
     * Get the last happened error.
     *
     * @return the last happened error.
     */
    kyotocabinet.Error error();

    /**
     * Retrieve the value of a record.
     *
     * @note Equal to the original DB.get method except that the parameter and
     * the return value are String.
     * @see #get(byte[])
     */
    String get(String key);

    /**
     * Retrieve the value of a record.
     *
     * @param key the key.
     *
     * @return the value of the corresponding record, or null on failure.
     */
    byte[] get(byte[] key);

    /**
     * Retrieve records at once.
     *
     * @param keys   the keys of the records to retrieve.
     * @param atomic true to perform all operations atomically, or false for
     *               non-atomic operations.
     *
     * @return an array of retrieved records, or null on failure. Each key and
     *         each value is placed alternately.
     */
    byte[][] get_bulk(byte[][] keys, boolean atomic);

    /**
     * Retrieve records at once.
     *
     * @note Equal to the original DB.get_bulk method except that the parameter
     * is List and the return value is Map.
     * @see #get_bulk(byte[][], boolean)
     */
    Map<String, String> get_bulk(List<String> keys, boolean atomic);

    /**
     * Add a number to the numeric integer value of a record.
     *
     * @param key  the key.
     * @param num  the additional number.
     * @param orig the origin number if no record corresponds to the key. If it
     *             is Long.MIN_VALUE and no record corresponds, this method fails. If it is
     *             Long.MAX_VALUE, the value is set as the additional number regardless of
     *             the current value.
     *
     * @return the result value, or Long.MIN_VALUE on failure.
     * @note The value is serialized as an 8-byte binary integer in big-endian
     * order, not a decimal string. If existing value is not 8-byte, this method
     * fails.
     */
    long increment(byte[] key, long num, long orig);

    /**
     * Add a number to the numeric integer value of a record.
     *
     * @note Equal to the original DB.increment method except that the parameter
     * is String.
     * @see #increment(byte[], long, long)
     */
    long increment(String key, long num, long orig);

    /**
     * Add a number to the numeric double value of a record.
     *
     * @param key  the key.
     * @param num  the additional number.
     * @param orig the origin number if no record corresponds to the key. If it
     *             is negative infinity and no record corresponds, this method fails. If it
     *             is positive infinity, the value is set as the additional number
     *             regardless of the current value.
     *
     * @return the result value, or Not-a-number on failure.
     */
    double increment_double(byte[] key, double num, double orig);

    /**
     * Add a number to the numeric double value of a record.
     *
     * @note Equal to the original DB.increment method except that the parameter
     * is String.
     * @see #increment_double(byte[], double, double)
     */
    double increment_double(String key, double num, double orig);

    /**
     *
     * @return false if db is open, otherwise return false
     *
     */
    boolean isOpen();

    /**
     * Iterate to accept a visitor for each record.
     *
     * @param visitor  a visitor object which implements the Visitor interface.
     * @param writable true for writable operation, or false for read-only
     *                 operation.
     *
     * @return true on success, or false on failure.
     * @note The whole iteration is performed atomically and other threads are
     * blocked. To avoid
     * deadlock, any explicit database operation must not be performed in this
     * method.
     */
    boolean iterate(Visitor visitor, boolean writable);

    /**
     * Load records from a snapshot file.
     *
     * @param src the name of the source file.
     *
     * @return true on success, or false on failure.
     */
    boolean load_snapshot(String src);

    /**
     * Get keys matching a prefix string.
     *
     * @param prefix the prefix string.
     * @param max    the maximum number to retrieve. If it is negative, no limit
     *               is
     *               specified.
     *
     * @return a list object of matching keys, or null on failure.
     */
    List<String> match_prefix(String prefix, long max);

    /**
     * Get keys matching a regular expression string.
     *
     * @param regex the regular expression string.
     * @param max   the maximum number to retrieve. If it is negative, no limit
     *              is
     *              specified.
     *
     * @return a list object of matching keys, or null on failure.
     */
    List<String> match_regex(String regex, long max);

    /**
     * Get keys similar to a string in terms of the levenshtein distance.
     *
     * @param origin the origin string.
     * @param range  the maximum distance of keys to adopt.
     * @param utf    flag to treat keys as UTF-8 strings.
     * @param max    the maximum number to retrieve. If it is negative, no limit
     *               is
     *               specified.
     *
     * @return a list object of matching keys, or null on failure.
     */
    List<String> match_similar(String origin,
                               long range,
                               boolean utf,
                               long max);

    /**
     * Merge records from other databases.
     *
     * @param srcary an array of the source database objects.
     * @param mode   the merge mode. DB.MSET to overwrite the existing value,
     *               DB.MADD to keep the existing value, DB.MAPPEND to append the new value.
     *
     * @return true on success, or false on failure.
     */
    boolean merge(DB[] srcary, int mode);

    /**
     * Occupy database by locking and do something meanwhile.
     *
     * @param writable true to use writer lock, or false to use reader lock.
     * @param proc     a processor object which implements the FileProcessor
     *                 interface. If it is null, no processing is performed.
     *
     * @return true on success, or false on failure.
     * @note The operation of the processor is performed atomically and other
     * threads accessing the same record are blocked. To avoid deadlock, any
     * explicit database operation must not be performed in this method.
     */
    boolean occupy(boolean writable, FileProcessor proc);

    /**
     * Get the path of the database file.
     *
     * @return the path of the database file, or null on failure.
     */
    String path();

    /**
     * Remove a record.
     *
     * @param key the key.
     *
     * @return true on success, or false on failure.
     * @note If no record corresponds to the key, false is returned.
     */
    boolean remove(byte[] key);

    /**
     * @note Equal to the original DB.remove method except that the parameter is
     * String.
     * @see #remove(byte[])
     */
    boolean remove(String key);

    /**
     * Remove records at once.
     *
     * @param keys   the keys of the records to remove.
     * @param atomic true to perform all operations atomically, or false for
     *               non-atomic operations.
     *
     * @return the number of removed records, or -1 on failure.
     */
    long remove_bulk(byte[][] recs, boolean atomic);

    /**
     * Remove records at once.
     *
     * @note Equal to the original DB.remove_bulk method except that the
     * parameter is List.
     * @see #remove_bulk(byte[][], boolean)
     */
    long remove_bulk(List<String> recs, boolean atomic);

    /**
     * Replace the value of a record.
     *
     * @note Equal to the original DB.replace method except that the parameters
     * are String.
     * @see #add(byte[], byte[])
     */
    boolean replace(String key, String value);

    /**
     * Replace the value of a record.
     *
     * @param key   the key.
     * @param value the value.
     *
     * @return true on success, or false on failure.
     * @note If no record corresponds to the key, no new record is created and
     * false is returned. If the corresponding record exists, the value is
     * modified.
     */
    boolean replace(byte[] key, byte[] value);

    /**
     * Retrieve the value of a record and remove it atomically.
     *
     * @param key the key.
     *
     * @return the value of the corresponding record, or null on failure.
     */
    byte[] seize(byte[] key);

    /**
     * Retrieve the value of a record and remove it atomically.
     *
     * @note Equal to the original DB.seize method except that the parameter and
     * the return value are String.
     * @see #seize(byte[])
     */
    String seize(String key);

    /**
     * Set the value of a record.
     *
     * @note Equal to the original DB.set method except that the parameters are
     * String.
     * @see #set(byte[], byte[])
     */
    boolean set(String key, String value);

    /**
     * Set the value of a record.
     *
     * @param key   the key.
     * @param value the value.
     *
     * @return true on success, or false on failure.
     * @note If no record corresponds to the key, a new record is created. If
     * the corresponding record exists, the value is overwritten.
     */
    boolean set(byte[] key, byte[] value);

    /**
     * Store records at once.
     *
     * @param recs   the records to store. Each key and each value must be
     *               placed
     *               alternately.
     * @param atomic true to perform all operations atomically, or false for
     *               non-atomic operations.
     *
     * @return the number of stored records, or -1 on failure.
     */
    long set_bulk(byte[][] recs, boolean atomic);

    /**
     * Store records at once.
     *
     * @note Equal to the original DB.set_bulk method except that the parameter
     * is Map.
     * @see #set_bulk(byte[][], boolean)
     */
    long set_bulk(Map<String, String> recs, boolean atomic);

    /**
     * Get the size of the database file.
     *
     * @return the size of the database file in bytes, or -1 on failure.
     */
    long size();

    /**
     * Start database. Before you try to use database, remember that you need
     * start it before
     *
     * @return
     * @throws Exception any error happen while start
     */
    boolean start() throws Exception;

    /**
     * Get the miscellaneous status information.
     *
     * @return a map object of the status information, or null on failure.
     */
    Map<String, String> status();

    /**
     * Synchronize updated contents with the file and the device.
     *
     * @param hard true for physical synchronization with the device, or false
     *             for logical synchronization with the file system.
     * @param proc a postprocessor object which implements the FileProcessor
     *             interface. If it is null, no post processing is performed.
     *
     * @return true on success, or false on failure.
     * @note The operation of the postprocessor is performed atomically and
     * other threads accessing the same record are blocked. To avoid deadlock,
     * any explicit database operation must not be performed in this method.
     */
    boolean synchronize(boolean hard, FileProcessor proc);

    /**
     * Set the encoding of external strings.
     *
     * @param encname the name of the encoding.
     *
     * @note The default encoding of external strings is UTF-8.
     * @return true on success, or false on failure.
     */
    boolean tune_encoding(String encname);

    /**
     * Set the rule about throwing exception.
     *
     * @param codes an array of error codes. If each method occurs an error
     *              corresponding to one of the specified codes, the error is thrown as an
     *              exception.
     *
     * @return true on success, or false on failure.
     */
    boolean tune_exception_rule(int[] codes);

}
