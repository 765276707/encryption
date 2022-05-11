package com.github.xzb617.encryption.autoconfigure.utils;


import com.github.xzb617.encryption.autoconfigure.constant.Charsets;

import java.io.*;
import java.nio.charset.Charset;

/**
 * IO流工具类
 * @author xzb617
 * @date 2022/4/26 11:16
 * @description:
 */
public class IOUtil {

    public static String toString(InputStream input) throws IOException {
        return toString(input, Charset.defaultCharset());
    }

    public static String toString(InputStream input, Charset encoding) throws IOException {
        StringBuilderWriter sw = new StringBuilderWriter();
        copy((InputStream)input, (Writer)sw, (Charset)encoding);
        return sw.toString();
    }

    public static void copy(InputStream input, Writer output, Charset encoding) throws IOException {
        InputStreamReader in = new InputStreamReader(input, Charsets.toCharset(encoding));
        copy((Reader)in, (Writer)output);
    }

    public static void copy(InputStream input, Writer output, String encoding) throws IOException {
        copy(input, output, Charsets.toCharset(encoding));
    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int)count;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        return copyLarge(input, output, new char[4096]);
    }

    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        long count = 0L;

        int n;
        for(boolean var5 = false; -1 != (n = input.read(buffer)); count += (long)n) {
            output.write(buffer, 0, n);
        }

        return count;
    }

    public static InputStream toInputStream(String input, String encoding) throws IOException {
        byte[] bytes = input.getBytes(Charsets.toCharset(encoding));
        return new ByteArrayInputStream(bytes);
    }

    static class StringBuilderWriter extends Writer implements Serializable {
        private final StringBuilder builder;

        public StringBuilderWriter() {
            this.builder = new StringBuilder();
        }

        public StringBuilderWriter(int capacity) {
            this.builder = new StringBuilder(capacity);
        }

        public StringBuilderWriter(StringBuilder builder) {
            this.builder = builder != null ? builder : new StringBuilder();
        }

        @Override
        public Writer append(char value) {
            this.builder.append(value);
            return this;
        }

        @Override
        public Writer append(CharSequence value) {
            this.builder.append(value);
            return this;
        }

        @Override
        public Writer append(CharSequence value, int start, int end) {
            this.builder.append(value, start, end);
            return this;
        }

        @Override
        public void close() {
        }

        @Override
        public void flush() {
        }

        @Override
        public void write(String value) {
            if (value != null) {
                this.builder.append(value);
            }

        }

        @Override
        public void write(char[] value, int offset, int length) {
            if (value != null) {
                this.builder.append(value, offset, length);
            }

        }

        public StringBuilder getBuilder() {
            return this.builder;
        }

        @Override
        public String toString() {
            return this.builder.toString();
        }
    }

}
