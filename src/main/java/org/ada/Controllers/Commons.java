package org.ada.Controllers;

public class Commons {
    private int[][] costos;
    private int[][] calendario;


    
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

       // Optional: Print individual team costs
        for (int i = 0; i < teamCosts.length; i++) {
            System.out.println("Costo total para el equipo " + (i + 1) + ": " + teamCosts[i]);
        }

        System.out.println("Costo total torneo: " + totalTournamentCost);

        return totalTournamentCost;
    }
}
