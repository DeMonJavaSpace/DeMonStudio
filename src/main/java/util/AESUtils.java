package util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author DeMon
 * Created on 2021/5/26.
 * E-mail idemon_liu@qq.com
 * Desc: AES加密实现
 */
public class AESUtils {
    /**
     * 偏移变量，AES固定占16位字节
     */
    private String ivParameter = "";
    /**
     * 加密密钥String字符串，长度必须>8
     */
    private String desKey = "des__key";
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    /**
     * 默认编码
     */
    private String charset = "UTF-8";

    private static AESUtils instance;

    private Cipher cipher;

    private KeyGenerator keyGenerator;

    private int digits = 128;

    private AESUtils() {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static AESUtils getInstance() {
        if (instance == null) {
            instance = new AESUtils();
        }
        return instance;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }


    /**
     * 偏移量
     *
     * @param ivParameter
     */
    public void setIvParameter(String ivParameter) {
        this.ivParameter = ivParameter;
    }

    /**
     * 密钥,AES无长度限制
     *
     * @param desKey
     */
    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }


    /**
     * 生成key
     *
     * @return
     * @throws Exception
     */
    private void generateKey(int mode) throws Exception {
        //2.根据密钥规则初始化密钥生成器生成一个128位的随机源,根据传入的字节数组
        keyGenerator.init(digits, new SecureRandom(desKey.getBytes()));
        //3.产生原始对称密钥
        SecretKey key = keyGenerator.generateKey();
        //4.获得原始对称密钥的字节数组
        byte[] raw = key.getEncoded();
        //5.根据字节数组生成AES密钥
        SecretKey secretKey = new SecretKeySpec(raw, ALGORITHM);
        //6.根据指定算法AES自成密码器
        //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
        if (Utils.isEmpty(ivParameter)) {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(mode, secretKey);
        } else {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            byte[] bytes = ivParameter.getBytes(charset);
            System.out.println("偏移量字节长度=" + bytes.length);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bytes);
            cipher.init(mode, secretKey, ivParameterSpec);
        }
    }


    /**
     * DES加密字符串
     *
     * @param data 待加密字符串
     * @return 加密后内容
     */
    public String encrypt(String data) throws Exception {
        if (data == null)
            return null;
        generateKey(Cipher.ENCRYPT_MODE);
        byte[] bytes = cipher.doFinal(data.getBytes(charset));
        //JDK1.8及以上可直接使用Base64，JDK1.7及以下可以使用BASE64Encoder
        //Android平台可以使用android.util.Base64
        return new String(Base64.getEncoder().encode(bytes));
    }


    /**
     * DES解密字符串
     *
     * @param data 待解密字符串
     * @return 解密后内容
     */
    public String decrypt(String data) throws Exception {
        if (data == null)
            return null;
        byte[] databyte = Base64.getDecoder().decode(data.getBytes(charset));
        generateKey(Cipher.DECRYPT_MODE);
        byte[] decode = cipher.doFinal(databyte);
        return new String(decode, charset);
    }


    public String encryptFile(String srcFile) throws Exception {
        File file = new File(srcFile);
        if (file.exists()) {
            String destFile = file.getParent() + "/encrypt_" + file.getName();
            return encryptFile(srcFile, destFile);
        } else {
            throw new Exception("加密失败，待加密的文件不存在！");
        }
    }

    /**
     * DES加密文件
     *
     * @param srcFile  待加密的文件
     * @param destFile 加密后存放的文件路径
     * @return 加密后的文件路径
     */
    public String encryptFile(String srcFile, String destFile) throws Exception {
        if (!new File(srcFile).exists()) {
            throw new Exception("加密失败，待加密的文件不存在！");
        }
        File file = new File(destFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        generateKey(Cipher.ENCRYPT_MODE);
        InputStream is = new FileInputStream(srcFile);
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
        return destFile;
    }

    public String decryptFile(String srcFile) throws Exception {
        File file = new File(srcFile);
        if (file.exists()) {
            String destFile = file.getParent() + "/decrypt_" + file.getName();
            return decryptFile(srcFile, destFile);
        } else {
            throw new Exception("加密失败，已加密的文件不存在！");
        }
    }

    /**
     * DES解密文件
     *
     * @param srcFile  已加密的文件
     * @param destFile 解密后存放的文件路径
     * @return 解密后的文件路径
     */
    public String decryptFile(String srcFile, String destFile) throws Exception {
        if (!new File(srcFile).exists()) {
            throw new Exception("加密失败，已加密的文件不存在！");
        }
        File file = new File(destFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        generateKey(Cipher.DECRYPT_MODE);
        InputStream is = new FileInputStream(srcFile);
        OutputStream out = new FileOutputStream(destFile);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        is.close();
        out.close();
        return destFile;
    }
}
