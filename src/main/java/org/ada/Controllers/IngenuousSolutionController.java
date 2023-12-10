package org.ada.Controllers;

import java.util.Arrays;
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
        int[][] firstPart = new int[teams - 1][teams];

        for(int i = 0; i < teams - 1; i++){
            int[] row = new int[teams];

            int count = 0;
            while(count < teams){
                int numberRandom = random.nextInt(4) + 1;
                boolean validateNumber = false;

                int countValidate = 0;
                while(countValidate != i){
                    int before = firstPart[countValidate][count];

                    if(numberRandom == before){
                        numberRandom = random.nextInt(4) + 1;
                        countValidate = 0;
                    }

                    if((count + 1 ) == numberRandom){
                        numberRandom = random.nextInt(4) + 1;
                        countValidate = 0;
                    }
                    countValidate++;
                }

                row[count] = numberRandom;
                count++;
            }


            firstPart[i] = row;
        }
       return  firstPart;
    }

    private void validateCalendarSolution(){

    }

    public int[][] getMatrixSolution(){
        return calendarSolution;
    };
}
