package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5加密，方法默认为32位加密，小写
 * 大写 = toUpperCase
 * 16位加密 = 32位加密.substring(8, 24)
 */
public class MD5Utils {

    /**
     * MD5加密String
     */
    public static String MD5Encode(String origin) throws Exception {
        return ByteUtils.bytesToHexString(MD5Bytes(origin));
    }

    /**
     * MD5加密字符串为bytes
     */
    public static byte[] MD5Bytes(String str) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] byteArray = str.getBytes(StandardCharsets.UTF_8);
        byte[] md5Bytes = md5.digest(byteArray);
        return md5Bytes;
    }

    /**
     * MD5加密Object为bytes
     */
    public static byte[] MD5Bytes(Object object) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md5.digest(ByteUtils.objectToBytes(object));
        return md5Bytes;
    }


    /**
     * MD5加密对象
     * 对象必须为可序列化对象implements Serializable
     */
    public static String MD5EncodeObject(Object object) throws Exception {
        byte[] md5Bytes = MD5Bytes(object);
        return ByteUtils.bytesToHexString(md5Bytes);
    }
}
