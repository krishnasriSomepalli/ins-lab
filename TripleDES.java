class TripleDES {

	public static String encrypt(String plainText, String key1, String key2) {
		return DES.encrypt(DES.decrypt(DES.encrypt(plainText, key1), key2), key1);
	}
	public static String decrypt(String cipherText, String key1, String key2) {
		return DES.decrypt(DES.encrypt(DES.decrypt(cipherText, key1), key2), key1);
	}
}