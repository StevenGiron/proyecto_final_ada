package org.ada.Controllers;

import java.util.Arrays;

public class NearestNeighborTSP {
    static int[][] matrizCostos = {
            {0, 745, 665, 929},
            {745, 0, 80, 337},
            {665, 80, 0, 380},
            {929, 337, 380, 0}
    };

    public static void main(String[] args) {
        int numCiudades = matrizCostos.length;
        boolean[] visitado = new boolean[numCiudades];
        int[] ruta = new int[numCiudades];
        Arrays.fill(visitado, false);

        int ciudadInicial = 0; // Ciudad de inicio
        visitado[ciudadInicial] = true;
        ruta[0] = ciudadInicial;
        int costoTotal = 0;

        for (int i = 0; i < numCiudades - 1; i++) {
            int ciudadActual = ruta[i];
            int ciudadCercana = encontrarCiudadCercana(ciudadActual, visitado);
            ruta[i + 1] = ciudadCercana;
            visitado[ciudadCercana] = true;
            costoTotal += matrizCostos[ciudadActual][ciudadCercana];
        }

        // Volver a la ciudad inicial
        costoTotal += matrizCostos[ruta[numCiudades - 1]][ciudadInicial];

        System.out.println("Ruta óptima: " + Arrays.toString(ruta));
        System.out.println("Costo mínimo: " + costoTotal);
    }

    public static int encontrarCiudadCercana(int ciudad, boolean[] visitado) {
        int ciudadCercana = -1;
        int distanciaMinima = Integer.MAX_VALUE;
        for (int i = 0; i < matrizCostos.length; i++) {
            if (!visitado[i] && matrizCostos[ciudad][i] < distanciaMinima) {
                distanciaMinima = matrizCostos[ciudad][i];
                ciudadCercana = i;
            }
        }
        return ciudadCercana;
    }
}
