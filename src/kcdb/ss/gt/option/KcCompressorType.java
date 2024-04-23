package kcdb.ss.gt.option;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author k3v1n1k88
 */
public enum KcCompressorType {

    DEFAULT("zlib"), //ZLIB raw compressor
    ZLIB_RAW("zlib"), //ZLIB raw compressor
    ZLIB_DEFLARE("def"), //ZLIB deflate compressor
    ZLIB_GZIP("gz"), //ZLIB gzip compressor
    LZO("lzo"), //LZO compressor
    LZMA("lzma"), //LZMA compresso
    ARC("arc"); //Arcfour ciphe

    private String value;

    private KcCompressorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
