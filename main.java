class Test {
   public static void main(String[] args) {
        // System.out.println(CaesarCipher.encode("AbCdE", 'a'));
        // System.out.println(CaesarCipher.decode("AbCdE", 'a'));

        // System.out.println(SubstitutionCipher.encode("AbCdE", "KdFGnsLvbWahexjmQCPzrtYiUo"));
        // System.out.println(SubstitutionCipher.decode("KdFgN", "KdFGnsLvbWahexjmQCPzrtYiUo"));

        // System.out.println(HillCipher.encode("MISSISSIPPI", 2, "DZYR"));
        // System.out.println(HillCipher.decode("CIKKGEUWERZT", 2, "DZYR"));

   		String key = "abcdefgh";
   		String plainText = "MySecret";
   		String encoded = DES.encrypt(plainText, key);
   		System.out.println(encoded);
   		System.out.println(DES.decrypt(encoded, key));
   }
}