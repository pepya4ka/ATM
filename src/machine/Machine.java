package machine;

public class Machine implements MachineActions {

    private int machineNumber;
    private int machineCashAmount;
    private boolean machineWork;
    private boolean occupation;
    private int actionsAmountByMachine;

    public Machine() {
        this.machineWork = true;
        this.occupation = false;
        this.machineCashAmount = 0;
    }

    public int getActionsAmountByMachine() {
        return actionsAmountByMachine;
    }

    public void setActionsAmountByMachine(int actionsAmountByMachine) {
        this.actionsAmountByMachine = actionsAmountByMachine;
    }

    public int getMachineCashAmount() {
        return machineCashAmount;
    }

    public void setMachineCashAmount(int machineCashAmount) {
        this.machineCashAmount = machineCashAmount;
    }

    public boolean isMachineWork() {
        return machineWork;
    }

    public void setMachineWork(boolean machineWork) {
        this.machineWork = machineWork;
    }

    public boolean isOccupation() {
        return occupation;
    }

    public void setOccupation(boolean occupation) {
        this.occupation = occupation;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }
}
