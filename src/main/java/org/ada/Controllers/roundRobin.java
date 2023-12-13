package org.ada.Controllers;

import java.util.Arrays;

public class roundRobin {
    private int teamsCount;
    private int[][] distanceMatrix;
    private int[][] schedule;

    public roundRobin(int teamsCount) {
        this.teamsCount = teamsCount;
        // this.distanceMatrix = distanceMatrix;
        this.schedule = new int[2 * (teamsCount - 1)][teamsCount];
    }

    public void solve() {
        // Inicializar el calendario con 0 para indicar que no se han asignado partidos
        for (int[] round : schedule) {
            Arrays.fill(round, 0);
        }

        // Ejemplo de generación de un calendario round-robin
        for (int round = 0; round < teamsCount - 1; round++) {
            for (int team = 0; team < teamsCount / 2; team++) {
                int home = (round + team) % (teamsCount - 1);
                int away = (teamsCount - 1 - team + round) % (teamsCount - 1);

                // Ajustar el último equipo para que juegue con los demás
                if (team == 0) {
                    away = teamsCount - 1;
                }

                // Asignar partidos en casa y fuera
                schedule[round][home] = away + 1; // +1 para ajustar el índice del equipo (de 0 a n-1) a un identificador de equipo (de 1 a n)
                schedule[round][away] = -(home + 1); // Negativo para indicar partido fuera

                // Asignar partidos de vuelta
                int reverseRound = round + teamsCount - 1;
                schedule[reverseRound][away] = (home + 1);
                schedule[reverseRound][home] = -(away + 1);
            }
        }

        for(int[] rows: schedule){
            System.out.println(Arrays.toString(rows));
        }
    }
}
