package network;
import database.Test;
import org.apache.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.InetAddress;
import database.TestMain;
import ui.MyDefaultTreeCellRenderer;
import ui.Myclass;

public class Server_Xian{
    static ServerSocket serverSocket = null;
    static Socket socket = null;
    static OutputStream os = null;
    static InputStream is = null;
    static InetAddress ia=null;
    static int port = 800;//监听端口号8087
    boolean link_value = false;
    private static  final Logger LOGGER = Logger.getLogger(Server_Xian.class);
    static String IP_A="1.1.1.1";
    static String IP_B=" ";
    Myclass myclass;
    public Server_Xian(Myclass myclass)
    {
        this.myclass=myclass;
    }
    //主函数
    public static void main(String[] args) {
//        deal();

    }
    //处理输入流
    public boolean socket_listen(){
        LOGGER.info("socket开启监听...");
//        send_message Send=new send_message();//实例发送短信
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Boolean result = false;
//        int count = 0;

//        try {
//            Thread.sleep(3 * 1000); //设置暂停的时间 5 秒
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try {//获取本机IP地址,并发送短信

//            ia = ia.getLocalHost();
//
//            String lip = ia.getHostAddress();
//            Send.data=lip;//ip地址
            //Send.sendSms();//发送短信
            //建立连接
            System.out.println(link_value);
            myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
            TestMain read_port = new TestMain();//实例读端口
            String Key = "Port";
            String ports = read_port.GetValueBykey(Key);//读出数据库的端口号
            port = (int) Short.parseShort(ports);
            //port=(int)Short.parseShort(TestMain.GetValueBykey(send_message.ports));//获取数据库的端口
            serverSocket = new ServerSocket(port);
            System.out.println(port);
            socket = serverSocket.accept();
            link_value = true;
            System.out.println(link_value);
            myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
            System.out.println(("connect successful!"));//输出“connect successful!”
            LOGGER.debug("连接成功...");
            is = socket.getInputStream();
            os = socket.getOutputStream();

            while (true){//for(int i = 0;i <4;i++){
                if(is.available()>0){
                    System.out.println(link_value=true);
                    myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
                }else {
                    System.out.println(link_value=false);
                    myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
                }

                    SocketDeal();//处理socket数据流
                    System.out.println(link_value=true);
                    myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));


                for(int i = 0;i <11;i++){  //10分钟
                    Thread.sleep(1000);//1s
                    if(i<10){
                        System.out.println(link_value);
//                        sendData();
                        LOGGER.debug("发送心跳包成功...");
                        //link_value =true;
                        //myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
                    }else {
                        link_value =false;
                        myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
                        System.out.println(link_value);
                        LOGGER.error("数据接收超时...");
                        break;
                    }

                }
            }

//
//            while (true){
//                Thread.sleep(2000);
//                sendData();
//            }

        }catch (Exception e){
            e.printStackTrace();
            link_value =false;
            myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
            System.out.println(link_value);
            LOGGER.error("socket连接失败...");
            try {
                os.close();
                is.close();
                socket.close();
                serverSocket.close();
                link_value =false;
                myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
                System.out.println(link_value);
                LOGGER.debug("关闭socket...");
            } catch (Exception a) {
                a.printStackTrace();
            }
            Myclass  m1 = new Myclass ();
            int[] heavydata=new int[100];
            boolean value = false;
            Server_Xian soc = new Server_Xian(m1);
            value = soc.socket_listen();
            LOGGER.debug("重新开启socket...");
        }finally {
            try {
//                os.close();
//                is.close();
//                socket.close();
//                serverSocket.close();
                link_value =false;
                myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
                System.out.println(link_value);
            } catch (Exception e) {
                e.printStackTrace();
                link_value =false;
                myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
                System.out.println(link_value);
                LOGGER.error("关闭socket失败...");
            }
        }
