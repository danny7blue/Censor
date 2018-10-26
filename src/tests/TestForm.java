/*
 * Created by JFormDesigner on Mon Oct 15 14:54:46 CST 2018
 */

package tests;

import database.Test;
import util.DateSelector;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * @author 305027244
 */
public class TestForm extends JPanel {
    private static Connection con = null;
    private static Statement stm = null;
    //默认显示的表格
    DefaultTableModel tableModel;
    DateSelector ser;
    static Test dataOper;
    int level = 0;

    static {
//        con = DatabaseCon.getConnection();
        dataOper = new Test();
        con = dataOper.getConn();
        try {
            stm = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TestForm() {
        initComponents();
        // 取得数据库的表的各行数据
        Vector rowData = getRows(null);
        // 取得数据库的表的表头数据
        Vector columnNames = getHead(null);
        // 新建表格
        tableModel = new DefaultTableModel(rowData,columnNames);
        dataTable.setModel(tableModel);
        //初始化树结构
        init_tree();
        //初始化树的右键菜单
        rootPopMenuInit();
        inspectorPopMenuInit();
        measurePointPopMenuInit();
    }

    //根节点右键点击分类导航树的菜单
    private void rootPopMenuInit() {
        rootRightClickPopMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("添加监测点");
        addItem.addActionListener(new TreeAddViewMenuEvent(this));
        rootRightClickPopMenu.add(addItem);
    }

    //监测点节点右键点击分类导航树的菜单
    private void inspectorPopMenuInit() {
        inspectorRightClickPopMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("添加测量点");
        addItem.addActionListener(new TreeAddViewMenuEvent(this));
        JMenuItem delItem = new JMenuItem("删除本监测点");
        delItem.addActionListener(new TreeDeleteViewMenuEvent(this));
        JMenuItem modifyItem = new JMenuItem("修改本监测点");
        modifyItem.addActionListener(new TreeModifyViewMenuEvent(this));
        inspectorRightClickPopMenu.add(addItem);
        inspectorRightClickPopMenu.add(delItem);
        inspectorRightClickPopMenu.add(modifyItem);
    }

    //测量点右键点击分类导航树的菜单
    private void measurePointPopMenuInit() {
        measureRightClickPopMenu = new JPopupMenu();
        JMenuItem delItem = new JMenuItem("删除本测量点");
        delItem.addActionListener(new TreeDeleteViewMenuEvent(this));
        JMenuItem modifyItem = new JMenuItem("修改本测量点");
        modifyItem.addActionListener(new TreeModifyViewMenuEvent(this));
        measureRightClickPopMenu.add(delItem);
        measureRightClickPopMenu.add(modifyItem);
    }

    /**
     * 完全展开一个JTree
     */
    public void expandTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("根节点");
        inspectorSelectorTree = new JTree(root);

        inspectorSelectorTree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) { //选中菜单节点的事件
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) inspectorSelectorTree.getLastSelectedPathComponent();
            }
        });
        inspectorSelectorTree.updateUI();
        jTreeScrollPane.getViewport().add(inspectorSelectorTree);
    }

    public static Test getDataOper(){
        return dataOper;
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

    private void setDateSelector() {
        Date date=new Date();
        String form=String.format("%tF",date);
        dateTextField = new JTextField(form);
        //获取日期控件工具类
        ser = DateSelector.getInstance();
        //使用日期控件工具
        ser.register(this);
        dateTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(dateTextField.getText());
            }
        });
    }

    public final void init_tree() {
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

    public static JTree getTree(){
        return inspectorSelectorTree;
    }

    public JTable getDataTable(){
        return dataTable;
    }

    public JPopupMenu getRootRightClickPopMenu(){
        return rootRightClickPopMenu;
    }

    public JPopupMenu getInspectorRightClickPopMenu(){
        return inspectorRightClickPopMenu;
    }

    public JPopupMenu getMeasureRightClickPopMenu(){
        return measureRightClickPopMenu;
    }

    public static JTextField getDateTextField(){
        return dateTextField;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - danny
        mainFrame = new JFrame();
        datePanel = new JPanel();
        selectDateLabel = new JLabel();
//        dateTextField = new JTextField();
        verticalSeparator = new JSeparator();
        horizontalSeparator = new JSeparator();
        jTreeScrollPane = new JScrollPane();
        inspectorSelectorTree = new JTree();
        jTableScrollPane = new JScrollPane();
        dataTable = new JTable();
        setDateSelector();

        //======== mainFrame ========
        {
            Container mainFrameContentPane = mainFrame.getContentPane();
            mainFrameContentPane.setLayout(new GridBagLayout());
            ((GridBagLayout)mainFrameContentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)mainFrameContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)mainFrameContentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)mainFrameContentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //======== datePanel ========
            {
                datePanel.setPreferredSize(new Dimension(161, 67));

                // JFormDesigner evaluation mark
//                datePanel.setBorder(new javax.swing.border.CompoundBorder(
//                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//                        java.awt.Color.red), datePanel.getBorder())); datePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

                datePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)datePanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)datePanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)datePanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)datePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //---- selectDateLabel ----
                selectDateLabel.setText("\u8bf7\u9009\u62e9\u65e5\u671f:");
                datePanel.add(selectDateLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- dateTextField ----
                dateTextField.setPreferredSize(new Dimension(75, 25));
//                dateTextField.setText("date");
                datePanel.add(dateTextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 5, 5), 0, 0));
            }
            mainFrameContentPane.add(datePanel, new GridBagConstraints(0, 0, 5, 3, 5.0, 3.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- verticalSeparator ----
            verticalSeparator.setOrientation(SwingConstants.VERTICAL);
            mainFrameContentPane.add(verticalSeparator, new GridBagConstraints(5, 0, 1, 10, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
            mainFrameContentPane.add(horizontalSeparator, new GridBagConstraints(0, 3, 20, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== jTreeScrollPane ========
            {
                jTreeScrollPane.setViewportView(inspectorSelectorTree);
            }
            mainFrameContentPane.add(jTreeScrollPane, new GridBagConstraints(0, 3, 5, 7, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //======== jTableScrollPane ========
            {

                //---- dataTable ----
                dataTable.setModel(new DefaultTableModel(
                    new Object[][] {
                        {null, null},
                        {null, null},
                    },
                    new String[] {
                        null, null
                    }
                ));
                dataTable.setPreferredScrollableViewportSize(new Dimension(550, 400));
                jTableScrollPane.setViewportView(dataTable);
            }
            mainFrameContentPane.add(jTableScrollPane, new GridBagConstraints(5, 3, 15, 7, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - danny
    private JFrame mainFrame;
    private JPanel datePanel;
    private JLabel selectDateLabel;
    private static JTextField dateTextField;
    private JSeparator verticalSeparator;
    private JSeparator horizontalSeparator;
    private JScrollPane jTreeScrollPane;
    private static JTree inspectorSelectorTree;
    private JScrollPane jTableScrollPane;
    private JTable dataTable;
    private JPopupMenu rootRightClickPopMenu;
    private JPopupMenu inspectorRightClickPopMenu;
    private JPopupMenu measureRightClickPopMenu;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args){
        TestForm form = new TestForm();
        form.mainFrame.setVisible(true);
        form.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public JFrame getMainFrame(){
        return mainFrame;
    }
}

/**
 * popmenu点击右键的修改处理
 */
class TreeModifyViewMenuEvent implements ActionListener {

    private TestForm testForm;

    public TreeModifyViewMenuEvent(TestForm testForm) {
        this.testForm = testForm;
    }

    public void actionPerformed(ActionEvent e) {
        if(testForm.getLevel() == 1) {
            InspectorModifyDialog inspectorModifyDialog = new InspectorModifyDialog(testForm.getMainFrame(), "修改监测点", true, testForm.getTree());
        }
        else if (testForm.getLevel() == 2)
        {
            MeasurePointModifyDialog measurePointModifyDialog = new MeasurePointModifyDialog(testForm.getMainFrame(), "修改测量点", true, testForm.getTree());
        }
//        String name = JOptionPane.showInputDialog("请输入新分类节点名称：");
//
//        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.adaptee.getTree().getSelectionPath().getLastPathComponent();
//        //改名
//        node.setUserObject(name);
//        //刷新
//        this.adaptee.getTree().updateUI();
    }
}

/**
 * popmenu点击右键的增加处理
 */
class TreeAddViewMenuEvent implements ActionListener {

    private TestForm testForm;

    public TreeAddViewMenuEvent(TestForm testForm) {
        this.testForm = testForm;
    }

    public void actionPerformed(ActionEvent e) {
        if(testForm.getLevel() == 0) {
            InspectorAddDialog inspectorAddDialog = new InspectorAddDialog(testForm.getMainFrame(), "添加监测点", true, testForm.getTree());
        }
        else if (testForm.getLevel() == 1)
        {
            MeasurePointAddDialog measurePointAddDialog = new MeasurePointAddDialog(testForm.getMainFrame(), "添加测量点", true, testForm.getTree());
        }
//        String name = JOptionPane.showInputDialog("请输入分类节点名称：");
//        DefaultMutableTreeNode treenode = new DefaultMutableTreeNode(name);
//        ((DefaultMutableTreeNode) this.adaptee.getTree().getLastSelectedPathComponent()).add(treenode);
//        this.adaptee.getTree().expandPath(new TreePath(((DefaultMutableTreeNode) this.adaptee.getTree().getLastSelectedPathComponent()).getPath()));
//        this.adaptee.getTree().updateUI();
    }
}

/**
 * popmenu点击右键的删除处理
 */
class TreeDeleteViewMenuEvent implements ActionListener {

    private TestForm testForm;

    public TreeDeleteViewMenuEvent(TestForm testForm) {
        this.testForm = testForm;
    }

    public void actionPerformed(ActionEvent e) {
        int conform = JOptionPane.showConfirmDialog(null, "是否确认删除？", "删除景点确认", JOptionPane.YES_NO_OPTION);
        if (conform == JOptionPane.YES_OPTION) {
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (((DefaultMutableTreeNode) this.testForm.getTree().getLastSelectedPathComponent()).getParent());
            ((DefaultMutableTreeNode) this.testForm.getTree().getLastSelectedPathComponent()).removeFromParent();
            this.testForm.getTree().updateUI();
        }
    }
}

class TreePopMenuEvent implements MouseListener {

    private TestForm testForm;

    public TreePopMenuEvent(TestForm testForm) {
        this.testForm = testForm;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        TreePath path = testForm.getTree().getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用
        if (path == null) {
            return;
        }
        testForm.getTree().setSelectionPath(path);
        //左键点击节点时查询数据库获得该节点数据并刷新右侧table
        if (e.getButton() == 1) {
            //点击监测节点时
            if (((DefaultMutableTreeNode)testForm.getTree().getLastSelectedPathComponent()).getLevel() == 1) {
                //Do nothing
                System.out.println("当前层级为1");
                System.out.println(testForm.getTree().getLastSelectedPathComponent());
                System.out.println(testForm.getDateTextField().getText());
            } else
                //点击测量节点时
                if (((DefaultMutableTreeNode)testForm.getTree().getLastSelectedPathComponent()).getLevel() == 2) {
                    System.out.println("当前层级为2");
                    try {
                        String monitorName = ((DefaultMutableTreeNode)testForm.getTree().getLastSelectedPathComponent()).getParent().toString();
                        String measureName = testForm.getTree().getLastSelectedPathComponent().toString();
                        String selectedDate = testForm.getDateTextField().getText();
                        System.out.println(monitorName);
                        System.out.println(measureName);
                        System.out.println(selectedDate);
                        ResultSet result1 = testForm.getDataOper().search(monitorName, measureName, selectedDate);
                        // 取得数据库的表的各行数据
                        Vector rowData = testForm.getRows(result1);
                        // 取得数据库的表的表头数据
                        Vector columnNames = testForm.getHead(result1);
                        // 新建表格
                        DefaultTableModel tableModel = new DefaultTableModel(rowData,columnNames);
                        testForm.getDataTable().setModel(tableModel);
                        testForm.updateUI();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
        }
        if (e.getButton() == 3) {
            //点击根节点时
            if (((DefaultMutableTreeNode)testForm.getTree().getLastSelectedPathComponent()).getLevel() == 0) {
                System.out.println("当前层级为0");
                System.out.println(testForm.getTree().getLastSelectedPathComponent());
                System.out.println(testForm.getDateTextField().getText());
                testForm.getRootRightClickPopMenu().show(testForm.getTree(), e.getX(), e.getY());
                //当前为根节点
                testForm.setLevel(0);
            }
            //点击监测节点时
            if (((DefaultMutableTreeNode)testForm.getTree().getLastSelectedPathComponent()).getLevel() == 1) {
                System.out.println("当前层级为1");
                System.out.println(testForm.getTree().getLastSelectedPathComponent());
                System.out.println(testForm.getDateTextField().getText());
                testForm.getInspectorRightClickPopMenu().show(testForm.getTree(), e.getX(), e.getY());
                //当前为监测节点
                testForm.setLevel(1);
            } else
                //点击测量节点时
                if (((DefaultMutableTreeNode)testForm.getTree().getLastSelectedPathComponent()).getLevel() == 2) {
                    System.out.println("当前层级为2");
                    System.out.println(testForm.getTree().getLastSelectedPathComponent());
                    System.out.println(testForm.getDateTextField().getText());
                    System.out.println(((DefaultMutableTreeNode)testForm.getTree().getLastSelectedPathComponent()).getParent());
                    testForm.getMeasureRightClickPopMenu().show(testForm.getTree(), e.getX(), e.getY());
                    //当前为测量节点
                    testForm.setLevel(2);
                }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
