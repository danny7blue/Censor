package Tests;

import java.io.*;
import java.net.*;

/**
 * FileName: SimpleSocketClient
 * Author:   DannyBlue
 * Date:     2018/9/22 17:19
 * Description: 简单的socket测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class SimpleSocketClient {

    public static void main(String[] args) {

        Socket socket = null;

        InputStream is = null;

        OutputStream os = null;

        //服务器端IP地址

        String serverIP = "127.0.0.1";

        //服务器端端口号

        int port = 10000;

        //发送内容

        String data = "Hello";

        try {

            //建立连接

            socket = new Socket(serverIP,port);

            //发送数据

            os = socket.getOutputStream();

            os.write(data.getBytes());

            //接收数据

            is = socket.getInputStream();

            byte[] b = new byte[1024];

            int n = is.read(b);

            //输出反馈数据

            System.out.println("服务器反馈：" + new String(b,0,n));

        } catch (Exception e) {

            e.printStackTrace(); //打印异常信息

        }finally{

            try {

                //关闭流和连接

                is.close();

                os.close();

                socket.close();

            } catch (Exception e2) {}

        }

    }

}