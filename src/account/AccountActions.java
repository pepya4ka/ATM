package account;

import machine.Machine;

public interface AccountActions {

    void putMoney(Machine machine);
    void withdrawMoney(Machine machine);
    int randomAmount(int amountMoney);

}
