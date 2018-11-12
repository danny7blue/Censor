package tests;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by 305027244 on 2018/11/12.
 */
public class MyDefaultNode extends DefaultMutableTreeNode {
    boolean isConnected = true;

    public MyDefaultNode(boolean isConnected) {
        super();
        this.isConnected = isConnected;
    }

    public MyDefaultNode(Object userObject) {
        super(userObject);
    }

    public MyDefaultNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
