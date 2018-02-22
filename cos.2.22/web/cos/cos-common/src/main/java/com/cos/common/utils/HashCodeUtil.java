package com.cos.common.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * TODO What the class does
 * @author qikunlun
 * @date   2015年11月18日-下午9:51:55
 * 加密UTIL
 */
public class HashCodeUtil {
	    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
	    // The following constants may be changed without breaking existing hashes.
	    public static final int SALT_BYTE_SIZE = 24;
	    public static final int HASH_BYTE_SIZE = 24;
	    public static final int PBKDF2_ITERATIONS = 1000;
	 
	    public static final int ITERATION_INDEX = 0;
	    public static final int SALT_INDEX = 1;
	    public static final int PBKDF2_INDEX = 2;
	 
	    /**
	     * Returns a salted PBKDF2 hash of the password.
	     *
	     * @param   password    the password to hash
	     * @return              a salted PBKDF2 hash of the password
	     */
	    public static String createHash(String password)
	        throws NoSuchAlgorithmException, InvalidKeySpecException
	    {
	        return createHash(password.toCharArray());
	    }
	 
	    /**
	     * Returns a salted PBKDF2 hash of the password.
	     *
	     * @param   password    the password to hash
	     * @return              a salted PBKDF2 hash of the password
	     */
	    public static String createHash(char[] password)
	        throws NoSuchAlgorithmException, InvalidKeySpecException
	    {
	        // Generate a random salt
	        SecureRandom random = new SecureRandom();
	        byte[] salt = new byte[SALT_BYTE_SIZE];
	        random.nextBytes(salt);
	 
	        // Hash the password
	        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
	        // format iterations:salt:hash
	        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" +  toHex(hash);
	    }
	 
	    /**
	     * Validates a password using a hash.
	     *
	     * @param   password        the password to check
	     * @param   correctHash     the hash of the valid password
	     * @return                  true if the password is correct, false if not
	     */
	    public static boolean validatePassword(String password, String correctHash)
	        throws NoSuchAlgorithmException, InvalidKeySpecException
	    {
	        return validatePassword(password.toCharArray(), correctHash);
	    }
	 
	    /**
	     * Validates a password using a hash.
	     *
	     * @param   password        the password to check
	     * @param   correctHash     the hash of the valid password
	     * @return                  true if the password is correct, false if not
	     */
	    public static boolean validatePassword(char[] password, String correctHash)
	        throws NoSuchAlgorithmException, InvalidKeySpecException
	    {
	        // Decode the hash into its parameters
	        String[] params = correctHash.split(":");
	        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
	        byte[] salt = fromHex(params[SALT_INDEX]);
	        byte[] hash = fromHex(params[PBKDF2_INDEX]);
	        // Compute the hash of the provided password, using the same salt, 
	        // iteration count, and hash length
	        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
	        // Compare the hashes in constant time. The password is correct if
	        // both hashes match.
	        return slowEquals(hash, testHash);
	    }
	 
	    /**
	     * Compares two byte arrays in length-constant time. This comparison method
	     * is used so that password hashes cannot be extracted from an on-line 
	     * system using a timing attack and then attacked off-line.
	     * 
	     * @param   a       the first byte array
	     * @param   b       the second byte array 
	     * @return          true if both byte arrays are the same, false if not
	     */
	    private static boolean slowEquals(byte[] a, byte[] b)
	    {
	        int diff = a.length ^ b.length;
	        for(int i = 0; i < a.length && i < b.length; i++)
	            diff |= a[i] ^ b[i];
	        return diff == 0;
	    }
	 
	    /**
	     *  Computes the PBKDF2 hash of a password.
	     *
	     * @param   password    the password to hash.
	     * @param   salt        the salt
	     * @param   iterations  the iteration count (slowness factor)
	     * @param   bytes       the length of the hash to compute in bytes
	     * @return              the PBDKF2 hash of the password
	     */
	    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
	        throws NoSuchAlgorithmException, InvalidKeySpecException
	    {
	        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
	        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
	        return skf.generateSecret(spec).getEncoded();
	    }
	 
	    /**
	     * Converts a string of hexadecimal characters into a byte array.
	     *
	     * @param   hex         the hex string
	     * @return              the hex string decoded into a byte array
	     */
	    private static byte[] fromHex(String hex)
	    {
	        byte[] binary = new byte[hex.length() / 2];
	        for(int i = 0; i < binary.length; i++)
	        {
	            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
	        }
	        return binary;
	    }
	 
	    /**
	     * Converts a byte array into a hexadecimal string.
	     *
	     * @param   array       the byte array to convert
	     * @return              a length*2 character string encoding the byte array
	     */
	    private static String toHex(byte[] array)
	    {
	        BigInteger bi = new BigInteger(1, array);
	        String hex = bi.toString(16);
	        int paddingLength = (array.length * 2) - hex.length();
	        if(paddingLength > 0)
	            return String.format("%0" + paddingLength + "d", 0) + hex;
	        else
	            return hex;
	    }
	 /**
	  * MD5加密函数
	  * @param inStr
	  * @return
	  * @throws Exception
	  */
		public static String md5Encode(String inStr) throws Exception {
	        MessageDigest md5 = null;
	        try {
	            md5 = MessageDigest.getInstance("MD5");
	        } catch (Exception e) {
	           throw e;
	        }
	        byte[] byteArray = inStr.getBytes("UTF-8");
	        byte[] md5Bytes = md5.digest(byteArray);
	        StringBuffer hexValue = new StringBuffer();
	        for (int i = 0; i < md5Bytes.length; i++) {
	            int val = ((int) md5Bytes[i]) & 0xff;
	            if (val < 16) {
	                hexValue.append("0");
	            }
	            hexValue.append(Integer.toHexString(val));
	        }
	        return hexValue.toString();
	    }

	    
	    /**
	     * Tests the basic functionality of the PasswordHash class
	     *
	     * @param   args        ignored
	     */
	    public static void main(String[] args)
	    {
	        try
	        {
	        	String paa="7fef3ecad4d281b09f357c39adb31eda";
	        	String p1=HashCodeUtil.createHash(paa);
	        	String p2=HashCodeUtil.createHash(paa);
	        	System.out.println(p1);
	        	System.out.println(p2);
	        	System.out.println(HashCodeUtil.validatePassword(paa, p1));
	        	System.out.println(HashCodeUtil.validatePassword(paa, p2));
	        	
	        	
	            // Print out 10 hashes
//	            for(int i = 0; i < 10; i++)
//	                System.out.println(HashCodeUtil.createHash("p\r\nassw0Rd!"));
//	 
//	            // Test password validation
//	            boolean failure = false;
//	            System.out.println("Running tests...");
//	            for(int i = 0; i < 100; i++)
//	            {
//	                String password = ""+i;
//	                String hash = createHash(password);
//	                String secondHash = createHash(password);
//	                if(hash.equals(secondHash)) {
//	                    System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
//	                    failure = true;
//	                }
//	                String wrongPassword = ""+(i+1);
//	                if(validatePassword(wrongPassword, hash)) {
//	                    System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
//	                    failure = true;
//	                }
//	                if(!validatePassword(password, hash)) {
//	                    System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
//	                    failure = true;
//	                }
//	            }
//	            if(failure)
//	                System.out.println("TESTS FAILED!");
//	            else
//	                System.out.println("TESTS PASSED!");
	        }
	        catch(Exception ex)
	        {
	            System.out.println("ERROR: " + ex);
	        }
	    }
	 
	}