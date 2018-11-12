package database;
import java.io.*;
import java.util.Properties;

public class TestMain{
        //  根据端口名称将端口号读出来。
      public  static  String GetValueBykey(String Key){
          Properties pps =new Properties();
          try{
              InputStream in =new BufferedInputStream(new FileInputStream("src/database/Test.properties"));
              pps.load(in);
              String value = pps.getProperty(Key);
              System.out.println(Key + "="+ value);
              return  value;
          }catch (IOException e){
              e.printStackTrace();
              return  "8307";//返回一个默认端口
          }
      }
        //根据端口名称写入端口号
    public static  void WriteProperties(String pKey,String pValue){
         try{
             Properties pps =new Properties();
             InputStream in =new FileInputStream("src/database/Test.properties");
             pps.load(in);
             OutputStream out =new FileOutputStream("src/database/Test.properties");
             pps.setProperty(pKey,pValue);
             pps.store(out,"Update"+pKey+"name");
             System.out.println("已更换端口号");
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


