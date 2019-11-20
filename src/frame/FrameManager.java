package frame;

import javax.swing.*;
import java.awt.*;

public class FrameManager {

    public static JFrame jFrame1 = new JFrame("Сеть банкоматов");
    //public static JTextArea jTextArea;
    private static int numberMachine = 0;

    public static JFrame frameManagerActive(JFrame jFrame, JTextArea jTextArea) {

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(6, 2));
        addLabelToFrame(jPanel, jTextArea);
        //addLabelToFrame(jPanel);
//        addLabelToFrame(jPanel);
        jFrame.getContentPane().add(BorderLayout.CENTER, jPanel);


        jFrame.setSize(800, 500);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocation(600, 300);

        return jFrame;
    }

    public static void addLabelToFrame(JPanel jPanel, JTextArea jTextArea) {
        numberMachine++;
        jTextArea = new JTextArea(2, 3);
        jTextArea.setName(String.valueOf(numberMachine));


        jTextArea.setEditable(false);
        jPanel.add(jTextArea);


//        JScrollPane scrollPane = new JScrollPane(jTextArea);
//        jTextArea.setLineWrap(true);
//        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        jTextArea.setEditable(false);
//        jPanel.add(scrollPane);
    }

    public static JPanel jPanelReturn() {
        JPanel jPanel = new JPanel();
        return jPanel;
    }

}
