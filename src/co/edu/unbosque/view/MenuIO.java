package co.edu.unbosque.view;

import co.edu.unbosque.model.MatrixMultiplier;
import co.edu.unbosque.model.MatrixUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MenuIO {
    private final File uiFile;
    private String[] ui;
    private Scanner sc;
    private MatrixUtils mu;
    private MatrixMultiplier mm;

    public MenuIO() {
        uiFile = new File("data\\ui.txt");
        try {
            loadUI();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        mu = new MatrixUtils();
        mm = new MatrixMultiplier();
        sc = new Scanner(System.in);
    }

    // --- FileUtils + Split CLI Output (Help, intro, etc.) ---

    private void loadUI() throws IOException {
        this.ui = FileUtils.readFileToString(uiFile, StandardCharsets.UTF_8).split("---");
    }

    public void printLogo() {
        System.out.println(ui[0]);
    }

    public void printHelpTip() {
        System.out.println(ui[1]);
    }

    public void printHelp() {
        System.out.println(ui[2]);
    }

    public void cmdSwitch() {
        while (1 == 1) {
            String command[] = sc.nextLine().split("\\s");
            switch (command[0]) {
                case "-h":
                    printHelp();
                    break;
                case "matrix":
                    Boolean rec = false;
                    for (String a : command) {
                        System.out.println("case matrix recursive command check: " + a);
                        if (a.equals("-rec")) {
                            rec = true;
                        }
                    }
                    if (rec) {
                        int[][] result = {};
                        result = recursiveMatrixIO(command);
                        if (Objects.isNull(result)) {
                            break;
                        } else {
                            mu.printMatrix(result);
                        }
                    } else {
                        int[][] result = {};
                        try {
                            result = iterativeMatrixIO(command);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (Objects.isNull(result)) {
                            break;
                        } else {
                            mu.printMatrix(result);
                        }
                    }
                    break;
                case "exit":
                    System.out.println("Hasta pronto! Muchas gracias por utilizar nuestra app!");
                    System.exit(0);
                default:
                    System.out.println("Comando inválido. Por favor, intente denuevo.");
                    break;
            }
        }
    }

    private int[][] recursiveMatrixIO(String[] cmd) {
        //error si no se le pasan matrices cuadradas
        String[] pM_d = {};
        String[] sM_d = {};
        for (String b : cmd) {
            if (StringUtils.contains(b, ",")) {
                pM_d = b.split(",");
                System.out.println(String.format("Tamaño de la primera matriz: %sx%s", pM_d[0], pM_d[1]));
                break;
            }
        }
        int count = 0;
        for (String b : cmd) {
            if (StringUtils.contains(b, ",")) {
                count++;
                if (count == 2) {
                    sM_d = b.split(",");
                    System.out.println(String.format("Tamaño de la segunda matriz: %sx%s", sM_d[0], sM_d[1]));
                }
            }
        }
        int pM_f = Integer.parseInt(pM_d[0]);
        int pM_c = Integer.parseInt(pM_d[1]);
        int sM_f = Integer.parseInt(sM_d[0]);
        int sM_c = Integer.parseInt(sM_d[1]);
        //Condicional para abortar la operación si no hay matriz cuadrada
        if (pM_f != pM_c || sM_f != sM_c) {
            System.out.println("No se puede aplicar la forma recursiva del algoritmo de multiplicación de matrices" +
                    "\nsi las matrices proporcionadas no son cuadradas");
            return null;
        } else {
            int[][] pM = mu.createRandomMatrix(pM_f, pM_c);
            System.out.println("Primera Matriz:");
            mu.printMatrix(pM);
            int[][] sM = mu.createRandomMatrix(sM_f, sM_c);
            System.out.println("Segunda Matriz:");
            mu.printMatrix(sM);
            int[][] tM = mm.multiply(pM, sM, false);
            if (Objects.isNull(tM)) {
                System.out.println("La multiplicación ha fallado porque las matrices no cumplen con los requerimientos de tamaño\npara ser multiplicadas. Por favor, intente denuevo con dimensiones diferentes.");
                return null;
            } else {
                System.out.println("Resultado:");
                mu.printMatrix(tM);
                return tM;
            }
        }
    }

    private int[][] iterativeMatrixIO(String[] cmd) {
        String[] pM_d = {};
        String[] sM_d = {};
        for (String b : cmd) {
            if (StringUtils.contains(b, ",")) {
                pM_d = b.split(",");
                System.out.println(String.format("Tamaño de la primera matriz: %sx%s", pM_d[0], pM_d[1]));
                break;
            }
        }
        int count = 0;
        for (String b : cmd) {
            if (StringUtils.contains(b, ",")) {
                count++;
                if (count == 2) {
                    sM_d = b.split(",");
                    System.out.println(String.format("Tamaño de la segunda matriz: %sx%s", sM_d[0], sM_d[1]));
                }
            }
        }
        int[][] pM = mu.createRandomMatrix(Integer.parseInt(pM_d[0]), Integer.parseInt(pM_d[1]));
        System.out.println("Primera Matriz:");
        mu.printMatrix(pM);
        int[][] sM = mu.createRandomMatrix(Integer.parseInt(sM_d[0]), Integer.parseInt(sM_d[1]));
        System.out.println("Segunda Matriz:");
        mu.printMatrix(sM);
        int[][] tM = mm.multiply(pM, sM, true);
        if (Objects.isNull(tM)) {
            System.out.println("La multiplicación ha fallado porque las matrices no cumplen con los requerimientos de tamaño\npara ser multiplicadas. Por favor, intente denuevo con dimensiones diferentes.");
            return null;
        } else {
            System.out.println("Resultado:");
            return tM;
        }

    }

}
