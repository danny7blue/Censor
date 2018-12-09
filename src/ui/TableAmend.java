package ui;
/*
   监测点的修改方法
 */
import database.Test;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.tree.DefaultMutableTreeNode;


public class TableAmend extends JDialog implements ActionListener {
    private static  final Logger LOGGER = Logger.getLogger(TableAmend.class);
    JButton jb1,jb2;
    JLabel jl1,jl2;
    JTextField jtf1,jtf2;
    JPanel jp1,jp2;
    Myclass owner;
    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    static Test dataOper;
    public  TableAmend(Frame owner,String title,boolean model)
    {
        super(owner, title,model);//调用父类构造方法，达到模式对话框效果
        //定义组件
        this.owner = (Myclass)owner;
        jl1=new JLabel("监测点名称");
        jl2=new JLabel("监测点位置");
        //定义当前节点
        DefaultMutableTreeNode currentNode=((DefaultMutableTreeNode)this.owner.getTree().getLastSelectedPathComponent());
        String moniterName=currentNode.toString();
        jtf1=new JTextField(moniterName,10);//获得文本框中监测点名称的原始值

        try {
            dataOper = new Test();
            String moniterPosition=dataOper.returnMonitor(moniterName);
            jtf2=new JTextField(moniterPosition,10);//将监测点位置原始值反回
       } catch (SQLException e)
        {
            e.printStackTrace();
        }


//        String moniterPosition=currentNode.


        jb1=new JButton("修改");
        jb1.addActionListener(this);
        jb2=new JButton("取消");
        jb2.addActionListener(this);
        jp1=new JPanel(new GridLayout(3,2,10,10));
        jp2=new JPanel();

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
            String name = jtf1.getText();
            LOGGER.debug("获得输入的监测点名称"+name);
            String position = jtf2.getText();

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.owner.getTree().getSelectionPath().getLastPathComponent();

            try {
//                dataOper = new Test();
                dataOper.updateMonitorInfo(name, position, node.toString());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }//改名
            node.setUserObject(name);

            //刷新
            this.owner.getTree().updateUI();
            this.setVisible(false);
            LOGGER.debug("更改监测点名称成功");

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


