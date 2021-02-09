package hr.fer.oprpp1.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A crypto algorithm class which includes checking hash digests, enrcypting files and decrypting files.
 * The following command line arguments are: <br><br>
 * 
 * <code>checksha &ltinput_file&gt</code><br>
 * <code>decrypt &ltsource_file&gt &lttarget_file&gt</code><br>
 * <code>decrypt &ltsource_file&gt &lttarget_file&gt</code><br><br>
 * 
 * After running the program, the user has to input the password and the initialization vector.
 * 
 * @author fabjanvucina
 */
public class Crypto {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		if(args.length == 0) {
			System.out.println("Invalid number of arguments. You have to specify the action you want to perform(checksha, decrypt, encrypt) and its arguments.");
		}
		
		else if(args[0].equals("checksha")) {
			if(args.length != 2) {
				System.out.println("Invalid number of arguments for checksha action. The valid program call is: checksha <binary_input_file>");
			}
			
			else {
				checksha(args[1]);
			}
		}
		
		else if(args[0].equals("encrypt") || args[0].equals("decrypt")) {
			if(args.length != 3) {
				System.out.println("Invalid number of arguments for encrypt/decrypt action. The valid program call is: encrypt/decrypt <input_file> <target_file>");
			}
			
			else {
				crypt(args[1], args[2], args[0]);
			}
		}
		
		else {
			System.out.println("Invalid program action. Valid actions are: checksha, encrypt, decrypt.");
		}
		
	}
	
	/**
	 * A method which generates a digest for the input file and compares it to the user-provided expected digest.
	 * @param inputFile
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	public static void checksha(String inputFile) throws NoSuchAlgorithmException, IOException {
		System.out.println("Please provide expected sha-256 digest for " + inputFile + ":");
		System.out.print("> ");
		
		//read expected digest
		String expectedDigest;
		try(Scanner sc = new Scanner(System.in)) {
			expectedDigest = sc.next();
		}
		
		//initialize digest
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		//create buffer array
		byte[] buf = new byte[4096];
		
		//initialize input stream
		Path p = Paths.get(inputFile);
		InputStream is = Files.newInputStream(p);
		
		//calculate digest
		while(true) {
			
			//number of bytes read from the input stream and stored into the buffer array
			int readBytes = is.read(buf);
			
			//end of input file
			if(readBytes < 1) {
				break;
			}
			
			//update calculated digest
			md.update(buf, 0, readBytes);
		}
		
		//calculated digest
		byte[] calculatedDigest = md.digest();
		
		//compare calculate digest to expected digest
		if(Util.byteToHex(calculatedDigest).equals(expectedDigest)) {
			System.out.println("Digesting completed. Digest of " + inputFile + " matches expected digest.");
		}
		
		else {
			System.out.println("Digesting completed. Digest of " + inputFile + " does not match expected digest. Digest was: " + Util.byteToHex(calculatedDigest));
		}
		
		//close stream
		is.close();
	}
	
	
	/**
	 * A method which encrypts/decrypts the input file and stores the encrypted/decrypted version under the specified target file name.
	 * @param inputFile the encryptee
	 * @param targetFile the encription
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static void crypt(String inputFile, String targetFile, String operation) throws InvalidKeyException, 
																						   NoSuchAlgorithmException, 
																						   NoSuchPaddingException, 
																						   InvalidAlgorithmParameterException, 
																						   IOException, 
																						   IllegalBlockSizeException, 
																						   BadPaddingException {
		
		//initialize cipher object
		Cipher cipher = getCipherFromUserInput(operation);
		
		//create buffer array
		byte[] buf = new byte[4096];
				
		//initialize input stream
		Path inputPath = Paths.get(inputFile);
		InputStream is = Files.newInputStream(inputPath);
		
		//initialize output stream
		Path targetPath = Paths.get(targetFile);
		OutputStream os = Files.newOutputStream(targetPath);
				
				
		//perform encryption/decryption iteratively
		while(true) {
			
			//number of bytes read from the input stream and stored into the buffer array
			int readBytes = is.read(buf);
					
			//end of input file
			if(readBytes < 1) {
				break;
			}
					
			//update cipher of input and write it
			byte[] update = cipher.update(buf, 0, readBytes);
			os.write(update);
		}
		
		//finalize cipher of input, add padding
		byte[] padding = cipher.doFinal();
		os.write(padding);
		
		//close streams
		is.close();
		os.close();
		
		System.out.println("Encryption completed. Generated file " + targetFile + " based on file " + inputFile + ".");
	}
	
	
	/**
	 * @param function "encrypt" or "decrypt"
	 * @return new <code>Cipher</code> object
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	private static Cipher getCipherFromUserInput(String operation) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		
		//initialize scanner
		Scanner sc = new Scanner(System.in);
		
		//read password
		String key = sc.next();
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		
		//read initialization vector
		String initVector = sc.next();
		sc.close();
		
		//initialize Cipher object
		SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(key), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(initVector));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(operation.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		
		return cipher;
	}
}
