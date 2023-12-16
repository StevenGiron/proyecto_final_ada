package org.ada;

import org.ada.Controllers.GeneticAlgorithmController;
import org.ada.Controllers.IngenuousSolutionController;
import org.ada.Controllers.RoundRobinController;
import org.ada.Views.IndexView;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int teams = 4;
        int min = 1;
        int max = 1;
        int[][] distance = {
                {0, 745, 665, 929},
                {745, 0, 80, 337},
                {665, 80, 0, 380},
                {929, 337, 380, 0},
        };
        /*IngenuousSolutionController controller = new IngenuousSolutionController(team, distance, min, max);
        controller.createCalendarSolution();
        int[][] calendarSolution = controller.getMatrixSolution();

        System.out.println("Matriz solucion");
        for (int[] row : calendarSolution) {
            System.out.println(Arrays.toString(row));
        }*/

        int populationSize = 4;
        double mutationRate = 0.1;



        GeneticAlgorithmController geneticAlgorithm = new GeneticAlgorithmController(populationSize, mutationRate, distance, teams);
        int[][] bestSolution = geneticAlgorithm.geneticAlgorithm();


        System.out.println("Mejor solucion");
        for (int[] row : bestSolution) {
            System.out.println(Arrays.toString(row));
        }
        //new IndexView();
    }
}