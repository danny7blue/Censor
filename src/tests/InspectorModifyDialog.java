package tests;
/*
   监测点的修改方法
 */

import database.Test;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class InspectorModifyDialog extends JDialog implements ActionListener {
    JButton addBtn, cancelBtn;
    JLabel inspectorNameLabel, inspectorPositionLabel;
    JTextField inspectorNameTextField, inspectorPositionTextField;
    JPanel infoPanel, controlPanel;
    JTree tree;
    static Test dataOper;
    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    public InspectorModifyDialog(Frame owner, String title, boolean model, JTree tree)
    {
        super(owner, title,model);//调用父类构造方法，达到模式对话框效果
        this.tree = tree;
        //定义组件
        inspectorNameLabel =new JLabel("监测点名称");
        inspectorPositionLabel =new JLabel("监测点位置");

        inspectorNameTextField =new JTextField(10);
        inspectorPositionTextField =new JTextField(10);
        addBtn =new JButton("修改");
        addBtn.addActionListener(this);
        cancelBtn =new JButton("取消");
        cancelBtn.addActionListener(this);
        infoPanel =new JPanel(new GridLayout(3,2,10,10));
        controlPanel =new JPanel();

        infoPanel.add(inspectorNameLabel);
        infoPanel.add(inspectorNameTextField);
        infoPanel.add(inspectorPositionLabel);
        infoPanel.add(inspectorPositionTextField);


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
            String name = inspectorNameTextField.getText();
            String position = inspectorPositionTextField.getText();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
//            try {
//                dataOper = new Test();
//                dataOper.updataMonitorInfo(Integer.parseInt(id), name, position, node.toString());
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
            //改名
            node.setUserObject(name);
            //刷新
            tree.updateUI();
            this.setVisible(false);


        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }


//    public void updateInspector(String name, String  ){
//        if (name.contains("监测")) {
//            sql.up
//        } else if(name.contains("测量")){
//
//        }
//    }

    public static void main(String[] args)
    {

    }


}


