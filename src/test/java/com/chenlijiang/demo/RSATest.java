package com.chenlijiang.demo;

import com.chenlijiang.demo.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static com.chenlijiang.demo.utils.RSAUtil.encrypt;
import static com.chenlijiang.demo.utils.RSAUtil.genKeyPair;

/** RSA加密解密
 * @author clj
 * @date 2021/2/2
 * @since 2021.1.0
 */
@SpringBootTest
@Slf4j
public class RSATest {
    /**
     * 首先使用GeneratorRSKKey工具生成公钥私钥
     */
    private static final  String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALv6I0HrxW91DKDRpJ67/cEHi2gn35fgzcDTH4DXswQ9Xv8mRS1jyjEZogzAuHA0zO6YNdKfPSjj+LQIMKEIwPkCAwEAAQ==";
    private static final  String PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAu/ojQevFb3UMoNGknrv9wQeLaCffl+DNwNMfgNezBD1e/yZFLWPKMRmiDMC4cDTM7pg10p89KOP4tAgwoQjA+QIDAQABAkBlXIVkh4kIB9rOYR1ehspXKwVOHaog0JTCNFXZePZMAwl5Y4XN1h3jq4KcAREATqZ8oy2peq3cnXoX32qsHoAxAiEA9/xFR1fyXGUVGbAlGIVO4PNtXZZAxk6lKFqRLG2QQdUCIQDCDWID4yKSRcu1L0TxBSGxzS8hiqOmgJFkR3Wmi5CwlQIgEAo6NO4kWAOVxM6W56ofuv8j0MzaLbmCh0SsZTJFBW0CIF++GKEQXQtMcoCXrO05+fDu6pUjATFyd8nBVDPy4lWtAiEAtTWUJCORLNYlRcCY/QWtoGXONYC8dxTK8ES/PGS6BaY=";

    @Test
    public void testEncrypt () throws Exception {
        String data = "加密内容";
        KeyPair keyPair = genKeyPair();
        // 获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();
        log.info("公钥：{}",Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        // 获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        log.info("私钥：{}", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        // 公钥加密
        String encryptedString = encrypt(data, publicKey);
        log.info("加密后：{}" , encryptedString);
        // 私钥解密
        String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
        log.info("解密后：{}", decryptedString);

    }
}
