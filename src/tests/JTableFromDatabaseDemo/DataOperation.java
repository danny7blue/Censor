package tests.JTableFromDatabaseDemo;

/**
 * Created by 305027244 on 2018/10/16.
 */
import tests.DatabaseCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class DataOperation {
    private static Connection conn = null;

    static {
        conn = DatabaseCon.getConnection();
    }
    // 得到数据库表数据
    public static Vector getRows(){
        Vector rows = null;
        Vector columnHeads = null;

        try {
//			if(!conn.isClosed())
//				System.out.println("成功连接数据库");
            PreparedStatement preparedStatement = null;
            preparedStatement = conn.prepareStatement("select * from category");
            ResultSet result1 = preparedStatement.executeQuery();

//            if(result1.wasNull())
//                JOptionPane.showMessageDialog(null, "结果集中无记录");

            rows = new Vector();

            ResultSetMetaData rsmd = result1.getMetaData();

            while(result1.next()){
                rows.addElement(getNextRow(result1,rsmd));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("未成功打开数据库。");
            e.printStackTrace();
        }
        return rows;
    }

    // 得到数据库表头
    public static Vector getHead(){
        PreparedStatement preparedStatement = null;

        Vector columnHeads = null;

        try {
//			if(!conn.isClosed())
//				System.out.println("成功连接数据库");
            preparedStatement = conn.prepareStatement("select * from category");
            ResultSet result1 = preparedStatement.executeQuery();

            boolean moreRecords = result1.next();
            if(!moreRecords)
                JOptionPane.showMessageDialog(null, "结果集中无记录");

            columnHeads = new Vector();
            ResultSetMetaData rsmd = result1.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
                columnHeads.addElement(rsmd.getColumnName(i));

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

	/*//主函数
	 public static void main(String[] args){
		 getRows();
	}*/
}

