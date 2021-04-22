package util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DESUtil {
    /**
     * 偏移变量，固定占8位字节
     */
    private final static String IV_PARAMETER = "12345678";
    /**
     * 加密密钥String字符串，长度必须>8
     */
    private final static String DES_KEY = "des__key";
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";

    private static DESUtil instance;

    private Cipher cipher;

    private SecretKeyFactory keyFactory;

    private IvParameterSpec ivParameterSpec;

    private DESUtil() {
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static DESUtil getInstance() {
        if (instance == null) {
            instance = new DESUtil();
        }
        return instance;
    }

    /**
     * 生成key
     *
     * @param password
     * @return
     * @throws Exception
     */
    private Key generateKey(String password) throws Exception {
        if (password == null || password.length() < 8) {
            throw new Exception("加/解密失败，key不能小于8位！");
        }
        ivParameterSpec = new IvParameterSpec(password.substring(0, 8).getBytes(CHARSET));
        DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET));
        return keyFactory.generateSecret(dks);
    }

    public final String encrypt(String data) throws Exception {
        return encrypt(DES_KEY, data);
    }


    /**
     * DES加密字符串
     *
     * @param password 加密密码，长度不能够小于8位
     * @param data     待加密字符串
     * @return 加密后内容
     */
    public String encrypt(String password, String data) throws Exception {
        if (data == null)
            return null;
        Key secretKey = generateKey(password);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));
        //JDK1.8及以上可直接使用Base64，JDK1.7及以下可以使用BASE64Encoder
        //Android平台可以使用android.util.Base64
        return new String(Base64.getEncoder().encode(bytes));
    }

    public final String decrypt(String data) throws Exception {
        return decrypt(DES_KEY, data);
    }

    /**
     * DES解密字符串
     *
     * @param password 解密密码，长度不能够小于8位
     * @param data     待解密字符串
     * @return 解密后内容
     */
    public String decrypt(String password, String data) throws Exception {
        if (data == null)
            return null;
        byte[] databyte = Base64.getDecoder().decode(data.getBytes(CHARSET));
        Key secretKey = generateKey(password);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] decode = cipher.doFinal(databyte);
        return new String(decode, CHARSET);
    }

    public String encryptFile(String srcFile) throws Exception {
        File file = new File(srcFile);
        if (file.exists()) {
            String destFile = file.getParent() + "/encrypt_" + file.getName();
            return encryptFile(DES_KEY, srcFile, destFile);
        } else {
            throw new Exception("加密失败，待加密的文件不存在！");
        }
    }

    public String encryptFile(String password, String srcFile) throws Exception {
        File file = new File(srcFile);
        if (file.exists()) {
            String destFile = file.getParent() + "/encrypt_" + file.getName();
            return encryptFile(password, srcFile, destFile);
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
    public String encryptFile(String password, String srcFile, String destFile) throws Exception {
        if (!new File(srcFile).exists()) {
            throw new Exception("加密失败，待加密的文件不存在！");
        }
        File file = new File(destFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        Key secretKey = generateKey(password);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
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
            return decryptFile(DES_KEY, srcFile, destFile);
        } else {
            throw new Exception("加密失败，已加密的文件不存在！");
        }
    }

    public String decryptFile(String password, String srcFile) throws Exception {
        File file = new File(srcFile);
        if (file.exists()) {
            String destFile = file.getParent() + "/decrypt_" + file.getName();
            return decryptFile(password, srcFile, destFile);
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
    public String decryptFile(String password, String srcFile, String destFile) throws Exception {
        if (!new File(srcFile).exists()) {
            throw new Exception("加密失败，已加密的文件不存在！");
        }
        File file = new File(destFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        Key secretKey = generateKey(password);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
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
