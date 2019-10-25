package com.cloud.spring.demo.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: DSA-Digital Signature Algorithm 签名算法
 * @author: Administrator
 * @date: 2019/10/25
 * @modified by:
 * @modified date:
 * @problem no:
 */
public class DSAUtil {
    /**
     * DSA不单单只有公钥、私钥，还有数字签名，私钥加密生成数字签名，公钥验证数据及签名。
     * 数字签名的作用就是校验数据在传输过程中不被修改。数字签名，是单向加密的升级！
     */

    public static final String ALGORITHM = "DSA";

    /**
     * 默认密钥字节数
     */
    private static final int KEY_SIZE = 1024;

    private static final String DEFAULT_SEED = "asssssss123456343344dsfghgvvvnbnnn";

    private static final String PUBLIC_KEY = "DSAPublicKey";

    private static final String PRIVATE_KEY = "DSAPrivateKey";

    private static final Map<String, Object> KEY_PAIRS = new HashMap<>();

    /**
     * 用私钥对信息生成数字签名
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        //解密由base64编码的私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey);

        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);

        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        //取私钥对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initSign(priKey);
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     * @param data 加密的数据
     * @param publicKey
     * @param sign
     * @return
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        //解密由base64编码的公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initVerify(pubKey);
        signature.update(data);

        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 生成密钥
     * @param seed 种子
     * @throws Exception
     */
    public static void initKey(String seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
        //初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        keygen.initialize(KEY_SIZE, secureRandom);
        KeyPair keyPairs = keygen.genKeyPair();

        DSAPublicKey publicKey = (DSAPublicKey) keyPairs.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keyPairs.getPrivate();

        KEY_PAIRS.put(PUBLIC_KEY, publicKey);
        KEY_PAIRS.put(PRIVATE_KEY, privateKey);
    }

    /**
     * 默认生成密钥
     * @return 密钥对象
     * @throws Exception
     */
    public static void initKey() throws Exception {
        initKey(DEFAULT_SEED);
    }

    /**
     * 获取公钥
     * @return
     */
    public static String getPublicKey() {
        DSAPublicKey publicKey = (DSAPublicKey) KEY_PAIRS.get(PUBLIC_KEY);
        return Base64.encodeBase64String(publicKey.getEncoded());
    }

    /**
     * 获取私钥
     * @return
     */
    public static String getPrivateKey() {
        DSAPrivateKey privateKey = (DSAPrivateKey) KEY_PAIRS.get(PRIVATE_KEY);
        return Base64.encodeBase64String(privateKey.getEncoded());
    }
}
