package ui;

/*
   测量点的增加方法
 */
import database.Test;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class PointAdd extends JDialog implements ActionListener {
    private static final Logger LOGGER = Logger.getLogger(PointAdd.class);
    JButton jb1,jb2;
    JLabel jl1,jl2,jl3;
    JTextField jtf1,jtf2,jtf3;
    JPanel jp1,jp2;
    Myclass owner;
    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    static Test dataOper;
    public  PointAdd(Frame owner,String title,boolean model)
    {
        super(owner, title,model);//调用父类构造方法，达到模式对话框效果
        //定义组件
        this.owner = (Myclass)owner;
        jl1=new JLabel("测量点编号");
        jl2=new JLabel("测量点名称");
        jl3=new JLabel("测量点变比");

        jtf1=new JTextField(10);
        jtf2=new JTextField(10);
        jtf3=new JTextField(10);
        jb1=new JButton("新建");
        jb1.addActionListener(this);
        jb2=new JButton("取消");
        jb2.addActionListener(this);
        jp1=new JPanel(new GridLayout(3,2,20,20));
        jp2=new JPanel();

        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl2);
        jp1.add(jtf2);
        jp1.add(jl3);
        jp1.add(jtf3);


        jp2.add(jb1);
        jp2.add(jb2);
        this.add(jp1,BorderLayout.NORTH);
        this.add(jp2,BorderLayout.SOUTH);
        //设置窗体
        this.setLocation(500,200);
        this.setSize(300,200);
        this.setVisible(true);
        LOGGER.info("显示窗体...");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
//            String msg=jtf2.getText();   //获得输入的测量点名称
            String id = jtf1.getText();
            String name = jtf2.getText();
            String parameter = jtf3.getText();
            String monitorName = owner.getTree().getLastSelectedPathComponent().toString();
            try {
                dataOper = new Test();
                dataOper.insertMeasurePointInfo(Integer.parseInt(id), name, Float.parseFloat(parameter), monitorName);
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "输入编号已重复，请重新输入编号.", "提示框", JOptionPane.NO_OPTION);
                return;
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                JOptionPane.showMessageDialog(null, "测量点编号或变比输入格式有误, 请重新输入", "提示框", JOptionPane.NO_OPTION);
                return;
            }
            //获取monitorName的值
            ResultSet result1 = null;
            try {
                result1 = owner.getDataOper().selectMeasurePointInfo(monitorName);
            } catch (SQLException e1) {

            }
            owner.generateDataTable(result1);
            LOGGER.debug("获得输入的测量点名称"+name);
            DefaultMutableTreeNode treenode = new DefaultMutableTreeNode(name);  //新建树节点存放测量点名称
            ((DefaultMutableTreeNode) owner.getTree().getLastSelectedPathComponent()).add(treenode);//添加该树节点到树模型中
            owner.getTree().expandPath(new TreePath(((DefaultMutableTreeNode)
                    this.owner.getTree().getLastSelectedPathComponent()).getPath()));
            owner.getTree().updateUI();    //刷新以显示该树模型
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


