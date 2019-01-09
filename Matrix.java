// assuming no ragged arrays
public class Matrix {

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

    public static int determinant(Integer[][] matrix, int size) {

        if(size == 1) {
            return matrix[0][0];
        }
        else if(size == 2) {
            return (matrix[0][0]*matrix[1][1])-(matrix[0][1]*matrix[1][0]);
        }

        int result = 0;
        for(int i=0; i<size; i++) {

                Integer[][] submatrix = new Integer[size-1][size-1];
                int rowIndex = 0, colIndex = 0;
                for(int m=0; m<size; m++) {
                    colIndex = 0;
                    for(int n=0; n<size; n++) 
                        if(m!=0 && n!=i) {
                            submatrix[rowIndex][colIndex++] = matrix[m][n];
                            if(colIndex==size-1)
                                rowIndex++;
                        }
                }

                int subdet = determinant(submatrix, size-1);
                if(i%2 == 0)
                    result = result+(matrix[0][i]*subdet);
                else
                    result = result-(matrix[0][i]*subdet);
        }
        return result;
    }

    public Matrix cofactorsMatrix() {
        if(rowsNo != colsNo)
            return null;

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