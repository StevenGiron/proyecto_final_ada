package org.ada.views;

import org.ada.controllers.GeneticAlgorithmController;
import org.ada.controllers.IngenuousSolutionController;
import org.ada.controllers.FileController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class IndexView {
    private JFrame frame;
    private  JPanel panel;
    private IngenuousSolutionController ingenuousSolutionController;
    private GeneticAlgorithmController geneticAlgorithmController;
    private FileController fileController;
    private int teams;
    private int max;
    private int min;
    private int[][] distanceCities;
    private int[][] calendarSolution;

    /**
     * Constructor
     * Se encarga de inicializar los atributos de la clase
     */
    public IndexView() {
        this.frame = new JFrame("Calendars");
        this.panel = new JPanel();
        this.configFrame();
        this.configPanel();
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

    /**
     * configPanel
     * Se encarga de configurar el panel principal
     */
    public void configPanel(){
        panel.setLayout(new GridLayout(0, 1));
    }

    /**
     * draw
     * Dibuja las filas en las culaes se encuentran los botones
     */
    public void draw(){
        addRowChooserFile();
        addRowButtons();
        frame.setVisible(true);
    }

    /**
     * addRowChooserFile
     * Anade la fila en la cual se encuentra el boton para seleccionar el archivo
     */
    public void addRowChooserFile(){
        JPanel row = new JPanel();
        row.setLayout(new GridLayout(1, 0));

        JButton button = drawButtonChooserFile();
        row.add(button);

        panel.add(row);
    }

    /**
     * addRowButtons
     * Anade la fila en la cual se encuentran los botones que ejecutan los algoritmos
     */
    public void addRowButtons(){
        JPanel row = new JPanel();
        row.setLayout(new GridLayout(1, 2));

        JButton buttonIngenuous = drawButtonIngenuousSolution();
        JButton buttonGeneticAlgorithm = drawButtonGeneticAlgorithm();

        row.add(buttonIngenuous);
        row.add(buttonGeneticAlgorithm);

        panel.add(row);
    }

    /**
     * drawButtonGeneticAlgorithm
     * Crea el button del algoritmo genetico e implementa el evento para ejecutar el algoritmo como tal
     * @return JButton
     */
    public JButton drawButtonGeneticAlgorithm(){
        JButton geneticAlgorithmButton = new JButton("Optimized Solution");
        panel.add(geneticAlgorithmButton);
        geneticAlgorithmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(fileController != null && fileController.getFile() != null){
                    geneticAlgorithmButton.setText("Loading...");
                    geneticAlgorithmController = new GeneticAlgorithmController(teams, distanceCities, max, min);
                    calendarSolution = geneticAlgorithmController.runGeneticAlgorithm();

                    fileController.saveFile(calendarSolution, teams, min, max);
                    geneticAlgorithmButton.setText("Optimized Solution");
                }else{
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun archivo aun");
                }

            }
        });

        return geneticAlgorithmButton;
    }

    /**
     * drawButtonIngenuousSolution
     * Crea el boton del algoritmo ingenuo e implementa el evento para ejecutar el algoritmo como tal
     * @return JButton
     */
    public JButton drawButtonIngenuousSolution(){
        JButton ingenuousSolutionButton = new JButton("Ingenuous Solution");
        panel.add(ingenuousSolutionButton);
        ingenuousSolutionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(fileController != null && fileController.getFile() != null){
                    ingenuousSolutionButton.setText("Loading...");
                    ingenuousSolutionController = new IngenuousSolutionController(teams, distanceCities, min, max);
                    ingenuousSolutionController.createCalendarSolution();
                    calendarSolution = ingenuousSolutionController.getMatrixSolution();

                    fileController.saveFile(calendarSolution, teams, min, max);
                    ingenuousSolutionButton.setText("Ingenuous Solution");
                }else{
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun archivo aun");
                }
            }
        });

        return ingenuousSolutionButton;
    }


    /**
     * drawButtonChooserFile
     * Crea el boton para elegir el archivo, ademas del evento para seleccionar
      * @return
     */
    public JButton drawButtonChooserFile(){
        JButton chooserFileButton = new JButton("Chooser File");
        panel.add(chooserFileButton);
        chooserFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSelectFile();
            }
        });

        return chooserFileButton;
    }

    /**
     * handleSelectFile
     * Se encarga de manejar el selector de archivos, ademas es donde se obtiene la informacion necesario para los algoritmos
     */
    public void handleSelectFile(){
        JFileChooser fileChooser = new JFileChooser();
        int fileSelected = fileChooser.showOpenDialog(null);

        if(fileSelected == JFileChooser.APPROVE_OPTION){
            String pathFile = fileChooser.getSelectedFile().getPath();
            File file = new File(pathFile);
            fileController = new FileController(file);

            if(fileController.validateFileContent()){
                distanceCities = fileController.getCalendarSolution();
                teams = fileController.getNumberTeams();
                min = fileController.getMinLength();
                max = fileController.getMaxLength();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Ningun archivo seleccionado");
        }
    }
}
