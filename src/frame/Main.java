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
            if (s.equals(AccountActionsName.PULL_OFF.getTitle()) || s.equals(AccountActionsName.TRANSFERRED.getTitle())) {
                int temp = account.randomAmount(account.getAmountMoney());
                account.setAmountMoney(account.getAmountMoney() - temp);
                machine.setMachineCashAmount(machine.getMachineCashAmount() + temp * 0.03);
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
//                Main.frameManager.getJTextAreaMap().get(machine.getMachineNumber()).append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в банкомате " + machine.getMachineNumber() + "\n");
                machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                Main.jLabelMapAction.get(machine.getMachineNumber()).setText("Кол-во операций " + machine.getActionsAmountByMachine());
//                Main.frameManager.getJLabelMapAction().get(machine.getMachineNumber()).setText("Кол-во операций " + machine.getActionsAmountByMachine());
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
//                Main.frameManager.getJTextAreaMap().get(machine.getMachineNumber()).append(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() + money + " в банкомате " + machine.getMachineNumber() + "\n");
                machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                Main.jLabelMapAction.get(machine.getMachineNumber()).setText("Кол-во операций " + machine.getActionsAmountByMachine());
//                Main.frameManager.getJLabelMapAction().get(machine.getMachineNumber()).setText("Кол-во операций " + machine.getActionsAmountByMachine());
                Main.jLabelMapMoney.get(machine.getMachineNumber()).setText("Прибыль " + (int) machine.getMachineCashAmount() + " р");
//                Main.frameManager.getJLabelMapMoney().get(machine.getMachineNumber()).setText("Прибыль банкомата " + (int) machine.getMachineCashAmount() + " р");
                Main.jLabelMapGains.get(machine.getMachineNumber()).setText("Доходы " + (int) (machine.getMachineCashAmount() - machine.getMachineCost()) + " р");
                if (Account.ran(100) > 94) {
                    Main.components.get(machine.getMachineNumber()).setEnabled(true);
//                    Main.frameManager.getComponents().get(machine.getMachineNumber()).setEnabled(true);
                    if (Account.ran(10) > 5)
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
                if (accountActionsName == AccountActionsName.END_OF_SERVICE) {
                    machine.setMachineCost(machine.getMachineCost() + 100);
                    machine.setOccupation(false);
                    Main.jLabelMapCosts.get(machine.getMachineNumber()).setText("Расходы " + machine.getMachineCost() + " р");
                    Main.jLabelMapGains.get(machine.getMachineNumber()).setText("Доходы " + (int) (machine.getMachineCashAmount() - machine.getMachineCost()) + " р");
                }
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
    //    public static FrameManager frameManager;
    public static Map<Integer, JTextArea> jTextAreaMap = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapMoney = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapAction = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapCosts = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapGains = new HashMap<>();

    public static void main(String[] args) {

//        frameManager = new FrameManager("Сеть банкоматов");
        accountArrayDeque = new LinkedBlockingQueue<>();
        frameManagerActive(jFrame);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runnable runnableForAccount = () -> {
            Account account;
            while (true) {
                if (accountArrayDeque.size() < 4) {
                    account = new Account();
                    accountArrayDeque.add(account);
                    System.out.println("                              " + account.getAccountNumber() + " встал в очередь");
                    try {
                        Thread.sleep(2850);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

        jFrame.setSize(1800, 600);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocation(75, 300);
    }

    private static JButton setupRepairButton(int numberMachine, JPanel jPanel) {
        JButton jButton = new JButton("Починить " + numberMachine + " банкомат");
        jButton.setEnabled(false);
        jButton.setPreferredSize(new Dimension(589, 50));
        jButton.addActionListener(new MachineButtonActionListener());
        jPanel.add(jButton, BorderLayout.PAGE_END);

        return jButton;
    }

    public static void addLabelToFrame(int numberMachine, JPanel jPanel) {
        Font font = new Font("Impact", Font.PLAIN, 20);

        JLabel label = new JLabel("Банкомат" + numberMachine + ": ");

        JLabel labelAmountATMAction = new JLabel("Кол-во операций ");
        JLabel labelAmountATMMoney = new JLabel("Прибыль ");
        JLabel labelAmountATMCosts = new JLabel("Расходы ");
        JLabel labelAmountATMGains = new JLabel("Доходы ");
        JTextArea jTextArea = new JTextArea(2, 3);

        label.setFont(font);
        labelAmountATMAction.setFont(font);
        labelAmountATMMoney.setFont(font);
        labelAmountATMCosts.setFont(font);
        labelAmountATMGains.setFont(font);

        jTextArea.setName(String.valueOf(numberMachine));

        jTextArea.setEditable(false);
        jPanel.add(label);
        jPanel.add(labelAmountATMAction);
        jPanel.add(labelAmountATMMoney);
        jPanel.add(labelAmountATMCosts);
        jPanel.add(labelAmountATMGains);
        jPanel.add(jTextArea);


        JScrollPane scrollPane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea.setEditable(false);

        jLabelMapAction.put(numberMachine, labelAmountATMAction);
        jLabelMapCosts.put(numberMachine, labelAmountATMCosts);
        jLabelMapGains.put(numberMachine, labelAmountATMGains);
        jLabelMapMoney.put(numberMachine, labelAmountATMMoney);
        jTextAreaMap.put(numberMachine, jTextArea);
        jPanel.add(scrollPane);
    }


}

class MachineButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Machine tempMachine;
        switch (e.getActionCommand()) {
            case "Починить 1 банкомат":
                tempMachine = Main.machineMap.get(1);
                tempMachine.setMachineWork(true);
                tempMachine.setMachineCost(tempMachine.getMachineCost() + 25);
                Main.jLabelMapCosts.get(tempMachine.getMachineNumber()).setText("Расходы " + tempMachine.getMachineCost() + " р");
                Main.jLabelMapGains.get(tempMachine.getMachineNumber()).setText("Доходы " + (int) (tempMachine.getMachineCashAmount() - tempMachine.getMachineCost()) + " р");
                Main.components.get(1).setEnabled(false);
                break;
            case "Починить 2 банкомат":
                tempMachine = Main.machineMap.get(2);
                tempMachine.setMachineWork(true);
                tempMachine.setMachineCost(tempMachine.getMachineCost() + 25);
                Main.jLabelMapCosts.get(tempMachine.getMachineNumber()).setText("Расходы " + tempMachine.getMachineCost() + " р");
                Main.jLabelMapGains.get(tempMachine.getMachineNumber()).setText("Доходы " + (int) (tempMachine.getMachineCashAmount() - tempMachine.getMachineCost()) + " р");
                Main.components.get(2).setEnabled(false);
                break;
            case "Починить 3 банкомат":
                tempMachine = Main.machineMap.get(3);
                tempMachine.setMachineWork(true);
                tempMachine.setMachineCost(tempMachine.getMachineCost() + 25);
                Main.jLabelMapCosts.get(tempMachine.getMachineNumber()).setText("Расходы " + tempMachine.getMachineCost() + " р");
                Main.jLabelMapGains.get(tempMachine.getMachineNumber()).setText("Доходы " + (int) (tempMachine.getMachineCashAmount() - tempMachine.getMachineCost()) + " р");
                Main.components.get(3).setEnabled(false);
                break;
        }
    }
}
