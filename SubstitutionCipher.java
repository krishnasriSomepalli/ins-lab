public class SubstitutionCipher {
	
	public static String encode(String plainText, String substitutionText) {

        StringBuilder cipherText = new StringBuilder();
        substitutionText = substitutionText.toLowerCase();

        for(int i = 0; i < plainText.length(); i++) {
            char letter = plainText.charAt(i);
            int intLetter = (int) letter;
            char cipherLetter;

            if(Character.isUpperCase(letter)) {
                cipherLetter = (char)(65 + ((int)substitutionText.charAt(intLetter-65)) - 97);
            }
            else {
                cipherLetter = (char)(97 + ((int)substitutionText.charAt(intLetter-97)) - 97);
            }
            cipherText.append(cipherLetter);
        }
        return cipherText.toString();
   }

   public static String decode(String cipherText, String substitutionText) {

        StringBuilder plainText = new StringBuilder();
        substitutionText = substitutionText.toLowerCase();

        for(int i = 0; i < cipherText.length(); i++) {
            char letter = cipherText.charAt(i);
            int intLetter = (int) letter;
            char plainLetter;

            if(Character.isUpperCase(letter)) {
                plainLetter = (char)(65 + (substitutionText.indexOf(intLetter-65+97)));
            }
            else {
                plainLetter = (char)(97 + (substitutionText.indexOf(intLetter)));
            }
            plainText.append(plainLetter);
        }
        return plainText.toString();
   }
}