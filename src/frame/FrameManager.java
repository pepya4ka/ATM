package frame;

import javax.swing.*;

public class FrameManager extends JFrame {

    public FrameManager() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ////
        JLabel label = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction = new JLabel("Кол-во операций ");
        JTextArea jTextArea = new JTextArea(2, 3);
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea, scrollPane);
        ////
        JLabel label1 = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction1 = new JLabel("Кол-во операций ");
        JTextArea jTextArea1 = new JTextArea(2, 3);
        JScrollPane scrollPane1 = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea1, scrollPane1);
        ////
        JLabel label2 = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction2 = new JLabel("Кол-во операций ");
        JTextArea jTextArea2 = new JTextArea(2, 3);
        JScrollPane scrollPane2 = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea2, scrollPane2);


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup())
                    .addComponent(label)
                    .addComponent(labelAmountATMAction)
                    .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup())
                    .addComponent(label1)
                    .addComponent(labelAmountATMAction1)
                    .addComponent(scrollPane1)
                .addGroup(layout.createParallelGroup())
                    .addComponent(label2)
                    .addComponent(labelAmountATMAction2)
                    .addComponent(scrollPane2)
        );

        //pack();
    }

    private void settingsJScrollPane(JTextArea jTextArea, JScrollPane jScrollPane) {
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea.setEditable(false);
    }

    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new FrameManager().setVisible(true);
            }
        });
    }

}
