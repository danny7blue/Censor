package ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * 添加树节点图标的操作
 */
class MyDefaultTreeCellRender extends DefaultTreeCellRenderer
{
    /**
     * ID
     */
    private static final long   serialVersionUID    = 1L;
     boolean falg=false;
    /**
     * 重写父类DefaultTreeCellRenderer的方法
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus)
    {

        //执行父类原型操作
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);

        setText(value.toString());

        if (sel)
        {
            setForeground(getTextSelectionColor());
        }
        else
        {
            setForeground(getTextNonSelectionColor());
        }

        //得到每个节点的TreeNode
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        //得到每个节点的text
        String str = node.toString();

        //判断是哪个文本的节点设置对应的值（这里如果节点传入的是一个实体,则可以根据实体里面的一个类型属性来显示对应的图标）
        if (str == "监测点列表") {
            if (falg == false) {
                this.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\连接失败.png"));
            } else {
                this.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\连接成功.png"));
            }
        }

        return this;
    }

}
