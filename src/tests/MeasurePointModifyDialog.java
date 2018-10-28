package tests;
/*
   测量点的修改方法
 */

import database.Test;
import ui.Myclass;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class MeasurePointModifyDialog extends JDialog implements ActionListener {
    JButton jb1,jb2;
    JLabel jl1,jl2;
    JTextField jtf1,jtf2;
    JPanel jp1,jp2;
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
        jl1=new JLabel("测量点编号");
        jl2=new JLabel("测量点名称");

        jtf1=new JTextField(10);
        jtf2=new JTextField(10);
        jb1=new JButton("修改");
        jb1.addActionListener(this);
        jb2=new JButton("取消");
        jb2.addActionListener(this);
        jp1=new JPanel(new GridLayout(3,2,10,10));
        jp2=new JPanel();
      //把定义的各组件加入对应的显示面板中
        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl2);
        jp1.add(jtf2);
        jp2.add(jb1);
        jp2.add(jb2);
        this.add(jp1,BorderLayout.NORTH);
        this.add(jp2,BorderLayout.SOUTH);
        //设置窗体
        this.setLocation(500,200);
        this.setSize(300,200);
        this.setVisible(true);

//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
            String id = jtf1.getText();
            String name = jtf2.getText();
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
//            try {
//                dataOper = new Test();
//                dataOper.updataTestInfo(Integer.parseInt(id), name, tree.getLastSelectedPathComponent().toString());
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
            //改名
            node.setUserObject(name);
            //刷新
            tree.updateUI();
            this.setVisible(false);


        } else if (e.getSource() == jb2) {
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


