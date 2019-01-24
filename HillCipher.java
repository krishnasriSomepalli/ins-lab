public class HillCipher {

	// if your block is of size n, then your key should be of size n^2
   public static String encrypt(String plainText, int blockSize, String key) {
        if(key.length() != blockSize*blockSize) {
            return null;
        }
        else {
            StringBuilder cipherText = new StringBuilder();

            // building the key Matrix
            Integer[][] keyData = new Integer[blockSize][blockSize];
            key = key.toLowerCase();
            for(int i=0; i<blockSize; i++) {
                for(int j=0; j<blockSize; j++) {
                    keyData[i][j] = key.charAt((blockSize*i)+j)-97;
                }
            }
            Matrix keyMatrix = new Matrix(blockSize, blockSize, keyData);

            // padding
            if(plainText.length()%blockSize != 0) {
                for(int i=0; i<plainText.length()%blockSize; i++)
                    plainText = plainText+"Z";
            }
            // enciphering
            int blocksNo = plainText.length()/blockSize;
            Integer[][][] plainTextData = new Integer[blocksNo][blockSize][1];
            for(int i=0; i<blocksNo; i++) {
                // encoding letters (in the plain text) to numbers
                for(int j=0; j<blockSize; j++) {
                    int intLetter = plainText.charAt((blockSize*i)+j);
                    if(Character.isUpperCase(intLetter))
                        intLetter = intLetter-65;
                    else
                        intLetter = intLetter-97;
                    plainTextData[i][j][0] = intLetter;
                }

                Integer[][] cipherTextData = new Integer[blockSize][1];
                // enciphering
                cipherTextData = (Matrix.multipication(keyMatrix, new Matrix(blockSize, 1, plainTextData[i]))).data;
                // decoding the numbers (in the cipher text) to letters
                for(int j=0; j<blockSize; j++) {
                    int intLetter = Math.round(cipherTextData[j][0]);
                    char cipherLetter;
                    if(Character.isUpperCase(plainText.charAt((blockSize*i)+j)))
                        cipherLetter = (char)((intLetter%26)+65);
                    else
                        cipherLetter = (char)((intLetter%26)+97);
                    cipherText.append(cipherLetter);
                }
            }
            return cipherText.toString();
        }
   }

   public static String decrypt(String cipherText, int blockSize, String key) {
        if(key.length() != blockSize*blockSize) {
            return null;
        }
        else {
            StringBuilder plainText = new StringBuilder();

            // building the key Matrix (inverse)
            Integer[][] keyData = new Integer[blockSize][blockSize];
            key = key.toLowerCase();
            for(int i=0; i<blockSize; i++) {
                for(int j=0; j<blockSize; j++) {
                    keyData[i][j] = key.charAt((blockSize*i)+j)-97;
                }
            }
            Matrix keyMatrix = new Matrix(blockSize, blockSize, keyData);
            int det = Matrix.determinant(keyMatrix.data, keyMatrix.rowsNo);
            int detModInv = Arithmetic.modInverse(det, 26);
            keyMatrix = keyMatrix.cofactorsMatrix().transpose();
            for(int i=0; i<blockSize; i++) 
                for(int j=0; j<blockSize; j++) 
                    keyMatrix.data[i][j] = Arithmetic.mod(keyMatrix.data[i][j]*detModInv,26);

            // deciphering
            int blocksNo = cipherText.length()/blockSize;
            Integer[][][] cipherTextData = new Integer[blocksNo][blockSize][1];
            for(int i=0; i<blocksNo; i++) {
                // encoding letters (in the cipher text) to numbers
                for(int j=0; j<blockSize; j++) {
                    int intLetter = cipherText.charAt((blockSize*i)+j);
                    if(Character.isUpperCase(intLetter))
                        intLetter = intLetter-65;
                    else
                        intLetter = intLetter-97;
                    cipherTextData[i][j][0] = intLetter;
                }

                Integer[][] plainTextData = new Integer[blockSize][1];
                // deciphering
                plainTextData = (Matrix.multipication(keyMatrix, new Matrix(blockSize, 1, cipherTextData[i]))).data;
                // decoding the numbers (in the plain text) to letters
                for(int j=0; j<blockSize; j++) {
                    int intLetter = plainTextData[j][0];
                    char plainLetter;
                    if(Character.isUpperCase(cipherText.charAt((blockSize*i)+j)))
                        plainLetter = (char)((intLetter%26)+65);
                    else
                        plainLetter = (char)((intLetter%26)+97);
                    plainText.append(plainLetter);
                }
            }
            return plainText.toString();
        }
   }
}