class Encrypt {
    
    public static String caesarEncode(String plainText, char charKey) {
        
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

    public static String caesarDecode(String cipherText, char charKey) {
        
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

   public static String substitutionEncode(String plainText, String substitutionText) {

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

   public static String substitutionDecode(String cipherText, String substitutionText) {

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


   // if your block is of size n, then your key should be of size n^2
   public static String hillEncode(String plainText, int blockSize, String key) {
        if(key.length() != blockSize*blockSize) {
            return null;
        }
        else {
            StringBuilder cipherText = new StringBuilder();

            Integer[][] keyData = new Integer[blockSize][blockSize];
            key = key.toLowerCase();
            for(int i=0; i<blockSize; i++) {
                for(int j=0; j<blockSize; j++) {
                    keyData[i][j] = key.charAt((blockSize*i)+j)-97;
                }
            }
            Matrix keyMatrix = new Matrix(blockSize, blockSize, keyData);

            if(plainText.length()%blockSize != 0) {
                for(int i=0; i<plainText.length()%blockSize; i++)
                    plainText = plainText+"Z";
            }
            int blocksNo = plainText.length()/blockSize;
            Integer[][][] plainTextData = new Integer[blocksNo][blockSize][1];
            for(int i=0; i<blocksNo; i++) {
                for(int j=0; j<blockSize; j++) {
                    int intLetter = plainText.charAt((blockSize*i)+j);
                    if(Character.isUpperCase(intLetter))
                        intLetter = intLetter-65;
                    else
                        intLetter = intLetter-97;
                    plainTextData[i][j][0] = intLetter;
                }

                Integer[][][] cipherTextData = new Integer[blocksNo][blockSize][1];
                cipherTextData[i] = (Matrix.multipication(keyMatrix, new Matrix(blockSize, 1, plainTextData[i]))).data;

                for(int j=0; j<blockSize; j++) {
                    int intLetter = Math.round(cipherTextData[i][j][0]);
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

   public static String hillDecode(String cipherText, int blockSize, String key) {
        if(key.length() != blockSize*blockSize) {
            return null;
        }
        else {
            StringBuilder plainText = new StringBuilder();

            Integer[][] keyData = new Integer[blockSize][blockSize];
            key = key.toLowerCase();
            for(int i=0; i<blockSize; i++) {
                for(int j=0; j<blockSize; j++) {
                    keyData[i][j] = key.charAt((blockSize*i)+j)-97;
                }
            }
            Matrix keyMatrix = new Matrix(blockSize, blockSize, keyData);
            int det = Matrix.determinant(keyMatrix.data, keyMatrix.rowsNo);
            int detModInv = modInverse(det, 26);
            keyMatrix = keyMatrix.cofactorsMatrix().transpose();
            for(int i=0; i<blockSize; i++) 
                for(int j=0; j<blockSize; j++) 
                    keyMatrix.data[i][j] = mod(keyMatrix.data[i][j]*detModInv,26);

            int blocksNo = cipherText.length()/blockSize;
            Integer[][][] cipherTextData = new Integer[blocksNo][blockSize][1];
            for(int i=0; i<blocksNo; i++) {
                for(int j=0; j<blockSize; j++) {
                    int intLetter = cipherText.charAt((blockSize*i)+j);
                    if(Character.isUpperCase(intLetter))
                        intLetter = intLetter-65;
                    else
                        intLetter = intLetter-97;
                    cipherTextData[i][j][0] = intLetter;
                }

                Integer[][][] plainTextData = new Integer[blocksNo][blockSize][1];
                plainTextData[i] = (Matrix.multipication(keyMatrix, new Matrix(blockSize, 1, cipherTextData[i]))).data;

                for(int j=0; j<blockSize; j++) {
                    int intLetter = plainTextData[i][j][0];
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

   // return A(mod C)
   private static int mod(int A, int C) {
        while(A<0)
            A = A+C;
        return A%C;
   }

   // Find A^-1 such that (A*A^-1)mod C = 1
   private static int modInverse(int A, int C) {
        for(int i=0; i<C-1; i++) 
            if(mod(A*i,C) == 1)
                return i;
        return -1;
   }

   public static void main(String[] args) {
        System.out.println(caesarEncode("AbCdE", 'a'));
        System.out.println(caesarDecode("AbCdE", 'a'));

        System.out.println(substitutionEncode("AbCdE", "KdFGnsLvbWahexjmQCPzrtYiUo"));
        System.out.println(substitutionDecode("KdFgN", "KdFGnsLvbWahexjmQCPzrtYiUo"));

        System.out.println(hillEncode("MISSISSIPPI", 2, "DZYR"));
        System.out.println(hillDecode("CIKKGEUWERZT", 2, "DZYR"));
   }
}

// assuming no ragged arrays
class Matrix {

    int rowsNo;
    int colsNo;
    Integer[][] data;

    Matrix() {
        rowsNo = -1;
        colsNo = -1;
        data = null;
    }

    Matrix(int rowsNo, int colsNo, Integer[][] data) {
        this.rowsNo = rowsNo;
        this.colsNo = colsNo;
        this.data = data;
    }

    public static Matrix multipication(Matrix A, Matrix B) {
        if(A.colsNo != B.rowsNo) {
            return new Matrix();
        }
        else {
            int resultRowsNo = A.rowsNo;
            int resultColsNo = B.colsNo;
            Integer[][] result = new Integer[resultRowsNo][resultColsNo];
            for(int i=0; i<resultRowsNo; i++) {
                for(int j=0; j<resultColsNo; j++) {
                    result[i][j] = 0;
                    for(int k=0; k<A.colsNo; k++) {
                        result[i][j] = result[i][j] + (A.data[i][k]*B.data[k][j]);
                    }
                }
            }
            return new Matrix(resultRowsNo, resultColsNo, result);
        }
    }

    public Matrix inverse() {
        Matrix result = cofactorsMatrix().transpose();
        int det = determinant(data, rowsNo);
        for(int i=0; i<result.rowsNo; i++)
            for(int j=0; j<result.colsNo; j++)
                result.data[i][j] = result.data[i][j]/det;
        return result;
    }

    public static int determinant(Integer[][] submatrix, int size) {
        // throw an exception when it isn't a square matrix or size is improper
        // if(rowsNo!=colsNo)
        //     ...

        if(size == 1) {
            return submatrix[0][0];
        }
        else if(size == 2) {
            return (submatrix[0][0]*submatrix[1][1])-(submatrix[0][1]*submatrix[1][0]);
        }

        int result = 0;
        for(int i=0; i<size; i++) {

                Integer[][] newsubmatrix = new Integer[size-1][size-1];
                int rowIndex = 0, colIndex = 0;
                for(int m=0; m<size; m++) {
                    colIndex = 0;
                    for(int n=0; n<size; n++) 
                        if(m!=0 && n!=i) {
                            newsubmatrix[rowIndex][colIndex++] = submatrix[m][n];
                            if(colIndex==size-1)
                                rowIndex++;
                        }
                }

                int subdet = determinant(newsubmatrix, size-1);
                if(i%2 == 0)
                    result = result+(submatrix[0][i]*subdet);
                else
                    result = result-(submatrix[0][i]*subdet);
        }
        return result;
    }

    public Matrix cofactorsMatrix() {
        // throw exception if not a square matrix
        Integer[][] result = new Integer[rowsNo][rowsNo];

        for(int i=0; i<rowsNo; i++) {
            for(int j=0; j<rowsNo; j++) {

                Integer[][] submatrix = new Integer[rowsNo-1][rowsNo-1];
                int rowIndex = 0, colIndex = 0;
                for(int m=0; m<rowsNo; m++) {
                    colIndex = 0;
                    for(int n=0; n<rowsNo; n++) 
                        if(m!=i && n!=j) {
                            submatrix[rowIndex][colIndex++] = data[m][n];
                            if(colIndex==rowsNo-1)
                                rowIndex++;
                        }
                }

                int subdet = determinant(submatrix, rowsNo-1);
                if((i%2==0 && j%2==0) || (i%2==1 && j%2==1))
                    result[i][j] = subdet;
                else
                    result[i][j] = -subdet;
            }
        }
        return new Matrix(rowsNo, rowsNo, result);
    }

    public Matrix transpose() {
        Integer[][] transpose = new Integer[colsNo][rowsNo];
        for(int i=0; i<rowsNo; i++) {
            for(int j=0; j<colsNo; j++) {
                transpose[j][i] = data[i][j];
            }
        }
        return new Matrix(colsNo, rowsNo, transpose);
    }

    public String toString() {
        StringBuilder stringMatrix = new StringBuilder();
        for(int i=0; i<rowsNo; i++) {
            for(int j=0; j<colsNo; j++) {
                stringMatrix.append(data[i][j]+" ");
            }
        }
        return stringMatrix.toString();
    } 
}