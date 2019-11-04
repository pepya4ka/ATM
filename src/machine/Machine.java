package machine;

public class Machine implements MachineActions {

    private int machineNumber;
    private int machineCashAmount;
    private boolean machineWork;
    private boolean occupation;

    public Machine() {
        this.machineWork = true;
        this.occupation = false;
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
