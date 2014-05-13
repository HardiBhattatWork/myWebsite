/**
 * Encryption and description function to encrypt the decrypt 
 * the data being transfered between the client and server
 * @author: Guojun Zhang
 */
package ftp.io;

import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;

public class Cryptograph {

	static final String REQUESTEDENCRYPTIONALGORITHM = "AES";
	static final int KEYLENGTH = 128;
	static final File KEYFILE = new File("c:\\key.txt");

	/**
	 * Generating a ket for encryption
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateKey() throws NoSuchAlgorithmException {
		// *** Get the KeyGenerator
		KeyGenerator kgen = KeyGenerator.getInstance(REQUESTEDENCRYPTIONALGORITHM);
		kgen.init(KEYLENGTH);

		// *** Generate the secret key specs.
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	/**
	 * Encrypting the given data in bytes
	 * @param plain
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public static byte[] encrypt(byte[] plain) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		byte[] key = getBytesFromFile(KEYFILE);
		SecretKeySpec skeySpec = new SecretKeySpec(key,
				REQUESTEDENCRYPTIONALGORITHM);

		// *** Instantiate the cipher
		Cipher cipher = Cipher.getInstance(REQUESTEDENCRYPTIONALGORITHM);

		// *** encrypt
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(plain);
	}

	/**
	 * Decrypting the data that is encrypted in bytes
	 * @param encrypted
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public static byte[] decrypt(byte[] encrypted)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		
		byte[] key = getBytesFromFile(KEYFILE);
		SecretKeySpec skeySpec = new SecretKeySpec(key,
				REQUESTEDENCRYPTIONALGORITHM);

		// *** Instantiate the cipher
		Cipher cipher = Cipher.getInstance(REQUESTEDENCRYPTIONALGORITHM);
		// *** now decrypt
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(encrypted);
	}

	/**
	 * Converting the given data to byte for encryption
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	
}
