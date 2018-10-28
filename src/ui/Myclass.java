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
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Myclass extends JFrame implements ActionListener{



    //北部区域
    JLabel label;
    JPanel jp1;
    JTextField dateTextField;
    //    JButton jb3;
    //西部区域
    JTree inspectorSelectorTree ;
    DefaultTreeModel tm;
    JScrollPane jTreeScrollPanel;

    //中部区域
//    JPanel datePanel;
//    JLabel selectDateLabel;
//    JSeparator verticalSeparator;
//    JSeparator horizontalSeparator ;
    JScrollPane jTableScrollPane ;
    JTable dataTable ;
    //定义三组菜单栏，分别对应根节点，监测点节点和测量点节点
    JPopupMenu rootpopMenu,inspectorpopMenu,measurePointpopMenu;
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
        Vector rowData = getRows(null);
        // 取得数据库的表的表头数据
        Vector columnNames = getHead(null);
        // 新建表格
        tableModel = new DefaultTableModel(rowData,columnNames);
        dataTable.setModel(tableModel);
//        try {
            treeInit();  //初始化树结构
            //初始化右键菜单
            rootpopMenuInit();
            inspectorpopMenuInit2();
            measurePointpopMenuInt();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }

        //把按钮加入JFrame界面
        jp1.add(label);
        jp1.add(dateTextField);
        jp1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        this.add(jp1, BorderLayout.NORTH);
        //对树的滚动面板进行设置
        jTreeScrollPanel.setViewportView(inspectorSelectorTree);
        jTreeScrollPanel.setPreferredSize(new Dimension(120,100));
        this.add(jTreeScrollPanel,BorderLayout.WEST);
        //对数据表格进行格式设置和元素添加
        dataTable.setPreferredScrollableViewportSize(new Dimension(550, 400));
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
    //创建日期显示
    private void setDateSelector()
    {


        Date date=new Date();
        String form=String.format("%tF",date);  //设置日期显示为xxxx-xx-xx型
        dateTextField = new JTextField(form);   //新建显示当前日期的文本框
        label=new JLabel("日期");    //日期显示标签
        //获取日期控件工具类
        Chooser ser = Chooser.getInstance();
        //使用日期控件工具
        ser.register(dateTextField);

        dateTextField.setColumns(12);   //设置文本框宽度
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
    }

    //右键点击根节点导航树的菜单
    private void rootpopMenuInit(){
        rootpopMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("添加");
        addItem.addActionListener(new TreeAddViewMenuEvent(this));
        rootpopMenu.add(addItem);
    }
    //右键点击监测点导航树的菜单
    private void inspectorpopMenuInit2(){
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
    private void measurePointpopMenuInt(){
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
    public void treeInit() {
//        if (jScrollPanel != null) {
//            this.remove(jScrollPanel);
//        }
//        //设置滚动面板的位置和大小
//        jScrollPanel.setBounds(new Rectangle(0, 0, 400, 600));
//        jScrollPanel.setAutoscrolls(true);    //设置面板为可见
//        this.getContentPane().add(jScrollPanel);
//        expandTree();
//        tree.addMouseListener(new TreePopMenuEvent(this));  //给树设置监听事件
//        this.repaint();   //当树的添加命令执行时，刷新面板
        try {
            ArrayList list = new ArrayList();
            list.add("监测点列表");
            String sql = "SELECT * from monitorinfo";

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Object value[] = {rs.getString(1), rs.getString(2)};
                list.add(value);
            }
            Object hierarchy[] = list.toArray();
            DefaultMutableTreeNode root = processHierarchy(hierarchy);

            DefaultTreeModel treeModel = new DefaultTreeModel(root);
            inspectorSelectorTree.setModel(treeModel);
            //添加树的右键监听事件
            inspectorSelectorTree.addMouseListener(new TreePopMenuEvent(this));
            this.repaint();
        } catch (Exception e) {
        }
    }
    //
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
                    node.add(child);//add each created child to root
                    String sql2 = "SELECT TestName from testinfo where TestMonitorID= '" + L1Id[childIndex] + "' ";
                    ResultSet rs3 = stm.executeQuery(sql2);
                    while (rs3.next()) {
                        grandchild = new DefaultMutableTreeNode(rs3.getString("TestName"));
                        child.add(grandchild);//add each grandchild to each child
                    }
                }

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

    //调用数据表的函数
//    private void initComponents() {


//        datePanel = new JPanel();
//        selectDateLabel = new JLabel();
//        verticalSeparator = new JSeparator();
//        horizontalSeparator = new JSeparator();

//        inspectorSelectorTree = new JTree();
//
//        dataTable = new JTable();

//        setDateSelector();

