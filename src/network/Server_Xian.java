package network;

import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

public class Server_Xian {
    static ServerSocket serverSocket = null;
    static Socket socket = null;
    static OutputStream os = null;
    static InputStream is = null;
    static InetAddress ia=null;

    public static void main(String[] args) {
        //监听端口号
        int port = 8087;
        //String hello = "Hello,ImServer";
        try {
            byte lipp[] = new byte[16];

            //获取本机IP地址
            ia=ia.getLocalHost();
            String lip=ia.getHostAddress();
            String[] lip_split=lip.split("\\.");//去掉间隔“.”
            for(String c:lip_split){
                System.out.print(c+" ");  //输出ip：169 254 117 061
            }
            System.out.println();
            //建立连接
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();

            os = socket.getOutputStream();//得到输出流
            System.out.println(("connect successful!"));
            lipp=send_data(lip_split);
            //String s=new String(lipp);

            //os.write(s.getBytes());
            String info="用户名：Tom,用户密码：123456";
            os.write(lipp);

            is = socket.getInputStream();//得到输入流
            byte[] b = new byte[1024];//定义字符串b
            int n = is.read(b);//计算读取到的b的长=度;
            System.out.println("客户端发送的内容为" +new String(b,0,n));
            //当接受完收据后断开连接（无）
            dnp(23);//对接收数据处理
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

    //发送数据处理
    private static byte[] send_data(String[] lip_split){
        byte lipp[] = new byte[17];
        //头文件，两个字节
        byte head_1 = 0x05;
        byte head_2 = 0x64;
        byte con_num1 = 0x01;
        String des_add = "10.1";
        String sou_add = "20.1";
        String[] des_add_split=des_add.split("\\.");//去掉间隔“.”
        String[] sou_add_split=sou_add.split("\\.");//去掉间隔“.”

        lipp[0] =head_1;
        lipp[1] =head_2;
        char lengh=17;
        lipp[2] =  (byte) ((lengh & 0xFF00) >> 8);
        lipp[3] = (byte) (lengh & 0xFF);
        lipp[4] =con_num1;//控制字
        lipp[5] =(byte)Short.parseShort(des_add_split[0]);//目的地址
        lipp[6] =(byte)Short.parseShort(des_add_split[1]);

        lipp[7] =(byte)Short.parseShort(sou_add_split[0]);//源地址
        lipp[8] =(byte)Short.parseShort(sou_add_split[1]);
        lipp[11] =(byte)Short.parseShort(lip_split[0]);//发送IP地址
        lipp[12] =(byte)Short.parseShort(lip_split[1]);
        lipp[13] =(byte)Short.parseShort(lip_split[2]);
        lipp[14] =(byte)Short.parseShort(lip_split[3]);
        //计算第一个crc值
        int crc_1 =getCrc(lipp);
        System.out.println("crc校验1： "+crc_1);
        lipp[9] =(byte) ((crc_1>>8)&0xff);
        lipp[10] =(byte) (crc_1&0xff);
        //计算第二个crc值
        int crc_2 =getCrc(lipp);
        System.out.println("crc校验2： "+crc_2);
        lipp[15] =(byte) ((crc_2>>8)&0xff);
        lipp[16] =(byte) (crc_2&0xff);
        return lipp;
    }

    private static void dnp(int n) {
        //接收数据处理
        int i;
        byte[] rev_data ={0x05,0x64,0x00,0x30,0x01,0x12,0x02,0x33,0x21,
                0x01,0x02,0x03,0x04,
                0x05,0x06,0x07,0x08,
                0x10,0x20,0x30,0x40,
                0x56,0x70}; //23
        if((rev_data[0]==0x05)&&(rev_data[1]==0x64))
        {
            String source_id = rev_data[7]+"."+rev_data[8];//输出源地址
            System.out.println("源地址为："+source_id);
            byte[] new_data = new byte[n-11];
            for(i=0;i<(n-11);i++){
                new_data[i]=rev_data[i+9];
            }
            getList(new_data,(n-11));
        }
        else {
            System.out.println("receive data error!!!");
        }
    }

    private static void getList(byte[] list,int n) {
        if (n%4==0){
            int i = 0;
            int[] da = new int[(n/4)];
            for (int j=0;j<(n/4);j++) {
                da[j] = (int)(list[(4*i)]*1)+(int)(list[(4*i)+1]*2)+(int)(list[(4*i)+2]*4)+(int)(list[(4*i)+3]*8);
                i++;
                System.out.println(da[j]);
                // System.out.println("jh");
            }

        }
        else {
            System.out.println("heavy data error!!!!");
        }
    }

    private static Integer getCrc(byte[] data) {
        int high;
        int flag;
        // 16位寄存器，所有数位均为1
        Integer wcrc = 0xffff;
        for (int i = 0; i < data.length; i++) {
            // 16 位寄存器的高位字节
            high = wcrc >> 8;
            // 取被校验串的一个字节与 16 位寄存器的高位字节进行“异或”运算
            wcrc = high ^ data[i];

            for (int j = 0; j < 8; j++) {
                flag = wcrc & 0x0001;
                // 把这个 16 寄存器向右移一位
                wcrc = wcrc >> 1;
                // 若向右(标记位)移出的数位是 1,则生成多项式 1010 0000 0000 0001 和这个寄存器进行“异或”运算
                if (flag == 1)
                    wcrc ^= 0xa001;
            }
        }
        return wcrc;//16位二进制
        // return Integer.toHexString(wcrc); //参数所表示的值以十六进制
    }

}

