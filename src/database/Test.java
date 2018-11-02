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
    /**
     *   获取数据库的连接的方法getConn();
     *   2018.10.10
     */

    public static   Connection getConn() {
        String user = "root";
       String password = "575615578";
//        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/StationDatabase?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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
     *  方法名为insertMeasurePointData()。
     */

    public static int insertMeasurePointData(int [] temp ) throws SQLException {
        Connection conn = getConn();
        int num=1;
        PreparedStatement pstmt;
        Statement stmt = conn.createStatement();
        try {
            //将数据进行分割处理，将测试点名称放在数组temp01，将对应的测量数据值放在数组temp02中
            int  temp01[],temp02[];
            int size01 =temp.length%2==0?(temp.length/2):(temp.length/2+1);
            int size02 =temp.length-size01;
            temp01 =new int[size01];
            temp02 =new int[size02];
            for(int i =0,j=0,k=0;i<temp.length;i++){
                if(i%2==0){
                    temp01[j++]=temp[i];
                }else {
                    temp02[k++]=temp[i];
                }
            }
            //分别将temp01的测量点名称和temp02的测量数据值插入数据库中对应的测量数据表中。
            for (int m=0,n=0; m<temp01.length|n<temp02.length;m++,n++) {
                String sql = "INSERT MeasureDataInfo(MeasurePointID,MeasureData) VALUES ('"+temp01[m]+"','"+temp02[n]+"')";
                num=stmt.executeUpdate(sql);
                System.out.println("插入数据成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }
    /**功能介绍如下：实现监测点信息表，用户实现增加，删除，修改的功能。
     * 主要构建四个方法，分别为用户向监测点信息表中添加相关信息，删除相关信息，修改相关信息，显示相关信息。
     * @param MonitorName,MonitorPosition;
     * @return
     * @throws SQLException
     */
    //(1)实现用户对监测点信息进行增加数据的操作。添加的信息主要有监测点编号，监测点名称，监测点位置这三个信息，并保存在监测点信息表中
    //输入参数为监测点编号，监测点名称，监测点所在的位置，调用此方法，实现监测点信息的增添功能。
    public boolean insertMonitorInfo( String MonitorName, String MonitorPosition) throws SQLException{
        boolean insflag =false;

            Statement stmt;
            Connection conn = getConn();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //当mysql中监测点信息表中的属性名称发生改变时，下面的sql语句要相应的修改。
            String insertInfo="INSERT INTO MonitorInfo(MonitorName,MonitorPosition) VALUES ('"+MonitorName+"','"+MonitorPosition+"'"+")";
            System.out.println("插入监测点信息的SQL语句为："+insertInfo);
            int count =stmt.executeUpdate(insertInfo);
            if(count>0){
                /* 如果有SQL语句被更新*/
                insflag=true;
            }else{
                /*如何没有SQL语句被更新*/
                insflag=false;
            }
        return insflag;
    }
    //(2)实现用户对监测点信息进行更新数据的操作。更新的信息主要有监测点编号，监测点名称，监测点位置这三个信息，并保存在监测点信息表中
    //输入参数为监测点编号，监测点名称，监测点所在的位置，调用此方法，实现监测点信息的更新功能。
    public boolean updateMonitorInfo( String MonitorName_new, String MonitorPosition_new,String MonitorName_old)throws SQLException{
        boolean upflag =false;
        try {
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String updateInfo ="UPDATE MonitorInfo SET  MonitorName ='"+MonitorName_new+"',MonitorPosition ='"+MonitorPosition_new+"'WHERE MonitorName ='"+MonitorName_old+"'";
            System.out.println("更新监测点信息的SQL语句为："+updateInfo);
            int count =stmt.executeUpdate(updateInfo);
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
    //删除监测点的记录信息。
    public boolean deleteMonitorInfo(String MonitorName)throws SQLException{
        boolean deflag=false;
        try{
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String deleteInfo ="DELETE FROM MonitorInfo "+" WHERE MonitorName ='"+MonitorName+"'";
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
    //(4)查询测量点信息的方法selectMonitorInfo.输入参数为监测点的名称，查询出的结果为编号，名称以及所在位置。
    public ResultSet selectMonitorInfo() throws SQLException{
        // boolean insflag =false;
        ResultSet rs = null;
        try {
            Statement stmt;
            Connection conn = getConn();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //当mysql中监测点信息表中的属性名称发生改变时，下面的sql语句要相应的修改。
            String selectInfo="select MonitorID as  监测点编号,MonitorName as 监测点名称,MonitorPosition as 监测点位置 from MonitorInfo ";
            System.out.println("插入监测点信息的SQL语句为："+selectInfo);
            rs = stmt.executeQuery(selectInfo);
        } catch (SQLException e) {
            System.out.println("SQLException异常"+e.getMessage());
            e.printStackTrace();
        }
        // return insflag;
        return rs;
    }
    /**功能介绍：实现用户对测量点信息表的增加，修改，删除,显示的操作
     * 主要构建三个方法：用户对测量信息表进行数据添加的功能，修改数据的功能，删除数据的功能以及显示数据的功能。
     *@param MeasurePointNo,MeasurePointName,Parameter,MonitorName;
     *  方法名为insertMeasurePointInfo();
     */
    //(1)实现用户对测量点信息进行增加数据的操作。添加的信息主要有测量点编号，测量点名称，所属的监测点编号这三个信息，并保存在测量点信息表中
    //输入参数为测量点编号，测量点名称，所属监测点的编号，调用此方法，实现测量点信息的增添功能。
    //方法名为insertMeasurePointInfo();
    public boolean insertMeasurePointInfo(int MeasurePointNo,String MeasurePointName,float Parameter,String MonitorName) throws SQLException{
        boolean insflag =false;
            Connection conn = getConn();
            Statement stmt;
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //当mysql中测量点信息表中的属性名称发生改变时，下面的sql语句要相应的修改。
            String insertInfo="INSERT INTO MeasurePointInfo(MeasurePointNo,MeasurePointName,Parameter,MonitorID) " +
                              "VALUES('"+MeasurePointNo+"','"+MeasurePointName+"','"+Parameter+"'," +
                              "(SELECT MonitorID FROM monitorinfo WHERE MonitorName='"+MonitorName+"'))";
            System.out.println("插入测量点信息的SQL语句为："+insertInfo);
            int count =stmt.executeUpdate(insertInfo);
            if(count>0){
                /* 如果有SQL语句被更新*/
                insflag=true;
            }else{
                /*如何没有SQL语句被更新*/
                insflag=false;
            }
        return insflag;
    }
    //(2)实现用户对测量点信息进行更新数据的操作。更新的信息主要有测量点编号，测量点名称，所属监测点的编号这三个信息，并保存在监测点信息表中
    //输入参数为测量点编号，测量点名称，所属监测点的编号，调用此方法，实现测量点信息的更新功能。
   // 方法名为updateMeasurePointInfo();
    public boolean updateMeasurePointInfo(int MeasurePointNo_new, String MeasurePointName_new,float Parameter_new, String MonitorName,String MeasurePointName_old)throws SQLException{
        boolean upflag =false;
        try {
            Connection conn = getConn();
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String updateInfo ="UPDATE MeasurePointInfo SET MeasurePointNO='"+MeasurePointNo_new+"' ,MeasurePointName ='"+MeasurePointName_new+"',Parameter='"+Parameter_new+"'" +
                               "WHERE" + " MeasurePointName='"+MeasurePointName_old+"' AND MonitorID=" +
                               "(select MonitorID FROM MonitorInfo where  MonitorName='"+MonitorName+"')";
            System.out.println("更新测量点信息的SQL语句为："+updateInfo);
            int count =stmt.executeUpdate(updateInfo);
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
    public boolean deleteMeasurePointInfo(String MonitorName ,String MeasurePointName)throws SQLException{
        boolean deflag=false;
        try{
            Connection conn = getConn();
            int i=0;
            Statement stmt;
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String deleteInfo ="UPDATE MeasurePointInfo AS mpinfo,MeasureDataInfo AS mdinfo SET mpinfo.IsDeleted=1,mdinfo.IsDeleted=1" +
                               " WHERE mpinfo.MeasurePointID=mdinfo.MeasurePointID AND MeasurePointName ='"+MeasurePointName+"'" +
                               " AND MonitorID=(SELECT MonitorID FROM MonitorInfo WHERE MonitorName='"+MonitorName+"')" ;
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
    //(4)查询测量点信息的方法selectMeasurePointInfo.输入参数为测量点的名称，查询出的结果为编号，名称以及所属监测点的编号。
    public ResultSet selectMeasurePointInfo(String MonitorName) throws SQLException{
        ResultSet rs = null;
        try {
            Statement stmt;
            Connection conn = getConn();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String selectInfo="select mpinfo.MeasurePointNO as 测量点编号,mpinfo.MeasurePointName as 测量点名称,mpinfo.Parameter as 变比系数, mpinfo.MonitorID" +
                              " as 所属监测点的编号 " + "from MonitorInfo as minfo,MeasurePointInfo as  mpinfo where minfo.MonitorID=mpinfo.MonitorID " +
                              "and MonitorName='"+MonitorName+"' and  IsDeleted=0";
            System.out.println("筛选测量点信息的SQL语句为："+selectInfo);
            rs = stmt.executeQuery(selectInfo);
        } catch (SQLException e) {
            System.out.println("SQLException异常"+e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }
    /**功能介绍：实现用户对测量数据表的操作：主要有查询某个测试点的数据.
     *此方法的输入参数为MonitorName,MeasurePointName,time即监测点名称，测试点名称，以及时间。
     */
    public ResultSet search(String MonitorName,String MeasurePointName,String time)throws SQLException{
        ResultSet rs =null;
        int rowcount =0;
        String pdemo="%";//定义一个字符%
        String time_new =time.concat(pdemo);//将传入的日期转换为 "yy-mm-dd %"，便于在mysql中进行模糊查询当前日期的所有数据。
        String selTestPointinfo ="";
        if(MonitorName.equals("")& MeasurePointName.equals("")){
            /*如果监测点和测量点的编号为空，则执行查询测量点数据的SQL语句*/
            System.out.println("未正确获得监测点名称和测量点名称，查询结果为空！");
        }else{
            selTestPointinfo="SELECT tinfo.MeasurePointName as 测试点名称,tinfo.MeasurePointNo as 测试点编号,tdinfo.MeasureData as 数据值,tdinfo.ReceivedTime as 时间" +
                             " from monitorinfo as minfo,MeasurePointinfo as tinfo,MeasureDataInfo as tdinfo " + "WHERE minfo.MonitorID=tinfo.MonitorID AND tinfo.MeasurePointID=tdinfo.MeasurePointID " +
                             "AND minfo.MonitorName='"+MonitorName+"' " + "AND tinfo.MeasurePointName='"+MeasurePointName+"'" + "AND tdinfo.ReceivedTime like '"+time_new+"'" +
                            " AND tinfo.IsDeleted =0 AND tdinfo.IsDeleted=0"  ;
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