//        //======== mainFrame ========
//        {
//            Container mainFrameContentPane = this.getContentPane();
//            mainFrameContentPane.setLayout(new GridBagLayout());
//            ((GridBagLayout)mainFrameContentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//            ((GridBagLayout)mainFrameContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//            ((GridBagLayout)mainFrameContentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
//            ((GridBagLayout)mainFrameContentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
//
//            //======== datePanel ========
//            {
//                datePanel.setPreferredSize(new Dimension(161, 67));
//
//                // JFormDesigner evaluation mark
////                datePanel.setBorder(new javax.swing.border.CompoundBorder(
////                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
////                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
////                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
////                        java.awt.Color.red), datePanel.getBorder())); datePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});
//
//                datePanel.setLayout(new GridBagLayout());
//                ((GridBagLayout)datePanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
//                ((GridBagLayout)datePanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
//                ((GridBagLayout)datePanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
//                ((GridBagLayout)datePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
//
//                //---- selectDateLabel ----
//                selectDateLabel.setText("\u8bf7\u9009\u62e9\u65e5\u671f:");
//                datePanel.add(selectDateLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
//                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
//                        new Insets(0, 0, 5, 5), 0, 0));
//
//                //---- dateTextField ----
//                dateTextField.setPreferredSize(new Dimension(75, 25));
////                dateTextField.setText("date");
//                datePanel.add(dateTextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
//                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
//                        new Insets(0, 0, 5, 5), 0, 0));
//            }
//            mainFrameContentPane.add(datePanel, new GridBagConstraints(0, 0, 5, 3, 5.0, 3.0,
//                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
//                    new Insets(0, 0, 5, 5), 0, 0));

        //---- verticalSeparator ----
//            verticalSeparator.setOrientation(SwingConstants.VERTICAL);
//            mainFrameContentPane.add(verticalSeparator, new GridBagConstraints(5, 0, 1, 10, 0.0, 0.0,
//                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                    new Insets(0, 0, 0, 5), 0, 0));
//            mainFrameContentPane.add(horizontalSeparator, new GridBagConstraints(0, 3, 20, 1, 0.0, 0.0,
//                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                    new Insets(0, 0, 5, 0), 0, 0));
//
//            //======== jTreeScrollPane ========
//            {
//                jTreeScrollPanel.setViewportView(inspectorSelectorTree);
//            }
//            mainFrameContentPane.add(jTreeScrollPanel, new GridBagConstraints(0, 3, 5, 7, 0.0, 0.0,
//                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                    new Insets(0, 0, 0, 5), 0, 0));
        //======== jTableScrollPane ========
        {

            //---- dataTable ----
//                dataTable.setModel(new DefaultTableModel(
//                        new Object[][] {
//                                {null, null},
//                                {null, null},
//                        },
//                        new String[] {
//                                null, null
//                        }
//                ));
//                dataTable.setPreferredScrollableViewportSize(new Dimension(550, 400));
//                jTableScrollPane.setViewportView(dataTable);
//            }
//            mainFrameContentPane.add(jTableScrollPane, new GridBagConstraints(5, 3, 15, 7, 0.0, 0.0,
//                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                    new Insets(0, 0, 0, 0), 0, 0));
//            this.pack();
//            this.setLocationRelativeTo(this.getOwner());
//        }
   }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents



    /**
     * 完全展开一个JTree
     */
    public void expandTree(){
        jTreeScrollPanel=new JScrollPane();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
        inspectorSelectorTree = new JTree(root);

        inspectorSelectorTree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) { //选中菜单节点的事件
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) inspectorSelectorTree.getLastSelectedPathComponent();
            }
        });
        inspectorSelectorTree.updateUI();
        jTreeScrollPanel.getViewport().add(inspectorSelectorTree);
    }

    public static Test getDataOper(){
        return dataOper;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

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
        if(adaptee.getJudge()==1) {
            TableAmend ta= new TableAmend(adaptee, "修改监测点信息", true);
        }
        else if(adaptee.getJudge()==2)
        {
           PointAmend ta1 = new  PointAmend (adaptee, "修改测量点信息", true);
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
        DefaultMutableTreeNode currentNode=((DefaultMutableTreeNode)path.getLastPathComponent());

        //判断右键点击的是父节点还是子节点，点击根节点时能添加但不能修改，点击父节点时能添加能修改
        //点击子节点时能修改但不能添加
        for(int i=0;i<=currentNode.getLevel();i++) {
            if (currentNode.getLevel() == 0) {
                //判断是否点击当前节点
                if (e.getButton() == 3) //鼠标单击右键时
                {
                    adaptee.getRootpopMenu().show(adaptee.getTree(), e.getX(), e.getY()); //显示菜单栏
                    adaptee.setJudge(0);

                }
            }else if(currentNode.getLevel() == 1){
                if (e.getButton() == 3) //鼠标单击右键时
                {
                    adaptee.inspectorpopMenu().show(adaptee.getTree(), e.getX(), e.getY());
                    adaptee.setJudge(1);
                }else if (e.getButton() == 1) //鼠标单击左键时
                {

                }

            }else if (currentNode.getLevel() ==2)
            {
                if (e.getButton() == 3) {
                    adaptee.measurePointpopMenu().show(adaptee.getTree(), e.getX(), e.getY());
                    adaptee.setJudge(2);
                }else if (e.getButton() == 1) {
                    try {
                        String monitorName = ((DefaultMutableTreeNode)adaptee.getTree().getLastSelectedPathComponent()).getParent().toString();
                        String measureName = adaptee.getTree().getLastSelectedPathComponent().toString();
                        String selectedDate = adaptee.getDateTextField().getText();
//                        System.out.println(monitorName);
//                        System.out.println(measureName);
//                        System.out.println(selectedDate);
                        ResultSet result1 = adaptee.getDataOper().search(monitorName, measureName, selectedDate);
                        // 取得数据库的表的各行数据
                        Vector rowData = adaptee.getRows(result1);
                        // 取得数据库的表的表头数据
                        Vector columnNames = adaptee.getHead(result1);
                        // 新建表格
                        DefaultTableModel tableModel = new DefaultTableModel(rowData,columnNames);
                        adaptee.getDataTable().setModel(tableModel);
                        adaptee.jTableScrollPane.updateUI();
                    } catch (SQLException e1) {
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






