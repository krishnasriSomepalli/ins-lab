class Test {
   public static void main(String[] args) {
        System.out.println(CaesarCipher.encode("AbCdE", 'b'));
        System.out.println(CaesarCipher.decode(CaesarCipher.encode("AbCdE", 'b'), 'b'));

        System.out.println(SubstitutionCipher.encode("AbCdE", "KdFGnsLvbWahexjmQCPzrtYiUo"));
        System.out.println(SubstitutionCipher.decode(SubstitutionCipher.encode("AbCdE", "KdFGnsLvbWahexjmQCPzrtYiUo"), "KdFGnsLvbWahexjmQCPzrtYiUo"));

        System.out.println(HillCipher.encode("MISSISSIPPI", 2, "DZYR"));
        System.out.println(HillCipher.decode(HillCipher.encode("MISSISSIPPI", 2, "DZYR"), 2, "DZYR"));

   		System.out.println(DES.encrypt("MySecrets", "abcdefgh"));
   		System.out.println(DES.decrypt(DES.encrypt("MySecrets", "abcdefgh"), "abcdefgh"));
   }
}