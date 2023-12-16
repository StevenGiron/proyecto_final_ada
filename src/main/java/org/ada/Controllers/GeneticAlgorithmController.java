package org.ada.Controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithmController {
    private int populationSize;
    private double mutationRate;
    private int[][] distanceCities; // Matriz de distancias entre ciudades
    private int teams;

    public GeneticAlgorithmController(int populationSize, double mutationRate, int[][] distanceCities, int teams) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.distanceCities = distanceCities;
        this.teams = teams;
    }

    public int[][] generateInitialPopulation() {
        int[][] population = new int[populationSize][2 * (teams - 1)];

        for (int i = 0; i < populationSize; i++) {
            population[i] = generateRandomSolution();
        }

        return population;
    }

    public double calculateFitness(int[] calendarSolution) {
        double fitness = 0.0;
        for (int i = 0; i < calendarSolution.length; i++) {
            // Ejemplo: calcular la aptitud según la distancia entre ciudades
            int city1 = calendarSolution[i];
            int city2 = calendarSolution[(i + 1) % calendarSolution.length];
            fitness += distanceCities[city1 - 1][city2 - 1]; // Suponiendo que los índices comienzan desde 1
        }
        return fitness;
    }

    public int[][] selection(int[][] population) {
        int[][] selected = new int[populationSize][population[0].length];

        for (int i = 0; i < populationSize; i++) {
            int index1 = getRandomIndex(populationSize);
            int index2 = getRandomIndex(populationSize);

            // Seleccionar el mejor de dos individuos aleatorios
            if (calculateFitness(population[index1]) < calculateFitness(population[index2])) {
                System.arraycopy(population[index1], 0, selected[i], 0, population[index1].length);
            } else {
                System.arraycopy(population[index2], 0, selected[i], 0, population[index2].length);
            }
        }
        return selected;
    }

    public int[][] crossover(int[][] parent1, int[][] parent2) {
        int[][] offspring = new int[parent1.length][parent1[0].length];

        for (int i = 0; i < parent1.length; i++) {
            int crossoverPoint = getRandomIndex(parent1[0].length);

            System.arraycopy(parent1[i], 0, offspring[i], 0, crossoverPoint);
            System.arraycopy(parent2[i], crossoverPoint, offspring[i], crossoverPoint, parent2[0].length - crossoverPoint);
        }

        return offspring;
    }

    private int[] generateRandomSolution() {
        List<Integer> teamsList = new ArrayList<>();
        for (int i = 1; i <= teams; i++) {
            teamsList.add(i);
        }
        java.util.Collections.shuffle(teamsList);

        int[] solution = new int[2 * (teams - 1)];
        for (int i = 0; i < solution.length; i++) {
            solution[i] = teamsList.get(i % teams);
        }
        return solution;
    }

    public int[][] geneticAlgorithm() {
        int[][] population = new int[populationSize][2 * (teams - 1)];

        // Generar población inicial
        for (int i = 0; i < populationSize; i++) {
            population[i] = generateRandomSolution();
        }

        // Ejecutar el algoritmo genético por un número fijo de generaciones
        int generations = 1000;
        for (int generation = 0; generation < generations; generation++) {
            int[][] selected = selection(population);

            for (int i = 0; i < populationSize; i += 2) {
                int[][] offspring = crossover(selected, selected);

                // Aplicar mutación
                for (int j = 0; j < offspring.length; j++) {
                    if (Math.random() < mutationRate) {
                        int mutationIndex = getRandomIndex(offspring[0].length);
                        offspring[j][mutationIndex] = getRandomIndex(teams) + 1;
                    }
                }

                population[i] = offspring[0];
                population[i + 1] = offspring[1];
            }
        }

        // Encontrar la mejor solución en la población final
        double bestFitness = calculateFitness(population[0]);
        int bestSolutionIndex = 0;

        for (int i = 1; i < populationSize; i++) {
            double fitness = calculateFitness(population[i]);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestSolutionIndex = i;
            }
        }

        return population;
    }

    // Método auxiliar para obtener un índice aleatorio
    private int getRandomIndex(int limit) {
        Random random = new Random();
        return random.nextInt(limit);
    }
}
