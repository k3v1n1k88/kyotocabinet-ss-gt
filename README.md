# About
kyotocabinet-ss-gt is supported tool for accesing KyotoCabinet Database safely and speedy
#### Why use it?
- Easy to create all database types with all options supported by kyotocabinet 
- Support options and its parameters more clearly
- Prevent wrong configuration to destroy database
- Prevent wrong operation when coding to destroy database
- Provide quick MapReduce can implement a MapReduce quickly
- Provide transaction task
- Support unit test
- You don't need rewrite your projects when using it, just embed it and enjoy
#### In future
- Will support value types like map, set, list, tree,.. with machanisc serialize on key
- Support password for protecting the file
#### Support
  1. Database types
      - KcFileHashDB: file hash database
      - KcFileTreeDB: file tree database
      - KcCacheHashDB: cache hash database
      - KcCacheTreeDB: cache tree database
      - KcPlainTextDB: plain text database
      - KcProtoTreeDB: prototype tree database
      - KcProtoHashDB: prototype hash database
      - KcStashDB: stash database
      - KcDirectoryHashDB: directory hash database
      - KcDirectoryTreeDB: directory tree database
   2. Comparator types: used when implement tree databases
      - LEXICAL: lexical ascending comparator
      - DECIMAL: decimal ascending comparator
      - LEXICAL_DESC: lexical descending comparator
      - DECIMAL_DESC: decimal descending comparator
   3. Compressor types: used when implement tree file databases
      - ZLIB_RAW: ZLIB raw compressor
      - ZLIB_DEFLARE: ZLIB deflare compressor
      - ZLIB_GZIP: ZLIB gzip compressor
      - LZO: LZO compressor
      - LZMA: LZMA compressor
      - ARC: Arcfour compressor
   4. Merge mode: used when execute merge operation
      - MADD: keep the existing value
      - MAPPEND: append the new value
      - MREPLACE: modify the existing record value
      - MSET: overwrite the existing value
   5. Mode connection: used when open database. Support:
      - Mode
        - ONLY_READ: read-only
        - READ_WRITE: read and write
      - create if not exist
      - force create (truncate)
      - sync file
      - auto transaction
      - auto repair
      - lock file
      - block file
   6. Option storing: compressor
      - TSMALL: use 32-bit addressing
      - TLINEAR: use linear conllision chaining
      - TCOMPRESS: compress each record
   7. Quick map reduce
   8. Transaction task
   9. Support throw exception kyotocabinet.Error
# Usage
### Create database
##### Create by constructor:
```
KcBasicDB kcdb = new KcFileHashDB("/data/databases", "example", new KcModeConnection().createIfNotExist(true));
```
##### Create by arguments:
```
KcFileHashDB.Args args = new KcFileHashDB.Args("/data/databases", "example")
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
kcdb = new KcFileHashDB(args);
```
###### :warning:  Before use it, remember start it
```
kcdb.start();
```
###### Default when directory not existed, you have not permission to create folder, turn on it by:

```
KcFileHashDB.Args args = new KcFileHashDB.Args("/data/databases", "example")
                    .modeConnection(new KcModeConnection().createIfNotExist(true));
kcdb = new KcFileHashDB(args);
```
or
```
KcBasicDB kcdb = new KcFileHashDB("/data/databases", "example", new KcModeConnection().createIfNotExist(true));
kcdb.setAcceptCreateFolder(true);
```
#### Open database with truncate
By default, you cannot use truncate options for safety. Because it can make loss all your data. However, you can use it by turn on force create and accept clear database.
```
KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .acceptClearDB(true)
                    .modeConnection(new KcModeConnection().createIfNotExist(true).forceCreate(true));
```
or
```
kcdb.setModeConnection(new KcModeConnection().forceCreate(true));
kcdb.setAcceptClearDB(true);
```

#### Enable throw internal exception
You can enable throw exceptions supported by kyotocabinet [View more](https://fallabs.com/kyotocabinet/api/classkyotocabinet_1_1BasicDB_1_1Error.html)
```
KcFileHashDB.Args args = new KcFileHashDB.Args(dir, dbName)
                    .acceptClearDB(true)
                    .enableAllThrowException()
                    .modeConnection(new KcModeConnection().createIfNotExist(true))
```
