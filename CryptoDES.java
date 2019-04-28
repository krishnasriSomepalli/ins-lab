import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

class CryptoDES {
	public static void main(String args[]) throws Exception {

		// check args
		if(args.length != 1) {
			System.out.println("Command: java [PlainText]");
			return;
		}

		// create a DES key
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		SecretKey key = keyGen.generateKey(); 

		// create a Cipher instance by specifying the algorithm name, mode (optional), and padding scheme (optional).
		Cipher des = Cipher.getInstance("DES/ECB/PKCS5Padding");

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
}