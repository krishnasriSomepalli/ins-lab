import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

class CryptoAES {
	public static void main(String args[]) {

		// check args
		if(args.length != 1) {
			System.out.println("Command: java CryptoAES [PlainText]");
			return;
		}

		try {
			// create a DES key
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			SecretKey key = keyGen.generateKey(); 

			// create a Cipher instance by specifying the algorithm name, mode (optional), and padding scheme (optional).
			Cipher des = Cipher.getInstance("AES/ECB/PKCS5Padding");

			// convert plainText to byte[] format
			String plainText = args[0];
			byte[] plainBytes = plainText.getBytes();

			// encrypt plainBytes
			des.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedBytes = des.doFinal(plainBytes);
			String encryptedText = new String(encryptedBytes);
			System.out.println("You entered: " + plainText);
			System.out.println("Encrypted data (DES): " + encryptedText);
		
			// decrypt encryptedBytes
			des.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptedBytes = des.doFinal(encryptedBytes);
			String decryptedText = new String(decryptedBytes);
			System.out.println("Decrypted data (DES): " + decryptedText);
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch(InvalidKeyException e) {
			e.printStackTrace();
		}
		catch(IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		catch(BadPaddingException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}