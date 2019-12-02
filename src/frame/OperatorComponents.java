package frame;

import machine.Actions;

public enum OperatorComponents {
    REPAIR_FIRST_MACHINE ("first", new Actions.RepairActions()),
    REPAIR_SECOND_MACHINE ("second", new Actions.RepairActions()),
    REPAIR_THIRD_MACHINE ("third", new Actions.RepairActions());

    private String representation;
    private Actions.BaseAction action;

    OperatorComponents(String representation, Actions.BaseAction action) {
        this.representation = representation;
        this.action = action;
    }

    public String getRepresentation() {
        return this.representation;
    }
    public Actions.BaseAction getAction() {
        return action;
    }

    @Override
    public String toString() {
        return this.representation;
    }
}
