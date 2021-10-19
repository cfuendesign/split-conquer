package co.edu.unbosque.model;

import org.jetbrains.annotations.*;

public class MatrixMultiplier {
    public MatrixMultiplier() {
    }

    @Nullable
    public int[][] multiply(@NotNull int[][] firstMatrix, @NotNull int[][] secondMatrix, @NotNull Boolean iterative) {
        //firstMatrix's number of columns should match secondMatrix's number of rows
        //Otherwise, method exits
        if (firstMatrix[0].length != secondMatrix.length) {
            //A null value is returned, which will trigger "Failed Multiplication" message in the class invoking the method.
            return null;
        } else {
            //Now, it's decided whether the algorithm will be executed iteratively or recursively (Divide & Conquer)
            if (iterative) {
                return iterativeMatrixMultiplication(firstMatrix, secondMatrix);
            } else {
                return recursiveMatrixMultiplication(firstMatrix, secondMatrix);
            }
        }
    }

    @NotNull
    private int[][] iterativeMatrixMultiplication(@NotNull int[][] fM, @NotNull int[][] sM) {
        int[][] tM = new int[fM.length][sM[0].length];
        int m = fM.length;
        int q = sM[0].length;
        int p = sM.length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < q; j++) {
                for (int k = 0; k < p; k++) {
                    tM[i][j] = tM[i][j] + (fM[i][k] * sM[k][j]);
                }
            }
        }
        return tM;
    }

    @NotNull
    private int[][] recursiveMatrixMultiplication(@NotNull int[][] fM, @NotNull int[][] sM) {
        int[][] tM = new int[fM.length][sM[0].length];
        int m = fM.length;
        int q = sM[0].length;
        int p = sM.length;
        if (fM.length == fM[0].length && sM.length == sM[0].length) {
            // Size of matrix
            // Considering size as 4 in order to illustrate
            int N = fM.length;

            // Matrix C calling method to get Result
            tM = multiply(fM, sM);
            return tM;
        } else {
            return null;
        }
    }

    @NotNull
    public int[][] multiply(@NotNull int[][] A, @NotNull int[][] B) {
        // Order of matrix
        int n = A.length;

        // Creating a 2D square matrix with size n
        int[][] R = new int[n][n];

        // Base case
        // If there is only single element
        if (n == 1)
            // Returning the simple multiplication of
            // two elements in matrices
            R[0][0] = A[0][0] * B[0][0];

            // Matrix
        else {
            // Step 1: Dividing Matrix into parts
            // by storing sub-parts to variables
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];

            // Step 2: Dividing matrix A into 4 halves
            split(A, A11, 0, 0);
            split(A, A12, 0, n / 2);
            split(A, A21, n / 2, 0);
            split(A, A22, n / 2, n / 2);

            // Step 2: Dividing matrix B into 4 halves
            split(B, B11, 0, 0);
            split(B, B12, 0, n / 2);
            split(B, B21, n / 2, 0);
            split(B, B22, n / 2, n / 2);

            // Using Formulas as described in algorithm
            // M1:=(A1+A3)×(B1+B2)
            int[][] M1
                    = multiply(add(A11, A22), add(B11, B22));

            // M2:=(A2+A4)×(B3+B4)
            int[][] M2 = multiply(add(A21, A22), B11);

            // M3:=(A1−A4)×(B1+A4)
            int[][] M3 = multiply(A11, sub(B12, B22));

            // M4:=A1×(B2−B4)
            int[][] M4 = multiply(A22, sub(B21, B11));

            // M5:=(A3+A4)×(B1)
            int[][] M5 = multiply(add(A11, A12), B22);

            // M6:=(A1+A2)×(B4)
            int[][] M6
                    = multiply(sub(A21, A11), add(B11, B12));

            // M7:=A4×(B3−B1)
            int[][] M7
                    = multiply(sub(A12, A22), add(B21, B22));

            // P:=M2+M3−M6−M7
            int[][] C11 = add(sub(add(M1, M4), M5), M7);

            // Q:=M4+M6
            int[][] C12 = add(M3, M5);

            // R:=M5+M7
            int[][] C21 = add(M2, M4);

            // S:=M1−M3−M4−M5
            int[][] C22 = add(sub(add(M1, M3), M2), M6);

            // Join 4 halves into one result matrix
            join(C11, R, 0, 0);
            join(C12, R, 0, n / 2);
            join(C21, R, n / 2, 0);
            join(C22, R, n / 2, n / 2);
        }
        return R;
    }

    // Function to subtract two matrices
    @NotNull
    public int[][] sub(@NotNull int[][] A, @NotNull int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];

        return C;
    }

    // Function to add two matrices
    @NotNull
    public int[][] add(@NotNull int[][] A, @NotNull int[][] B) {

        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    // Function to split parent matrix
    // into child matrices
    public void split(@NotNull int[][] P, @NotNull int[][] C, @NotNull int iB, @NotNull int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for (int j1 = 0, j2 = jB; j1 < C.length;
                 j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }

    // Function to join child matrices
    // into (to) parent matrix
    public void join(@NotNull int[][] C, @NotNull int[][] P, @NotNull int iB, @NotNull int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for (int j1 = 0, j2 = jB; j1 < C.length;
                 j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }


}
