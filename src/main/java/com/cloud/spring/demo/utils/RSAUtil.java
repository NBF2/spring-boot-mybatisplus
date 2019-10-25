package com.cloud.spring.demo.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: RSA, 一种非对称加密算法
 * @author: d17632
 * @date: 2019/10/24
 * @modified by:
 * @modified date:
 * @problem no:
 */
public class RSAUtil {
    /*
        RSA算法，需要两个密钥来进行加密和解密，分别是公钥和私钥。
        需要注意的一点，这个公钥和私钥必须是一对的，如果用公钥对数据进行加密，那么只有使用对应的私钥才能解密，反之亦然。
        由于加密和解密使用的是两个不同的密钥，因此，这种算法叫做非对称加密算法。

        其算法具体实现基于一个十分简单的数论事实：将两个大素数相乘十分容易，但是想要对其乘积进行因式分解却极其困难，因此可以将乘积公开作为加密密钥。
     */

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KRY = "RSAPublicKey";
    private static final String PRIVATE_KRY = "RSAPrivateKey";

    private static final int SEGMENT_SIZE = 117;

    private static final Map<String, Object> KEY_PAIRS = new HashMap<>();

    public static void generateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            // ‘512’，表示生成的是128位字符
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            KEY_PAIRS.put(PUBLIC_KRY, rsaPublicKey);
            KEY_PAIRS.put(PRIVATE_KRY, rsaPrivateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取私钥
     * @return
     */
    public static RSAPrivateKey getPrivateKey() {
        return (RSAPrivateKey) KEY_PAIRS.get(PRIVATE_KRY);
    }

    /**
     * 获取公钥
     * @return
     */
    public static RSAPublicKey getPublicKey() {
        return (RSAPublicKey) KEY_PAIRS.get(PUBLIC_KRY);
    }

    /**
     * RSA私钥加密
     *
     * @param message 要加密的信息
     * @return 加密后的字符串
     */
    public static String encryptByPrivateKey(String message, RSAPrivateKey rsaPrivateKey) {
        try {
            //取得私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //加密信息
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(message.getBytes());
            return Hex.encodeHexString(resultBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA公钥加密
     *
     * @param message 要加密的信息
     * @return 加密后的字符串
     */
    public static String encryptByPublicKey(String message, RSAPublicKey rsaPublicKey) {
        try {
            //取得公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //加密信息
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(message.getBytes());
            return Hex.encodeHexString(resultBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA私钥解密
     *
     * @param message 要解密的信息
     * @return 解密后的字符串
     */
    public static String decryptByPrivateKey(String message, RSAPrivateKey rsaPrivateKey) {
        try {
            //取得私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(Hex.decodeHex(message.toCharArray()));
            return new String(resultBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA公钥解密
     *
     * @param message 要解密的信息
     * @return 解密后的字符串
     */
    public static String decryptByPublicKey(String message, RSAPublicKey rsaPublicKey) {
        try {
            //取得公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(Hex.decodeHex(message.toCharArray()));
            return new String(resultBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > SEGMENT_SIZE) {
                cache = cipher.doFinal(srcBytes, offSet, SEGMENT_SIZE);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * SEGMENT_SIZE;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data
     *            加密数据
     * @param rsaPrivateKey
     *            私钥
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, RSAPrivateKey rsaPrivateKey) throws Exception {
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data
     *            加密数据
     * @param rsaPublicKey
     *            公钥
     * @param sign
     *            数字签名
     *
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, RSAPublicKey rsaPublicKey, String sign)
            throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = rsaPublicKey.getEncoded();

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));
    }

}
