package co.edu.unbosque.model;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MatrixUtils {
    public MatrixUtils() {

    }

    @NotNull
    public int[][] createRandomMatrix(@NotNull int f, @NotNull int c) {
        int[][] newMatrix = new int[f][c];
        for (int cc = 0; cc < c; cc++){
            for (int ff = 0; ff < f; ff++){
                newMatrix[ff][cc] = (int)(Math.random()*(100-0+1));
            }
        }
        return newMatrix;
    }

    public void printMatrix(@NotNull int[][] m){
        for (int[] row : m){
            System.out.print("[ ");
            for (int col : row){
                System.out.print(col+" ");
            }
            System.out.println("]");
        }
    }


}
