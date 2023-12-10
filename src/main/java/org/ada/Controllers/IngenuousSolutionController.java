package org.ada.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class IngenuousSolutionController {
    Random random = new Random();
    private int teams;
    private int[][] distanceCities;
    private int min;
    private int max;
    private int[][] calendarSolution;

    public IngenuousSolutionController(int teams, int[][] distanceCities, int min, int max) {
        this.teams = teams;
        this.distanceCities = distanceCities;
        this.min = min;
        this.max = max;
    }

    public int[][] createCalendarSolution(){
        int[][] calendarSolution = new int[2 * (teams - 1)][teams];
        int[][] firstPart = new int[teams - 1][teams];
        int[][] secondPart =  new int[teams - 1][teams];
        int[] numbers = new int[teams];
        Arrays.fill(numbers, 1);


        for(int j = 0; j < firstPart[0].length; j++){
            int countPositive = 1;
            int countNegative = 1;
            int controladorSigno = 1;

            for(int i = 0; i < firstPart.length; i++){
                if(firstPart[i][j] == 0){
                    ArrayList<Integer> repeats = this.findRowsAndColumns(firstPart, i, j);

                    for(int indexNumbers = 0; indexNumbers < numbers.length; indexNumbers++){
                        if(numbers[indexNumbers] != 0 && indexNumbers != j && !repeats.contains(indexNumbers + 1)){

                            if(!(min <= (countPositive) && countPositive <= max ) || !((countNegative) >= min && countNegative <= max)){
                                controladorSigno = controladorSigno * -1;

                                if(countPositive == max) countPositive = 1;
                                if(countNegative == max) countNegative = 1;
                            }

                            int numberSelected = (indexNumbers + 1) * controladorSigno;

                            firstPart[i][j] = numberSelected;
                            firstPart[i][indexNumbers] = (j + 1) * -1 * controladorSigno ;


                            secondPart[i][j] = numberSelected * -1;
                            secondPart[i][indexNumbers] = (j + 1) * controladorSigno;
                            numbers[indexNumbers]--;

                            if(numberSelected > 0) countPositive++;
                            if(numberSelected < 0) countNegative++;
                            break;
                        }
                    }

                    Arrays.fill(numbers, 1);
                }
            }
        }

        System.arraycopy(firstPart, 0, calendarSolution, 0, firstPart.length);
        System.arraycopy(secondPart, 0, calendarSolution, firstPart.length, secondPart.length);

        /*int countPositive = 0;
        int countNegative = 0;
        int index = 0;
        for(int[] row: calendarSolution){
            if(row[0] > 0) countPositive++;
            if(row[0] < 0) countNegative++;

            if(countPositive == max || countNegative == max){
                int[] currentRow = row;
                int
            }
        }*/
        for(int[] row: calendarSolution){
            System.out.println(Arrays.toString(row));
        }

       return  firstPart;
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

    private int countPermanecia(int[][] A, int column){
        return 0;
    }
}
