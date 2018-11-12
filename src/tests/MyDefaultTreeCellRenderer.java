package tests;

/**
 * Created by 305027244 on 2018/11/12.
 */
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * 自定义树描述类,将树的每个节点设置成不同的图标
 *
 */
public class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer
{
    /**
     * ID
     */
    private static final long   serialVersionUID    = 1L;

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
        MyDefaultNode node = (MyDefaultNode) value;

        //得到每个节点的level和isConnected
        int level = node.getLevel();
        boolean isConnected = node.isConnected();

        //判断是哪个文本的节点设置对应的值（这里如果节点传入的是一个实体,则可以根据实体里面的一个类型属性来显示对应的图标）
        if (level == 0)
        {
            //Do nothing
        }
        if (level == 1)
        {
            if (isConnected)
                this.setIcon(new ImageIcon("resources/connected_small.png"));
            else
                this.setIcon(new ImageIcon("resources/disconnected_small.png"));
        }
        if (level == 2)
        {
            //Do nothing
        }

        return this;
    }

}
