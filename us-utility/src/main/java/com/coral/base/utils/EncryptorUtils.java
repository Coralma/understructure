package com.coral.base.utils;

import com.coral.base.common.BaseConstant;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 加密工具类.
 */
public final class EncryptorUtils {
    /**
     * 允许的字符
     */
    public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

    private static final Logger LOG = LoggerFactory.getLogger(EncryptorUtils.class.getName());

    /**
     * 容量倍数
     */
    private static final int CAPACITY_MULTIPLE = ConstantUtils.getValue(3);

    private static final int STRING_RADIX = ConstantUtils.getValue(16);

    /**
     * ASCII 常量
     */
    private static final int ASC_0XFF = ConstantUtils.getValue(0xff);

    private static final int ASC_0X10 = ConstantUtils.getValue(0x10);

    private static final int ASC_0XF = ConstantUtils.getValue(0xF);

    private static final int ASC_0XC0 = ConstantUtils.getValue(0xc0);

    private static final int ASC_0X80 = ConstantUtils.getValue(0x80);

    private static final int ASC_0X3F = ConstantUtils.getValue(0x3f);

    private static final int ASC_0XE0 = ConstantUtils.getValue(0xe0);

    private static final int ASC_0X1F = ConstantUtils.getValue(0x1f);

    private static final int ASC_0X00 = ConstantUtils.getValue(0x00);

    private static final int ASC_0XF0 = ConstantUtils.getValue(0xf0);

    private static final int ASC_0X0F = ConstantUtils.getValue(0x0f);

    private static final int ASC_0XF8 = ConstantUtils.getValue(0xf8);

    private static final int ASC_0X07 = ConstantUtils.getValue(0x07);

    private static final int ASC_0XFC = ConstantUtils.getValue(0xfc);

    private static final int ASC_0X03 = ConstantUtils.getValue(0x03);

    private static final int ASC_0X01 = ConstantUtils.getValue(0x01);

    // int 常量
    private static final int NUM_1 = ConstantUtils.getValue(1);

    private static final int NUM_2 = ConstantUtils.getValue(2);

    private static final int NUM_3 = ConstantUtils.getValue(3);

    private static final int NUM_4 = ConstantUtils.getValue(4);

    private static final int NUM_5 = ConstantUtils.getValue(5);

    private static final int NUM_6 = ConstantUtils.getValue(6);

    private static final int NUM_10 = ConstantUtils.getValue(10);

    private EncryptorUtils() {

    }

    /**
     * 将字符串进行Base64编码
     *
     * @param s 被编码的字符串
     * @return 编码后的字符串
     */
    public static String encoderBASE64(final String s) {
        if (s == null) {
            return null;
        }
        try {
            return Base64.encodeBase64String(s.getBytes(BaseConstant.ENCODING));
        } catch (final UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     *
     * @param s 被解码的字符串
     * @return 解码后的字符串
     */
    public static String decoderBASE64(final String s) {
        String str = null;
        try {
            if (StringUtils.isNotBlank(s)) {
                final byte[] b = Base64.decodeBase64(s);
                str = new String(b, BaseConstant.ENCODING);
            }
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * 将URL进行编码，如果URL中的字符没有包含在ALLOWED_CHARS中就将这个字符转换成16进制的字符串每个字节前面加% 如 "汉 字AAA" 被转换成 "%E6%B1%89%20%E5%AD%97AAA"
     *
     * @param input 字符串
     * @return 转换后的结果
     */
    public static String encodeURIComponent(final String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }

        final int l = input.length();
        final StringBuilder o = new StringBuilder(l * CAPACITY_MULTIPLE);
        for (int i = 0; i < l; i++) {
            final String e = input.substring(i, i + 1);
            if (!ALLOWED_CHARS.contains(e)) {
                final byte[] b = e.getBytes(Charset.forName(BaseConstant.ENCODING));
                o.append(getHex(b));
                continue;
            }
            o.append(e);
        }
        return o.toString();
    }

    private static String getHex(final byte[] buf) {
        final StringBuilder o = new StringBuilder(buf.length * CAPACITY_MULTIPLE);
        for (final byte b : buf) {
            final int n = b & ASC_0XFF;
            o.append("%");
            if (n < ASC_0X10) {
                o.append("0");
            }
            o.append(Long.toString(n, STRING_RADIX).toUpperCase());
        }
        return o.toString();
    }

    /**
     * 将encodeURIComponent解码
     *
     * @param encodedURI encodeURIComponent编码后的字符串
     * @return 解码后的结果
     */
    public static String decodeURIComponent(final String encodedURI) {
        char actualChar;

        final StringBuilder buffer = new StringBuilder();

        int bytePattern, sumb = 0;

        for (int i = 0, more = -1; i < encodedURI.length(); i++) {
            actualChar = encodedURI.charAt(i);

            switch (actualChar) {
                case '%':
                    actualChar = encodedURI.charAt(++i);
                    final int hb = (Character.isDigit(actualChar) ? actualChar - '0' : NUM_10 + Character.toLowerCase(actualChar) - 'a') & ASC_0XF;
                    actualChar = encodedURI.charAt(++i);
                    final int lb = (Character.isDigit(actualChar) ? actualChar - '0' : NUM_10 + Character.toLowerCase(actualChar) - 'a') & ASC_0XF;
                    bytePattern = (hb << NUM_4) | lb;
                    break;

                case '+':
                    bytePattern = ' ';
                    break;

                default:
                    bytePattern = actualChar;
                    break;

            }

            if ((bytePattern & ASC_0XC0) == ASC_0X80) { // 10xxxxxx
                sumb = (sumb << NUM_6) | (bytePattern & ASC_0X3F);
                if (--more == 0) {
                    buffer.append((char) sumb);
                }
            } else if ((bytePattern & ASC_0X80) == ASC_0X00) { // 0xxxxxxx
                buffer.append((char) bytePattern);
            } else if ((bytePattern & ASC_0XE0) == ASC_0XC0) { // 110xxxxx
                sumb = bytePattern & ASC_0X1F;
                more = NUM_1;
            } else if ((bytePattern & ASC_0XF0) == ASC_0XE0) { // 1110xxxx
                sumb = bytePattern & ASC_0X0F;
                more = NUM_2;
            } else if ((bytePattern & ASC_0XF8) == ASC_0XF0) { // 11110xxx
                sumb = bytePattern & ASC_0X07;
                more = NUM_3;
            } else if ((bytePattern & ASC_0XFC) == ASC_0XF8) { // 111110xx
                sumb = bytePattern & ASC_0X03;
                more = NUM_4;
            } else { // 1111110x
                sumb = bytePattern & ASC_0X01;
                more = NUM_5;
            }
        }
        return buffer.toString();
    }
}
