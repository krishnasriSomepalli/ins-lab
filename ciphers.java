class Encrypt {
    
    private static String caeserCipherText(String plainText, char charKey) {
        
        StringBuilder cipherText = new StringBuilder();
        int intKey;
        if(Character.isUpperCase(charKey)) {
            intKey = charKey - 65;
        }
        else {
            intKey = charKey - 97;
        }
        
        for(int i = 0; i < plainText.length(); i++) {
            char letter = plainText.charAt(i);
            int intLetter = (int) letter;
            char cipherLetter;

            if(Character.isUpperCase(letter)) {
                cipherLetter = (char)(65+((intLetter-65+intKey)%26));
            }
            else {
                cipherLetter = (char)(97+((intLetter-97+intKey)%26));
            }
            cipherText.append(cipherLetter);
        }
        return cipherText.toString();
    }

   private static String substitutionCipherText(String plainText, String substitutionText) {

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
}