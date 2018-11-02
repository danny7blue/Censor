package tests;
/*
   测量点的修改方法
 */

import database.Test;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class MeasurePointModifyDialog extends JDialog implements ActionListener {
    JButton addBtn, cancelBtn;
    JLabel measurePointNoLabel, measurePointNameLabel, parameterLabel;
    JTextField measurePointNoTextField, measurePointNameTextField, parameterTextField;
    JPanel infoPanel, controlPanel;
    JTree tree;
    static Test dataOper;
    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    public MeasurePointModifyDialog(Frame owner, String title, boolean model, JTree tree)
    {
        super(owner, title,model);//调用父类构造方法，达到模式对话框效果
        this.tree = tree;
        //定义组件
        measurePointNoLabel =new JLabel("测量点编号");
        measurePointNameLabel =new JLabel("测量点名称");
        parameterLabel = new JLabel("测量点变比");

        measurePointNoTextField =new JTextField(10);
        measurePointNameTextField =new JTextField(10);
        parameterTextField = new JTextField(10);
        addBtn =new JButton("修改");
        addBtn.addActionListener(this);
        cancelBtn =new JButton("取消");
        cancelBtn.addActionListener(this);
        infoPanel =new JPanel(new GridLayout(3,2,10,10));
        controlPanel =new JPanel();
      //把定义的各组件加入对应的显示面板中
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
            DefaultMutableTreeNode node = ((DefaultMutableTreeNode)tree.getLastSelectedPathComponent());
            try {
                dataOper = new Test();
                dataOper.updateMeasurePointInfo(Integer.parseInt(id), name, Float.parseFloat(parameter), node.getParent().toString(), node.toString());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            //改名
            node.setUserObject(name);
            //刷新
            tree.updateUI();
            this.setVisible(false);


        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }
}


