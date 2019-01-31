class Test {
   public static void main(String[] args) {
        System.out.println(CaesarCipher.decrypt(CaesarCipher.encrypt("AbCdE", 'b'), 'b'));

        System.out.println(SubstitutionCipher.decrypt(SubstitutionCipher.encrypt("AbCdE", "KdFGnsLvbWahexjmQCPzrtYiUo"), "KdFGnsLvbWahexjmQCPzrtYiUo"));

        System.out.println(HillCipher.decrypt(HillCipher.encrypt("MISSISSIPPI", 2, "DZYR"), 2, "DZYR"));

   		System.out.println(Conversions.hexToAscii(DES.decrypt(DES.encrypt("4c6f72656d497073756d", "abcdefgh"), "abcdefgh")));

   		System.out.println(Conversions.hexToAscii(TripleDES.decrypt(TripleDES.encrypt("4c6f72656d497073756d", "abcdefgh", "ijklmnop"), "abcdefgh", "ijklmnop")));
   		
   		int p=61, q=53, e=17;
   		int[] keys = RSA.generateKeys(p, q, e);
   		int d = keys[0], n = keys[1];
   		System.out.println(RSA.decrypt(RSA.encrypt("LoremIpsum", e, n), d, n));
   }
}