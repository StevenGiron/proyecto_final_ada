package org.ada.Controllers;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileController {
    private File file;
    private int[][] distanceCities;
    private int numberTeams;
    private int minLength;
    private int maxLength;

    /**
     * Constructor de la clase FileController.
     * @param file El objeto File que representa el archivo asociado al controlador.
     */
    public FileController(File file) {
        this.file = file;
    }

    /**
     * Valida el contenido de un archivo que se espera que contenga información sobre el problema.
     * Lee y procesa la información del archivo, verificando que cumpla con el formato y los requisitos esperados.
     * @return true si el archivo contiene información válida y se procesa correctamente, false de lo contrario.
     */
    public boolean validateFileContent() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            if ((line = br.readLine()) != null) {
                numberTeams = Integer.parseInt(line);
            } else {
                JOptionPane.showMessageDialog(null, "El archivo esta vacio o no tiene el formato esperado.");
                return false;
            }

            if ((line = br.readLine()) != null) {
                minLength = Integer.parseInt(line);
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no tiene la longitud mínima especificada.");
                return false;
            }

            if ((line = br.readLine()) != null) {
                maxLength = Integer.parseInt(line);
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no tiene la longitud máxima especificada.");
                return false;
            }

            distanceCities = new int[numberTeams][numberTeams];
            for (int i = 0; i < numberTeams; i++) {
                if ((line = br.readLine()) != null) {
                    String[] values = line.split("\\s+");
                    if (values.length != numberTeams) {
                        JOptionPane.showMessageDialog(null, "La fila " + (i + 1) + " no tiene la longitud esperada.");
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
            JOptionPane.showMessageDialog(null, "Error al leer o convertir datos.");
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

    /**
     * Guarda la matriz de solución en un archivo de texto con información adicional.
     *
     * @param matrix        La matriz de solución que se va a guardar.
     * @param teams         El número de equipos.
     * @param min           El número mínimo permitido de ocurrencias consecutivas.
     * @param max           El número máximo permitido de ocurrencias consecutivas.
     * @param directoryPath La ruta del directorio donde se guardará el archivo.
     */
    private static void saveMatrixToFile(int[][] matrix, int teams, int min, int max, String directoryPath) {

        // Implementar lógica para guardar la matriz en un archivo
        String filePath = directoryPath + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(teams + "");
            writer.newLine();
            writer.write(min + "");
            writer.newLine();
            writer.write(max + "");
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

    /**
     * Convierte un arreglo de enteros en una cadena de texto, separando los elementos por comas.
     *
     * @param array El arreglo de enteros que se va a convertir.
     * @return Una cadena de texto que representa el arreglo.
     */
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

    /**
     * Permite al usuario seleccionar un directorio y guarda la matriz de solución en un archivo.
     *
     * @param matrix La matriz de solución que se va a guardar.
     * @param teams  El número de equipos.
     * @param min    El número mínimo permitido de ocurrencias consecutivas.
     * @param max    El número máximo permitido de ocurrencias consecutivas.
     */
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
            String directoryPath = fileChooser.getSelectedFile().getAbsolutePath();

            // Guardar la matriz de solución en un archivo con información adicional
            saveMatrixToFile(matrix, teams, min, max, directoryPath);
        }
    }
}
