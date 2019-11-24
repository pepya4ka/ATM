/*
package frame;

import javax.swing.*;

import static javax.swing.GroupLayout.Alignment.*;

public class FrameManager extends JFrame {

    public FrameManager() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ////
        JLabel label = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction = new JLabel("Кол-во операций ");
        JTextArea jTextArea = new JTextArea(2, 3);
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea, scrollPane);
        ////
        JLabel label1 = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction1 = new JLabel("Кол-во операций ");
        JTextArea jTextArea1 = new JTextArea(2, 3);
        JScrollPane scrollPane1 = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea1, scrollPane1);
        ////
        JLabel label2 = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction2 = new JLabel("Кол-во операций ");
        JTextArea jTextArea2 = new JTextArea(2, 3);
        JScrollPane scrollPane2 = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea2, scrollPane2);


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addComponent(labelAmountATMAction)
                .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup(LEADING))
                    .addComponent(label1)
                    .addComponent(labelAmountATMAction1)
                    .addComponent(scrollPane1)
                .addGroup(layout.createParallelGroup(LEADING))
                    .addComponent(label2)
                    .addComponent(labelAmountATMAction2)
                    .addComponent(scrollPane2)
        );

        //pack();
    }

    private void settingsJScrollPane(JTextArea jTextArea, JScrollPane jScrollPane) {
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea.setEditable(false);
    }

    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new FrameManager().setVisible(true);
            }
        });
    }

}
*/

package frame;
// Пример использования менеджера расположения GroupLayout

import javax.swing.*;

import static javax.swing.GroupLayout.Alignment.*;

public class FrameManager extends JFrame
{
    public FrameManager()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Список компонентов формы
        JLabel label = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction = new JLabel("Кол-во операций ");
        JTextArea jTextArea = new JTextArea(2, 3);
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea, scrollPane);
        ////
        JLabel label1 = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction1 = new JLabel("Кол-во операций ");
        JTextArea jTextArea1 = new JTextArea(2, 3);
        JScrollPane scrollPane1 = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea1, scrollPane1);
        ////
        JLabel label2 = new JLabel("Банкомат1: ");
        JLabel labelAmountATMAction2 = new JLabel("Кол-во операций ");
        JTextArea jTextArea2 = new JTextArea(2, 3);
        JScrollPane scrollPane2 = new JScrollPane(jTextArea);
        settingsJScrollPane(jTextArea2, scrollPane2);
        //

        JTextField  textField       = new JTextField();
        JCheckBox   cbCaseSensitive = new JCheckBox("Учет регистра");
        JCheckBox   cbWholeWords    = new JCheckBox("Целое слово"  );
        JCheckBox   cbBackward      = new JCheckBox("Поиск назад"  );
        JButton     btnFind         = new JButton("Найти"   );
        JButton     btnCancel       = new JButton("Отменить");

        //cbCaseSensitive.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        //cbWholeWords   .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        //cbBackward     .setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Определение менеджера расположения
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        // Создание горизонтальной группы
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(textField)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(cbCaseSensitive)
                                        .addComponent(cbBackward))
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(cbWholeWords))))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(btnFind)
                        .addComponent(btnCancel))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, btnFind, btnCancel);

        // Создание вертикальной группы
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(label)
                        .addComponent(textField)
                        .addComponent(btnFind))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(cbCaseSensitive)
                                        .addComponent(cbWholeWords))
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(cbBackward)))
                        .addComponent(btnCancel))
        );

        setTitle("Поиск");
        pack();
    }
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new FrameManager().setVisible(true);
            }
        });
    }

    private void settingsJScrollPane(JTextArea jTextArea, JScrollPane jScrollPane) {
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea.setEditable(false);
    }
}
