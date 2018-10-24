package ui;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Myclass extends JFrame implements ActionListener{

    //北部区域
    JLabel label;
    JPanel jp1;
    JTextField textField;
    //    JButton jb3;
    //西部区域
    JTree tree;
    DefaultTreeModel tm;
    JScrollPane jScrollPanel;

    //中部区域
    Vector rowData,columnNanes; //rowData存放行数据,columnNames存放列名
    JTable jt=null;
    JScrollPane jsp=null;
    JPopupMenu popMenu;

    int judge=0;   //设置一个全局变量
    boolean  x=true;  //设置全局变量x判断父节点下子节点数是否为0

    public JScrollPane getjScrollPane1() {
        return jScrollPanel;
    }

    //设置一个滚动面板
    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPanel = jScrollPane1;
    }

    public JPopupMenu getPopMenu() {
        return popMenu;
    }

    public void setPopMenu(JPopupMenu popMenu) {
        this.popMenu = popMenu;
    }

    public Myclass() {
        /*
        数据表格程序
         */
        columnNanes = new Vector();
        //添加列名
        columnNanes.add("测量点");
        columnNanes.add("采集数据");
        columnNanes.add("时间");

        rowData = new Vector();
        //rowData可以存放多行
        Vector hang1 = new Vector();
        hang1.add("测量点1");
        hang1.add("30g");
        hang1.add("12:01");
        rowData.add(hang1);   //把单行加入rowData中
        Vector hang2 = new Vector();
        hang2.add("测量点2");
        hang2.add("25.5g");
        hang2.add("12:19");
        rowData.add(hang2);   //把单行加入rowData中

        jt = new JTable(rowData, columnNanes);   //初始化JTable
        jsp = new JScrollPane(jt);    //初始化JScrollPane
        jScrollPanel = new JScrollPane(); //创建滚动面板
//        jb3 = new JButton("新建");
//        jb3.addActionListener(this);


        jp1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        /*

         */
        try {
            treeInit();
            popMenuInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }



        //创建日期显示
        Date date=new Date();
        String form=String.format("%tF",date);  //设置日期显示为xxxx-xx-xx型
        textField = new JTextField(form);   //新建显示当前日期的文本框
        label=new JLabel("日期");    //日期显示标签
        //获取日期控件工具类
        Chooser ser = Chooser.getInstance();
        //使用日期控件工具
        ser.register(textField);

        textField.setColumns(12);   //设置文本框宽度
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


        //把按钮加入JFrame界面
        this.add(jp1, BorderLayout.NORTH);
        this.add(jsp,BorderLayout.CENTER);
        this.add(jScrollPanel,BorderLayout.WEST);
        jp1.add(label);
        jp1.add(textField);
        jScrollPanel.setPreferredSize(new Dimension(120,100));
        this.pack();

        //定义JFrame界面各参数
        this.setSize(800,600);
        this.setLocation(350,80);
        this.setTitle("我的窗口");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    //初始化景点分类树
    public void treeInit() {
        if (jScrollPanel != null) {
            this.remove(jScrollPanel);
        }
        //设置滚动面板的位置和大小
        jScrollPanel.setBounds(new Rectangle(0, 0, 400, 600));
        jScrollPanel.setAutoscrolls(true);    //设置面板为可见
        this.getContentPane().add(jScrollPanel);
        expandTree();
        tree.addMouseListener(new TreePopMenuEvent(this));  //给树设置监听事件
        this.repaint();   //当树的添加命令执行时，刷新面板
    }

    //右键点击分类导航树的菜单
    private void popMenuInit(){
        popMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("添加");
        addItem.addActionListener(new TreeAddViewMenuEvent(this));
        JMenuItem delItem = new JMenuItem("删除");
        delItem.addActionListener(new TreeDeleteViewMenuEvent(this));
        JMenuItem modifyItem = new JMenuItem("修改");
        modifyItem.addActionListener(new TreeModifyViewMenuEvent(this));
        popMenu.add(addItem);
        popMenu.add(delItem);
        popMenu.add(modifyItem);
    }

    /**
     * 完全展开一个JTree
     */
    public void expandTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("");//新建根节点
        tree = new JTree(root);

        tree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) { //选中菜单节点的事件
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            }
        });
        tree.updateUI();
        jScrollPanel.getViewport().add(tree);
    }
    public JTree getTree(){
        return tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}


