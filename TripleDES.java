class TripleDES {

	public static String encrypt(String plainText, String key1, String key2) {
		// String a = DES.encrypt(plainText, key1);
		// String b = DES.decrypt(a, key2);
		// String c = DES.encrypt(b, key1);
		// System.out.println("a: "+a+"\tb: "+b+"\tc: "+c+"\n");
		// return c;
		return DES.encrypt(DES.decrypt(DES.encrypt(plainText, key1), key2), key1);
	}
	public static String decrypt(String cipherText, String key1, String key2) {
		// String a = DES.decrypt(cipherText, key1);
		// String b = DES.encrypt(a, key2);
		// String c = DES.decrypt(b, key1);
		// System.out.println("a: "+a+"\tb: "+b+"\tc: "+c+"\n");
		// return c;
		return DES.decrypt(DES.encrypt(DES.decrypt(cipherText, key1), key2), key1);
	}
}