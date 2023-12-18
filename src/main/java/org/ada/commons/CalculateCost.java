package org.ada.commons;

/**
 * La clase CalculateCost representa un conjunto de utilidades comunes entre las clases que manejan la logica de la aplicaicon
 * y nos permite evitar la duplicidad de codigo
 */
public class CalculateCost {

     /**
     * Calcula el costo total de un torneo en función de los costos de viaje entre ciudades
     * y el calendario de partidos.
     *
     * @param distance Matriz de costos entre ciudades, donde costos[i][j] es el costo de viajar
     *               desde la ciudad i a la ciudad j.
     * @param calendar Matriz de calendario que representa los partidos y su ubicacion,
     *                   donde calendario[i][j] es el numero del partido i para el equipo j.
     *                   El signo de calendario[i][j] indica si el equipo j juega en casa o fuera
     *                   de casa (positivo para jugar en casa, negativo para jugar fuera).
     * @return El costo total del torneo, que es la suma de los costos de viaje de todos los equipos.
     */

    public static int calculateTotalCost(int[][] distance, int[][] calendar) {
        int totalTournamentCost = 0;
        int[] teamCosts = new int[distance.length];

        for (int team = 0; team < distance.length; team++) {
            int currentCity = team;
            for (int match = 0; match < calendar.length; match++) {
                int opponent = Math.abs(calendar[match][team]) - 1; // Convert to zero-based index
                boolean isHomeGame = calendar[match][team] > 0;

                if (!isHomeGame) {
                    // Team travels to opponent's city
                    teamCosts[team] += distance[currentCity][opponent];
                    currentCity = opponent;
                } else if (currentCity != team) {
                    // Team returns home after an away game
                    teamCosts[team] += distance[currentCity][team];
                    currentCity = team;
                }
            }
            if (currentCity != team) {
                // Team returns home after the last away game
                teamCosts[team] += distance[currentCity][team];
            }
            totalTournamentCost += teamCosts[team];
        }

        return totalTournamentCost;
    }
}
