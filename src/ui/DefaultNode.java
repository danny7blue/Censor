package ui;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 继承主类中的树节点
 */
public class DefaultNode extends DefaultMutableTreeNode {
    boolean isConnected = true;

    public DefaultNode(boolean isConnected) {
        super();
        this.isConnected = isConnected;
    }

    public DefaultNode(Object userObject) {
        super(userObject);
    }

    public DefaultNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
