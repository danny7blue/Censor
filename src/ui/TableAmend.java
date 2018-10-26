package ui;
/*
   监测点的修改方法
 */
import log.Log4JTest;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.DefaultMutableTreeNode;


public class TableAmend extends JDialog implements ActionListener {
    private static  final Logger LOGGER = Logger.getLogger(TableAmend.class);
    JButton jb1,jb2;
    JLabel jl1,jl2,jl3;
    JTextField jtf1,jtf2,jtf3;
    JPanel jp1,jp2;
    Myclass owner;
    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    public  TableAmend(Frame owner,String title,boolean model)
    {
        super(owner, title,model);//调用父类构造方法，达到模式对话框效果
        LOGGER.info("进入修改监测点模式...");
        //定义组件
        this.owner = (Myclass)owner;
        jl1=new JLabel("监测点编号");
        jl2=new JLabel("监测点名称");
        jl3=new JLabel("监测点位置");

        jtf1=new JTextField(10);
        jtf2=new JTextField(10);
        jtf3=new JTextField(10);
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
            String msg=jtf2.getText();
            LOGGER.debug("获得输入的监测点名称");
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.owner.getTree().getSelectionPath().getLastPathComponent();
            //改名
            node.setUserObject(msg);
            LOGGER.debug("更改监测点名称成功");
            //刷新
            this.owner.getTree().updateUI();
            this.setVisible(false);
            LOGGER.debug("刷新监测点信息成功");

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


