package tests;

/**
 * Created by 305027244 on 2018/10/15.
 */

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseCon {
    public static Connection getConnection() {
        String sql_string = "jdbc:mysql://localhost/jtree_category?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String account = "root";
        String password = "123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(sql_string, account, password);
            return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
