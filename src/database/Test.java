package database;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 305027244 on 2018/9/28.
 */

public class Test {
    /**
     *   获取数据库的连接的方法getConn();
     *   2018.10.10
     */

    public static   Connection getConn() {
        String user = "root";
        String password = "575615578";
        String url = "jdbc:mysql://localhost:3306/StationDatabase?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String driver = "com.mysql.cj.jdbc.Driver";
        Connection conn=null;
        try {
            //加载驱动
            Class.forName(driver);
            //创建连接
            conn = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**功能介绍：与数据采集进行信息交互，主要将前端采集过来的数据，存放在数据库中。
     * 实现插入数据的操作方法。输入参数为一个数组。
     *  如果传过来的数组数据不是按照测试点名称和测试点数据值，那么如何处理这种异常？
     *  这个异常处理机制还没有解决。思考ing中。
     */

    public static int insert(String [] temp ) throws SQLException {
        Connection conn = getConn();
        int num=1;
        PreparedStatement pstmt;
        Statement stmt = conn.createStatement();
        try {
            //将数据进行分割处理，将测试点名称放在数组temp01，将对应的测量数据值放在数组temp02中
            String  temp01[],temp02[];
            int size01 =temp.length%2==0?(temp.length/2):(temp.length/2+1);
            int size02 =temp.length-size01;
            temp01 =new String[size01];
            temp02 =new String[size02];
            for(int i =0,j=0,k=0;i<temp.length;i++){
                if(i%2==0){
                    temp01[j++]=temp[i];
                }else {
                    temp02[k++]=temp[i];
                }
            }
            //分别将temp01的测量点名称和temp02的测量数据值插入数据库中对应的测量数据表中。
            for (int m=0,n=0; m<temp01.length|n<temp02.length;m++,n++) {
                String sql = "INSERT INTO 测量数据表(测量点名称,测量数据值) VALUES ('"+temp01[m]+"','"+temp02[n]+"')";
                num=stmt.executeUpdate(sql);
                System.out.println("插入数据成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**功能介绍如下：实现监测点信息表，用户实现增加，删除，修改的功能。
     * 主要构建三个方法，分别为用户向监测点信息表中添加相关信息，删除相关信息，修改相关信息。
     * @param MonitorId,MonitorName,MonitorPosition;
     * @return
     * @throws SQLException
     */
    //(1)实现用户对监测点信息进行增加数据的操作。添加的信息主要有监测点编号，监测点名称，监测点位置这三个信息，并保存在监测点信息表中
    public boolean insertMonitorInfo(String MonitorId, String MonitorName, String MonitorPosition) throws SQLException{
        boolean insflag =false;

        try {
            Connection conn = getConn();
            Statement stmt;
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //当mysql中监测点信息表中的属性名称发生改变时，下面的sql语句要相应的修改。
            String insertInfo="INSERT INTO 监测点信息表(监测点编号,监测点名称,监测点位置) VALUES ('"+MonitorId+"','"+MonitorName+"','"+MonitorPosition+"'";
            System.out.println("插入监测点信息的SQL语句为："+insertInfo);
            int count =stmt.executeUpdate(insertInfo);
            if(count>0){
                /* 如果有SQL语句被更新*/
                insflag=true;
            }else{
                /*如何没有SQL语句被更新*/
                insflag=false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException异常"+e.getMessage());
            e.printStackTrace();
        }
        return insflag;
    }
    //(2)实现用户对监测点信息进行更新数据的操作。更新的信息主要有监测点编号，监测点名称，监测点位置这三个信息，并保存在监测点信息表中
    public boolean updataMonitorInfo(String MonitorId, String MonitorName, String MonitorPosition)throws SQLException{
        boolean upflag =false;
        try {
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String updataInfo ="UPDATE 监测点信息表 SET 监测点编号 ='"+MonitorId+"',监测点名称 ='"+MonitorName+"',监测点位置 ='"+MonitorPosition+"'";
            System.out.println("更新监测点信息的SQL语句为："+updataInfo);
            int count =stmt.executeUpdate(updataInfo);
            if(count>0){
                /*如果有SQL语句被更新*/
                upflag =true;
            }else {
                /*如果没有SQL语句被更新*/
                upflag = false;
            }
        }catch (SQLException e){
            System.out.println("SQLException异常"+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    //(3)实现用户对监测点信息进行删除数据的操作。以监测点信息表的监测点编号，删除的信息主要有监测点名称，监测点位置这两个信息。
    public boolean deleteMonitorInfo(String MonitorId)throws SQLException{
        boolean deflag=false;
        try{
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String deleteInfo ="DELETE FROM 监测点信息表 "+" WHERE 监测点名称 ='"+MonitorId+"'";
            System.out.println("删除监测点信息的SQl语句为"+deleteInfo);
            int count = stmt.executeUpdate(deleteInfo);
            if(count>0){
                /*如果有SQL语句被更新*/
                deflag =true;
            }else{
                /*如果没有SQL语句被更新*/
                deflag =false;
            }
        }catch (SQLException e){
            System.out.println("SQLException异常"+e.getMessage());
            e.printStackTrace();
        }
        return deflag;
    }
    /**功能介绍：实现用户对测量点信息表的增加，修改，删除的操作
     * 主要构建三个方法：用户对测量信息表进行数据添加的功能，修改数据的功能，删除数据的功能。
     *@param TestId,TestName,TestMonitorId;
     */
    //(1)实现用户对测量点信息进行增加数据的操作。添加的信息主要有测量点编号，测量点名称，所属的监测点编号这三个信息，并保存在测量点信息表中
    public boolean insertTestInfo(String TestId, String TestName, String TestMonitorId) throws SQLException{
        boolean insflag =false;

        try {
            Connection conn = getConn();
            Statement stmt;
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //当mysql中测量点信息表中的属性名称发生改变时，下面的sql语句要相应的修改。
            String insertInfo="INSERT INTO 测量点信息表(测量点编号,测量点名称,所属的监测点编号) VALUES ('"+TestId+"','"+TestName+"','"+TestMonitorId+"'";
            System.out.println("插入测量点信息的SQL语句为："+insertInfo);
            int count =stmt.executeUpdate(insertInfo);
            if(count>0){
                /* 如果有SQL语句被更新*/
                insflag=true;
            }else{
                /*如何没有SQL语句被更新*/
                insflag=false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException异常"+e.getMessage());
            e.printStackTrace();
        }
        return insflag;
    }
    //(2)实现用户对测量点信息进行更新数据的操作。更新的信息主要有测量点编号，测量点名称，所属监测点的编号这三个信息，并保存在监测点信息表中
    public boolean updataTestInfo(String TestId, String TestName, String TestMonitorId)throws SQLException{
        boolean upflag =false;
        try {
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String updataInfo ="UPDATE 测量点信息表 SET 测量点编号='"+TestId+"',测量点名称 ='"+TestName+"',所属的监测点编号 ='"+TestMonitorId+"'";
            System.out.println("更新测量点信息的SQL语句为："+updataInfo);
            int count =stmt.executeUpdate(updataInfo);
            if(count>0){
                /*如果有SQL语句被更新*/
                upflag =true;
            }else {
                /*如果没有SQL语句被更新*/
                upflag = false;
            }
        }catch (SQLException e){
            System.out.println("SQLException异常"+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    //(3)实现用户对测量点信息进行删除数据的操作。以测量点信息表的测量点编号，删除的信息主要有测量点名称，所属监测点的编号这两个信息。
    public boolean deleteTestInfo(String TestId)throws SQLException{
        boolean deflag=false;
        try{
            Connection conn = getConn();
            int i=0;
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String deleteInfo ="DELETE FROM 测量点信息表 "+" WHERE 测量点编号 ='"+TestId+"'";
            System.out.println("删除测量点信息的SQl语句为"+deleteInfo);
            int count = stmt.executeUpdate(deleteInfo);
            if(count>0){
                /*如果有SQL语句被更新*/
                deflag =true;
            }else{
                /*如果没有SQL语句被更新*/
                deflag =false;
            }
        }catch (SQLException e){
            System.out.println("SQLException异常"+e.getMessage());
            e.printStackTrace();
        }
        return deflag;
    }
    /**功能介绍：实现用户对测量数据表的操作：主要有查询某个测试点的数据，
     *
     */
}
