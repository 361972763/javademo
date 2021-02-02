package com.chenlijiang.demo.utils;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/** RSA签名生成和验签工具
 *  java.Security包
 * @author clj
 * @date 2021/2/2
 * @since 2021.1.0
 */
public class SignUtils {
    private final static String RSA = "RSA";

    private final static String MD5_WITH_RSA = "MD5withRSA";

    /**
     * 执行签名
     *
     * @param rsaPrivateKey 私钥
     * @param param 参数内容
     * @return 签名后的内容，base64后的字符串
     * @throws InvalidKeyException      InvalidKeyException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     * @throws SignatureException       SignatureException
     */
    public static String executeSignature(String rsaPrivateKey, String param) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException, SignatureException {
        // base64解码私钥
        byte[] decodePrivateKey = Base64.getDecoder().decode(rsaPrivateKey.replace("\r\n", ""));

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodePrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用md5生成内容摘要，再用RSA的私钥加密，进而生成数字签名
        Signature signature = Signature.getInstance(MD5_WITH_RSA);
        signature.initSign(privateKey);
        signature.update(param.getBytes());
        // 生成签名
        byte[] result = signature.sign();
        // base64编码签名为字符串
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * 验证签名
     *
     * @param rsaPublicKey 公钥
     * @param sign         签名
     * @param param          参数内容
     * @return 验证结果
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     * @throws InvalidKeyException      InvalidKeyException
     * @throws SignatureException       SignatureException
     */
    public static boolean doCheck(String rsaPublicKey, String sign, String param) throws NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException, SignatureException {
        // base64解码公钥
        byte[] decodePublicKey = Base64.getDecoder().decode(rsaPublicKey.replace("\r\n", ""));

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodePublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance(MD5_WITH_RSA);
        signature.initVerify(publicKey);
        signature.update(param.getBytes());
        // base64解码签名为字节数组
        byte[] decodeSign = Base64.getDecoder().decode(sign);
        // 验证签名
        return signature.verify(decodeSign);
    }
}
