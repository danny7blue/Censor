package ui;
/*
   监测点的增加方法
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


public class TableAdd extends JDialog implements ActionListener {
    private static final Logger LOGGER = Logger.getLogger(TableAdd.class);
    //定义面板所需各组件
    JButton jb1, jb2;
    JLabel jl1, jl2;
    JTextField jtf1, jtf2 ;
    JPanel jp1, jp2;
    Myclass owner;
    Test t1;

    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    static Test dataOper;
    public TableAdd(Frame owner, String title, boolean model) {
        super(owner, title, model);//调用父类构造方法，达到模式对话框效果
        LOGGER.info("进入添加监测点模式...");
        //定义组件
        this.owner = (Myclass) owner;
        jl1 = new JLabel("监测点编号");
        jl2 = new JLabel("监测点名称");


        jtf1 = new JTextField(10);
        jtf2 = new JTextField(10);

        jb1 = new JButton("新建");
        jb1.addActionListener(this);   //为jb1设置监听事件
        jb2 = new JButton("取消");
        jb2.addActionListener(this);   //为jb2设置监听事件
        jp1 = new JPanel(new GridLayout(3, 2, 10, 10));   //设置JPanel为三行两列的网格布局
        jp2 = new JPanel();
        //添加组件
        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl2);
        jp1.add(jtf2);



        jp2.add(jb1);
        jp2.add(jb2);
        this.add(jp1, BorderLayout.NORTH);
        this.add(jp2, BorderLayout.SOUTH);
        //设置窗体
        this.setLocation(500, 200);
        this.setSize(300, 200);
        this.setVisible(true);
        LOGGER.info("显示窗体...");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    //按键监听方法
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jb1) {
            String name =jtf1.getText();
            LOGGER.debug("获得输入的监测点名称"+name);
            String position = jtf2.getText();

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
            ((DefaultMutableTreeNode) owner.getTree().getLastSelectedPathComponent()).add(treenode);
            LOGGER.debug("将新建的测量点名称添加到树模型中");
            owner.getTree().expandPath(new TreePath(((DefaultMutableTreeNode) this.owner.getTree().getLastSelectedPathComponent()).getPath()));
            owner.getTree().updateUI();
            LOGGER.debug("刷新显示树模型");
            this.setVisible(false);
            //获取MonitorInfo的信息
            ResultSet result1 = null;
            try {
                result1 = owner.getDataOper().selectMonitorInfo();
            } catch (SQLException e1) {

            }
            owner.generateDataTable(result1);

        } else if (e.getSource() == jb2) {
            this.setVisible(false);
        }
    }


}
