package com.cloud.spring.demo;

import com.cloud.spring.demo.utils.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 *
 * @Description: Administrator
 *
 * @author: 
 * @date: 2019/10/24
 *
 * @modified by:
 * @modified date:
 * @problem no:
 */
public class AlgorithmTest {
    @Test
    public void Base64Test() {
        String message = "zhangsan";
        System.out.println("待Base64算法加密的字符串为：" + message);
        String secretMessage = Base64Util.encode(message);
        System.out.println("原始信息<" + message + ">, Base64加密后，信息为：" + secretMessage);
        String originMessage = Base64Util.decode(secretMessage);
        System.out.println("加密信息<" + secretMessage + ">, Base64解密后，信息为：" + originMessage);
    }

    @Test
    public void M5DTest() {
        String message = "123456";
        System.out.println("待MD5算法加密的字符串为：" + message);
        String secretMessage = MD5Util.encrypt(message.getBytes());
        System.out.println("原始信息<" + message + ">, MD5加密后，信息为：" + secretMessage);
    }

    @Test
    public void SHATest() {
        String message = "123456";
        System.out.println("待SHA算法加密的字符串为：" + message);
        String secretMessage = SHAUtil.encrypt(message.getBytes());
        System.out.println("原始信息<" + message + ">, SHA加密后，信息为：" + secretMessage);
    }

    @Test
    public void AESTest() {
        String message = "123456";
        String encryptMessge = AESUtil.encrypt(message);
        System.out.println("加密前：" + message + ", 加密后：" + encryptMessge);
        String decryptMessage = AESUtil.decrypt(encryptMessge);
        System.out.println("解密前：" + encryptMessge + "，解密后：" + decryptMessage);
    }

    @Test
    public void RSATest() throws Exception {
        RSAUtil.generateKey();
        RSAPublicKey rsaPublicKey = RSAUtil.getPublicKey();
        RSAPrivateKey rsaPrivateKey =RSAUtil.getPrivateKey();
        String message = "123456";
        System.out.println("将要加密的信息：" + message);
        System.out.println("生成的公钥：" + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
        System.out.println("生成的私钥：" + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
        String encryptMessage = RSAUtil.encryptByPrivateKey(message, rsaPrivateKey);
        System.out.println("私钥加密后的信息：" + encryptMessage);
        String decryptMessage = RSAUtil.decryptByPublicKey(encryptMessage, rsaPublicKey);
        System.out.println("公钥解密后的信息：" + decryptMessage);
        String encryptMessageByPubKey = RSAUtil.encryptByPublicKey(message, rsaPublicKey);
        System.out.println("公钥加密后的信息：" + encryptMessageByPubKey);
        String decryptMessageByPriKey = RSAUtil.decryptByPrivateKey(encryptMessageByPubKey, rsaPrivateKey);
        System.out.println("私钥解密后的信息：" + decryptMessageByPriKey);
        String signMessage = RSAUtil.sign(message.getBytes(), rsaPrivateKey);
        System.out.println("私钥签名后的信息：" + signMessage);
        boolean signVerfy = RSAUtil.verify(message.getBytes(), rsaPublicKey, signMessage);
        System.out.println("公钥验签结果：" + signVerfy);
    }
}
