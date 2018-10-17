package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

public class Myclass extends JFrame implements ActionListener {

    //北部区域
    JLabel label;
    JPanel jp1;
    JTextField textField;
    JButton jb3;
    //西部区域

    JPanel jp2;


    //中部区域
    Vector rowData,columnNanes; //rowData存放行数据,columnNames存放列名
    JTable jt=null;
    JScrollPane jsp=null;
    //南部区域
    JPanel jp3;
    JButton jb1,jb2;
    //JComboBox jcb1,jcb2;
    public static void main(String[] args)
    {

        Myclass mc=new Myclass();
    }
    public Myclass()
    {
        /*
        数据表格程序
         */
        columnNanes=new Vector();
        //添加列名
        columnNanes.add("测量点");
        columnNanes.add("采集数据");
        columnNanes.add("时间");

        rowData=new Vector();
        //rowData可以存放多行
        Vector hang1=new Vector();
        hang1.add("测量点1");
        hang1.add("30g");
        hang1.add("12:01");
        rowData.add(hang1);   //把单行加入rowData中
        Vector hang2=new Vector();
        hang2.add("测量点2");
        hang2.add("25.5g");
        hang2.add("12:19");
        rowData.add(hang2);   //把单行加入rowData中

        jt=new JTable(rowData,columnNanes);   //初始化JTable
        jsp=new JScrollPane(jt);    //初始化JScrollPane
//        this.add(jsp);

        /*

         */
        String[] obp={"监测点1","监测点2","监测点3"};
        String[] mp={"测量点1","测量点2","测量点3"};
        jb1=new JButton("添加");
        jb2=new JButton("删除");
        jb3=new JButton("新建");
        jb3.addActionListener(this);


        jp1=new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));
        jp2=new JPanel();
        jp3=new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));//把JPanel定义为流式布局


        JComboBox jcb1=new JComboBox(obp) ;
        jcb1.setSelectedIndex(2);    //设置选中的项的索引
        String s=(String)jcb1.getSelectedItem();   //得到选中项的内容

        JComboBox jcb2=new JComboBox(mp);
        jcb2.setSelectedIndex(2);    //设置选中的项的索引
        String a=(String)jcb2.getSelectedItem();   //得到选中项的内容


        //创建日期显示
        Date date=new Date();
        String form=String.format("%tF",date);
        textField = new JTextField(form);
        label=new JLabel("日期");
        //获取日期控件工具类
        Chooser ser = Chooser.getInstance();
        //使用日期控件工具
        ser.register(textField, null);

        textField.setColumns(10);
        GroupLayout gl_contentPane = new GroupLayout(jp1);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(200)  //水平距离
                                .addComponent(label)
                                .addGap(15)   //label和textfield之间的距离
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(10, Short.MAX_VALUE)
                        )
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(5)   //垂直距离
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(10, Short.MAX_VALUE)  //高度
                        )
        );
//        jp1.setLayout(gl_contentPane);

        //把按钮加入JFrame界面
        this.add(jp1, BorderLayout.NORTH);
        this.add(jsp,BorderLayout.CENTER);
        this.add(jp3,BorderLayout.SOUTH);
        this.add(jp2,BorderLayout.WEST);
        jp1.add(label);
        jp1.add(textField);
        jp1.add(jb3);
        jp2.add(jcb1);
        jp2.add(jcb2);
        jp3.add(jb1);
        jp3.add(jb2);



        this.pack();
//        this.add(jp1,BorderLayout.WEST);



        //定义JFrame界面各参数
        this.setSize(800,600);
        this.setLocation(350,80);
        this.setTitle("我的窗口");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb3)
        {
            TableAdd ta=new TableAdd(this,"添加监测点",true);
        }
    }
}

