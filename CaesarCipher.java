public class CaesarCipher {
	
	public static String encode(String plainText, char charKey) {
        
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

    public static String decode(String cipherText, char charKey) {
        
        StringBuilder plainText = new StringBuilder();
        int intKey;
        if(Character.isUpperCase(charKey)) {
            intKey = charKey - 65;
        }
        else {
            intKey = charKey - 97;
        }
        
        for(int i = 0; i < cipherText.length(); i++) {
            char letter = cipherText.charAt(i);
            int intLetter = (int) letter;
            char plainLetter;

            if(Character.isUpperCase(letter)) {
                plainLetter = (char)(65+((intLetter-65)-intKey));
            }
            else {
                plainLetter = (char)(97+((intLetter-97)-intKey));
            }
            plainText.append(plainLetter);
        }
        return plainText.toString();
    }
}