package database;
//测试test里面的方法
import java.sql.SQLException;

public class TestMethod {
    public static void main(String[] args) {
        //(1)测试MeasurePointData数据的插入

        /** Test t =new Test();
         int temp[]={1,200,2,300,3,400,4,500};
         try {
         t.insertMeasurePointData(temp);
         }catch (SQLException e){
         e.printStackTrace();
         }
         **/
        //(2)测试MonitorInfo表信息的增加
        /** Test t =new Test();
         String MonitorName="监测点1号";
         String MonitorPosition="贵州";
         try{
         t.insertMonitorInfo(MonitorName,MonitorPosition);
         }catch (SQLException e){
         e.printStackTrace();
         }
         **/
        //(3)测试MonitorInfo表中信息的更新
        /**Test t =new Test();
         String MonitorName_new ="M1";
         String MonitorPosition_new="贵州一号";
         String MonitorName_old="监测点1号";
         try{
         t.updateMonitorInfo(MonitorName_new,MonitorPosition_new,MonitorName_old);
         }catch (SQLException e){
         e.printStackTrace();
         }
         **/
        //(4)测试MonitorInfo表中信息的删除
       /** Test t = new Test();
        String MonitorName = "M1";
        try {
            t.deleteMonitorInfo(MonitorName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        **/
       //(5)测试MonitorInfo表中信息的显示
       /**Test t = new Test();
        try{
            t.selectMonitorInfo();
        }catch (SQLException e){
            e.printStackTrace();
        }
        **/
       //(6)测试MeasurePointInfo表中的信息的添加
       /**Test t = new Test();
        int MeasurePointNo =1;
        String MeasurePointName ="测试点1号";
       int Parameter =70;
        String MonitorName ="M1";
        try{
            t.insertMeasurePointInfo(MeasurePointNo,MeasurePointName,Parameter,MonitorName);
        }catch (SQLException e){
            e.printStackTrace();
        }
        **/
       //(7)测试MeasurePointInfo表中信息的更新
       /** Test t = new Test();
        int MeasurePointNo_new =100;
        String MeasurePointName_new ="T1";
        int Parameter_new =79;
        String MeasurePointName_old="测试点1号";
        String MonitorName ="M1";
        try{
            t.updateMeasurePointInfo(MeasurePointNo_new,MeasurePointName_new,Parameter_new,MonitorName,MeasurePointName_old);
        }catch (SQLException e){
            e.printStackTrace();
        }
        **/
       //(8)测试MeasurePointInfo表中信息的删除
      /** Test t = new Test();
        String MonitorName="M1";
        String MeasurePointName="T1";
        try{
            t.deleteMeasurePointInfo(MonitorName,MeasurePointName);
        }catch (SQLException e){
            e.printStackTrace();
        }
       **/
        //(9)测试MeasurePointInfo表中信息的显示
       /** Test t = new Test();
        String MonitorName="M1";
        try{
            t.selectMeasurePointInfo(MonitorName);
        }catch (SQLException e){
            e.printStackTrace();
        }
        **/
       //(10)测试MeasureDataInfo表中数据的显示
        Test t = new Test();
        String MonitorName="M1";
        String MeasurePointName="T1";
        String time="2018-11-01";
        try{
            t.search(MonitorName,MeasurePointName,time);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
