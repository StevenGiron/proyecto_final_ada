package org.ada;

import org.ada.Controllers.IngenuousSolutionController;
import org.ada.Views.IndexView;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int team = 4;
        int min = 1;
        int max = 3;
        int[][] distance = {
            {0, 745, 665, 929},
            {745, 0, 80, 337},
            {665, 80, 0, 380},
            {929, 337, 380, 0}
        };
        IngenuousSolutionController controller = new IngenuousSolutionController(team, distance, min, max);
        int [][] calendarSolution = controller.createCalendarSolution();

        System.out.println("Matriz soliucion");
        for(int[] row: calendarSolution){
            System.out.println(Arrays.toString(row));
        }
        //new IndexView();
    }
}