package network;
import org.apache.log4j.Logger;

import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class Server_Xian {
    static ServerSocket serverSocket = null;
    static Socket socket = null;
    static OutputStream os = null;
    static InputStream is = null;
    static InetAddress ia=null;
    private static  final Logger LOGGER = Logger.getLogger(Server_Xian.class);
    //主函数
    public static void main(String[] args) {
        LOGGER.warn("socket主程序...");
        int port = 8087;//监听端口号
        //String hello = "Hello,ImServer";
        try {
            byte lipp[] = new byte[16];

            //获取本机IP地址
            ia=ia.getLocalHost();
            String lip=ia.getHostAddress();
            String[] lip_split=lip.split("\\.");//去掉IP地址的间隔“.”
            for(String c:lip_split){
                System.out.print(c+" ");  //输出以空格间隔的ip
            }
            LOGGER.debug("获取本地IP地址成功:"+lip);
            System.out.println();//隔一行
            //建立连接
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            //发送数据
            os = socket.getOutputStream();//得到输出流

            System.out.println(("connect successful!"));//输出“connect successful!”
            LOGGER.info("已经建立连接并开始发送数据...");
            lipp=sendData(lip_split);//送到方法处理发送的数据
            os.write(lipp);
            LOGGER.info("发送数据成功:");
            //接收数据
            LOGGER.info("开始接收数据...");
            is = socket.getInputStream();//得到输入流
            LOGGER.info("已获取得到输入流，开始解析...");
            byte[] b = new byte[1024];//定义字符串b
            int n = is.read(b);//计算读取到的b的长=度;
            System.out.println("客户端发送的内容为" +new String(b,0,n));//显示
            dnp(b,n);//数据解析
            //当接受完收据后断开连接（无）
            os.close();
            is.close();
            socket.close();
            serverSocket.close();
            LOGGER.warn("关闭socket");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("socket连接失败...");
        }finally {
            try {
                os.close();
                is.close();
                socket.close();
                serverSocket.close();
            }catch (Exception e){
                e.printStackTrace();
                LOGGER.error("关闭socket失败...");
            }
        }
    }
    //方法，数据发送处理
    private static byte[] sendData(String[] lip_split){
        byte lipp[] = new byte[17];//定义输出数组的长度
        //定义头文件
        byte head_1 = 0x05;
        byte head_2 = 0x64;
        byte con_num1 = 0x01;//定义控制字
        String des_add = "10.1";//定义目的地址
        String sou_add = "20.1";//定义源地址
        String[] des_add_split=des_add.split("\\.");//去掉目的地址的间隔“.”
        String[] sou_add_split=sou_add.split("\\.");//去掉源地址的间隔“.”
        //添加头文件
        lipp[0] =head_1;
        lipp[1] =head_2;
        char lengh=17;//定义长度

        lipp[2] =  (byte) ((lengh & 0xFF00) >> 8);//长度的高位字节
        lipp[3] = (byte) (lengh & 0xFF);//长度的低位字节

        lipp[4] =con_num1;//控制字

        lipp[5] =(byte)Short.parseShort(des_add_split[0]);//目的地址
        lipp[6] =(byte)Short.parseShort(des_add_split[1]);

        lipp[7] =(byte)Short.parseShort(sou_add_split[0]);//源地址
        lipp[8] =(byte)Short.parseShort(sou_add_split[1]);
        //发送IP地址数据
        lipp[11] =(byte)Short.parseShort(lip_split[0]);
        lipp[12] =(byte)Short.parseShort(lip_split[1]);
        lipp[13] =(byte)Short.parseShort(lip_split[2]);
        lipp[14] =(byte)Short.parseShort(lip_split[3]);
        //计算第一个crc值

        int crc_1 =getCrc(lipp);
        System.out.println("crc校验1： "+crc_1);//显示crc值
        lipp[9] =(byte) ((crc_1>>8)&0xff);//crc处理高位
        lipp[10] =(byte) (crc_1&0xff);//crc处理低位
        LOGGER.debug("计算第一个CRC:"+crc_1);
        //计算第二个crc值
        int crc_2 =getCrc(lipp);
        System.out.println("crc校验2： "+crc_2);//显示crc值
        lipp[15] =(byte) ((crc_2>>8)&0xff);//crc处理高位
        lipp[16] =(byte) (crc_2&0xff);//crc处理低位
        LOGGER.debug("计算第二个CRC:"+crc_2);
        return lipp;//返回发送的数组
    }
    //方法，数据接收解析，数组
    private static void dnp(byte[] rev_data,int n) {  //rev_data为接收的数组，n为该数组的长度
        //private static void dnp(int n) {
        //接收数据处理
        int i;
      //  if((rev_data[0]==0x05)&&(rev_data[1]==0x64)){    //判断头文件，报错

            String source_id = rev_data[7]+"."+rev_data[8];//输出源地址
            System.out.println("源地址为："+source_id);
            LOGGER.debug("获取到源地址:"+source_id);
            //只获取有效的重量数据
            LOGGER.debug("开始提取有效重量数据...");
            byte[] new_data = new byte[n-11];
            for(i=0;i<(n-11);i++){
                new_data[i]=rev_data[i+9];//重新定义一个新的只含有效数据的数组
            }
            getList(new_data,(n-11),source_id);//对新的数组进行处理
       // }
      //  else {
        //    System.out.println("receive data error!!!");//当头文件不是0x0564时报错
      //  }
    }
    //方法，解析有效数据方法
    private static void getList(byte[] list,int n,String Sourse_id) {
        String[] strdata=new String[100];
        if (n%4==0){
            int i = 0;
            int[] da = new int[(n/4)];//每隔四位重新新建一个数组
            for (int j=0;j<(n/4);j++) {
                //将四个数据重新组合成一个数据
                da[j] = (int) (list[(4*i)]*1)+(int)(list[(4*i)+1]*2)+(int)(list[(4*i)+2]*4)+(int)(list[(4*i)+3]*8);
                i++;
                strdata[2*j]="测量点"+(j+1);
                strdata[(2*j+1)] =String.valueOf(da[j]);
                System.out.println(da[j]);//显示这个数据
                LOGGER.debug("重量数据"+(j+1)+":"+da[j]);
            }
            System.out.println(Sourse_id);
            for(int p=0;p<strdata.length;p++)
            {
                System.out.println(strdata[p]);
            }

        }
        else {
            System.out.println("heavy data error!!!!");
            LOGGER.error("heavy data error!!!!");
        }
        try {
            insert(strdata); //往数据库插入数据
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("往数据库插入数据失败...");
        }

    }
    //方法，crc校验 16位
    private static Integer getCrc(byte[] data) {
        int high;
        int flag;
        Integer wcrc = 0xffff; // 16位寄存器，所有数位均为1
        for (int i = 0; i < data.length; i++) {
            high = wcrc >> 8; // 16 位寄存器的高位字节
            wcrc = high ^ data[i];// 取被校验串的一个字节与 16 位寄存器的高位字节进行“异或”运算
            for (int j = 0; j < 8; j++) {
                flag = wcrc & 0x0001;
                wcrc = wcrc >> 1;// 把这个 16 寄存器向右移一位
                // 若向右(标记位)移出的数位是 1,则生成多项式 1010 0000 0000 0001 和这个寄存器进行“异或”运算
                if (flag == 1)
                    wcrc ^= 0xa001;
            }
        }
        return wcrc;//16位二进制
        // return Integer.toHexString(wcrc); //参数所表示的值以十六进制
    }
    public static Connection getConn() {
        LOGGER.debug("建立数据库连接...");
        String user = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/StationDatabase?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String driver = "com.mysql.cj.jdbc.Driver";
        Connection conn=null;
        try {
            //加载驱动
            Class.forName(driver);
            //创建连接
            conn = DriverManager.getConnection(url, user, password);
            LOGGER.debug("数据库连接成功");
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
        LOGGER.info("开始写入数据库...");
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
            LOGGER.debug("开始分类数组...");
            for(int i =0,j=0,k=0;i<temp.length;i++){
                if(i%2==0){
                    temp01[j++]=temp[i];
                }else {
                    temp02[k++]=temp[i];
                }
            }
            LOGGER.debug("分类成功。");
            //分别将temp01的测量点名称和temp02的测量数据值插入数据库中对应的测量数据表中。
            LOGGER.info("开始往数据表插入数据...");
            for (int m=0,n=0; m<temp01.length|n<temp02.length;m++,n++) {
                String sql = "INSERT INTO 测量数据表(测量点名称,测量数据值) VALUES ('"+temp01[m]+"','"+temp02[n]+"')" ;
                num=stmt.executeUpdate(sql);
                System.out.println("插入数据成功！");
            }
            LOGGER.debug("插入完成。");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.info("写入数据库成功");
        return num;
    }

    /**功能介绍如下：实现监测点信息表，用户实现增加，删除，修改的功能。
     * 主要构建三个方法，分别为用户向监测点信息表中添加相关信息，删除相关信息，修改相关信息。
     * @param MonitorId,MonitorName,MonitorPosition;
     * @return
     * @throws SQLException
     */
}