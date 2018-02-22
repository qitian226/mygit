package com.cos.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author ngh
 * AES128 算法
 *
 * CBC 模式
 *
 * PKCS7Padding 填充模式
 *
 * CBC模式需要添加一个参数iv
 *
 * 介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
 * 要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */
public class AESUtil {
 // 算法名称
 final String KEY_ALGORITHM = "AES";
 // 加解密算法/模式/填充方式
 final String algorithmStr = "AES/CBC/PKCS7Padding";
 //
 private Key key;
 private Cipher cipher;
 boolean isInited = false;

 byte[] iv ="1234567890000000".getBytes(); //{ 0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38 };
 private void init(byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {

  // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
  int base = 16;
  if (keyBytes.length % base != 0) {
   int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
   byte[] temp = new byte[groups * base];
   byte f=0x30;
   Arrays.fill(temp, f);
   System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
   keyBytes = temp;
  }
  // 初始化
  Security.addProvider(new BouncyCastleProvider());
  // 转化成JAVA的密钥格式
  key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
  try {
   // 初始化cipher
   cipher = Cipher.getInstance(algorithmStr, "BC");
  } catch (NoSuchAlgorithmException e) {
   throw e;
  } catch (NoSuchPaddingException e) {
  throw e;
  } catch (NoSuchProviderException e) {
    throw e;
  }
 }
 private void init(String pkey) throws Exception{
	 // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
	  int base = 16;
	  char[] bs=  pkey.toCharArray();
	  int l=bs.length;
	  char[] temp=new char[base];
	  if(l<base){
		  for(int i=0;i<base;i++){
			  if(i<l){
				  temp[i]=bs[i];
			  }else{
				  temp[i]='q';
			  }
		  }
	  }
	 // System.out.println(new String(temp));
	  byte[] keyBytes= new String(temp).getBytes();
	  // 初始化
	  Security.addProvider(new BouncyCastleProvider());
	  // 转化成JAVA的密钥格式
	  key = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
	  try {
	   // 初始化cipher
	   cipher = Cipher.getInstance(algorithmStr, "BC");
	  } catch (NoSuchAlgorithmException e) {
	   throw e;
	  } catch (NoSuchPaddingException e) {
	  throw e;
	  } catch (NoSuchProviderException e) {
	    throw e;
	  }
 }
 /**
  * 加密方法
  *
  * @param content
  *            要加密的字符串
  * @param keyBytes
  *            加密密钥
  * @return
 * @throws Exception 
  */
 private byte[] encrypt(byte[] content, String keyBytes) throws Exception {
  byte[] encryptedText = null;
  try {
	init(keyBytes);
   cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
   encryptedText = cipher.doFinal(content);
  } catch (Exception e) {
  throw e;
  }
  return encryptedText;
 }
 /**
  * 解密方法
  *
  * @param encryptedData
  *            要解密的字符串
  * @param keyBytes
  *            解密密钥
  * @return
 * @throws Exception 
  */
 public byte[] decrypt(byte[] encryptedData, String keyBytes) throws Exception {
  byte[] encryptedText = null;
  try {
   init(keyBytes);
   cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
   encryptedText = cipher.doFinal(encryptedData);
  } catch (Exception e) {
   throw e;
  }
  return encryptedText;
 }
 
 public String encryptString(String word,String key) throws Exception{
	 byte[] enc = encrypt(word.getBytes(), key);
	  return new String(Hex.encode(enc));
 }
 public String decryptString(String word,String key) throws Exception{
	  byte[] decs=  Hex.decode(word);
	  byte[] dec = decrypt(decs, key);
	  return  new String(dec);
 }
 public static void main(String[] args) throws UnsupportedEncodingException {
	  AESUtil aes = new AESUtil();
	//   加解密 密钥
	  byte[] keybytes = "2345".getBytes("UTF-8");
	  System.out.println(keybytes);
	  String content = "qitian@#126";
	  // 加密字符串
	  System.out.println("加密前的：" + content);
	  System.out.println("加密密钥：" + new String(keybytes));
	  try {
		String ee=aes.encryptString(content, "2iuw");
		System.out.println(ee);
		String dd=aes.decryptString(ee, "2iuw");
		System.out.println(dd);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  // 加密方法 57a7e77e11c2323852a47c6a044fb138 6e05416de77affcebde090585dfcc5c6
	 // byte[] enc = aes.encrypt(content.getBytes(), keybytes);
	 // System.out.println("加密后的内容：" + new String(Hex.encode(enc)));
	  // 解密方法
	 // byte[] dec = aes.decrypt(enc, keybytes);
	  //System.out.println("解密后的内容：" + new String(dec));
	  //7bb9beb75f81b8a1a3deb36a537f95fa 7bb9beb75f81b8a1a3deb36a537f95fa
	  //ff529744d55df65cb8ff297edfb837a1 ff529744d55df65cb8ff297edfb837a1 fdb95ea193fd890f9a48fe80ec1d16ef
	/*  byte[] tt="1234567890000000".getBytes();
	  System.out.println(tt);
	  byte[] keyBytes="123456789".getBytes();
	  int base=16;
	  if (keyBytes.length % base != 0) {
		   int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
		   byte[] temp = new byte[groups * base];
		   Arrays.fill(temp, (byte) 0);
		   System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
		   keyBytes = temp;
		  }
	  for(byte b:keyBytes){
		  System.out.println(b);
	  }*/
	 }

}