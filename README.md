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
##### Create database
KcBasicDB db = new KcFileHashDB()
