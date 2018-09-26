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
 * 〈测试JDBC的基本功能, 包括连接和命令的执行〉<br>
 * 〈〉
 *
 * @author DannyBlue
 * @create 2018/9/25
 * @since 1.0.0
 */
public class SimpleJdbc {
    public static void main(String[] args)
            throws SQLException, ClassNotFoundException {
        String user = "root";
        String password = "123456";
        String createWebsites = "CREATE TABLE `websites` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` char(20) NOT NULL DEFAULT '' COMMENT '站点名称',\n" +
                "  `url` varchar(255) NOT NULL DEFAULT '',\n" +
                "  `alexa` int(11) NOT NULL DEFAULT '0' COMMENT 'Alexa 排名',\n" +
                "  `country` char(10) NOT NULL DEFAULT '' COMMENT '国家',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ")";
        String insertWebsites = "INSERT INTO `websites` VALUES " +
                "('1', 'Google', 'https://www.google.cm/', '1', 'USA'), " +
                "('2', '淘宝', 'https://www.taobao.com/', '13', 'CN'), " +
                "('3', '菜鸟教程', 'http://www.runoob.com', '5892', ''), " +
                "('4', '微博', 'http://weibo.com/', '20', 'CN'), " +
                "('5', 'Facebook', 'https://www.facebook.com/', '3', 'USA');";
        // Load the JDBC driver
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");

        // Establish a connection
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/test_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
        System.out.println("Database connected");

        // Create a statement
        Statement statement = connection.createStatement();

        // Create a table
//        statement.executeUpdate
//                ("create table Temp (col1 char(5), col2 char(5))");

//        statement.executeUpdate(createWebsites);
//        statement.executeUpdate(insertWebsites);

        // Execute a statement
        ResultSet resultSet = statement.executeQuery
                ("select name, url from websites where country = 'USA'");

        while (resultSet.next()){
            String name = resultSet.getString("name");
            String url = resultSet.getString("url");

        // 输出数据
        System.out.print(", 站点名称: " + name);
        System.out.print(", 站点 URL: " + url);
        System.out.print("\n");

        // Close the connection
        }
        connection.close();
    }
}

