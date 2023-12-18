package org.ada.controllers;

import java.util.ArrayList;
import java.util.Random;

/**
 * IngenuousSolutionController
 * Esta clase se encarga de generar una solucion ingenua para el problema de la generacion de un calendario de partidos
 */
public class IngenuousSolutionController {
    Random random = new Random();
    private static final int OPPORTUNITIES_SELECT_NUMBER = 100;
    private int teams;
    private int[][] distanceCities;
    private final int min;
    private final int max;
    private final int[][] calendarSolution;

    /**
     * Controlador para una solución ingenua del problema de la generación de un calendario de partidos.
     * Inicializa los atributos de la clase.
     *
     * @param teams          El número de equipos.
     * @param distanceCities La matriz de distancias entre ciudades.
     * @param min            El número mínimo permitido de ocurrencias consecutivas.
     * @param max            El número máximo permitido de ocurrencias consecutivas.
     */

    public IngenuousSolutionController(int teams, int[][] distanceCities, int min, int max) {
        this.teams = teams;
        this.distanceCities = distanceCities;
        this.min = min;
        this.max = max;
        this.calendarSolution = new int[2 * (teams - 1)][teams];
    }

    /**
     * getMatrixSolution
     * Este metodo se encarga de retornar la matriz solucion
     *
     * @return int[][]
     */
    public int[][] getMatrixSolution() {
        return calendarSolution;
    }

    /**
     * createCalendarSolution
     * Este metodo se encarga de generar una solucion ingenua para el problema de la generacion de un calendario de partidos
     */
    public void createCalendarSolution() {
        try {

            do {

                for (int i = 0; i < calendarSolution.length; i++) {
                    int[] row = generateRow(i);
                    calendarSolution[i] = row;
                }

            } while (!isValid());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * generateRow
     * Este metodo se encarga de generar una fila de la matriz solucion
     *
     * @param row
     * @return int[]
     */
    public int[] generateRow(int row) {
        int[] rowGenerated = new int[teams];

        for (int i = 0; i < rowGenerated.length; i++) {
            ArrayList<Integer> availableNumber = findAvailableNumbers(row, i, rowGenerated);

            if (!availableNumber.isEmpty() && rowGenerated[i] == 0) {
                generateValidNumbers(rowGenerated, i, availableNumber, row);
            }
        }

        return rowGenerated;
    }

    /**
     * generateValidNumbers
     * Este metodo se encarga de generar un numero valido para la fila de la matriz solucion
     *
     * @param rowGenerated
     * @param column
     * @param availableNumbers
     * @param row
     */
    public void generateValidNumbers(int[] rowGenerated, int column, ArrayList<Integer> availableNumbers, int row) {
        int countOpportunities = 0;

        int[] consecutive;
        int[] consecutiveReflex;
        do {
            countOpportunities++;
            int numberSelected = availableNumbers.get(random.nextInt(availableNumbers.size()));
            int numberReflex = numberSelected > 0 ? (column + 1) * -1 : (column + 1);
            int indexReflex = (numberSelected > 0 ? numberSelected : numberSelected * -1) - 1;

            consecutive = countConsecutive(row, column, (numberSelected > 0));
            consecutiveReflex = countConsecutive(row, indexReflex, (numberReflex > 0));

            if (isValidPlacement(consecutive, consecutiveReflex)) {
                rowGenerated[column] = numberSelected;
                rowGenerated[indexReflex] = numberReflex;
            }

        } while (!isValidPlacement(consecutive, consecutiveReflex) && countOpportunities <= OPPORTUNITIES_SELECT_NUMBER);
    }

    /**
     * isValidPlacement
     * Este metodo se encarga de validar si el numero seleccionado es valido para la fila de la matriz solucion
     *
     * @param consecutive
     * @param consecutiveReflex
     * @return boolean
     */
    public boolean isValidPlacement(int[] consecutive, int[] consecutiveReflex) {
        return consecutive[0] <= max && consecutive[1] >= min &&
                consecutiveReflex[0] <= max && consecutiveReflex[1] >= min;
    }

    /**
     * findAvailableNumbers
     * Este metodo se encarga de encontrar los numeros disponibles para la fila de la matriz solucion
     *
     * @param row
     * @param column
     * @param arrayRow
     * @return ArrayList&lt;Integer&gt;
     */
    public ArrayList<Integer> findAvailableNumbers(int row, int column, int[] arrayRow) {
        ArrayList<Integer> availableNumbers = new ArrayList<>();
        ArrayList<Integer> NoAvailableNumbers = new ArrayList<>();

        NoAvailableNumbers.add(column + 1);
        NoAvailableNumbers.add((column + 1) * -1);

        for (int i = 0; i < row; i++) {
            int number = calendarSolution[i][column];
            if (number != 0) {
                NoAvailableNumbers.add(number);
            }
        }

        for (int number : arrayRow) {
            if (number != 0) {
                NoAvailableNumbers.add(number);
                NoAvailableNumbers.add(number * -1);
            }
        }

        for (int number = -teams; number <= teams; number++) {
            if (!NoAvailableNumbers.contains(number) && number != 0) {
                availableNumbers.add(number);
            }
        }

        return availableNumbers;
    }

    /**
     * countConsecutive
     * Este metodo se encarga de contar los numeros consecutivos para la fila de la matriz solucion
     *
     * @param row
     * @param column
     * @param signNumber
     * @return int[]
     */
    public int[] countConsecutive(int row, int column, boolean signNumber) {
        int consecutiveMax = 1;
        int consecutiveMin = 1;

        if ((row - max) >= 0) {
            for (int i = (row - 1); i >= (row - max); i--) {
                // Cuenta los positivos
                if (calendarSolution[i][column] != 0 && calendarSolution[i][column] > 0 && signNumber) {
                    consecutiveMax++;
                }

                // Cuenta los negativos
                if (calendarSolution[i][column] != 0 && calendarSolution[i][column] < 0 && !signNumber) {
                    consecutiveMax++;
                }
            }
        }


        if ((row + 1) >= min) {
            for (int i = (row - 1); i >= (row - min + 1); i--) {
                // Cuenta los positivos
                if (calendarSolution[i][column] != 0 && calendarSolution[i][column] > 0 && signNumber) {
                    consecutiveMin++;
                }

                // Cuenta los negativos
                if (calendarSolution[i][column] != 0 && calendarSolution[i][column] < 0 && !signNumber) {
                    consecutiveMin++;
                }
            }
        } else {
            consecutiveMin = min;
        }

        return new int[]{consecutiveMax, consecutiveMin};
    }

    /**
     * isValid
     * Este metodo se encarga de validar si la matriz solucion es valida
     *
     * @return boolean
     */
    public boolean isValid() {
        int count = 0;

        for (int[] ints : calendarSolution) {
            for (int anInt : ints) {
                if (anInt == 0) {
                    count++;
                }
            }
        }

        return count == 0;
    }
}
