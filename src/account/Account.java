package account;

import machine.Machine;

import java.util.Random;

public class Account implements AccountActions, Runnable {

    private static int numberOfAccounts = 0;
    private int accountNumber;
    private int amountMoney;

    public Account(int amountMoney) {
        numberOfAccounts++;
        accountNumber = numberOfAccounts;
        this.amountMoney = amountMoney;
    }

    public Account() {
        numberOfAccounts++;
        accountNumber = numberOfAccounts;
        this.amountMoney = 15000;
    }

    public int getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(int amountMoney) {
        this.amountMoney = amountMoney;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void putMoney(Machine machine) {
        int tempAmount = randomAmount(20000);
        this.amountMoney += tempAmount;
        machine.setMachineCashAmount(machine.getMachineCashAmount() + amountMoney);
    }

    @Override
    public void withdrawMoney(Machine machine) {
        int tempAmount = randomAmount(this.amountMoney);
        this.amountMoney -= tempAmount;
        machine.setMachineCashAmount(machine.getMachineCashAmount() - amountMoney);
    }

    @Override
    public int randomAmount(int amountMoney) {
        return new Random().nextInt(amountMoney);
    }

    @Override
    public void run() {

    }
}
