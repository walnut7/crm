package com.kpleasing.crm.ejb.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

import java.security.Key;

public class EncryptUtil {
	private static final String KEY = "12345678";   // 8位
	private static final String IV = "abcdefgh";    // 8位

	/**
	 * 加密
	 * @param key
	 * @param str
	 * @param ivString
	 * @return
	 * @throws Exception
	 */
	public static String encrypt( String key, String ivString, String str) throws Exception {
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		Key secretKey = keyFactory.generateSecret(desKeySpec);

		IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] bytes = cipher.doFinal(str.getBytes());

		Base64 base64 = new Base64();
		return new String(base64.encode(bytes));
	}
	
	/**
	 * 加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String str) throws Exception {
		return encrypt(KEY, IV, str);
	}

	
	/**
	 * 解密
	 * @param key
	 * @param str
	 * @param ivString
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String key, String ivString, String str) throws Exception {
		Base64 base64 = new Base64();
		byte[] data = base64.decode(str.getBytes());
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] decryptedBytes = cipher.doFinal(data);
		return new String(decryptedBytes);
	}
	
	
	/**
	 * 解密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String str) throws Exception {
		return decrypt(KEY, IV, str);
	}
}
