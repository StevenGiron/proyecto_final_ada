package org.ada.Controllers;

/**
 * La clase Commons representa un conjunto de utilidades comunes entre las clases que manejan la logica de la aplicaicon 
 * y nos permite evitar la duplicidad de codigo
 */
public class Commons {

     /**
     * Calcula el costo total de un torneo en función de los costos de viaje entre ciudades
     * y el calendario de partidos.
     *
     * @param costos Matriz de costos entre ciudades, donde costos[i][j] es el costo de viajar
     *               desde la ciudad i a la ciudad j.
     * @param calendario Matriz de calendario que representa los partidos y su ubicacion,
     *                   donde calendario[i][j] es el numero del partido i para el equipo j.
     *                   El signo de calendario[i][j] indica si el equipo j juega en casa o fuera
     *                   de casa (positivo para jugar en casa, negativo para jugar fuera).
     * @return El costo total del torneo, que es la suma de los costos de viaje de todos los equipos.
     */

    public static int calculateTotalCost(int[][] costos, int[][] calendario) {
        int totalTournamentCost = 0;
        int[] teamCosts = new int[costos.length];

        for (int team = 0; team < costos.length; team++) {
            int currentCity = team;
            for (int match = 0; match < calendario.length; match++) {
                int opponent = Math.abs(calendario[match][team]) - 1; // Convert to zero-based index
                boolean isHomeGame = calendario[match][team] > 0;

                if (!isHomeGame) {
                    // Team travels to opponent's city
                    teamCosts[team] += costos[currentCity][opponent];
                    currentCity = opponent;
                } else if (currentCity != team) {
                    // Team returns home after an away game
                    teamCosts[team] += costos[currentCity][team];
                    currentCity = team;
                }
            }
            if (currentCity != team) {
                // Team returns home after the last away game
                teamCosts[team] += costos[currentCity][team];
            }
            totalTournamentCost += teamCosts[team];
        }

      
        for (int i = 0; i < teamCosts.length; i++) {
            System.out.println("Costo total para el equipo " + (i + 1) + ": " + teamCosts[i]);
        }

        System.out.println("Costo total torneo: " + totalTournamentCost);

        return totalTournamentCost;
    }
}
