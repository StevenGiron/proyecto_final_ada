package org.ada.Views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Index {

    public Index() {
        this.draw();
    }

    private void draw(){
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
                } else {
                    JOptionPane.showMessageDialog(null, "Ningun archivo seleccionado");
                }
            }
        });

        frame.setVisible(true);
        }
}
