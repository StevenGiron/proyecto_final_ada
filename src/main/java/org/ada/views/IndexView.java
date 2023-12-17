package org.ada.views;

import org.ada.controllers.FileController;
import org.ada.controllers.IngenuousSolutionController;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class IndexView {
    private JFrame frame;
    private  JPanel panel;
    IngenuousSolutionController ingenuousSolutionController;

    /**
     * Constructor
     * Se encarga de inicializar los atributos de la clase
     */
    public IndexView() {
        this.frame = new JFrame("Calendars");
        this.panel = new JPanel();
        this.configFrame();
        this.draw();
    }

    /**
     * configFrame
     * Se encarga de configurar el frame
     */
    public void configFrame(){
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
    }

    /*public void draw() {

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
    }*/

    public void draw(){
        drawButtonGeneticAlgorithm();
        drawButtonIngenuousSolution();
        drawButtonChooserFile();
        frame.setVisible(true);
    }

    public void drawButtonGeneticAlgorithm(){
        JButton geneticAlgorithmButton = new JButton("Genetic Algorithm");
        panel.add(geneticAlgorithmButton);
        geneticAlgorithmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Genetic Algorithm");
            }
        });
    }

    public void drawButtonIngenuousSolution(){
        JButton ingenuousSolutionButton = new JButton("Ingenuous Solution");
        panel.add(ingenuousSolutionButton);
        ingenuousSolutionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ingenuous Solution");
            }
        });
    }

    public void drawButtonChooserFile(){
        JButton chooserFileButton = new JButton("Chooser File");
        panel.add(chooserFileButton);
        chooserFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Chooser File");
            }
        });
    }
}
