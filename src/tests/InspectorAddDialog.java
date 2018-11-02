package tests;

import database.Test;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InspectorAddDialog extends JDialog implements ActionListener {
    JButton addBtn, cancelBtn;
    JLabel inspectorNameLabel, inspectorPositionLabel;
    JTextField inspectorNameTextField, inspectorPositionTextField;
    JPanel infoPanel, controlPanel;
    TestForm testForm;
    static Test dataOper;

    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    public InspectorAddDialog(JFrame owner, String title, boolean model, TestForm testForm) {
        super(owner, title, model);//调用父类构造方法，达到模式对话框效果
        //定义组件
        this.testForm = testForm;
        inspectorNameLabel = new JLabel("监测点名称");
        inspectorPositionLabel = new JLabel("监测点位置");

        inspectorNameTextField = new JTextField(10);
        inspectorPositionTextField = new JTextField(10);
        addBtn = new JButton("新建");
        addBtn.addActionListener(this);
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(this);
        infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        controlPanel = new JPanel();

        infoPanel.add(inspectorNameLabel);
        infoPanel.add(inspectorNameTextField);
        infoPanel.add(inspectorPositionLabel);
        infoPanel.add(inspectorPositionTextField);


        controlPanel.add(addBtn);
        controlPanel.add(cancelBtn);
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(controlPanel, BorderLayout.SOUTH);
        //设置窗体
        this.setLocation(500, 200);
        this.setSize(300, 200);
        this.setVisible(true);

//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            String name = inspectorNameTextField.getText();
            String position = inspectorPositionTextField.getText();
            DefaultMutableTreeNode treenode = new DefaultMutableTreeNode(name);
            try {
                dataOper = new Test();
                dataOper.insertMonitorInfo(name, position);
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "输入编号已重复，请重新输入编号.", "提示框", JOptionPane.NO_OPTION);
                return;
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                JOptionPane.showMessageDialog(null, "监测点编号只能输入数字, 请重新输入", "提示框", JOptionPane.NO_OPTION);
                return;
            }
            ((DefaultMutableTreeNode) testForm.getTree().getLastSelectedPathComponent()).add(treenode);
            testForm.getTree().expandPath(new TreePath(((DefaultMutableTreeNode) testForm.getTree().getLastSelectedPathComponent()).getPath()));
            testForm.getTree().updateUI();
            ResultSet result1 = null;
            try {
                result1 = testForm.getDataOper().selectMonitorInfo();
            } catch (SQLException e1) {

            }
            testForm.generateDataTable(result1);
            this.setVisible(false);
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {

    }
}
