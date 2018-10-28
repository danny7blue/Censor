package tests;

import database.Test;
import ui.Myclass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Vector;

public class InspectorAddDialog extends JDialog implements ActionListener {
    JButton jb1, jb2;
    JLabel jl1, jl2, jl3;
    JTextField jtf1, jtf2, jtf3;
    JPanel jp1, jp2;
    TestForm testForm;
    static Test dataOper;

    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    public InspectorAddDialog(JFrame owner, String title, boolean model, TestForm testForm) {
        super(owner, title, model);//调用父类构造方法，达到模式对话框效果
        //定义组件
        this.testForm = testForm;
        jl1 = new JLabel("监测点编号");
        jl2 = new JLabel("监测点名称");
        jl3 = new JLabel("监测点位置");

        jtf1 = new JTextField(10);
        jtf2 = new JTextField(10);
        jtf3 = new JTextField(10);
        jb1 = new JButton("新建");
        jb1.addActionListener(this);
        jb2 = new JButton("取消");
        jb2.addActionListener(this);
        jp1 = new JPanel(new GridLayout(3, 2, 10, 10));
        jp2 = new JPanel();

        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl2);
        jp1.add(jtf2);
        jp1.add(jl3);
        jp1.add(jtf3);


        jp2.add(jb1);
        jp2.add(jb2);
        this.add(jp1, BorderLayout.NORTH);
        this.add(jp2, BorderLayout.SOUTH);
        //设置窗体
        this.setLocation(500, 200);
        this.setSize(300, 200);
        this.setVisible(true);

//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
            String id = jtf1.getText();
            String name = jtf2.getText();
            String position = jtf3.getText();
            DefaultMutableTreeNode treenode = new DefaultMutableTreeNode(name);
            try {
                dataOper = new Test();
                dataOper.insertMonitorInfo(Integer.parseInt(id), name, position);
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
        } else if (e.getSource() == jb2) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {

    }
}
