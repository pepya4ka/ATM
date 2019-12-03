package frame;

import account.Account;
import account.AccountActionsName;
import account.Actions;
import machine.Machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

class ThreadForMachine implements Runnable {

    private static int numberOfMachines = 0;
    private int machineNumber;

    public ThreadForMachine() {
        numberOfMachines++;
        machineNumber = numberOfMachines;
    }

    public String replenishAndWithdraw(Account account, String s, Machine machine) {
        if (!s.equals(AccountActionsName.END_OF_SERVICE.getTitle())) {
            if (s.equals(AccountActionsName.ADDED_BALANCE.getTitle())) {
                int temp = account.randomAmount(account.getAmountMoney());
                account.setAmountMoney(account.getAmountMoney() - temp);
                return "(" + temp + ") ";
            }
            if (s.equals(AccountActionsName.PULL_OFF.getTitle())) {
                int temp = account.randomAmount(account.getAmountMoney());
                account.setAmountMoney(account.getAmountMoney() - temp);
                machine.setMachineCashAmount(machine.getMachineCashAmount() + temp * 0.02);
                return "(" + temp + ") ";
            }
        } else {
            return "";
        }
        return "";
    }

    @Override
    public void run() {
        Machine machine = new Machine();
        machine.setMachineNumber(this.machineNumber);
        Main.machineMap.put(this.machineNumber, machine);

        machine.setMachineWork(true);
        Account tempAccount = null;
        while (machine.isMachineWork()) {
            try {
                tempAccount = Main.accountArrayDeque.take();
                Main.jTextAreaMap.get(machine.getMachineNumber()).append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в банкомате " + machine.getMachineNumber() + "\n");
                machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                Main.jLabelMapAction.get(machine.getMachineNumber()).setText("Кол-во операций " + machine.getActionsAmountByMachine());
                machine.setOccupation(true);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (machine.isOccupation()) {
                AccountActionsName accountActionsName = new Actions().accountActionsName();
                String money = replenishAndWithdraw(tempAccount, accountActionsName.getTitle(), machine);
                Main.jTextAreaMap.get(machine.getMachineNumber()).append(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() + money + " в банкомате " + machine.getMachineNumber() + "\n");
                machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                Main.jLabelMapAction.get(machine.getMachineNumber()).setText("Кол-во операций " + machine.getActionsAmountByMachine());
                Main.jLabelMapMoney.get(machine.getMachineNumber()).setText("Прибыль банкомата " + (int) machine.getMachineCashAmount() + " р");
                if (new Account().randomAmount(100) > 80) {
                    Main.components.get(machine.getMachineNumber()).setEnabled(true);
                    if (new Account().randomAmount(10) > 5)
                        JOptionPane.showMessageDialog(null, "Банкомат " + machine.getMachineNumber() + " не может подключиться к серверу!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Банкомат " + machine.getMachineNumber() + " съел карту", "Ошибка", JOptionPane.ERROR_MESSAGE);

                    machine.setMachineWork(false);
                    do {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (!machine.isMachineWork());
                }
                if (accountActionsName == AccountActionsName.END_OF_SERVICE) machine.setOccupation(false);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

public class Main {

    public static LinkedBlockingQueue<Account> accountArrayDeque;
    public static JFrame jFrame = new JFrame("Сеть банкоматов");
    public static Map<Integer, JButton> components = new HashMap<>();
    public static Map<Integer, Machine> machineMap = new HashMap<>();
    public static Map<Integer, JTextArea> jTextAreaMap = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapMoney = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapAction = new HashMap<>();

    public static void main(String[] args) {

        accountArrayDeque = new LinkedBlockingQueue<>();
        frameManagerActive(jFrame);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runnable runnableForAccount = () -> {
            while (true) {
                accountArrayDeque.add(new Account());
                System.out.println("                              Новый человек встал в очередь");
                try {
                    Thread.sleep(1800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread threadAccount = new Thread(runnableForAccount);
        Thread firstThreadMachineWorks = new Thread(new ThreadForMachine());
        Thread secondThreadMachineWorks = new Thread(new ThreadForMachine());
        Thread thirdThreadMachineWorks = new Thread(new ThreadForMachine());


        threadAccount.start();
        firstThreadMachineWorks.start();
        secondThreadMachineWorks.start();
        thirdThreadMachineWorks.start();


    }

    public static void frameManagerActive(JFrame jFrame) {


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

        jFrame.getContentPane().add(BorderLayout.CENTER, jPanel);
        jFrame.getContentPane().add(BorderLayout.SOUTH, jPanelForButton);

        jFrame.setSize(1500, 600);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocation(300, 300);
    }

    private static JButton setupRepairButton(int numberMachine, JPanel jPanel) {
        JButton jButton = new JButton("Починить " + numberMachine + " банкомат");
        jButton.setEnabled(false);
        jButton.setPreferredSize(new Dimension(489, 50));
        jButton.addActionListener(new MachineButtonActionListener());
        jPanel.add(jButton, BorderLayout.PAGE_END);

        return jButton;
    }

    public static void addLabelToFrame(int numberMachine, JPanel jPanel) {
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
                Main.components.get(1).setEnabled(false);
                break;
            case "Починить 2 банкомат":
                Main.machineMap.get(2).setMachineWork(true);
                Main.components.get(2).setEnabled(false);
                break;
            case "Починить 3 банкомат":
                Main.machineMap.get(3).setMachineWork(true);
                Main.components.get(3).setEnabled(false);
                break;
        }
    }
}
