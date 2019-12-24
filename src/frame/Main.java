package frame;

import account.Account;
import account.AccountActionsName;
import machine.Machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.InflaterInputStream;

class ThreadForMachine implements Runnable {

    private static int numberOfMachines = 0;
    private int machineNumber;

    public ThreadForMachine() {
        numberOfMachines++;
        machineNumber = numberOfMachines;
    }

    public String replenishAndWithdraw(Account account, String s, Machine machine) {
        if (!s.equals(AccountActionsName.END_OF_SERVICE.getTitle())) {
            if (s.equals(AccountActionsName.ON_MY_WAY.getTitle())) {
                int temp = account.randomAmount(account.getAmountMoney());
                account.setAmountMoney(account.getAmountMoney() - temp);
                machine.setMachineCashAmount(machine.getMachineCashAmount() + temp * 0.5);
                machine.setMachineCost(machine.getMachineCost() + (int) (temp * 0.5 * 0.25));
                return "";
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
                Main.jTextAreaMap.get(machine.getMachineNumber()).append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в такси " + machine.getMachineNumber() + "\n");
                machine.setOccupation(true);
                machine.setMachineFree(false);
                machine.setMachineBroken(false);
                Main.jLabelMap.get(machine.getMachineNumber()).setText(machine.getWorkFreeBroken());
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.jTextAreaMap.get(machine.getMachineNumber()).append(tempAccount.getAccountNumber() + " " + AccountActionsName.ON_MY_WAY.getTitle() + " в такси " + machine.getMachineNumber() + "\n");

            if (Account.ran(100) > 61) {
                Main.components.get(machine.getMachineNumber()).setEnabled(true);
                if (Account.ran(10) > 5)
                    JOptionPane.showMessageDialog(null, "У такси " + machine.getMachineNumber() + " лопнуло колесо!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "Такси " + machine.getMachineNumber() + " вылетел в кювет", "Ошибка", JOptionPane.ERROR_MESSAGE);
                machine.setOccupation(true);
                machine.setMachineFree(false);
                machine.setMachineBroken(true);
                Main.jLabelMap.get(machine.getMachineNumber()).setText(machine.getWorkFreeBroken());
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
            try {
                Thread.sleep(Account.ran(25000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            replenishAndWithdraw(tempAccount, AccountActionsName.ON_MY_WAY.getTitle(), machine);
            Main.jTextAreaMap.get(machine.getMachineNumber()).append(tempAccount.getAccountNumber() + " " + AccountActionsName.END_OF_SERVICE.getTitle() + " в такси " + machine.getMachineNumber() + "\n");
            machine.setOccupation(false);
            machine.setMachineFree(true);
            machine.setMachineBroken(false);
            Main.jLabelMap.get(machine.getMachineNumber()).setText(machine.getWorkFreeBroken());
            Main.jLabelMapMoney.get(machine.getMachineNumber()).setText("Доходы " + (int) machine.getMachineCashAmount() + " р");
            Main.jLabelMapCosts.get(machine.getMachineNumber()).setText("Расходы " + machine.getMachineCost() + " р");
            Main.jLabelMapGains.get(machine.getMachineNumber()).setText("Прибыль " + (int) (machine.getMachineCashAmount() - machine.getMachineCost()) + " р");
            machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
            Main.jLabelMapAction.get(machine.getMachineNumber()).setText("Кол-во поездок " + machine.getActionsAmountByMachine());
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

public class Main {

    public static LinkedBlockingQueue<Account> accountArrayDeque;
    public static JFrame jFrame = new JFrame("Диспетчерская такси");
    public static Map<Integer, JButton> components = new HashMap<>();
    public static Map<Integer, Machine> machineMap = new HashMap<>();
    public static Map<Integer, JTextArea> jTextAreaMap = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapMoney = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapAction = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapCosts = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMapGains = new HashMap<>();
    public static Map<Integer, JLabel> jLabelMap = new HashMap<>();

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
        jPanel.setBackground(Color.GRAY);
        addLabelToFrame(1, jPanel);
        addLabelToFrame(2, jPanel);
        addLabelToFrame(3, jPanel);

        JPanel jPanelForButton = new JPanel();
        jPanelForButton.setBackground(Color.GRAY);
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
        JButton jButton = new JButton("Починить " + numberMachine);
        jButton.setBackground(Color.PINK);
        jButton.setEnabled(false);
        jButton.setPreferredSize(new Dimension(589, 50));
        jButton.addActionListener(new MachineButtonActionListener());
        jPanel.add(jButton, BorderLayout.PAGE_END);

        return jButton;
    }

    public static void addLabelToFrame(int numberMachine, JPanel jPanel) {
        Font font = new Font("Impact", Font.ITALIC, 20);

        JLabel label = new JLabel("Такси" + numberMachine + " - свободно");

        JLabel labelAmountATMMoney = new JLabel("Доходы ");
        JLabel labelAmountATMAction = new JLabel("Кол-во поездок ");
        JLabel labelAmountATMCosts = new JLabel("Расходы ");
        JLabel labelAmountATMGains = new JLabel("Прибыль ");
        JTextArea jTextArea = new JTextArea(2, 3);

        label.setFont(font);
        labelAmountATMAction.setFont(font);
        labelAmountATMMoney.setFont(font);
        labelAmountATMCosts.setFont(font);
        labelAmountATMGains.setFont(font);

        jTextArea.setBackground(Color.LIGHT_GRAY);
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

        jLabelMap.put(numberMachine, label);
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
            case "Починить 1":
                tempMachine = Main.machineMap.get(1);

                tempMachine.setOccupation(true);
                tempMachine.setMachineFree(false);
                tempMachine.setMachineBroken(false);
                Main.jLabelMap.get(tempMachine.getMachineNumber()).setText(tempMachine.getWorkFreeBroken());

                tempMachine.setMachineWork(true);
                tempMachine.setMachineCost(tempMachine.getMachineCost() + 550);
                Main.jLabelMapCosts.get(tempMachine.getMachineNumber()).setText("Расходы " + tempMachine.getMachineCost() + " р");
                Main.jLabelMapGains.get(tempMachine.getMachineNumber()).setText("Прибыль " + (int) (tempMachine.getMachineCashAmount() - tempMachine.getMachineCost()) + " р");
                Main.components.get(1).setEnabled(false);
                break;
            case "Починить 2":
                tempMachine = Main.machineMap.get(2);

                tempMachine.setOccupation(true);
                tempMachine.setMachineFree(false);
                tempMachine.setMachineBroken(false);
                Main.jLabelMap.get(tempMachine.getMachineNumber()).setText(tempMachine.getWorkFreeBroken());

                tempMachine.setMachineWork(true);
                tempMachine.setMachineCost(tempMachine.getMachineCost() + 550);
                Main.jLabelMapCosts.get(tempMachine.getMachineNumber()).setText("Расходы " + tempMachine.getMachineCost() + " р");
                Main.jLabelMapGains.get(tempMachine.getMachineNumber()).setText("Прибыль " + (int) (tempMachine.getMachineCashAmount() - tempMachine.getMachineCost()) + " р");
                Main.components.get(2).setEnabled(false);
                break;
            case "Починить 3":
                tempMachine = Main.machineMap.get(3);

                tempMachine.setOccupation(true);
                tempMachine.setMachineFree(false);
                tempMachine.setMachineBroken(false);
                Main.jLabelMap.get(tempMachine.getMachineNumber()).setText(tempMachine.getWorkFreeBroken());

                tempMachine.setMachineWork(true);
                tempMachine.setMachineCost(tempMachine.getMachineCost() + 550);
                Main.jLabelMapCosts.get(tempMachine.getMachineNumber()).setText("Расходы " + tempMachine.getMachineCost() + " р");
                Main.jLabelMapGains.get(tempMachine.getMachineNumber()).setText("Прибыль " + (int) (tempMachine.getMachineCashAmount() - tempMachine.getMachineCost()) + " р");
                Main.components.get(3).setEnabled(false);
                break;
        }
    }
}
