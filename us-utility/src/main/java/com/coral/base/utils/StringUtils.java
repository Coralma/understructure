package com.coral.base.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coral.base.common.BaseConstant;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 操作字符串的工具类
 */
public final class StringUtils {
    private static final Logger LOG = LoggerFactory.getLogger(StringUtils.class.getName());

    private final static String DEFAULT_CHARSET = ConstantUtils.getValue("GBK");

    /**
     * constructor
     */
    private StringUtils() {

    }

    /**
     * 判断字符串是否为Null
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNull(final String str) {
        if (str == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串为空或Null
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmpty(final String str) {
        return org.apache.commons.lang.StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否为空，null或者‘’均为空
     *
     * @param str 字符串
     * @return boolean 如果不为空的话返回true，如果为空的话返回false
     */
    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串为空，或者空白字符或者Null
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isBlank(final String str) {
        return org.apache.commons.lang.StringUtils.isBlank(str);
    }

    /**
     * 判断字符串不为空（不是空字符,或则空白字符串，且不为Null）
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNotBlank(final String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * 去掉字符串前后的空白字符
     *
     * @param str 字符串
     * @return boolean
     */
    public static String trim(final String str) {
        return org.apache.commons.lang.StringUtils.trim(str);
    }

    /**
     * 将null的字符串转化为空字符串
     *
     * @param str 字符串
     * @return str
     */
    public static String nullToBlank(final String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 如果为null，就转换为‘’
     *
     * @param str 字符串
     * @return str 如果字符串为null，就返回‘’，否则原样返回
     */
    public static String nullToBlank(final Object str) {
        if (str == null) {
            return "";
        }
        return str.toString();
    }

    /**
     * 将空白字符串转化为null
     *
     * @param str 字符串
     * @return str
     */
    public static String blankToNull(final String str) {
        if (str == null || "".equals(str.trim())) {
            return null;
        }
        return str;
    }

    /**
     * 判断2个字符串是否相等
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return boolean
     */
    public static boolean equals(final String str1, final String str2) {
        return org.apache.commons.lang.StringUtils.equals(str1, str2);
    }

    /**
     * 忽略字符串大小写，判断2个字符串是否相等，
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return boolean
     */
    public static boolean equalsIgnoreCase(final String str1, final String str2) {
        return org.apache.commons.lang.StringUtils.equalsIgnoreCase(str1, str2);
    }

    /**
     * 获取字符的起始位置，字符串str可为Null
     * <pre>
     * StringUtils.indexOf(null, *)         = -1
     * StringUtils.indexOf("", *)           = -1
     * StringUtils.indexOf("aabaabaa", 'a') = 0
     * StringUtils.indexOf("aabaabaa", 'b') = 2
     * </pre>
     *
     * @param str        字符串
     * @param searchChar 搜索字符
     * @return int
     */
    public static int indexOf(final String str, final char searchChar) {
        return org.apache.commons.lang.StringUtils.indexOf(str, searchChar);
    }

    /**
     * 获取字符串searchStr在字符串str的起始位置
     * <pre>
     * StringUtils.indexOf(null, *)          = -1
     * StringUtils.indexOf(*, null)          = -1
     * StringUtils.indexOf("", "")           = 0
     * StringUtils.indexOf("aabaabaa", "a")  = 0
     * StringUtils.indexOf("aabaabaa", "b")  = 2
     * StringUtils.indexOf("aabaabaa", "ab") = 1
     * StringUtils.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str       字符串
     * @param searchStr 搜索字符
     * @return int
     */
    public static int indexOf(final String str, final String searchStr) {
        return org.apache.commons.lang.StringUtils.indexOf(str, searchStr);
    }

    /**
     * 获取字符串searchStr在字符串str中最后一个位置的index
     * <pre>
     * StringUtils.lastIndexOf(null, *)          = -1
     * StringUtils.lastIndexOf(*, null)          = -1
     * StringUtils.lastIndexOf("", "")           = 0
     * StringUtils.lastIndexOf("aabaabaa", "a")  = 0
     * StringUtils.lastIndexOf("aabaabaa", "b")  = 2
     * StringUtils.lastIndexOf("aabaabaa", "ab") = 1
     * StringUtils.lastIndexOf("aabaabaa", "")   = 8
     * </pre>
     *
     * @param str       字符串
     * @param searchStr 搜索字符
     * @return int
     */
    public static int lastIndexOf(final String str, final String searchStr) {
        return org.apache.commons.lang.StringUtils.lastIndexOf(str, searchStr);
    }

    /**
     * <p>
     * Escapes the characters in a <code>String</code> using HTML entities.
     * </p>
     *
     * @param str 字符串
     * @return str
     */
    public static String escapeHtml(final String str) {
        return StringEscapeUtils.escapeHtml(str);
    }

    /**
     * <p>
     * Unescapes a string containing entity escapes to a string containing the actual Unicode characters corresponding
     * to the escapes. Supports HTML 4.0 entities.
     * </p>
     *
     * @param str 字符串
     * @return str
     */
    public static String unEscapeHtml(final String str) {
        return StringEscapeUtils.unescapeHtml(str);
    }

    /**
     * <p>
     * Splits the provided text into an array, separators specified. This is an alternative to using StringTokenizer.
     * </p>
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str           字符串
     * @param separatorChar 分隔符
     * @return String[]
     */
    public static String[] split(final String str, final String separatorChar) {
        return org.apache.commons.lang.StringUtils.split(str, separatorChar);
    }

    /**
     * 将字符串转换成Boolean
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean toBoolean(final String str) {
        return "TRUE".equalsIgnoreCase(str) || "Y".equalsIgnoreCase(str) || "YES".equalsIgnoreCase(str);
    }

    /**
     * 得到字符串的长度，英文占一个字符，中文占两个字符
     *
     * @param str 字符串
     * @return int
     */
    public static int getStringLength(final String str) {
        int len = 0;
        if (isEmpty(str)) {
            len = 0;
        } else {
            final byte[] b = str.getBytes(Charset.forName(DEFAULT_CHARSET));
            len = b.length;
        }
        return len;
    }

    /**
     * <p>
     * Checks if String contains a search String, handling <code>null</code>. This method uses
     * {@link String#indexOf(String)}.
     * </p>
     * <pre>
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
     * </pre>
     *
     * @param str    字符串
     * @param subStr 截取字符
     * @return boolean
     */
    public static boolean contains(final String str, final String subStr) {
        return org.apache.commons.lang.StringUtils.contains(str, subStr);
    }

    /**
     * <p>
     * Capitalizes a String changing the first letter to title case as per {@link Character#toTitleCase(char)}. No other
     * letters are changed.
     * </p>
     * <pre>
     * StringUtils.capitalize(null)  = null
     * StringUtils.capitalize("")    = ""
     * StringUtils.capitalize("cat") = "Cat"
     * StringUtils.capitalize("cAt") = "CAt"
     * </pre>
     *
     * @param str 字符串
     * @return str
     */
    public static String capitalize(final String str) {
        return org.apache.commons.lang.StringUtils.capitalize(str);
    }

    /**
     * <p>
     * Uncapitalizes a String changing the first letter to title case as per {@link Character#toLowerCase(char)}. No
     * other letters are changed.
     * </p>
     * <pre>
     * StringUtils.unCapitalize(null)  = null
     * StringUtils.unCapitalize("")    = ""
     * StringUtils.unCapitalize("Cat") = "cat"
     * StringUtils.unCapitalize("CAT") = "cAT"
     * </pre>
     *
     * @param str 字符串
     * @return str
     */
    public static String unCapitalize(final String str) {
        return org.apache.commons.lang.StringUtils.uncapitalize(str);
    }

    /**
     * <p>
     * Converts a String to upper case as per {@link String#toUpperCase()}.
     * </p>
     * <p>
     * A <code>null</code> input String returns <code>null</code>.
     * </p>
     * <pre>
     * StringUtils.upperCase(null)  = null
     * StringUtils.upperCase("")    = ""
     * StringUtils.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param str 字符串
     * @return str
     */
    public static String upperCase(final String str) {
        return org.apache.commons.lang.StringUtils.upperCase(str);
    }

    /**
     * <p>
     * Converts a String to lower case as per {@link String#toLowerCase()}.
     * </p>
     * <p>
     * A <code>null</code> input String returns <code>null</code>.
     * </p>
     * <pre>
     * StringUtils.lowerCase(null)  = null
     * StringUtils.lowerCase("")    = ""
     * StringUtils.lowerCase("aBc") = "abc"
     * </pre>
     *
     * @param str 字符串
     * @return str
     */
    public static String lowerCase(final String str) {
        return org.apache.commons.lang.StringUtils.lowerCase(str);
    }

    /**
     * <p>
     * Checks whether the String a valid Java number.
     * </p>
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNumber(final String str) {
        return org.apache.commons.lang.math.NumberUtils.isNumber(str);
    }

    /**
     * 判断字符串是否是整数
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isInteger(final String str) {
        if (str != null && !"".equals(str) && isDigits(str) && !"0".startsWith(str)) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Checks whether the <code>String</code> contains only digit characters.
     * </p>
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isDigits(final String str) {
        return org.apache.commons.lang.math.NumberUtils.isDigits(str);
    }

    /**
     * <p>
     * Replaces all occurrences of a String within another String.
     * </p>
     * <p>
     * A <code>null</code> reference passed to this method is a no-op.
     * </p>
     * <pre>
     * StringUtils.replace(null, *, *)        = null
     * StringUtils.replace("", *, *)          = ""
     * StringUtils.replace("any", null, *)    = "any"
     * StringUtils.replace("any", *, null)    = "any"
     * StringUtils.replace("any", "", *)      = "any"
     * StringUtils.replace("aba", "a", null)  = "aba"
     * StringUtils.replace("aba", "a", "")    = "b"
     * StringUtils.replace("aba", "a", "z")   = "zbz"
     * </pre>
     *
     * @param text 文本
     * @param repl 替换
     * @param with 到
     * @return String
     */
    public static String replace(final String text, final String repl, final String with) {
        return org.apache.commons.lang.StringUtils.replace(text, repl, with);
    }

    /**
     * 把一个字符串列表用指定的分隔符连接在一起
     *
     * @param list      字符串列表
     * @param separator 分隔符
     * @return sb
     */
    public static String join(final List<String> list, final String separator) {
        final StringBuilder sb = new StringBuilder();
        int idx = 0;
        final int size = list.size();
        for (final String s : list) {
            sb.append(s);
            if (idx < size - 1) {
                sb.append(separator);
            }
            idx++;
        }
        return sb.toString();
    }

    /**
     * 返回小写字符串
     *
     * @param str 字符串
     * @return str
     */
    public static String toLowerCase(final String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.toLowerCase();
    }

    /**
     * 返回大写字符串
     *
     * @param str 字符串
     * @return str
     */
    public static String toUpperCase(final String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.toUpperCase();
    }

    /**
     * 获取字符串str含有separatorChar的个数
     *
     * @param str           字符串
     * @param separatorChar 分隔符
     * @return num
     */
    public static int getSeparatorCharNum(final String str, final char separatorChar) {
        if (isBlank(str)) {
            return 0;
        }
        int num = 0;
        final char[] ch = str.toCharArray();
        for (final char c : ch) {
            if (c == separatorChar) {
                num++;
            }
        }
        return num;
    }

    /**
     * 对象转string,可转null为""
     *
     * @param obj 对象
     * @return obj
     */
    public static String convertToString(final Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     * 转义SQL通配字符
     *
     * @param str SQL
     * @return str
     */
    public static String wildcardEscape(String str) {
        if (isNotBlank(str)) {
            str = str.replace("%", "/%");
            str = str.replace("_", "/_");
            str = str.replace("/", "//");
        }
        return str;
    }

    /**
     * 把用下划线(_)分割的字符串变成驼峰显示: FOO_BAR -> fooBar; foo_bar -> fooBar
     *
     * @param name 字符串
     * @return 驼峰显示字符串
     */
    public static String camelCase(final String name) {
        if (name == null) {
            return null;
        }
        final StringTokenizer tokenizer = new StringTokenizer(name, "_");
        final StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        while (tokenizer.hasMoreTokens()) {
            final String word = tokenizer.nextToken();
            if (firstTime) {
                sb.append(word.toLowerCase());
                firstTime = false;
            } else {
                final String lowercase = word.toLowerCase();
                sb.append(lowercase.substring(0, 1).toUpperCase());
                sb.append(lowercase.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * 按照字节码长度来截取字符串
     *
     * @param text        原始字符串
     * @param maxDataSize 最大字符串长度
     * @return 截取字符串
     */
    public static String limitStrOfBytes(final String text, final int maxDataSize) {
        byte[] bytes = new byte[0];
        try {
            bytes = nullToBlank(text).getBytes(BaseConstant.ENCODING);
            if (bytes.length > maxDataSize) {
                final byte[] newBytes = Arrays.copyOf(bytes, maxDataSize);
                return new String(newBytes, BaseConstant.ENCODING);
            }
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
        }
        return text;
    }

    /**
     * 截取字符串长度，若str长度大于maxSize则取maxSize长度，若str长度小于maxSize则取str原长度
     *
     * @param str     字符串
     * @param maxSize 最大
     * @return str
     */
    public static String limitStr(final String str, final int maxSize) {
        String limitStr = null;
        if (isBlank(str) || maxSize < 0) {
            limitStr = "";
        } else {
            limitStr = trim(str).length() >= maxSize ? trim(str).substring(0, maxSize) : trim(str);
        }
        return limitStr;
    }

    /**
     * 获取异常栈信息
     *
     * @param e           Throwable
     * @param maxByteSize 最大
     * @return result
     */
    public static String getExceptionStackTrace(final Throwable e, final Integer maxByteSize) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            String result = sw.getBuffer().toString();
            if (maxByteSize != null) {
                result = limitStrOfBytes(result, maxByteSize);
            }
            return result;
        } finally {
            try {
                if (sw != null) {
                    sw.close();
                }
            } catch (final IOException e1) {
                LOG.error(e1.getMessage(), e1);
            }
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 指定字符串是否以指定的文字结尾
     *
     * @param txt 要检查的字符串
     * @param end 要比较的结尾符
     * @return 是否以指定的文字结尾
     */
    public static boolean endsWith(final String txt, final String... end) {
        for (final String ed : end) {
            if (txt.endsWith(ed)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含中文
     *
     * @param word 文本
     * @return boolean
     */
    public static boolean isContainChinese(final String word) {
        if (isNotBlank(word)) {
            final Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
            final Matcher m = p.matcher(word);
            return m.find();
        }
        return false;
    }

    /**
     * 判断是否字符串仅仅包含英文字母和数字
     *
     * @param str 字符串
     * @return 只包含英文字母和数字
     */
    public static boolean onlyLettersAndNumbers(final String str) {
        if (isNotBlank(str)) {
            final Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
            final Matcher m = p.matcher(str);
            return m.find();
        }
        return false;
    }

    /**
     * 反转String
     *
     * @param str 字符串
     * @return str
     */
    public static String reverse(final String str) {
        String rtnStr = null;
        if (isNotEmpty(str)) {
            final StringBuffer sb = new StringBuffer(str);
            rtnStr = sb.reverse().toString();
        }
        return rtnStr;
    }

    /**
     * 左补白：如 lpad("abc",5,"x"),结果为"xxabc"
     *
     * @param str     源字符串
     * @param length  长度，字符串填补后的总长
     * @param replace 在源字符串左边补充的字符
     * @return str
     */
    public static String lpad(String str, final int length, final String replace) {
        if (str == null) {
            str = "";
        }
        while (StringUtils.getStringLength(str) < length) {
            str = replace + str;
        }
        return str;
    }

    /**
     * 右补白:如 rpad("abc",5,"x")结果为"abcxx"
     *
     * @param str     源字符串
     * @param length  长度，字符串填补后的总长
     * @param replace 在源字符串右边补充的字符
     * @return str
     */
    public static String rpad(String str, final int length, final String replace) {
        if (str == null) {
            str = "";
        }
        while (StringUtils.getStringLength(str) < length) {
            str = str + replace;
        }
        return str;
    }

    /**
     * 返回第一个不为空白字符串的字符串
     *
     * @param args 参数
     * @return String
     */
    public static String getNotBlankStr(final String... args) {
        for (final String arg : args) {
            if (isNotBlank(arg)) {
                return arg;
            }
        }
        return null;
    }

    /**
     * 是否中文
     *
     * @param c 参数
     * @return boolean
     * @throws UnsupportedEncodingException 编码异常
     */
    public static boolean isChineseChar(final char c) throws UnsupportedEncodingException {
        // 如果字节数大于1，是汉字
        return String.valueOf(c).getBytes(BaseConstant.ENCODING).length > 1;
    }

    /**
     * 中文处理
     *
     * @param str 字符
     * @param len 长度
     * @return result
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String substringByByte(final String str, int len) throws UnsupportedEncodingException {
        String result = str;
        // 原始字符不为null，也不是空字符串, 要截取的字节数大于0，且小于原始字符串的字节数
        if (isNotBlank(str) && len > 0 && len < str.getBytes("GBK").length) {
            final StringBuilder buff = new StringBuilder();
            char c;
            for (int i = 0; i < len; i++) {
                c = str.charAt(i);
                if (isChineseChar(c)) {
                    --len;
                }
                if (len > i) {
                    buff.append(c);
                }
            }
            result = buff.toString();
        }
        return result;
    }
    

    /**
     * 将对应位置的{n}替换为对应params中的数值
     *
     * @param str    要替换的字符串
     * @param params 参数列表，对应的param[1]替换{1},param[2]替换{2}...
     * @return 替换后结果
     */
    public static String replaceStringParam(String str, final List<String> params) {
        for (int i = 0; i < params.size(); i++) {
            str = str.replace("{" + i + "}", params.get(i));
        }
        return str;
    }

    /**
     * 将对应位置的{n}替换为对应params中的数值
     *
     * @param str    要替换的字符串
     * @param params 参数列表，对应的param[1]替换{1},param[2]替换{2}...
     * @return 替换后结果
     */
    public static String replaceStringParam(String str, final String... params) {
        for (int i = 0; i < params.length; i++) {
            str = str.replace("{" + i + "}", params[i]);
        }
        return str;
    }

    /**
     * 将123,456,789这样的字符串转化为List<Long>
     *
     * @param strValue
     * @param split    分隔符
     * @return
     */
    public static List<Long> str2List(final String strValue, final String split) {
        final List<Long> stringList = Lists.newArrayList();
        if (strValue == null || "".equals(strValue.trim())) {
            return stringList;
        } else {
            final String[] strings = strValue.split(split);
            try {
                for (final String str : strings) {
                    stringList.add(Long.valueOf(str.trim()));
                }
            } catch (final Exception e) {
                LOG.debug(e.getMessage());
                return stringList;
            }
            return stringList;
        }
    }
}
