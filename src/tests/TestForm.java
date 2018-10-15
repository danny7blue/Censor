/*
 * Created by JFormDesigner on Mon Oct 15 14:54:46 CST 2018
 */

package tests;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import net.miginfocom.swing.*;

/**
 * @author 305027244
 */
public class TestForm extends JPanel {
    public TestForm() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - danny
        mainFrame = new JFrame();
        verticalSeparator = new JSeparator();
        dateSelector = new JLabel();
        horizontalSeparator = new JSeparator();
        jTreeScrollPane = new JScrollPane();
        inspectorSelectorTree = new JTree();
        jTableScrollPane = new JScrollPane();
        dataTable = new JTable();

        //======== mainFrame ========
        {
            Container mainFrameContentPane = mainFrame.getContentPane();
            mainFrameContentPane.setLayout(new GridBagLayout());
            ((GridBagLayout)mainFrameContentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)mainFrameContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)mainFrameContentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)mainFrameContentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- verticalSeparator ----
            verticalSeparator.setOrientation(SwingConstants.VERTICAL);
            mainFrameContentPane.add(verticalSeparator, new GridBagConstraints(5, 0, 1, 10, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- dateSelector ----
            dateSelector.setText("\u65e5\u5386");
            dateSelector.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 18));
            mainFrameContentPane.add(dateSelector, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));
            mainFrameContentPane.add(horizontalSeparator, new GridBagConstraints(0, 3, 20, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== jTreeScrollPane ========
            {
                jTreeScrollPane.setViewportView(inspectorSelectorTree);
            }
            mainFrameContentPane.add(jTreeScrollPane, new GridBagConstraints(0, 3, 5, 7, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //======== jTableScrollPane ========
            {
                jTableScrollPane.setViewportView(dataTable);
            }
            mainFrameContentPane.add(jTableScrollPane, new GridBagConstraints(5, 3, 15, 7, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - danny
    private JFrame mainFrame;
    private JSeparator verticalSeparator;
    private JLabel dateSelector;
    private JSeparator horizontalSeparator;
    private JScrollPane jTreeScrollPane;
    private JTree inspectorSelectorTree;
    private JScrollPane jTableScrollPane;
    private JTable dataTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
