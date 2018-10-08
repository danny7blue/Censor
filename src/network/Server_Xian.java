package network;

import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class Server_Xian {
    static ServerSocket serverSocket = null;
    static Socket socket = null;
    static OutputStream os = null;
    static InputStream is = null;
    static InetAddress ia=null;
    //主函数
    public static void main(String[] args) {
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
            System.out.println();//隔一行
            //建立连接
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();

            //发送数据
            os = socket.getOutputStream();//得到输出流
            System.out.println(("connect successful!"));//输出“connect successful!”
            lipp=sendData(lip_split);//送到方法处理发送的数据
            os.write(lipp);
            //接收数据
            is = socket.getInputStream();//得到输入流
            byte[] b = new byte[1024];//定义字符串b
            int n = is.read(b);//计算读取到的b的长=度;
            System.out.println("客户端发送的内容为" +new String(b,0,n));//显示
            dnp(b,n);//数据解析
            //当接受完收据后断开连接（无）
            os.close();
            is.close();
            socket.close();
            serverSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                os.close();
                is.close();
                socket.close();
                serverSocket.close();
            }catch (Exception e){
                e.printStackTrace();
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
        //计算第二个crc值
        int crc_2 =getCrc(lipp);
        System.out.println("crc校验2： "+crc_2);//显示crc值
        lipp[15] =(byte) ((crc_2>>8)&0xff);//crc处理高位
        lipp[16] =(byte) (crc_2&0xff);//crc处理低位
        return lipp;//返回发送的数组
    }
    //方法，数据接收解析，数组
    private static void dnp(byte[] rev_data,int n) {  //rev_data为接收的数组，n为该数组的长度
    //private static void dnp(int n) {
        //接收数据处理
        int i;
        if((rev_data[0]==0x05)&&(rev_data[1]==0x64)){    //判断头文件，报错

            String source_id = rev_data[7]+"."+rev_data[8];//输出源地址
            System.out.println("源地址为："+source_id);
            //只获取有效的重量数据
            byte[] new_data = new byte[n-11];
            for(i=0;i<(n-11);i++){
                new_data[i]=rev_data[i+9];//重新定义一个新的只含有效数据的数组
            }
            getList(new_data,(n-11),source_id);//对新的数组进行处理
        }
        else {
            System.out.println("receive data error!!!");//当头文件不是0x0564时报错
        }
    }
    //方法，解析有效数据方法
    private static void getList(byte[] list,int n,String Sourse_id) {
        if (n%4==0){
            int i = 0;
            int[] da = new int[(n/4)];//每隔四位重新新建一个数组
            for (int j=0;j<(n/4);j++) {
                //将四个数据重新组合成一个数据
                da[j] = (int)(list[(4*i)]*1)+(int)(list[(4*i)+1]*2)+(int)(list[(4*i)+2]*4)+(int)(list[(4*i)+3]*8);
                i++;
                System.out.println(da[j]);//显示这个数据
                // System.out.println("jh");
            }

            try {// 准备文件666.txt其中的内容是空的
                File f1 = new File("D:/"+Sourse_id+".txt");//在d盘新建一个以源地址命名的txt文件
                if (f1.exists()==false){
                    f1.getParentFile().mkdirs();
                }
                // 创建基于文件的输出流
                FileOutputStream fos = new FileOutputStream(f1);
                // 把数据写入到输出流
                String file_data="";
                int m=0;
                for (m=0;m<(n/4);m++){
                    file_data =da[m]+" ";
                    System.out.print(file_data);
                    fos.write(file_data.getBytes());
                }
                // 关闭输出流
                fos.close();
                System.out.println("输入完成");} catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                System.out.println("creat file error!");
            }
        }
        else {
            System.out.println("heavy data error!!!!");
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

}

