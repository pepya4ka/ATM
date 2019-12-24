package machine;

public class Machine implements MachineActions {

    private int machineNumber;
    private double machineCashAmount;
    private boolean machineWork;
    private boolean machineBroken;
    private boolean machineFree;
    private boolean occupation;
    private int actionsAmountByMachine;
    private int machineCost;

    public Machine() {
        this.machineWork = true;
        this.machineFree = true;
        this.machineBroken = false;
        this.occupation = false;
        this.machineCashAmount = 0;
        this.machineCost = 0;
    }

    public String getWorkFreeBroken() {
        if (machineFree)
            return "Такси" + machineNumber + " - свободно";
        if (machineBroken)
            return "Такси" + machineNumber + " - сломано";
        if (occupation)
            return "Такси" + machineNumber + " - на заказе";
        return "";
    }

    public boolean isMachineFree() {
        return machineFree;
    }

    public void setMachineFree(boolean machineFree) {
        this.machineFree = machineFree;
    }

    public boolean isMachineBroken() {
        return machineBroken;
    }

    public void setMachineBroken(boolean machineBroken) {
        this.machineBroken = machineBroken;
    }

    public int getActionsAmountByMachine() {
        return actionsAmountByMachine;
    }

    public void setActionsAmountByMachine(int actionsAmountByMachine) {
        this.actionsAmountByMachine = actionsAmountByMachine;
    }

    public double getMachineCashAmount() {
        return machineCashAmount;
    }

    public void setMachineCashAmount(double machineCashAmount) {
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

    public int getMachineCost() {
        return machineCost;
    }

    public void setMachineCost(int machineCost) {
        this.machineCost = machineCost;
    }
}
