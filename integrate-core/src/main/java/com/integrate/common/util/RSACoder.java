package com.integrate.common.util;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public abstract class RSACoder extends Coder {

    public static final String KEY_ALGORTHM = "RSA";
    public static final String SIGN_ALGORITHM= "SHA1withRSA";
    public static final String CIPHER_ALGORITHM= "RSA/ECB/PKCS1Padding";

    /**
     * 用私钥加密
     * @param data	加密数据
     * @param key	密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data,String key) throws Exception {
        //对私钥解密
        byte[] keyBytes = decryptBASE64(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用私钥解密
     * @param data 	加密数据
     * @param key	密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data,String key) throws Exception {
        //对私钥解密
        byte[] keyBytes = decryptBASE64(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     * @param data	加密数据
     * @param key	密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data,String key) throws Exception {
        //对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥解密
     * @param data	加密数据
     * @param key	密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data,String key) throws Exception {
        //对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密并对字节数组结果进行base64
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey2Base64(String publicKey, String data) throws Exception {
        byte[] encryptByte = encryptByPublicKey(data.getBytes(), publicKey);
        return encryptBASE64(encryptByte);
    }

    /**
     * 用私钥加密并对字节数组结果进行base64
     * @param privateKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey2Base64(String privateKey, String data) throws Exception {
        byte[] encryptByte = encryptByPrivateKey(data.getBytes(), privateKey);
        return encryptBASE64(encryptByte);
    }

    /**
     * 对密文进行base64解密成字节数组再用公钥解密
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey4Base64(String publicKey, String data) throws Exception {
        byte[] bytes = decryptBASE64(data);
        byte[] decryptByte = decryptByPublicKey(bytes, publicKey);
        return new String(decryptByte);
    }

    /**
     * 对密文进行base64解密成字节数组再用私钥解密
     * @param privateKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey4Base64(String privateKey, String data) throws Exception {
        byte[] bytes = decryptBASE64(data);
        byte[] decryptByte = decryptByPrivateKey(bytes, privateKey);
        return new String(decryptByte);
    }

    /**
     *	用私钥对信息生成RSA数字签名
     * @param content	    待签名数据
     * @param input_charset  编码格式
     * @param privateKey	私钥
     * @return  签名值
     */
    public static String sign(String content, String input_charset, String privateKey) throws Exception {
        //解密私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(priKey);
        signature.update(content.getBytes(input_charset));

        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     * @param content 待签名数据
     * @param input_charset 编码格式
     * @param publicKey	公钥
     * @param sign	数字签名
     * @return  布尔值
     */
    public static boolean verify(String content, String input_charset, String publicKey, String sign) throws Exception {
        //解密公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(content.getBytes(input_charset));

        //验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

}
