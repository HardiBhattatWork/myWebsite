/**
 * Description: This class is used to encrypt and decrypt
 * data transit between client and server
 * @author Guojun Zhang
 **/
package ftp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ftp.config.Settings;

public class DESCrypto implements Settings{
	
	private static final File KEYFILE = new File(KEY_FILE);
	private static SecretKeySpec skeySpec;
	private static Cipher cipher;
	
	static{
		byte[] key;
		try {
			
			FileOutputStream keyFile = new FileOutputStream(KEYFILE);
			keyFile.write(generateKey());
			keyFile.flush();
			keyFile.close();
			key = getBytesFromFile(KEYFILE);
			skeySpec = new SecretKeySpec(key,
					REQUESTEDENCRYPTIONALGORITHM);
			cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
					
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * generate key file
	 * @return
	 */
	public static byte[] generateKey() throws NoSuchAlgorithmException {
		// *** Get the KeyGenerator
		KeyGenerator kgen = KeyGenerator
				.getInstance(REQUESTEDENCRYPTIONALGORITHM);
		kgen.init(KEYLENGTH);

		// *** Generate the secret key specs.
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	/**
	 * encrypt input byte block into cipher
	 * @param plain
	 * @param offset
	 * @param inputLen
	 * @return
	 */
	public static byte[] encrypt(byte[] plain, int offset, int inputLen) {
		
		byte[] encrypted = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encrypted = cipher.doFinal(plain, offset, inputLen);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return encrypted;
		
	}

	/**
	 * decrypt cipher byte block into plain byte
	 * @param plain
	 * @return
	 */
	public static byte[] decrypt(byte[] encrypted) {
			
		byte[] decrypted = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			decrypted = cipher.doFinal(encrypted);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return decrypted; 
	}

	/**
	 * get key from key file stored on local file system
	 * @param file
	 * @return
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length = file.length();

		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		is.close();
		return bytes;
	}


}
