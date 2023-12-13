package org.ada.Views;

import org.ada.Controllers.FileController;
import org.ada.Controllers.IngenuousSolutionController;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

public class IndexView {

    IngenuousSolutionController ingenuousSolutionController;

    public IndexView() {
        this.draw();
    }

    private void draw() {
        JFrame frame = new JFrame("File Chooser Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton openButton = new JButton("Open File Chooser");
        panel.add(openButton);

        JFileChooser fileChooser = new JFileChooser();
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null, "Archivo seleccionado: " + fileChooser.getSelectedFile().getName());
                    String pathFile = fileChooser.getSelectedFile().getPath();
                    File file = new File(pathFile);
                    FileController fileCOntroller = new FileController(file);

                    if (fileCOntroller.validateFileContent()) {
                        int[][] distance = fileCOntroller.getCalendarSolution();
                        int teams = fileCOntroller.getNumberTeams();
                        int min = fileCOntroller.getMinLength();
                        int max = fileCOntroller.getMaxLength();

                        ingenuousSolutionController = new IngenuousSolutionController(teams, distance, min, max);
                        ingenuousSolutionController.createCalendarSolution();
                        int[][] calendario = ingenuousSolutionController.getMatrixSolution();
                        System.out.println("Calendario de partidos:");
                        for (int[] fila : calendario) {
                            System.out.println(Arrays.toString(fila));
                        }


                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ningun archivo seleccionado");
                }
            }
        });

        frame.setVisible(true);
    }
}
