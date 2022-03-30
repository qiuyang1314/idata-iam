package com.zyaud.idata.iam.common.utils;

import cn.hutool.core.util.ObjectUtil;
import com.zyaud.fzhx.common.utils.BizAssert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;


/**
 * RSA算法加密/解密工具类V2
 */
public class RSAUtils {
    /**
     * 算法名称
     */
    private static final String ALGORITHM = "RSA";
    /**
     * 默认密钥大小
     */
    private static final int KEY_SIZE = 1024;
    /**
     * 密钥对生成器
     */
    private static KeyPairGenerator keyPairGenerator = null;

    /**
     * 缓存的密钥对
     */
    private static KeyPair keyPair = null;
    /**
     * Base64 编码/解码器 JDK1.8
     */
    private static Base64.Decoder decoder = Base64.getDecoder();
    private static Base64.Encoder encoder = Base64.getEncoder();


    /** 初始化密钥工厂 */
    static {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            KeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成密钥对
     * 将密钥分别用Base64编码保存到#publicKey.properties#和#privateKey.properties#文件中
     * 保存的默认名称分别为publicKey和privateKey
     */
    public static synchronized KeyPair generateKeyPair() {
        try {
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom(UUID.randomUUID().toString().replaceAll("-", "").getBytes()));
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    /**
     * 获取公钥字符串
     *
     * @param keyPair
     * @return
     */
    public static String getPublicKeyString(KeyPair keyPair) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        return encoder.encodeToString(rsaPublicKey.getEncoded());
    }

    /**
     * 获取公钥
     *
     * @param publicKeyString
     * @return
     */
    public static RSAPublicKey getRSAPublicKey(String publicKeyString) {
        byte[] decoded = decoder.decode(publicKeyString);
        RSAPublicKey rsaPublicKey = null;
        try {
            rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return rsaPublicKey;
    }

    /**
     * 获取私钥字符串
     *
     * @param keyPair
     * @return
     */
    public static String getPrivateKeyString(KeyPair keyPair) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return encoder.encodeToString(rsaPrivateKey.getEncoded());
    }

    /**
     * 获取私钥
     *
     * @param privateKeyString
     * @return
     */
    public static RSAPrivateKey getRSAPrivateKey(String privateKeyString) {
        //base64编码的私钥
        byte[] decoded = decoder.decode(privateKeyString);
        RSAPrivateKey privateKey = null;
        try {
            privateKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM)
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return privateKey;
    }


    /**
     * RSA公钥加密
     *
     * @param string          等待加密的数据
     * @param publicKeyString RSA 公钥 if null then getPublicKey()
     * @return 加密后的密文(16进制的字符串)
     */
    public static String encryptByPublic(String string, String publicKeyString) {
        try {
            RSAPublicKey publicKey = getRSAPublicKey(publicKeyString);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            //该密钥能够加密的最大字节长度
            int splitLength = publicKey.getModulus().bitLength() / 8 - 11;
            byte[][] arrays = splitBytes(string.getBytes(), splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte[] array : arrays) {
                stringBuffer.append(bytesToHexString(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA私钥加密
     *
     * @param string           等待加密的数据
     * @param privateKeyString RSA 私钥 if null then getPrivateKey();
     * @return 加密后的密文(16进制的字符串)
     */
    public static String encryptByPrivate(String string, String privateKeyString) {
        if (null == privateKeyString && "".equals(privateKeyString)) {
            throw new RuntimeException("私钥不能为空！");
        }
        try {
            RSAPrivateKey privateKey = getRSAPrivateKey(privateKeyString);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            //该密钥能够加密的最大字节长度
            int splitLength = privateKey.getModulus().bitLength() / 8 - 11;
            byte[][] arrays = splitBytes(string.getBytes(), splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte[] array : arrays) {
                stringBuffer.append(bytesToHexString(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * RSA私钥解密
     *
     * @param content          等待解密的数据
     * @param privateKeyString RSA 私钥 if null then getPrivateKey()
     * @return 解密后的明文
     */
    public static String decryptByPrivate(String content, String privateKeyString) {
        try {
            RSAPrivateKey privateKey = getRSAPrivateKey(privateKeyString);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //该密钥能够加密的最大字节长度
            int splitLength = privateKey.getModulus().bitLength() / 8;
            byte[] contentBytes = hexStringToBytes(content);
            byte[][] arrays = splitBytes(contentBytes, splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte[] array : arrays) {
                stringBuffer.append(new String(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA公钥解密
     *
     * @param content         等待解密的数据
     * @param publicKeyString RSA 公钥 if null then getPublicKey()
     * @return 解密后的明文
     */
    public static String decryptByPublic(String content, String publicKeyString) {
        if (null == publicKeyString && "".equals(publicKeyString)) {
            throw new RuntimeException("公钥不能为空！");
        }
        try {
            RSAPublicKey publicKey = getRSAPublicKey(publicKeyString);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            //该密钥能够加密的最大字节长度
            int splitLength = publicKey.getModulus().bitLength() / 8;
            byte[] contentBytes = hexStringToBytes(content);
            byte[][] arrays = splitBytes(contentBytes, splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte[] array : arrays) {
                stringBuffer.append(new String(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据限定的每组字节长度，将字节数组分组
     *
     * @param bytes       等待分组的字节组
     * @param splitLength 每组长度
     * @return 分组后的字节组
     */
    public static byte[][] splitBytes(byte[] bytes, int splitLength) {
        //bytes与splitLength的余数
        int remainder = bytes.length % splitLength;
        //数据拆分后的组数，余数不为0时加1
        int quotient = remainder != 0 ? bytes.length / splitLength + 1 : bytes.length / splitLength;
        byte[][] arrays = new byte[quotient][];
        byte[] array = null;
        for (int i = 0; i < quotient; i++) {
            //如果是最后一组（quotient-1）,同时余数不等于0，就将最后一组设置为remainder的长度
            if (i == quotient - 1 && remainder != 0) {
                array = new byte[remainder];
                System.arraycopy(bytes, i * splitLength, array, 0, remainder);
            } else {
                array = new byte[splitLength];
                System.arraycopy(bytes, i * splitLength, array, 0, splitLength);
            }
            arrays[i] = array;
        }
        return arrays;
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param bytes 即将转换的数据
     * @return 16进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(0xFF & bytes[i]);
            if (temp.length() < 2) {
                sb.append(0);
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hex) {
        int len = (hex.length() / 2);
        hex = hex.toUpperCase();
        byte[] result = new byte[len];
        char[] chars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(chars[pos]) << 4 | toByte(chars[pos + 1]));
        }
        return result;
    }

    /**
     * 将char转换为byte
     *
     * @param c char
     * @return byte
     */
    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 签名
     *
     * @param params           待签名数据
     * @param privateKeyString 私钥
     * @return 签名
     */
    public static String sign(Map<String, Object> params, String privateKeyString) {
        String string = null;
        RSAPrivateKey rsaPrivateKey = getRSAPrivateKey(privateKeyString);
        Signature signature = null;
        try {
            signature = Signature.getInstance("MD5withRSA");
            signature.initSign(rsaPrivateKey);
            signature.update(createSign(params).getBytes());
            string = new String(encoder.encode(signature.sign()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 验签
     *
     * @param params          原始数据
     * @param publicKeyString 公钥
     * @param sign            签名
     * @return 是否验签通过
     */
    public static boolean verify(Map<String, Object> params, String publicKeyString, String sign) {
        RSAPublicKey rsaPublicKey = getRSAPublicKey(publicKeyString);
        Signature signature = null;
        Boolean b = null;
        try {
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(rsaPublicKey);
            signature.update(createSign(params).getBytes());
            b = signature.verify(decoder.decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
            BizAssert.fail("验签失败");
        }
        return b;
    }

    //类似微信接口的签名生成方法
    public static String createSign(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, Object> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString().trim();
            if (!ObjectUtil.isEmpty(value))
                sb.append("&").append(key).append("=").append(value);
        }
        String stringA = sb.toString().replaceFirst("&", "");
        return stringA;
    }

}
