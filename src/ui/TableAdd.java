package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableAdd extends JDialog implements ActionListener {
    JButton jb1,jb2;
    JLabel jl;
    JTextField jtf;
    JPanel jp1,jp2;
    //owner代表父窗口
    //title代表窗口名
    //model指定的是模式窗口好事非模式窗口
    public  TableAdd(Frame owner,String title,boolean model)
    {
        super(owner, title,model);//调用父类构造方法，达到模式对话框效果
        //定义组件
        jl=new JLabel("监测点名称");
        jtf=new JTextField(10);
        jb1=new JButton("新建");
        jb1.addActionListener(this);
        jb2=new JButton("取消");
        jp1=new JPanel();
        jp2=new JPanel();

        jp1.add(jl);
        jp1.add(jtf);
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
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==jb1)
        {

        }

    }
    public static void main(String[] args)
    {

    }


}
