/**
 * FileName: SimpleSocketServer
 * Author:   DannyBlue
 * Date:     2018/9/22 17:25
 * Description: 简单的socket server端测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package tests;

import java.io.*;
import java.net.*;

/**
 * echo服务器
 * 功能：将客户端发送的内容反馈给客户
 *
 * @author DannyBlue
 * @create 2018/9/22
 * @since 1.0.0
 */


public class SimpleSocketServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        Socket socket = null;

        OutputStream os = null;

        InputStream is = null;

        //监听端口号

        int port = 10000;

        try {

            //建立连接

            serverSocket = new ServerSocket(port);

            //获得连接

            socket = serverSocket.accept();

            //接收客户端发送内容

            is = socket.getInputStream();

            byte[] b = new byte[1024];

            int n = is.read(b);

            //输出

            System.out.println("客户端发送内容为：" + new String(b,0,n));

            //向客户端发送反馈内容

            os = socket.getOutputStream();

            os.write(b, 0, n);

        } catch (Exception e) {

            e.printStackTrace();

        }finally{

            try{

                //关闭流和连接

                os.close();

                is.close();

                socket.close();

                serverSocket.close();

            }catch(Exception e){}

        }

    }

}
