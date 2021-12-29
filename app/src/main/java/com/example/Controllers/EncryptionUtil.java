package com.example.Controllers;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {
    SecretKey secretKey;

    public EncryptionUtil() {
        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            System.out.println("EncryptionUtil>>" + e.getLocalizedMessage());
        }
    }

    public static String encrypt(String data) {
        byte[] result = new byte[0];
        try {
            byte[] rawKey = getRawKey();
            result = encrypt(rawKey, data.getBytes());
        } catch (Exception e) {
            System.out.println("encrypt" + e.getLocalizedMessage());
        }
        return toHex(result);
    }

    public static String decrypt(String data) {
        byte[] result=new byte[0];
        try {
            byte[] encr=toByte(data);
            result=decr(encr);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return new String(result);
    }

    private static byte[] decr(byte[] encr) throws Exception{
        SecretKey secretKey=new SecretKeySpec(keyValue,"AES");
        Cipher cipher=Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] decrypted =cipher.doFinal(encr);
        return decrypted;
    }

    private static byte[] toByte(String data) {
        int len= data.length();
        byte[] result=new byte[len];
        for (int i=0;i<len;i++)
            result[i] = Integer.valueOf(data.substring(2 * i,2 * i+2),16).byteValue();
    return result;
    }

    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[]{'T', 'h', 'y', 'r', 'o', 'c', 'a', 'r', 'e', '$', '1', '2', '3', '4', '5', '6'};
    private final static String HEX = "0123456789ABCDEF";


    private static String toHex(byte[] result) {
        if (result == null)
            return "";
        StringBuffer Bufferresult = new StringBuffer(2 * result.length);
        for (int i = 0; i < result.length; i++) {
            appendHex(Bufferresult, result[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer bufferresult, byte b) {
        bufferresult.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    private static byte[] encrypt(byte[] rawKey, byte[] bytes) throws Exception {
        SecretKey key = new SecretKeySpec(rawKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(bytes);
        return encrypted;
    }

    private static byte[] getRawKey() {
        SecretKey key = new SecretKeySpec(keyValue, "AES");
        byte[] raw = key.getEncoded();
        return raw;
    }



}
