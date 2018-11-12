package ui;

import database.Test;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Myclass extends JFrame implements ActionListener{


    //北部区域
    JLabel titlelabel;
    JLabel label;
    JPanel jp1;
    JTextField dateTextField;
    JButton  portnumAmend;
    //    JButton jb3;
    //西部区域
    JTree inspectorSelectorTree ;
    DefaultTreeModel tm;
    JScrollPane jTreeScrollPanel;

    //中部区域
    JScrollPane jTableScrollPane ;
    JTable dataTable ;
    Vector rowData,columnNames;
    //定义三组菜单栏，分别对应根节点，监测点节点和测量点节点
    private JPopupMenu rootpopMenu;
    private JPopupMenu inspectorpopMenu;
    private JPopupMenu measurePointpopMenu;
    int judge=1;   //设置一个全局变量
    boolean  x=true;  //设置全局变量x判断父节点下子节点数是否为0
    private static Connection con = null;
    private static Statement stm = null;
//    //默认显示的表格
    DefaultTableModel tableModel;
//    DateSelector ser;
    static Test dataOper;

    static {

        dataOper = new Test();
        con = dataOper.getConn();
        try {
            stm = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setJudge(int judge)
    {
        this.judge=judge;
    }
    public  int getJudge()
    {
        return judge;
    }
    //设置一个滚动面板
    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jTreeScrollPanel= jScrollPane1;
    }
    //创建根节点的popmenu的get和set方法
    public JPopupMenu getRootpopMenu() {
        return rootpopMenu;

    }
    //创建父节点的popmenu的get和set方法
    public JPopupMenu inspectorpopMenu() {
        return inspectorpopMenu;
    }

    //创建子节点的popmenu的get和set方法
    public JPopupMenu measurePointpopMenu() {
        return measurePointpopMenu;
    }

    public Myclass() {
        /*
        数据表格程序
         */
//        initComponents(); //初始化
        // 取得数据库的表的各行数据
        rowData=new Vector();
        columnNames=new Vector();
        rowData = getRows(null);
        // 取得数据库的表的表头数据
        columnNames = getHead(null);
        // 新建表格
        tableModel = new DefaultTableModel(rowData,columnNames);
        dataTable=new JTable();
        dataTable.setModel(tableModel);
        try {
            //初始化树结构
            init_tree();
            //初始化右键菜单
            rootpopMenuInit();
            inspectorpopMenuInit();
            measurePointpopMenuInit();
        }catch (Exception exception)
        {
            exception.printStackTrace();
        }
        //把按钮加入JFrame界面
        // 创建日期显示
        Date date=new Date();
        String form=String.format("%tF",date);  //设置日期显示为xxxx-xx-xx型
        dateTextField = new JTextField(form);   //新建显示当前日期的文本框
        jp1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        label=new JLabel("日期");    //日期显示标签
        titlelabel=new JLabel("电 研 所 数 据 采 集 终 端");   //设置标题
        titlelabel.setFont(new Font("宋体",Font.PLAIN,30));
        titlelabel.setLocation(50,10);
        portnumAmend=new JButton("修改端口号");    //修改端口号按钮
        portnumAmend.addActionListener(this);
        //获取日期控件工具类
        Chooser ser = Chooser.getInstance();
        //使用日期控件工具
        ser.register(dateTextField);

        dateTextField.setColumns(10);
        GroupLayout gl_contentPane = new GroupLayout(jp1);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(200)  //水平距离
                                .addComponent(label)
                                .addGap(15)   //label和textfield之间的距离
                                .addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(10, Short.MAX_VALUE)
                        )
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(5)   //垂直距离
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(10, Short.MAX_VALUE)  //高度
                        )
        );
        jp1.add(titlelabel);
        jp1.add(label);
        jp1.add(dateTextField);
        jp1.add(portnumAmend);
        this.add(jp1, BorderLayout.NORTH);
        //对树的滚动面板进行设置

        jTreeScrollPanel=new JScrollPane();
        jTreeScrollPanel.setViewportView(inspectorSelectorTree);
        jTreeScrollPanel.setPreferredSize(new Dimension(120,100));
        this.add(jTreeScrollPanel,BorderLayout.WEST);
       // 对数据表格进行格式设置和元素添加
        dataTable.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null},
                        {null, null},
                },
                new String[]{
                        null, null
                }
        ));
        dataTable.setPreferredScrollableViewportSize(new Dimension(550, 400));
        jTableScrollPane=new JScrollPane();
        jTableScrollPane.setViewportView(dataTable);
        this.add(jTableScrollPane,BorderLayout.CENTER);
        this.pack();

        //定义JFrame界面各参数
        this.setSize(800,600);
        this.setLocation(350,80);
        this.setTitle("我的窗口");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }




    //右键点击根节点导航树的菜单
    private void rootpopMenuInit(){
        rootpopMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("添加");
        addItem.addActionListener(new TreeAddViewMenuEvent(this));
        rootpopMenu.add(addItem);
    }
    //右键点击监测点导航树的菜单
    private void inspectorpopMenuInit(){
        inspectorpopMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("添加");
        addItem.addActionListener(new TreeAddViewMenuEvent(this));
        JMenuItem delItem = new JMenuItem("删除");
        delItem.addActionListener(new TreeDeleteViewMenuEvent(this));
        JMenuItem modifyItem = new JMenuItem("修改");
        modifyItem.addActionListener(new TreeModifyViewMenuEvent(this));
        inspectorpopMenu.add(addItem);
        inspectorpopMenu.add(delItem);
        inspectorpopMenu.add(modifyItem);
    }
    //右键点击测量点导航树的菜单
    private void measurePointpopMenuInit(){
        measurePointpopMenu= new JPopupMenu();
//        JMenuItem addItem = new JMenuItem("添加");
//        addItem.addActionListener(new TreeAddViewMenuEvent(this));
        JMenuItem delItem = new JMenuItem("删除");
        delItem.addActionListener(new TreeDeleteViewMenuEvent(this));
        JMenuItem modifyItem = new JMenuItem("修改");
        modifyItem.addActionListener(new TreeModifyViewMenuEvent(this));
        measurePointpopMenu.add(delItem);
        measurePointpopMenu.add(modifyItem);
    }

    //初始化景点分类树
    public final void init_tree() {

        try {
//            expandTree();
            ArrayList list = new ArrayList();
            list.add("监测点列表");
            String sql = "SELECT * from monitorinfo";

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Object value[] = {rs.getString(1), rs.getString(2)};
                list.add(value);
            }
            Object hierarchy[] = list.toArray();
            DefaultMutableTreeNode root = processHierarchy(hierarchy);     //调用processHierarchy方法

            DefaultTreeModel treeModel = new DefaultTreeModel(root);
            inspectorSelectorTree.setModel(treeModel);

            //添加树的右键监听事件
            inspectorSelectorTree.addMouseListener(new TreePopMenuEvent(this));
            this.repaint();
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("CallToThreadDumpStack")
    public DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
        try {

            int ctrow = 0;
            int i = 0;
            try {
                String sql = "SELECT MonitorID, MonitorName from monitorinfo";
                ResultSet rs = stm.executeQuery(sql);

                while (rs.next()) {
                    ctrow = rs.getRow();
                }
                String L1Nam[] = new String[ctrow];
                String L1Id[] = new String[ctrow];
                ResultSet rs1 = stm.executeQuery(sql);
                while (rs1.next()) {
                    L1Nam[i] = rs1.getString("MonitorName");
                    L1Id[i] = rs1.getString("MonitorID");
                    i++;
                }
                DefaultMutableTreeNode child, grandchild;
                for (int childIndex = 0; childIndex < L1Nam.length; childIndex++) {
                    child = new DefaultMutableTreeNode(L1Nam[childIndex]);
                    node.add(child);      //add each created child to root
                    String sql2 = "SELECT MeasurePointName from measurepointinfo where MonitorID= '" + L1Id[childIndex] + "' ";
                    ResultSet rs3 = stm.executeQuery(sql2);
                    while (rs3.next()) {
                        grandchild = new DefaultMutableTreeNode(rs3.getString("MeasurePointName"));
                        child.add(grandchild);//add each grandchild to each child

                    }
                }
                expandTree(); //显示树结构，若缺少该语句，将不能返回以显示树结构

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception e) {
        }

        return (node);
    }



    public  JTree getTree(){
        return inspectorSelectorTree;
    }
    public JTable getDataTable(){
        return dataTable;
    }
    //获得传回的日期标签的值
    public  JTextField getDateTextField(){
        return dateTextField;
    }
    public static Test getDataOper(){
        return dataOper;
    }



    /**
     * 完全展开一个JTree
     */
    public void expandTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
//        root.se
        inspectorSelectorTree = new JTree(root);
        inspectorSelectorTree.addTreeSelectionListener(new TreeSelectionListener() {

         public void valueChanged(TreeSelectionEvent e) { //选中菜单节点的事件
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) inspectorSelectorTree.getLastSelectedPathComponent();
            }
        });
        inspectorSelectorTree.updateUI();
        jTreeScrollPanel.getViewport().add(inspectorSelectorTree);
    }

