/*
package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class FrameManager {

    protected static Map<Integer, JButton> components = new HashMap<>();
    private Map<Integer, JTextArea> jTextAreaMap = new HashMap<>();
    private Map<Integer, JLabel> jLabelMapMoney = new HashMap<>();
    private Map<Integer, JLabel> jLabelMapAction = new HashMap<>();

    public Map<Integer, JButton> getComponents() {
        return components;
    }

    public Map<Integer, JTextArea> getJTextAreaMap() {
        return jTextAreaMap;
    }

    public Map<Integer, JLabel> getJLabelMapMoney() {
        return jLabelMapMoney;
    }

    public Map<Integer, JLabel> getJLabelMapAction() {
        return jLabelMapAction;
    }

    public FrameManager(String nameFrame) {
        JFrame jFrame = new JFrame(nameFrame);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3, 4));
        addLabelToFrame(1, jPanel);
        addLabelToFrame(2, jPanel);
        addLabelToFrame(3, jPanel);

        JPanel jPanelForButton = new JPanel();
        components.put(1, setupRepairButton(1, jPanelForButton));
        components.put(2, setupRepairButton(2, jPanelForButton));
        components.put(3, setupRepairButton(3, jPanelForButton));

        jFrame.getContentPane().add(BorderLayout.SOUTH, jPanelForButton);

        jFrame.setSize(1500, 600);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocation(0, 300);
    }

    private JButton setupRepairButton(int numberMachine, JPanel jPanel) {
        JButton jButton = new JButton("Починить " + numberMachine + " банкомат");
        jButton.setEnabled(false);
        jButton.setPreferredSize(new Dimension(489, 50));
        jButton.addActionListener(new MachineButtonActionListener());
        jPanel.add(jButton, BorderLayout.PAGE_END);

        return jButton;
    }

    public void addLabelToFrame(int numberMachine, JPanel jPanel) {
        Font font = new Font("Impact", Font.PLAIN, 20);

        JLabel label = new JLabel("Банкомат" + numberMachine + ": ");

        JLabel labelAmountATMAction = new JLabel("Кол-во операций ");
        JLabel labelAmountATMMoney = new JLabel("Прибыль банкомата ");
        JTextArea jTextArea = new JTextArea(2, 3);

        label.setFont(font);
        labelAmountATMAction.setFont(font);
        labelAmountATMMoney.setFont(font);

        jTextArea.setName(String.valueOf(numberMachine));

        jTextArea.setEditable(false);
        jPanel.add(label);
        jPanel.add(labelAmountATMAction);
        jPanel.add(labelAmountATMMoney);
        jPanel.add(jTextArea);


        JScrollPane scrollPane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea.setEditable(false);

        jLabelMapAction.put(numberMachine, labelAmountATMAction);
        jLabelMapMoney.put(numberMachine, labelAmountATMMoney);
        jTextAreaMap.put(numberMachine, jTextArea);
        jPanel.add(scrollPane);
    }

}

class MachineButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Починить 1 банкомат":
                Main.machineMap.get(1).setMachineWork(true);
                FrameManager.components.get(1).setEnabled(false);
                break;
            case "Починить 2 банкомат":
                Main.machineMap.get(2).setMachineWork(true);
                FrameManager.components.get(2).setEnabled(false);
                break;
            case "Починить 3 банкомат":
                Main.machineMap.get(3).setMachineWork(true);
                FrameManager.components.get(3).setEnabled(false);
                break;
        }
    }
}*/
