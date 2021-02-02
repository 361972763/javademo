package com.chenlijiang.demo;

import com.chenlijiang.demo.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名，验签测试
 * @author clj
 * @date 2021/2/2
 * @since 2021.1.0
 */
@SpringBootTest
@Slf4j
public class SignTest {

    /**
     * 首先使用GeneratorRSKKey工具生成公钥私钥
     */
    private static final  String PUBLIC_KEY  = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALv6I0HrxW91DKDRpJ67/cEHi2gn35fgzcDTH4DXswQ9Xv8mRS1jyjEZogzAuHA0zO6YNdKfPSjj+LQIMKEIwPkCAwEAAQ==";
    private static final  String PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAu/ojQevFb3UMoNGknrv9wQeLaCffl+DNwNMfgNezBD1e/yZFLWPKMRmiDMC4cDTM7pg10p89KOP4tAgwoQjA+QIDAQABAkBlXIVkh4kIB9rOYR1ehspXKwVOHaog0JTCNFXZePZMAwl5Y4XN1h3jq4KcAREATqZ8oy2peq3cnXoX32qsHoAxAiEA9/xFR1fyXGUVGbAlGIVO4PNtXZZAxk6lKFqRLG2QQdUCIQDCDWID4yKSRcu1L0TxBSGxzS8hiqOmgJFkR3Wmi5CwlQIgEAo6NO4kWAOVxM6W56ofuv8j0MzaLbmCh0SsZTJFBW0CIF++GKEQXQtMcoCXrO05+fDu6pUjATFyd8nBVDPy4lWtAiEAtTWUJCORLNYlRcCY/QWtoGXONYC8dxTK8ES/PGS6BaY=";


    @Test
    public void  testVerify () throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Map<String,Object> param = new TreeMap<>();
        param.put("name","陈利江");
        param.put("sex",1);
        param.put("status",1);

        //生成签名
        StringBuilder sb = new StringBuilder();
        param.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry ->
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&")
        );
        String paramStr = sb.toString().substring(0, sb.length() - 1);
        //私钥签名
        String sign = SignUtils.executeSignature(PRIVATE_KEY,paramStr);
        log.info("生成签名={}",sign);
        //公钥验签
        Boolean checkResult = SignUtils.doCheck(PUBLIC_KEY,sign,paramStr);
        log.info("验证结果 = {}",checkResult);
    }


}
