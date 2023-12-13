package org.ada.Controllers;

import javax.swing.*;
import java.io.*;

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

    private static void saveMatrixToFile(int[][] matrix, int teams, int min, int max, String directoryPath) {

        String filePath = directoryPath  + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(teams+"");
            writer.newLine();
            writer.write(min+"");
            writer.newLine();
            writer.write(max+"");
            writer.newLine();

            for (int[] row : matrix) {
                writer.write(arrayToString(row));
                writer.newLine();
            }
            System.out.println("Matriz guardada en: " + filePath);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    // guardar el archivo en un directorio

    public static void saveFile(int[][] matrix, int teams, int min, int max) {
        // Crear un cuadro de diálogo para seleccionar el directorio donde se guardará el archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar directorio");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        // Mostrar el cuadro de diálogo para seleccionar el directorio
        int option = fileChooser.showOpenDialog(null);
        // Si el usuario selecciona un directorio
        if (option == JFileChooser.APPROVE_OPTION) {
            // Obtener el directorio seleccionado
            String directoryPath  = fileChooser.getSelectedFile().getAbsolutePath();

            // Guardar la matriz de solución en un archivo con información adicional
            saveMatrixToFile(matrix, teams, min, max, directoryPath);
        }
    }
}
