package org.ada.controllers;

import org.ada.controllers.*;
import org.ada.commons.CalculateCost;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GeneticAlgorithmController
 * Esta clase se encarga de generar una solucion optimizada para el problema de la generacion de un calendario de partidos
 */
public class GeneticAlgorithmController {
    /**
     * POPULATON_SIZE
     * Esta constante es la que se encarga de manejar el tamano de la poblacion
     */
    public static final int POPULATION_SIZE = 1000;
    /**
     * MUTATION_RATE
     * Esta constante se encarga de manejar la probabilidad de mutacion del individuo
     */
    public static final double MUTATION_RATE = 20;
    /**
     * MAX_GENERATIONS
     * Esta constante se encarga de controlar el numero de generaciones que se van a producir
     */
    public static final int MAX_GENERATIONS = 1000;
    private final Random random = new Random();
    private final int max;
    private final int min;
    private final int teams;
    private final int[][] distanceCities;
    private ArrayList<int[][]> populationCalendar;
    private int[] populationCosts;

    /**
     * Controlador para una solucion optimizada del problema de la generacion de un calendario de partidos.
     * Inicializa los atributos de la clase.
     *
     * @param teams             El numero de equipos.
     * @param distanceCities    La matriz de distancias entre ciudades.
     * @param max               El numero maximo permitido de ocurrencias consecutivas.
     * @param min               El numero minimo permitido de ocurrencias consecutivas.
     */
    public GeneticAlgorithmController(int teams, int[][] distanceCities, int max, int min) {
        this.teams = teams;
        this.distanceCities = distanceCities;
        this.max = max;
        this.min = min;
        this.populationCosts = new int[POPULATION_SIZE];
        this.initializePopulation();
    }

    /**
     * runGeneticAlgorithm
     * Este metodo se encarga de ejecutar el algoritmo genetico
     *
     * @return int[][]
     */
    public int[][] runGeneticAlgorithm() {
        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            int[] parents = selectParent();
            for(int i = 0; i < parents.length; i++){
                int indexRandom1 = random.nextInt(parents.length);
                int indexRandom2 = random.nextInt(parents.length);
                int[][][] children = crossover(populationCalendar.get(parents[indexRandom1]), populationCalendar.get(parents[indexRandom2]));

                if (random.nextInt(100) < MUTATION_RATE) {
                    int childRandom = (int) Math.round(random.nextDouble());
                    children[childRandom] = mutate(children[childRandom]);
                }

                List<Integer> exclusions = new ArrayList<>();
                for(int num: parents){
                    exclusions.add(num);
                }

                int indexRandom3;
                int indexRandom4;

                do {
                    indexRandom3 = random.nextInt(POPULATION_SIZE);
                    indexRandom4 = random.nextInt(POPULATION_SIZE);
                } while (exclusions.contains(indexRandom3) || exclusions.contains(indexRandom4) || indexRandom3 == indexRandom4);

                populationCalendar.set(indexRandom3, children[0]);
                populationCalendar.set(indexRandom4, children[1]);

                populationCosts[indexRandom3] = CalculateCost.calculateTotalCost(distanceCities, children[0]);
                populationCosts[indexRandom4] = CalculateCost.calculateTotalCost(distanceCities, children[1]);
            }
        }

        return getBestCalendar();
    }

    /**
     * initializePopulation
     * Este metodo se encarga de inicializar la poblacion
     */
    public void initializePopulation() {
        populationCalendar = new ArrayList<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            int [][] calendar = generateRandomCalendar();
            populationCalendar.add(calendar);
            populationCosts[i] = CalculateCost.calculateTotalCost(distanceCities, calendar);
        }
    }

    /**
     * generateRandomCalendar
     * Este metodo se encarga de generar un calendario aleatorio
     *
     * @return int[][]
     */
    public int[][] generateRandomCalendar() {
        IngenuousSolutionController ingenuousSolutionController = new IngenuousSolutionController(teams, distanceCities, min, max);
        ingenuousSolutionController.createCalendarSolution();

        return ingenuousSolutionController.getMatrixSolution();
    }

    /**
     * selectParent
     * Este metodo se encarga de seleccionar los padres
     *
     * @return int[]
     */
    public int[] selectParent() {
        int[] parentsSelected = new int[POPULATION_SIZE / 2];

        for (int i = 0; i < POPULATION_SIZE / 2; i++) {
           int parent = evaluateFitness(i, populationCosts.length / 2 + 1);
              parentsSelected[i] = parent;
        }

        return parentsSelected;
    }

    /**
     * evaluateFitness
     * Este metodo se encarga de evaluar la aptitud de los individuos
     *
     * @param parent1
     * @param parent2
     * @return int
     */
    public int evaluateFitness(int parent1, int parent2) {
        int totalCostParent1 = populationCosts[parent1];
        int totalCostParent2 = populationCosts[parent2];

        if(totalCostParent1 > totalCostParent2) return parent2;

        return parent1;
    }

    /**
     * crossover
     * Este metodo se encarga de realizar el cruce de los individuos
     *
     * @param parent1
     * @param parent2
     * @return int[][][]
     */
    public int[][][] crossover(int[][] parent1, int[][] parent2) {
        int[][] child1 = parent1.clone();
        int[][] child2 = parent2.clone();

        outerloop:
        for(int i = 0; i < parent1.length; i++) {
            for(int j = 0; j < parent2.length; j++) {
                boolean isSimilar = validateRowSimilarity(parent1[i], parent2[j]);
                if(isSimilar) {
                    // Intercambiar en el hijo 1
                    int[] temp = child1[j];
                    child1[j] = parent2[j];
                    child1[i] = temp;

                    // Intercambiar en el hijo 2
                    temp = child2[i];
                    child2[i] = parent1[i];
                    child2[j] = temp;
                    break outerloop;
                }
            }
        }

        return new int[][][] { child1, child2 };
    }

    /**
     * mutate
     * Este metodo se encarga de mutar los individuos
     *
     * @param individual
     * @return int[][]
     */
    public int[][] mutate(int[][] individual) {
        int index1 = random.nextInt(individual.length);

        int[] rowMain = individual[index1].clone();
        for(int i = 0; i < rowMain.length; i++) {
            rowMain[i] = rowMain[i] * -1;
        }

        for(int i = 0; i < individual.length; i++) {
            int[] row = individual[i];
            if(validateRowSimilarity(rowMain, row)) {
                int[] temp = individual[i].clone();
                for(int j = 0; j < temp.length; j++) {
                    temp[j] = temp[j] * -1;
                }
                individual[i] = temp;
                individual[index1] = rowMain;
                break;
            }
        }

        return individual;
    }

    /**
     * getBestCalendar
     * Este metodo se encarga de obtener el mejor calendario
     *
     * @return int[][]
     */
    public int[][] getBestCalendar() {
        int bestCost = Integer.MAX_VALUE;
        int bestIndex = 0;
        for(int i = 0; i < populationCosts.length; i++) {
            if(populationCosts[i] < bestCost) {
                bestCost = populationCosts[i];
                bestIndex = i;
            }
        }

        return populationCalendar.get(bestIndex);
    }

    /**
     * validateRowSimilarity
     * Este metodo se encarga de validar la similitud de las filas
     *
     * @param row1
     * @param row2
     * @return boolean
     */
    public boolean validateRowSimilarity(int[] row1, int[] row2) {
        int count = 0;
        for(int i = 0; i < row1.length; i++) {
            if(row1[i] == row2[i]) count++;
        }

        return count == row1.length;
    }
}
