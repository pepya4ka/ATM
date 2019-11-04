package frame;

import account.Account;
import account.AccountActionsName;
import account.Actions;
import machine.Machine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;

class ThreadForMachine implements Runnable {

    private static int numberOfMachines = 0;
    private int machineNumber;

    public ThreadForMachine() {
        numberOfMachines++;
        machineNumber = numberOfMachines;
    }

    @Override
    public void run() {
        Machine machine = new Machine();
        machine.setMachineNumber(this.machineNumber);

        machine.setMachineWork(true);
        Account tempAccount = null;
        while (machine.isMachineWork()) {
            if (Main.accountArrayDeque.peekFirst() != null) {
                tempAccount = Main.accountArrayDeque.pollFirst();
                System.out.println(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber());
                machine.setOccupation(true);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (machine.isOccupation()) {
                AccountActionsName accountActionsName = new Actions().accountActionsName();
                System.out.println(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() + " на банкомате " + machine.getMachineNumber());
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

    public static ArrayDeque<Account> accountArrayDeque;
    public static JFrame jFrame = new JFrame("Сеть банкоматов");
    public static JTextArea jTextArea;
    private static int numberMachine = 0;

    public static void appendJTextArea(int accountNumber) {
        jTextArea.append("                              " + accountNumber + " встал в очередь");
    }

    public static void main(String[] args) {

        accountArrayDeque = new ArrayDeque<>();
        frameManagerActive(jFrame);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runnable runnableForAccount = () -> {
            while (true) {
                accountArrayDeque.add(new Account());
//                jTextArea.append("                              " + accountArrayDeque.peekLast().getAccountNumber() + " встал в очередь");
                System.out.println("                              " + accountArrayDeque.peekLast().getAccountNumber() + " встал в очередь");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread threadAccount = new Thread(runnableForAccount);
        Thread firstThreadMachineWorks = new Thread(new ThreadForMachine());
        Thread secondThreadMachineWorks = new Thread(new ThreadForMachine());


        threadAccount.start();
        firstThreadMachineWorks.start();
        secondThreadMachineWorks.start();


    }

    public static void frameManagerActive(JFrame jFrame) {

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

}
