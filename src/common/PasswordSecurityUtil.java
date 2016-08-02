package common;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Encryption and decryption utility.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class PasswordSecurityUtil {
	Cipher ecipher;
	Cipher dcipher;
	String key;

	// 8-byte Salt
	byte[] salt = {
		(byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
		(byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
	};
    
	public String encryptString(String stringVal, String key)
	{
		try {
			DesEncrypter(key);
			String encValue = encrypt(stringVal);
			System.out.println(" Encrypted String :"+encValue);
			return encValue;
		}
		catch(Exception ex)
		{
			System.out.println(" Exception :"+ex);
			//System.exit(0);
		}	
		return null;
	} 
	public String decryptString(String stringVal, String key)
	{
		try {
			DesEncrypter(key);
			String decValue = decrypt(stringVal);
			//System.out.println(" Decrypted String :"+decValue);
			return decValue;
		}
		catch(Exception ex)
		{
			System.out.println(" Exception :"+ex);
			//System.exit(0);
		}
		return null;
	} 
	public void DesEncrypter(String passPhrase) {
		try {
			// Create the key
			int iterationCount=19;
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());
    
			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
    
			// Create the ciphers
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} 
		catch (java.security.InvalidAlgorithmParameterException e) {} 
		catch (java.security.spec.InvalidKeySpecException e) {} 
		catch (javax.crypto.NoSuchPaddingException e) {} 
		catch (java.security.NoSuchAlgorithmException e) {} 
		catch (java.security.InvalidKeyException e) {}
	}
	
	public String decrypt(String str) {
		try {
			// Decode base64 to get bytes
			//byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			new Base64();
			byte[] dec = Base64.decodeBase64(str);
    
			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);
    
			// Decode using utf-8
			return new String(utf8, "UTF8");
		} 
		catch (javax.crypto.BadPaddingException e) {} 
		catch (IllegalBlockSizeException e) {} 
		catch (UnsupportedEncodingException e) {} 
		return null;
	}
	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
    
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
    
			// Encode bytes to base64 to get a string
			//return new sun.misc.BASE64Encoder().encode(enc);
			new Base64();
			return Base64.encodeBase64String(enc);
		} 
		catch (javax.crypto.BadPaddingException e) {} 
		catch (IllegalBlockSizeException e) {} 
		catch (UnsupportedEncodingException e) {} 
//		catch (java.io.IOException e) {}
		return null;
	}
}
