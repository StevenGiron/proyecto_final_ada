package org.ada.Controllers;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileController {
    private File file;
    private int[][] distanceCities;
    private int numberTeams;
    private int minLength;
    private int maxLength;

    public FileController(File file) {
        this.file = file;
    }

    public boolean validateFileContent() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            if ((line = br.readLine()) != null) {
                numberTeams = Integer.parseInt(line);
            } else {
                JOptionPane.showMessageDialog(null,"El archivo esta vacio o no tiene el formato esperado.");
                return false;
            }

            if ((line = br.readLine()) != null) {
                minLength = Integer.parseInt(line);
            } else {
                JOptionPane.showMessageDialog(null,"El archivo no tiene la longitud mínima especificada.");
                return false;
            }

            if ((line = br.readLine()) != null) {
                maxLength = Integer.parseInt(line);
            } else {
                JOptionPane.showMessageDialog(null,"El archivo no tiene la longitud máxima especificada.");
                return false;
            }

            distanceCities = new int[numberTeams][numberTeams];
            for (int i = 0; i < numberTeams; i++) {
                if ((line = br.readLine()) != null) {
                    String[] values = line.split("\\s+");
                    if (values.length != numberTeams) {
                        JOptionPane.showMessageDialog(null,"La fila " + (i + 1) + " no tiene la longitud esperada.");
                        return false;
                    }
                    for (int j = 0; j < numberTeams; j++) {
                        distanceCities[i][j] = Integer.parseInt(values[j]);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Faltan filas en la matriz.");
                    return false;
                }
            }

            return true;
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Error al leer o convertir datos.");
            return false;
        }
    }

    public int[][] getCalendarSolution() {
        return distanceCities;
    }

    public int getNumberTeams() {
        return numberTeams;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
