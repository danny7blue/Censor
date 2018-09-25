/**
 * FileName: JDBC
 * Author:   DannyBlue
 * Date:     2018/9/25 21:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package Tests;

import java.sql.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author DannyBlue
 * @create 2018/9/25
 * @since 1.0.0
 */
public class SimpleJdbc {
    public static void main(String[] args)
            throws SQLException, ClassNotFoundException {
        // Load the JDBC driver
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");

        // Establish a connection
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/test");
        System.out.println("Database connected");

        // Create a statement
        Statement statement = connection.createStatement();

        // Execute a statement
        ResultSet resultSet = statement.executeQuery
                ("select firstName, mi, lastName from Student where lastName "
                        + " = 'Smith'");

        // Iterate through the result and print the student names
        while (resultSet.next())
            System.out.println(resultSet.getString(1) + "\t" +
                    resultSet.getString(2) + "\t" + resultSet.getString(3));

        // Close the connection
        connection.close();
    }
}