//添加端口号的修改事件
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==portnumAmend)
        {
            PortnumAmend pa=new PortnumAmend(this,"修改端口号",true);
        }
    }
    // 得到数据库表数据
    public static Vector getRows(ResultSet rs){
        Vector rows = null;
        Vector columnHeads = null;

        try {
//            PreparedStatement preparedStatement = null;
//            preparedStatement = con.prepareStatement("select * from price_data");
//            ResultSet result1 = preparedStatement.executeQuery();

//            ResultSet result1 = dataOper.search(((DefaultMutableTreeNode)getTree().getLastSelectedPathComponent()).getParent().toString(), getTree().getLastSelectedPathComponent().toString(), getDateTextField().toString());
            rows = new Vector();
            if (rs != null) {

                ResultSetMetaData rsmd = rs.getMetaData();

                while(rs.next()){
                    rows.addElement(getNextRow(rs,rsmd));
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("未成功打开数据库。");
            e.printStackTrace();
        }
        return rows;
    }


    // 得到数据库表头
    public static Vector getHead(ResultSet rs){
        PreparedStatement preparedStatement = null;

        Vector columnHeads = null;

        try {
//            preparedStatement = con.prepareStatement("select * from price_data");
//            ResultSet result1 = preparedStatement.executeQuery();

//            ResultSet result1 = dataOper.search(((DefaultMutableTreeNode)getTree().getLastSelectedPathComponent()).getParent().toString(), getTree().getLastSelectedPathComponent().toString(), getDateTextField().toString());

            columnHeads = new Vector();
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for(int i = 1; i <= rsmd.getColumnCount(); i++)
                    columnHeads.addElement(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("未成功打开数据库。");
            e.printStackTrace();
        }
        return columnHeads;
    }

    // 得到数据库中下一行数据
    private static Vector getNextRow(ResultSet rs,ResultSetMetaData rsmd) throws SQLException{
        Vector currentRow = new Vector();
        for(int i = 1; i <= rsmd.getColumnCount(); i++){
            currentRow.addElement(rs.getString(i));
        }
        return currentRow;
    }
    //从数据库获得监测点信息,并填入树结构中
        public void generateDataTable(ResultSet rs) {
            // 取得数据库的表的各行数据
            Vector rowData = getRows(rs);
            // 取得数据库的表的表头数据
            Vector columnNames = getHead(rs);
            // 新建表格
            DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);
            getDataTable().setModel(tableModel);
            this.getTree().updateUI();
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
        if(adaptee.getJudge()==0) {
            TableAdd ta = new TableAdd(adaptee, "添加监测点", true);
        }
        else if(adaptee.getJudge()==1)
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

        int conform = JOptionPane.showConfirmDialog(null, "是否确认删除？", "删除景点确认", JOptionPane.YES_NO_OPTION);
        if (conform == JOptionPane.YES_OPTION) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) this.adaptee.getTree().getLastSelectedPathComponent();
            String name = currentNode.toString();
            try {
                if (adaptee.getJudge()== 1) {
//                    ResultSet rs = adaptee.getDataOper().selectMeasurePointInfo(name);
                    if (adaptee.x==false) {
                        adaptee.getDataOper().deleteMonitorInfo(name);
                        currentNode.removeFromParent();
                        this.adaptee.getTree().updateUI();
                    }
                    else {
                        //父节点下还有未删除的子节点，弹出“不能删除”的提示框
                        JOptionPane.showMessageDialog(null,"该检测点下还有测量点, 请先删除所有测量点!","提示框",JOptionPane.NO_OPTION);
                    }
                } else if (adaptee.getJudge()== 2) {
                    adaptee.getDataOper().deleteMeasurePointInfo(currentNode.getParent().toString(), name);
                    currentNode.removeFromParent();
                    this.adaptee.getTree().updateUI();
                }
            } catch (SQLException e1) {

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
        if(adaptee.getJudge()==1) {
            TableAmend ta= new TableAmend(adaptee, "修改监测点信息", true);
        }
        else if(adaptee.getJudge()==2)
        {
           PointAmend ta1 = new  PointAmend (adaptee, "修改测量点信息", true,adaptee.getTree());
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
        adaptee.getTree().setSelectionPath(path);
        //判断当前节点
        DefaultMutableTreeNode currentNode=((DefaultMutableTreeNode)adaptee.getTree().getLastSelectedPathComponent());

        //判断右键点击的是父节点还是子节点，点击根节点时能添加但不能修改，点击父节点时能添加能修改
        //点击子节点时能修改但不能添加
        for(int i=0;i<=currentNode.getLevel();i++) {
            if (currentNode.getLevel() == 0) {
                //判断是否点击当前节点
                if (e.getButton() == 3) //鼠标单击右键时
                {
                    adaptee.getRootpopMenu().show(adaptee.getTree(), e.getX(), e.getY()); //显示菜单栏
                    adaptee.setJudge(0);

                }else if (e.getButton() == 1) //鼠标单击左键时
                {
                    try {
                        //显示监测点列表
                        ResultSet result1 = adaptee.getDataOper().selectMonitorInfo();
                        adaptee.generateDataTable(result1);
                    }catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }

                }
            }else if(currentNode.getLevel() == 1){
                if (e.getButton() == 3) //鼠标单击右键时
                {
                    adaptee.inspectorpopMenu().show(adaptee.getTree(), e.getX(), e.getY());
                    adaptee.setJudge(1);
                }else if (e.getButton() == 1) //鼠标单击左键时
                {
                    String monitorName = currentNode.toString();
                    try {
                        ResultSet result1 = adaptee.getDataOper().selectMeasurePointInfo(monitorName);
                        adaptee.generateDataTable(result1);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }

            }else if (currentNode.getLevel() ==2)
            {
                if (e.getButton() == 3) {
                    adaptee.measurePointpopMenu().show(adaptee.getTree(), e.getX(), e.getY());
                    adaptee.setJudge(2);
                }else if (e.getButton() == 1) {
                    try {
                        String monitorName = currentNode.getParent().toString();
                        String measureName = adaptee.getTree().getLastSelectedPathComponent().toString();
                        String selectedDate = adaptee.getDateTextField().getText();
                        ResultSet result1 = adaptee.dataOper.search(monitorName,measureName,selectedDate);
                        adaptee.generateDataTable(result1);
                    }catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }


            }

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
            Myclass  m1 = new Myclass ();
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






