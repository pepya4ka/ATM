package account;

import java.util.Random;

public class Actions {

    public AccountActionsName accountActionsName() {
        int randomNumber = new Random().nextInt(3) + 1;
        AccountActionsName accountActionsName = null;

        switch (randomNumber) {
            case 1: accountActionsName = AccountActionsName.ADDED_BALANCE; break;
            case 2: accountActionsName = AccountActionsName.PULL_OFF; break;
            case 3: accountActionsName = AccountActionsName.END_OF_SERVICE; break;
        }

        return accountActionsName;
    }

}
