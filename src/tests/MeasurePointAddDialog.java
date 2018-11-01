package tests;

import database.Test;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MeasurePointAddDialog extends JDialog implements ActionListener {
    JButton addBtn, cancelBtn;
    JLabel measurePointNoLabel, measurePointNameLabel, parameterLabel;
    JTextField measurePointNoTextField, measurePointNameTextField, parameterTextField;
    JPanel infoPanel, controlPanel;
    TestForm testForm;
    static Test dataOper;
    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    public MeasurePointAddDialog(JFrame owner, String title, boolean model, TestForm testForm)
    {
        super(owner, title,model);//调用父类构造方法，达到模式对话框效果
        this.testForm = testForm;
        //定义组件
        measurePointNoLabel =new JLabel("测量点编号");
        measurePointNameLabel =new JLabel("测量点名称");
        parameterLabel = new JLabel("测量点变比");

        measurePointNoTextField =new JTextField(10);
        measurePointNameTextField =new JTextField(10);
        parameterTextField = new JTextField(10);
        addBtn =new JButton("新建");
        addBtn.addActionListener(this);
        cancelBtn =new JButton("取消");
        cancelBtn.addActionListener(this);
        infoPanel =new JPanel(new GridLayout(3,2,10,10));
        controlPanel =new JPanel();

        infoPanel.add(measurePointNoLabel);
        infoPanel.add(measurePointNoTextField);
        infoPanel.add(measurePointNameLabel);
        infoPanel.add(measurePointNameTextField);
        infoPanel.add(parameterLabel);
        infoPanel.add(parameterTextField);


        controlPanel.add(addBtn);
        controlPanel.add(cancelBtn);
        this.add(infoPanel,BorderLayout.NORTH);
        this.add(controlPanel,BorderLayout.SOUTH);
        //设置窗体
        this.setLocation(500,200);
        this.setSize(300,200);
        this.setVisible(true);

//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            String id = measurePointNoTextField.getText();
            String name = measurePointNameTextField.getText();
            String parameter = parameterTextField.getText();
            DefaultMutableTreeNode treenode = new DefaultMutableTreeNode(name);
            try {
                dataOper = new Test();
                dataOper.insertTestInfo(Integer.parseInt(id), name, testForm.getTree().getLastSelectedPathComponent().toString());
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "输入编号已重复，请重新输入编号.", "提示框", JOptionPane.NO_OPTION);
                return;
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                JOptionPane.showMessageDialog(null, "测量点编号只能输入数字, 请重新输入", "提示框", JOptionPane.NO_OPTION);
                return;
            }
            ((DefaultMutableTreeNode) testForm.getTree().getLastSelectedPathComponent()).add(treenode);
            testForm.getTree().expandPath(new TreePath(((DefaultMutableTreeNode) testForm.getTree().getLastSelectedPathComponent()).getPath()));
            testForm.getTree().updateUI();
            this.setVisible(false);
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }
}


