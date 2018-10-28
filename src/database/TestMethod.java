package database;

import java.sql.SQLException;

public class TestMethod {
    public static void main(String[] args) {
        Test t =new Test();
        int MonitorId=1;
        String MonitorName="Ma";
        String MonitorPosition ="dada";
        try{
            t.insertMonitorInfo(MonitorId,MonitorName,MonitorPosition);
        }catch(SQLException e){
            e.printStackTrace();
        }


    }
}
