package database;
import org.junit.rules.TestName;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 305027244 on 2018/9/28.
 */

public class Test {


    public static void main(String[] args) {
        Test t =new Test();
        //测试将数据送到数据库的方法。(测试通过)
//        String [] temp ={"1","100","2","200","3","300","4","400","5","500"};
//        try{
//            t.insert(temp);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
        //测试对监测点信息表数据的插入（测试通过）
//        try {
//            int MonitorId = 3;
//            String MonitorName = "M3";
//            String MonitorPosition = "西安";
//            t.insertMonitorInfo(MonitorId, MonitorName, MonitorPosition);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
        //测试对监测点信息表数据的更新（测试通过）
//        try {
//            int MonitorId =1 ;
//            String MonitorName = "M3";
//            String MonitorPosition = "重庆";
//            t.updataMonitorInfo(MonitorId, MonitorName, MonitorPosition);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        //测试对监测点信息表的删除（测试通过）
//        try {
//            int MonitorId = 1;
//            t.deleteMonitorInfo(MonitorId);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
        //测试测量点信息表数据的插入（测试通过）
//        try {
//            int TestId = 5;
//            String TestName = "T1";
//            int    TestMonitorId = 1;
//            t.insertTestInfo(TestId,TestName,TestMonitorId);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
        //测试测量点信息表数据的更新（测试通过）
//        try {
//            int TestId = 5;
//            String TestName = "测试点三号";
//            int TestMonitorId = 2;
//            t.updataTestInfo(TestId,TestName,TestMonitorId);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        //测试测量点信息表的删除功能（测试通过）
//        try {
//            int TestId = 1;
//            t.deleteTestInfo(TestId);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
        //测试将测量点的压力值显示出来的功能（测试通过）
//        try {
//            String MonitorName="M1";
//           String TestName = "T1";
//           String time ="2018-10-21";
//            t.search(MonitorName,TestName,time);
//         }catch (SQLException e){
//           e.printStackTrace();
//       }
    }

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
     *  输入参数为一个字符型的数组，即从前端采集的数据放在字符型数组中，调用此方法可以实现数据的存储。
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
                String sql = "INSERT INTO 测量数据表(测量点编号,测量数据值) VALUES ('"+temp01[m]+"','"+temp02[n]+"')";
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
    //输入参数为监测点编号，监测点名称，监测点所在的位置，调用此方法，实现监测点信息的增添功能。
    public boolean insertMonitorInfo(int MonitorId, String MonitorName, String MonitorPosition) throws SQLException{
        boolean insflag =false;

        try {
            Statement stmt;
            Connection conn = getConn();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //当mysql中监测点信息表中的属性名称发生改变时，下面的sql语句要相应的修改。
            String insertInfo="INSERT INTO MonitorInfo(MonitorID,MonitorName,MonitorPosition) VALUES ("+"'"+MonitorId+"','"+MonitorName+"','"+MonitorPosition+"'"+")";
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
    //输入参数为监测点编号，监测点名称，监测点所在的位置，调用此方法，实现监测点信息的更新功能。
    public boolean updataMonitorInfo(int MonitorId, String MonitorName, String MonitorPosition)throws SQLException{
        boolean upflag =false;
        try {
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String updataInfo ="UPDATE MonitorInfo SET MonitorName ='"+MonitorName+"',MonitorPosition ='"+MonitorPosition+"'WHERE MonitorID ='"+MonitorId+"'";
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
    // 输入参数为监测点编号，调用此方法，实现监测点信息的删除功能。
    public boolean deleteMonitorInfo(int MonitorId)throws SQLException{
        boolean deflag=false;
        try{
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String deleteInfo ="DELETE FROM MonitorInfo "+" WHERE MonitorID ='"+MonitorId+"'";
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
    //输入参数为测量点编号，测量点名称，所属监测点的编号，调用此方法，实现测量点信息的增添功能。
    public boolean insertTestInfo(int TestId, String TestName, int TestMonitorId) throws SQLException{
        boolean insflag =false;

        try {
            Connection conn = getConn();
            Statement stmt;
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //当mysql中测量点信息表中的属性名称发生改变时，下面的sql语句要相应的修改。
            String insertInfo="INSERT INTO TestInfo(TestID,TestName,TestMonitorID) VALUES ("+"'"+TestId+"','"+TestName+"','"+TestMonitorId+"'"+")";
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
    //输入参数为测量点编号，测量点名称，所属监测点的编号，调用此方法，实现测量点信息的更新功能。
    public boolean updataTestInfo(int TestId, String TestName, int TestMonitorId)throws SQLException{
        boolean upflag =false;
        try {
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String updataInfo ="UPDATE TestInfo SET TestName ='"+TestName+"',TestMonitorID ='"+TestMonitorId+"'where TestID='"+TestId+"'";
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
    //输入参数为测量点编号调用此方法，实现测量点信息的删除功能。
    public boolean deleteTestInfo(int TestId)throws SQLException{
        boolean deflag=false;
        try{
            Connection conn = getConn();
            int i=0;
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String deleteInfo ="DELETE FROM TestInfo "+" WHERE TestID ='"+TestId+"'";
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
    /**功能介绍：实现用户对测量数据表的操作：主要有查询某个测试点的数据.
     *此方法的输入参数为MonitorName,TestName,time即测试点的编号，返回结果为查询的数据，时间和变比值。
     */
    public ResultSet search(String MonitorName,String TestName,String time)throws SQLException{
        ResultSet rs =null;
        int rowcount =0;
        String pdemo="%";//定义一个字符%
        String time_new =time.concat(pdemo);//将传入的日期转换为 "yy-mm-dd %"，便于在mysql中进行模糊查询当前日期的所有数据。
        String selTestPointinfo ="";
        if(MonitorName.equals("")&TestName.equals("")){
            /*如果监测点和测量点的编号为空，则执行查询测量点数据的SQL语句*/
            System.out.println("未正确获得监测点名称和测量点名称，查询结果为空！");
        }else{
            selTestPointinfo="SELECT tdinfo.ID,tinfo.TestName,tdinfo.TestID,tdinfo.TestData,tdinfo.Time from monitorinfo as minfo,testinfo as tinfo,testdatainfo as tdinfo " +
                    "WHERE minfo.MonitorID=tinfo.TestMonitorID AND tinfo.TestID=tdinfo.TestID " +
                    "AND minfo.MonitorName='"+MonitorName+"' " +
                    "AND tinfo.TestName='"+TestName+"'" +
                    "AND tdinfo.Time like '"+time_new+"'";
        }
        System.out.println("查询测量点数据的SQL语句为"+selTestPointinfo);
        try{
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery(selTestPointinfo);//执行查询语句
            rs.last();//将结果集位置移到最后。

            rowcount =rs.getRow();//获得当前的行编号
            System.out.println("检索出的记录为"+rowcount);
            if(rowcount>0){
                return rs;//返回取得的结果集。
            }else{
                rs=null;
            }
        }catch (SQLException e){
            System.out.println("SQLException异常"+e.getMessage());//打印输出异常信息
            e.printStackTrace();
        }
        return rs;
    }
}
