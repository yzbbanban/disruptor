package com.yzb.study.disruptor.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密解密工具类（提供对称加密）
 */
public final class AESUtil {
    private static final String DEFAULT_CHARSET = "UTF-8";  // 默认字符集
    private static final String KEY_AES = "AES";    // 加密方式
    private static final String KEY = "k6UpC9igjDrTRI9UtmK1";   // 加密密钥

    private static final int EN_CODE = Cipher.ENCRYPT_MODE;   // 加密模式
    private static final int DE_CODE = Cipher.DECRYPT_MODE;   // 解密模式

    private static final String DES_ALGORITHM = "AES";

    /**
     * 加密
     *
     * @param data 需要加密的数据
     * @return 返回加密后的结果
     */
    public static String encrypt(String data) {
        return doAES(data, KEY, EN_CODE);
    }

    /**
     * 解密
     *
     * @param data 需要解密的数据
     * @return 返回解密后的结果
     */
    public static String decrypt(String data) {
        return doAES(data, KEY, DE_CODE);
    }

    /**
     * 加密或解密方法
     *
     * @param data  需要加密或解密的内容
     * @param key   加密或解密的密钥
     * @param model 处理模式
     * @return 返回处理结果
     */
    private static String doAES(String data, String key, final int model) {
        try {
            data = data == null ? "" : data;
            key = key == null ? "" : key;

            if ("".equalsIgnoreCase(data) || "".equalsIgnoreCase(key)) {
                return null;
            }

            // 构造密钥生成器，指定为AES方式，不区分大小写
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);

            // 根据EcnodeRules规则初始化密钥生成器
            // 根据传入的字节数组，生成一个128位的随机源
            kgen.init(128, new SecureRandom(key.getBytes(DEFAULT_CHARSET)));

            // 产生原始对称密钥
            SecretKey secretKey = generateKey(key);

            // 获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();

            // 根据字节数组生成AES密钥
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, KEY_AES);

            // 根据指定的AES算法创建密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);

            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(model, keySpec);

            byte[] content; // 用于存储加密内容
            byte[] result;
            switch (model) {
                case EN_CODE:
                    content = data.getBytes(DEFAULT_CHARSET);
                    result = cipher.doFinal(content);

                    // 将二进制转换成16进制
                    return parseByte2HexStr(result);
                case DE_CODE:
                    content = parseHexStr2Byte(data);
                    result = cipher.doFinal(content);

                    // 将二进制转为字符串
                    return new String(result, DEFAULT_CHARSET);
                default:
                    throw new RuntimeException("The Encrypt Model Is Wrong!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得秘密密钥
     *
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException {
        try {
            //防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(secretKey.getBytes());

            // 为我们选择的DES算法生成一个KeyGenerator对象
            KeyGenerator kg = null;
            try {
                kg = KeyGenerator.getInstance(DES_ALGORITHM);
            } catch (NoSuchAlgorithmException e) {
            }
            kg.init(secureRandom);
            //kg.init(56, secureRandom);

            // 生成密钥
            return kg.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf 二进制数据
     * @return 返回16进制结果
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 16进制字符串
     * @return 返回二进制数据
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