/**
 * popmenu点击右键的增加处理
 */
class TreeAddViewMenuEvent implements ActionListener {

    private Myclass  adaptee;

    public TreeAddViewMenuEvent(Myclass  adaptee) {
        this.adaptee = adaptee;
    }
    //通过判断全局变量judge的值，触发不同页面
    public void actionPerformed(ActionEvent e) {
        //judge为0时，弹出监测点添加页面；
        //judge为1时，弹出测量点添加页面；
        if(adaptee.judge==0) {
            TableAdd ta = new TableAdd(adaptee, "添加监测点", true);
        }
        else if(adaptee.judge==1)
        {
            PointAdd ta1 = new  PointAdd(adaptee, "添加测量点", true);
        }

    }
}

/**
 * popmenu点击右键的删除处理
 */
class TreeDeleteViewMenuEvent implements ActionListener {

    private Myclass  adaptee;

    public TreeDeleteViewMenuEvent(Myclass adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        //全局变量x为真时，父节点下还有未删除的子节点，弹出“不能删除”的提示框
        if (adaptee.x==true) {
           JOptionPane.showMessageDialog(null,"不能删除该节点!","提示框",JOptionPane.NO_OPTION);

        }else {
            //弹出是否确认删除提示框
            int conform = JOptionPane.showConfirmDialog(null, "是否确认删除？", "删除节点确认", JOptionPane.YES_NO_OPTION);
            //点击删除按钮，删除该节点
            if (conform == JOptionPane.YES_OPTION) {
                ((DefaultMutableTreeNode) this.adaptee.getTree().getLastSelectedPathComponent()).removeFromParent();
                this.adaptee.getTree().updateUI();

            }
        }

    }
}


/**
 * popmenu点击右键的修改处理
 */
class TreeModifyViewMenuEvent implements ActionListener {

    private Myclass adaptee;

    public TreeModifyViewMenuEvent(Myclass adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
//
        if(adaptee.judge==1) {
            TableAmend ta= new TableAmend(adaptee, "修改监测点信息", true);
        }
        else if(adaptee.judge==2)
        {
            PointAmend  ta1 = new  PointAmend (adaptee, "修改测量点信息", true);
        }
    }
}

/**
 * 菜单点击右键的事件处理
 */
class TreePopMenuEvent implements MouseListener {

    private Myclass adaptee;

    public TreePopMenuEvent(Myclass  adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        TreePath path = adaptee.getTree().getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用
        if (path == null) {
            return;
        }
        //判断当前节点
        DefaultMutableTreeNode currentNode=((DefaultMutableTreeNode)path.getLastPathComponent());

        //判断右键点击的是父节点还是子节点，点击根节点时能添加但不能修改，点击父节点时能添加能修改
        //点击子节点时能修改但不能添加
        for(int i=0;i<=currentNode.getLevel();i++) {
            if (currentNode.getLevel() == 0) {
                //判断是否点击当前节点
                if (e.getButton() == 3) {
                    adaptee.getPopMenu().show(adaptee.getTree(), e.getX(), e.getY()); //显示菜单栏
                    adaptee.judge=0;
                }
            }else if(currentNode.getLevel() == 1){
                if (e.getButton() == 3) {
                    adaptee.getPopMenu().show(adaptee.getTree(), e.getX(), e.getY());
                    adaptee.judge=1;


                }

           }else if (currentNode.getLevel() ==2)
            {
                adaptee.getPopMenu().show(adaptee.getTree(), e.getX(), e.getY());
                adaptee.judge=2;
            }
            //当监测点下还有未删除的测量点，则不能删除该监测点
            if (currentNode.getChildCount()!=0)
            {
                adaptee.x=true;
            }else
            {
                adaptee.x=false;
            }
        }
        adaptee.getTree().setSelectionPath(path);
        if (e.getButton() == 3) {
            adaptee.getPopMenu().show(adaptee.getTree(), e.getX(), e.getY());
        }

    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    public static void main(String[] args) {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            Myclass  userframe = new Myclass ();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Myclass .class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Myclass .class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Myclass .class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Myclass .class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}






