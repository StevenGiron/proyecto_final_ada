package org.ada.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Predicate;

public class IngenuousSolutionController {
    Random random = new Random();
    private int teams;
    private int[][] distanceCities;
    private int min;
    private int max;
    private int[][] calendarSolution;
    private boolean isPossible;

    public IngenuousSolutionController(int teams, int[][] distanceCities, int min, int max) {
        this.teams = teams;
        this.distanceCities = distanceCities;
        this.min = min;
        this.max = max;
        this.calendarSolution = new int[2 * (teams - 1)][teams];
        this.isPossible = true;

        int[][] nuevaMatriz = {
                {6, 3, -2, -5, 4, -1},
                {4, 6, 5, -1, -3, -2},
                {-5, 4, 6, -2, 1, -3},
                {-6, 5, -4, 3, -2, 1},
                {-3, -4, 1, 2, -6, 5},
                {3, -5, -1, -6, 2, 4},
                {5, -3, 2, 6, -1, -4},
                {-2, 1, 4, -3, 6, -5},
                {-4, -6, -5, 1, 3, 2},
                {2, -1, -6, 5, -4, 3}
        };

        boolean verified = verificarSecuencias(nuevaMatriz, max, min);
        System.out.println("Matriz prueba verificacion: " + verified);
    }

    private void validateCalendarSolution(){

    }

    public int[][] getMatrixSolution(){
        return calendarSolution;
    };

    private ArrayList<Integer> findRowsAndColumns (int[][] A, int row, int column){
        ArrayList<Integer> result = new ArrayList<>();

        for (int[] ints : A) {
            int number = ints[column];
            if(number < 0){
                number = number * -1;
            }
            result.add(number);
        }

        for(int j = 0; j < A[0].length; j++){
            int number = A[row][j];
            if(number < 0){
                number = number * -1;
            }
            result.add(number);
        }

        return  result;
    }

    public boolean verificarSecuencias(int[][] matriz, int max, int min) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        // Verificar filas
        for (int i = 0; i < filas; i++) {
            int countPositivos = 0;
            int countNegativos = 0;

            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] > 0) {
                    countPositivos++;
                    countNegativos = 0;
                } else if (matriz[i][j] < 0) {
                    countNegativos++;
                    countPositivos = 0;
                } else {
                    countPositivos = 0;
                    countNegativos = 0;
                }

                if (countPositivos > max || countNegativos > max) {
                    return true;
                }
            }
        }

        // Verificar columnas
        for (int j = 0; j < columnas; j++) {
            int countPositivos = 0;
            int countNegativos = 0;

            for (int i = 0; i < filas; i++) {
                if (matriz[i][j] > 0) {
                    countPositivos++;
                    countNegativos = 0;
                } else if (matriz[i][j] < 0) {
                    countNegativos++;
                    countPositivos = 0;
                } else {
                    countPositivos = 0;
                    countNegativos = 0;
                }

                if (countPositivos > max || countNegativos > max) {
                    return true;
                }
            }
        }

        // Verificar si hay menos de min números positivos o negativos seguidos
        for (int i = 0; i < filas; i++) {
            int countPositivos = 0;
            int countNegativos = 0;

            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] > 0) {
                    countPositivos++;
                    countNegativos = 0;
                } else if (matriz[i][j] < 0) {
                    countNegativos++;
                    countPositivos = 0;
                } else {
                    countPositivos = 0;
                    countNegativos = 0;
                }

                if (countPositivos < min || countNegativos < min) {
                    return true;
                }
            }
        }

        // Verificar si hay menos de min números positivos o negativos seguidos en columnas
        for (int j = 0; j < columnas; j++) {
            int countPositivos = 0;
            int countNegativos = 0;

            for (int i = 0; i < filas; i++) {
                if (matriz[i][j] > 0) {
                    countPositivos++;
                    countNegativos = 0;
                } else if (matriz[i][j] < 0) {
                    countNegativos++;
                    countPositivos = 0;
                } else {
                    countPositivos = 0;
                    countNegativos = 0;
                }

                if (countPositivos < min || countNegativos < min) {
                    return true;
                }
            }
        }

        return false;
    }

    public int[][] createCalendarSolution(){
        try{
            do {
                for(int i = 0; i < calendarSolution.length; i++){
                    int[] row = generateRow(i);
                    calendarSolution[i] = row;
                }
            }while(!isValid() || !verificarSecuencias(calendarSolution, max, min));


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return calendarSolution;

    }

    private int[] generateRow (int row){
        int[] rowGenerated = new int[teams];

        for(int i = 0; i < rowGenerated.length; i++){
            ArrayList<Integer> availableNumber = findAvailableNumbers(row, i, rowGenerated);
            int consecutive;
            int consecutiveReflex;

            if(!availableNumber.isEmpty()){
                if(rowGenerated[i] == 0){
                    do{
                        int numberSelected = availableNumber.get(random.nextInt(availableNumber.size()));
                        int numberReflex = numberSelected > 0 ? (i + 1) * -1 : (i + 1);
                        int indexReflex = (numberSelected > 0 ? numberSelected : numberSelected * -1) - 1;

                        consecutive = countConsecutive(row, i, (numberSelected >= 0));
                        consecutiveReflex = countConsecutive(row, indexReflex, (numberReflex >= 0));

                        if(consecutive <= max && consecutive >= min && consecutiveReflex <= max && consecutiveReflex >= min){
                            rowGenerated[i] = numberSelected;
                            rowGenerated[indexReflex] = numberReflex;
                        }
                    }while (consecutive > max || consecutive < min || consecutiveReflex > max || consecutiveReflex < min);
                }
            }
        }

        return  rowGenerated;
    }

    private ArrayList<Integer> findAvailableNumbers(int row, int column, int[] arrayRow){
        ArrayList<Integer> availableNumbers = new ArrayList<>();
        ArrayList<Integer> NoAvailableNumbers = new ArrayList<>();

        NoAvailableNumbers.add(column + 1);
        NoAvailableNumbers.add((column + 1) * -1);

        for(int i = 0; i < row; i++){
            int number = calendarSolution[i][column];
            if(number != 0){
                NoAvailableNumbers.add(number);
            }
        }

        for (int number : arrayRow) {
            if (number != 0) {
                NoAvailableNumbers.add(number);
                NoAvailableNumbers.add(number * -1);
            }
        }

        for(int number = -teams; number <= teams; number++){
            if(!NoAvailableNumbers.contains(number) && number != 0){
                availableNumbers.add(number);
            }
        }

        return availableNumbers;
    }

    private int countConsecutive(int row, int column, boolean signNumber){
        int consecutive = 1;
        if((row - max) >= 0){
            for(int i = (row - 1); i > (row - max); i--){
                // Cuenta los positivos
                if(calendarSolution[i][column] != 0 && calendarSolution[i][column] > 0 && signNumber){
                    consecutive++;
                }

                // Cuenta los negativos
                if(calendarSolution[i][column] != 0 && calendarSolution[i][column] < 0 && !signNumber){
                    consecutive++;
                }
            }
        }

        return consecutive;
    }

    private boolean isValid (){
        int count = 0;

        for (int i = 0; i < calendarSolution.length; i++) {
            for (int j = 0; j < calendarSolution[i].length; j++) {
                if (calendarSolution[i][j] == 0) {
                    count++;
                }
            }
        }

        return count == 0;
    }
}
