package frame;

import account.Account;
import account.AccountActionsName;
import account.Actions;
import machine.Machine;

import javax.swing.*;
import java.awt.*;
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

        machine.setMachineWork(true);
        Account tempAccount = null;
        while (machine.isMachineWork()) {
            //if (Main.accountArrayDeque.peekFirst() != null) {
            try {
                tempAccount = Main.accountArrayDeque.take();
                //System.out.println(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber());
                switch (machine.getMachineNumber()) {
                    case 1:
                        //Main.jTextArea.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber() + "\n");
                        Main.jTextArea.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в банкомате " + "1\n");
                        machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                        Main.labelAmountATMAction.setText("Кол-во операций " + machine.getActionsAmountByMachine());
                        break;
                    case 2:
                        //Main.jTextArea.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber() + "\n");
                        Main.jTextArea1.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в банкомате " + "2\n");
                        machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                        Main.labelAmountATMAction1.setText("Кол-во операций " + machine.getActionsAmountByMachine());
                        break;
                    case 3:
                        //Main.jTextArea.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber() + "\n");
                        Main.jTextArea2.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в банкомате " + "3\n");
                        machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                        Main.labelAmountATMAction2.setText("Кол-во операций " + machine.getActionsAmountByMachine());
                        break;
                }
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
                //System.out.println(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() + " на банкомате " + machine.getMachineNumber());
                String money = replenishAndWithdraw(tempAccount, accountActionsName.getTitle(), machine);
                switch (machine.getMachineNumber()) {
                    case 1:
                        Main.jTextArea.append(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() +  money + " в банкомате " + "1\n");
                        machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                        Main.labelAmountATMAction.setText("Кол-во операций " + machine.getActionsAmountByMachine());
                        Main.labelAmountATMMoney.setText("Прибыль банкомата " + (int) machine.getMachineCashAmount() + " р");
                        break;
                    case 2:
                        Main.jTextArea1.append(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() +  money + " в банкомате " + "2\n");
                        machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                        Main.labelAmountATMAction1.setText("Кол-во операций " + machine.getActionsAmountByMachine());
                        Main.labelAmountATMMoney1.setText("Прибыль банкомата " + (int) machine.getMachineCashAmount() + " р");
                        break;
                    case 3:
                        Main.jTextArea2.append(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() +  money + " в банкомате " + "3\n");
                        machine.setActionsAmountByMachine(machine.getActionsAmountByMachine() + 1);
                        Main.labelAmountATMAction2.setText("Кол-во операций " + machine.getActionsAmountByMachine());
                        Main.labelAmountATMMoney2.setText("Прибыль банкомата " + (int) machine.getMachineCashAmount() + " р");
                        break;
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
    public static JTextArea jTextArea;//1 машина
    public static JTextArea jTextArea1;//2 машина
    public static JTextArea jTextArea2;//3 машина
    public static JLabel labelAmountATMMoney;
    public static JLabel labelAmountATMMoney1;
    public static JLabel labelAmountATMMoney2;
    public static JLabel labelAmountATMAction;
    public static JLabel labelAmountATMAction1;
    public static JLabel labelAmountATMAction2;
    private static int numberMachine = 0;

    public static void main(String[] args) {

        //accountArrayDeque = new ArrayDeque<>();
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
//                jTextArea.append("                              " + accountArrayDeque.peekLast().getAccountNumber() + " встал в очередь\n");
//                System.out.println("                              " + accountArrayDeque.peekLast().getAccountNumber() + " встал в очередь");
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
        addLabelToFrame(jPanel);
        //addLabelToFrame(jPanel);
//        addLabelToFrame(jPanel);

//        jFrame.getContentPane().add(BorderLayout.CENTER, jPanel);
        jFrame.getContentPane().add(BorderLayout.CENTER, jPanel);


        jFrame.setSize(1500, 600);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocation(300, 300);
    }

    public static void addLabelToFrame(JPanel jPanel) {
        Font font = new Font("Impact", Font.ITALIC, 12);

        numberMachine++;
        JLabel label = new JLabel("Банкомат1: ");
        labelAmountATMAction = new JLabel("Кол-во операций ");
        label.setFont(font);
        labelAmountATMMoney = new JLabel("Прибыль банкомата ");
        jTextArea = new JTextArea(2, 3);
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
        jPanel.add(scrollPane);

        //

        numberMachine++;
        JLabel label1 = new JLabel("Банкомат2: ");
        labelAmountATMAction1 = new JLabel("Кол-во операций ");
        labelAmountATMMoney1 = new JLabel("Прибыль банкомата ");
        label1.setFont(font);
        jTextArea1 = new JTextArea(2, 3);
        jTextArea1.setName(String.valueOf(numberMachine));


        jTextArea1.setEditable(false);
        jPanel.add(label1);
        jPanel.add(labelAmountATMAction1);
        jPanel.add(labelAmountATMMoney1);
        jPanel.add(jTextArea1);


        JScrollPane scrollPane1 = new JScrollPane(jTextArea1);
        jTextArea1.setLineWrap(true);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea1.setEditable(false);
        jPanel.add(scrollPane1);

        //
        numberMachine++;
        JLabel label2 = new JLabel("Банкомат3: ");
        labelAmountATMAction2 = new JLabel("Кол-во операций ");
        labelAmountATMMoney2 = new JLabel("Прибыль банкомата ");
        label2.setFont(font);
        jTextArea2 = new JTextArea(2, 3);
        jTextArea2.setName(String.valueOf(numberMachine));


        jTextArea2.setEditable(false);
        jPanel.add(label2);
        jPanel.add(labelAmountATMAction2);
        jPanel.add(labelAmountATMMoney2);
        jPanel.add(jTextArea2);


        JScrollPane scrollPane2 = new JScrollPane(jTextArea2);
        jTextArea2.setLineWrap(true);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea2.setEditable(false);
        jPanel.add(scrollPane2);



    }

}