//        myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
        return link_value;
    }
    public  void SocketDeal() {
        LOGGER.info("接收数据流...");
//        for(int i = 0;i <4;i++){//for(int i = 0;i <4;i++)
            byte[] b = new byte[1024];//定义字符串b
            try {
                int n = is.read(b);//计算读取到的b的长=度;
                System.out.println("客户端发送的内容为" + new String(b, 0, n));//显示
                dnp(b, n);//数据解析
                LOGGER.debug("数据解析中...");
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("读取数据流失败...");
            }
    }

    //方法，数据发送处理
    public void sendData(){
        LOGGER.info("发送心跳包...");
        try {
            ia = ia.getLocalHost();

            IP_A = ia.getHostAddress();
        }catch (Exception e){
            e.printStackTrace();
        }

        byte lipp[] = new byte[16];
        String[] lip_split = IP_A.split("\\.");//去掉IP地址的间隔“.”
        for (String c : lip_split) {
            System.out.print(c + " ");  //输出以空格间隔的ip
        }
        LOGGER.debug("获取本地IP地址成功:" + IP_A);
        System.out.println();//隔一行
        //lipp=sendData(lip_split);
        //                    lipp = sendData(lip_split);//送到方法处理发送的数据

        byte lippi[] = new byte[17];//定义输出数组的长度
        //定义头文件
        byte head_1 = 0x05;
        byte head_2 = 0x64;
        byte con_num1 = 0x01;//定义控制字
        String des_add = "10.1";//定义目的地址
        String sou_add = "20.1";//定义源地址
        String[] des_add_split=des_add.split("\\.");//去掉目的地址的间隔“.”
        String[] sou_add_split=sou_add.split("\\.");//去掉源地址的间隔“.”
        //添加头文件
        lippi[0] =head_1;
        lippi[1] =head_2;
        char lengh=17;//定义长度

        lippi[2] =  (byte) ((lengh & 0xFF00) >> 8);//长度的高位字节
        lippi[3] = (byte) (lengh & 0xFF);//长度的低位字节

        lippi[4] =con_num1;//控制字

        lippi[5] =(byte)Short.parseShort(des_add_split[0]);//目的地址
        lippi[6] =(byte)Short.parseShort(des_add_split[1]);

        lippi[7] =(byte)Short.parseShort(sou_add_split[0]);//源地址
        lippi[8] =(byte)Short.parseShort(sou_add_split[1]);
        //发送IP地址数据
        lippi[11] =(byte)Short.parseShort(lip_split[0]);
        lippi[12] =(byte)Short.parseShort(lip_split[1]);
        lippi[13] =(byte)Short.parseShort(lip_split[2]);
        lippi[14] =(byte)Short.parseShort(lip_split[3]);
        //计算第一个crc值``````````````````````

        int crc_1 =getCrc(lipp);
        System.out.println("crc校验1： "+crc_1);//显示crc值
        lippi[9] =(byte) ((crc_1>>8)&0xff);//crc处理高位
        lippi[10] =(byte) (crc_1&0xff);//crc处理低位
        LOGGER.debug("计算第一个CRC:"+crc_1);
        //计算第二个crc值
        int crc_2 =getCrc(lipp);
        System.out.println("crc校验2： "+crc_2);//显示crc值
        lippi[15] =(byte) ((crc_2>>8)&0xff);//crc处理高位
        lippi[16] =(byte) (crc_2&0xff);//crc处理低位
        LOGGER.debug("计算第二个CRC:"+crc_2);
        try {
            os.write(lippi);
            link_value =true;
            myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
            LOGGER.debug("发送成功。");
        } catch (IOException e) {
            //e.printStackTrace();
            link_value =false;
            myclass.getTree().setCellRenderer(new MyDefaultTreeCellRenderer(link_value));
            //sendData();
            try {
                os.close();
                os.write(lippi);
            }catch (IOException A){
                A.printStackTrace();
            }
        }

//        try {
//            os.close();
//            is.close();
//            socket.close();
//            serverSocket.close();
//            link_value =false;
//            System.out.println(link_value);
//        }catch (Exception e){
//                    e.printStackTrace();
//                }
    }
    //方法，数据接收解析，数组
    private static void dnp(byte[] rev_data,int n) {  //rev_data为接收的数组，n为该数组的长度
        //private static void dnp(int n) {
        //接收数据处理
        int i;
        LOGGER.info("提取有效数据...");
//        if((rev_data[0]==0x05)&&(rev_data[1]==0x64)){    //判断头文件，报错
        String source_id = (rev_data[7]&0xff)+"."+(rev_data[8]&0xff);//输出源地址
        System.out.println("源地址为："+source_id);
        LOGGER.debug("获取到源地址:"+source_id);
        //只获取有效的重量数据
        byte[] new_data = new byte[n-11];
        for(i=0;i<(n-11);i++){
            new_data[i]=rev_data[i+9];//重新定义一个新的只含有效数据的数组
        }
        getList(new_data,(n-11),source_id);//对新的数组进行处理
//         }
//          else {
//            System.out.println("receive data error!!!");//当头文件不是0x0564时报错
//          }
    }
    //方法，解析有效数据方法
    public static void getList(byte[] list,int n,String Sourse_id) {
        Test data_a = new Test();
        int[] strdata=new int[n/2];
        if (n%4==0){

            int i = 0;
            int[] da = new int[(n/4)];//每隔四位重新新建一个数组
            for (int j=0;j<(n/4);j++) {
                byte[] buf = new byte[4];
                //将四个数据重新组合成一个数据
                //da[j] = (int) (list[(4*i)]*1)+(int)(list[(4*i)+1]*2)+(int)(list[(4*i)+2]*4)+(int)(list[(4*i)+3]*8);
                da[j]=((list[(4*i)]*1)&0xff)<<24
                        | (list[(4*i)+1]*2)<<16
                        | (list[(4*i)+2]*4)<<8
                        | (list[(4*i)+3]*8);
                i++;
                strdata[2*j]=(j+1);//"测量点"
                strdata[(2*j+1)] =da[j];
                System.out.println(da[j]);//显示这个数据
                LOGGER.debug("重量数据"+(j+1)+":"+da[j]);//"重量数据"
            }
            System.out.println(Sourse_id);
            for(int p=0;p<strdata.length;p++)
            {
                System.out.println(strdata[p]);
            }

        }
        else {
            System.out.println("重量数据错误!!!!");
            LOGGER.error("重量数据错误!!!!");
        }
        try {

            data_a.insertMeasurePointData(strdata); //往数据库插
            LOGGER.debug("插入数据中... ");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("往数据库插入数据失败...");
        }
}
    //方法，crc校验 16位
    private static Integer getCrc(byte[] data) {
        LOGGER.info("计算CRC...");
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


//try{
//        socket.sendUrgentData(0xFF);
//    }catch(Exception ex){
//        reconnect();
//    }
}