package frame;

import account.Account;
import account.AccountActionsName;
import account.Actions;
import machine.Machine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

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
            //if (Main.accountArrayDeque.peekFirst() != null) {
            try {
                tempAccount = Main.accountArrayDeque.take();
                //System.out.println(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber());
                switch (machine.getMachineNumber()) {
                    case 1:
                        //Main.jTextArea.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber() + "\n");
                        Main.jTextArea.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в банкомате " + "1\n");
                        break;
                    case 2:
                        //Main.jTextArea.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber() + "\n");
                        Main.jTextArea1.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в банкомате " + "2\n");
                        break;
                    case 3:
                        //Main.jTextArea.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " на банкомате " + machine.getMachineNumber() + "\n");
                        Main.jTextArea2.append(tempAccount.getAccountNumber() + " " + AccountActionsName.STARTED_SERVICE.getTitle() + " в банкомате " + "3\n");
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
                switch (machine.getMachineNumber()) {
                    case 1:
                        Main.jTextArea.append(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() + " в банкомате " + "1\n");
                        break;
                    case 2:
                        Main.jTextArea1.append(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() + " в банкомате " + "2\n");
                        break;
                    case 3:
                        Main.jTextArea2.append(tempAccount.getAccountNumber() + " " + accountActionsName.getTitle() + " в банкомате " + "3\n");
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

    //public static ArrayDeque<Account> accountArrayDeque;
    //public static BlockingDeque<Account> accountArrayDeque;
    public static LinkedBlockingQueue<Account> accountArrayDeque;
    public static JFrame jFrame = new JFrame("Сеть банкоматов");
    public static JTextArea jTextArea;//1 машина
    public static JTextArea jTextArea1;//2 машина
    public static JTextArea jTextArea2;//3 машина
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
        jPanel.setLayout(new GridLayout(6, 2));
        addLabelToFrame(jPanel);
        //addLabelToFrame(jPanel);
//        addLabelToFrame(jPanel);
        jFrame.getContentPane().add(BorderLayout.CENTER, jPanel);


        jFrame.setSize(800, 500);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocation(600, 300);
    }

    public static void addLabelToFrame(JPanel jPanel) {
        numberMachine++;
        jTextArea = new JTextArea(2, 3);
        jTextArea.setName(String.valueOf(numberMachine));


        jTextArea.setEditable(false);
        jPanel.add(jTextArea);


        JScrollPane scrollPane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea.setEditable(false);
        jPanel.add(scrollPane);

        //

        numberMachine++;
        jTextArea1 = new JTextArea(2, 3);
        jTextArea1.setName(String.valueOf(numberMachine));


        jTextArea1.setEditable(false);
        jPanel.add(jTextArea1);


        JScrollPane scrollPane1 = new JScrollPane(jTextArea1);
        jTextArea1.setLineWrap(true);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea1.setEditable(false);
        jPanel.add(scrollPane1);

        //
        numberMachine++;
        jTextArea2 = new JTextArea(2, 3);
        jTextArea2.setName(String.valueOf(numberMachine));


        jTextArea2.setEditable(false);
        jPanel.add(jTextArea2);


        JScrollPane scrollPane2 = new JScrollPane(jTextArea2);
        jTextArea2.setLineWrap(true);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea2.setEditable(false);
        jPanel.add(scrollPane2);
    }

}
