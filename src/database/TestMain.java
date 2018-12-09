package database;
import network.Server_Xian;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class TestMain{
    private static  final Logger LOGGER = Logger.getLogger(TestMain.class);
        //  根据端口名称将端口号读出来。
      public  static  String GetValueBykey(String Key){

          LOGGER.info("读取端口号...");
          Properties pps =new Properties();
          try{
              InputStream in =new BufferedInputStream(new FileInputStream("src/database/Test.properties"));
              pps.load(in);
              String value = pps.getProperty(Key);
              System.out.println(Key + "="+ value);
              LOGGER.debug("端口号:"+value);
              return  value;
          }catch (IOException e){
              e.printStackTrace();
              LOGGER.debug("默认端口号:8307");
              return  "8307";//返回一个默认端口

          }

      }
        //根据端口名称写入端口号
    public static  void WriteProperties(String pKey,String pValue){
        LOGGER.info("写入端口号...");
         try{
             Properties pps =new Properties();
             InputStream in =new FileInputStream("src/database/Test.properties");
             pps.load(in);
             OutputStream out =new FileOutputStream("src/database/Test.properties");
             pps.setProperty(pKey,pValue);
             pps.store(out,"Update"+pKey+"name");
             System.out.println("已更换端口号");
             LOGGER.info("已更换端口:"+pKey+"  "+pValue);
         }catch (IOException e){
             e.printStackTrace();
         }

    }
    //测试端口号的写入和读出
    public static void main(String[] args) {
        TestMain tm =new TestMain();
        String Key="Port";
        tm.GetValueBykey(Key);
        String pKey="Port";
        String pValue="3307";
        tm.WriteProperties(pKey,pValue);
    }
}


