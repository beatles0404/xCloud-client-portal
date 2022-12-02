package com.lenovo.sap.api.util;




import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;
import java.util.Random;


public class AesUtil {

    public static String decrypt(String content, String key, String vi) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {

        Key k = toKey(key.getBytes());
        byte[] encoded = k.getEncoded();
        SecretKeySpec aes = new SecretKeySpec(encoded, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(vi.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, aes, iv);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = cipher.doFinal(decoder.decode(content));

        return new String(bytes, "UTF-8");
    }

    public static String encrypt(String data, String key, String vi) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {


        Key k = toKey(key.getBytes());
        byte[] encoded = k.getEncoded();
        SecretKeySpec aes = new SecretKeySpec(encoded, "AES");
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(vi.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, aes, iv);
        byte[] bytes = cipher.doFinal(data.getBytes("UTF-8"));
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    private static Key toKey(byte[] key){

        SecretKeySpec aes = new SecretKeySpec(key, "AES");
        return aes;
    }

    public static String generateString(Integer length){

        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random = new Random();

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; ++i){

            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }

        return new String(sb);
    }


    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {

        //16 bytes
//        String key = "N1DEJAVZNf3OdM34";
        String key = generateString(16);
        String vi = "xDHgt7hbKpsIR4b4";

        System.out.println("original: root");

        String e = encrypt("root", key, vi);
        System.out.println("encryption: " + e);
        String f = decrypt(e, key, vi);
        System.out.println("decryption: " + f);

    }


}

