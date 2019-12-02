package machine;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Actions {

    public static abstract class BaseAction extends AbstractAction {
        protected Machine machine;

        public void setMachine(Machine machine) {
            this.machine = machine;
        }

        public Machine getMachine() {
            return this.machine;
        }
    }

    public static class RepairActions extends BaseAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            //JOptionPane.showMessageDialog(null, "Повысьте давление чтобы продолжить", "Сообщение", 1);
            machine.setMachineWork(true);
        }
    }

}
