package com.cccis.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public final class IOUtils {

    /**
     * 默认的缓冲字节数
     */
    public static final int DEFAULT_BUF_SIZE = 1024;
    
    /**
     * LOG
     */
    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    /**
     * constructor
     */
    private IOUtils() {

    }

    /**
     * 把InputStream复制到指定文件
     *
     * @param input  输入InputStream
     * @param output 输出文件
     * @return 传输量
     * @throws IOException copy时可能抛出IO异常
     */
    public static long copy(final InputStream input, final File output) throws IOException {
        return copy(input, output, Long.MAX_VALUE);
    }

    /**
     * 复制输入流到文件, 只复制指定长度
     *
     * @param input  输入流
     * @param output 文件
     * @param length 复制长度
     * @return 实际复制的长度
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(final InputStream input, final File output, final long length) throws IOException {
        FileOutputStream fos = null;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("coping file  to {}", output.getPath());
            }

            // 输出文件不存在,先创建
            if (!output.exists()) {
                final boolean created = output.createNewFile();
                if (!created && LOG.isDebugEnabled()) {
                    LOG.debug("file already exists : {}", output.getPath());
                }
            }

            // 把数据从InputStream读出来复制到输出
            fos = new FileOutputStream(output);
            return copy(input, fos, length);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * 复制输入流到输出流, 只复制指定长度
     *
     * @param input  输入流
     * @param output 输出流
     * @param length 复制长度
     * @return 实际复制的长度
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(final InputStream input, final OutputStream output, final long length) throws IOException {
        return copy(input, output, new byte[DEFAULT_BUF_SIZE], length);
    }

    /**
     * 把InputStream复制到指定文件
     *
     * @param input  输入InputStream
     * @param output 输出文件
     * @param buf    Buffer
     * @return 传输量
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(final InputStream input, final File output, final byte[] buf) throws IOException {
        FileOutputStream fos = null;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("coping file  to {}", output.getPath());
            }

            // 输出文件不存在,先创建
            if (!output.exists()) {
                final boolean created = output.createNewFile();
                if (!created && LOG.isDebugEnabled()) {
                    LOG.debug("file already exists : {}", output.getPath());
                }
            }

            // 把数据从InputStream读出来复制到输出
            fos = new FileOutputStream(output);
            return copy(input, fos, buf);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * 把InputStream复制到指定OutpuStream
     *
     * @param input  输入InputStream
     * @param output 输出OutpuStream
     * @param buf    Buffer
     * @return 传输量
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(final InputStream input, final OutputStream output, final byte[] buf) throws IOException {
        return copy(input, output, buf, Long.MAX_VALUE);
    }

    /**
     * 把InputStream复制到指定OutpuStream
     *
     * @param input     输入InputStream
     * @param output    输出OutpuStream
     * @param buf       Buffer
     * @param maxLength 最大长度
     * @return 传输量
     * @throws IOException Exception 可能抛出IO异常
     */
    public static long copy(final InputStream input, final OutputStream output, byte[] buf, final long maxLength) throws IOException {
        buf = (buf == null ? new byte[DEFAULT_BUF_SIZE] : buf);
        long totalTransferred = 0;
        int len;
        int bufLen = buf.length;
        while ((len = input.read(buf, 0, bufLen)) != -1) {
            // 如果要复制的总长度大于了总长度上限, 则复制满足总长度的部分
            if (totalTransferred + len > maxLength) {
                len = (int) (maxLength - totalTransferred);
            }
            output.write(buf, 0, len);
            output.flush();
            totalTransferred += len;
            if (maxLength - totalTransferred < bufLen) {
                bufLen = (int) (maxLength - totalTransferred);
            }
            if (totalTransferred >= maxLength) {
                break;
            }
        }
        output.flush();
        return totalTransferred;
    }

    /**
     * 把InputStream复制到指定OutpuStream
     *
     * @param input  输入InputStream
     * @param output 输出OutpuStream
     * @return 传输量
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(final InputStream input, final OutputStream output) throws IOException {
        return copy(input, output, new byte[DEFAULT_BUF_SIZE], Long.MAX_VALUE);
    }

    /**
     * 把文件复制到输出流
     *
     * @param file         文件
     * @param outputStream 输出流
     * @return 传输量
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(final File file, final OutputStream outputStream) throws IOException {
        return copy(file, outputStream, new byte[DEFAULT_BUF_SIZE]);
    }

    /**
     * 文件复制到输出流
     *
     * @param file         文件
     * @param outputStream 输出流
     * @param buf          buffer
     * @return long
     * @throws IOException Exception
     */
    public static long copy(final File file, final OutputStream outputStream, final byte[] buf) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return copy(fis, outputStream, buf);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (final IOException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * 把字节数组的数据写到一个文件
     *
     * @param data 数据
     * @param file 文件
     * @return long
     * @throws IOException Exception
     */
    public static long copy(final byte[] data, final File file) throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        return copy(bais, file);
    }

    /**
     * 把字节数组的数据写到一个输出流
     *
     * @param data 数据
     * @param os   输出流
     * @return long
     * @throws IOException Exception
     */
    public static long copy(final byte[] data, final OutputStream os) throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        return copy(bais, os);
    }

    /**
     * 把字符输入流复制到字符输出流
     *
     * @param r         字符输入流
     * @param w         字符输出流
     * @param buf       缓冲区
     * @param maxLength 最大复制多少字符
     * @return long
     * @throws IOException Exception
     */
    public static long copy(final Reader r, final Writer w, char[] buf, final long maxLength) throws IOException {
        buf = (buf == null ? new char[DEFAULT_BUF_SIZE] : buf);
        long totalTransferred = 0;
        int len;
        int bufLen = buf.length;
        while ((len = r.read(buf, 0, bufLen)) != -1) {
            // 如果要复制的总长度大于了总长度上限, 则复制满足总长度的部分
            if (totalTransferred + len > maxLength) {
                len = (int) (maxLength - totalTransferred);
            }
            w.write(buf, 0, len);
            w.flush();
            totalTransferred += len;
            if (maxLength - totalTransferred < bufLen) {
                bufLen = (int) (maxLength - totalTransferred);
            }
            if (totalTransferred >= maxLength) {
                break;
            }
        }
        w.flush();
        return totalTransferred;
    }

    /**
     * 把字符输入流复制到字符输出流
     *
     * @param in       输入流
     * @param encoding 输入流的字符集
     * @param w        字符输出流
     * @return long
     * @throws IOException Exception
     */
    public static long copy(final InputStream in, final Writer w, final String encoding) throws IOException {
        return copy(new InputStreamReader(in, encoding), w, new char[DEFAULT_BUF_SIZE], Long.MAX_VALUE);
    }

    /**
     * 把字符输入流复制到字符输出流
     *
     * @param r 字符输入流
     * @param w 字符输出流
     * @return long
     * @throws IOException Exception
     */
    public static long copy(final Reader r, final Writer w) throws IOException {
        return copy(r, w, new char[DEFAULT_BUF_SIZE], Long.MAX_VALUE);
    }

    /**
     * 关闭reader
     *
     * @param reader 要关闭的Reader
     */
    public static void closeReader(final Reader reader) {
        org.apache.commons.io.IOUtils.closeQuietly(reader);
    }

    /**
     * 关闭writer
     *
     * @param writer 要关闭的Writer
     */
    public static void closeWriter(final Writer writer) {
        org.apache.commons.io.IOUtils.closeQuietly(writer);
    }

    /**
     * 关闭inputStream
     *
     * @param inputStream 要关闭的输入流
     */
    public static void closeInputStream(final InputStream inputStream) {
        org.apache.commons.io.IOUtils.closeQuietly(inputStream);
    }

    /**
     * 关闭outputStream
     *
     * @param outputStream 要关闭的输出流
     */
    public static void closeOutputStream(final OutputStream outputStream) {
        org.apache.commons.io.IOUtils.closeQuietly(outputStream);
    }

    /**
     * 读取一个文件到字符串
     *
     * @param stream   输入流
     * @param encoding 字符集
     * @return 字符(文件的内容)
     * @throws IOException 可能抛出IO异常
     */
    public static String readToString(final InputStream stream, final String encoding) throws IOException {
        final StringWriter sw = new StringWriter();
        copy(stream, sw, encoding);
        return sw.toString();
    }
}
