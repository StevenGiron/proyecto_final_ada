package org.ada.Controllers;

public class IngenuousSolutionController {
    private int teams;
    private int[][] matrixD;
    private int min;
    private int max;
    private int[][] matrixSolution;

    public IngenuousSolutionController(int teams, int[][] matrixD, int min, int max) {
        this.teams = teams;
        this.matrixD = matrixD;
        this.min = min;
        this.max = max;
    }

    public void createMatrixSolution(){

    }

    private void validationMatrixSolution(){

    }

    public int[][] getMatrixSolution(){
        return matrixSolution;
    };
}
