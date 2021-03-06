/**
 * FileName: MulSocketServer
 * Author:   DannyBlue
 * Date:     2018/9/22 17:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package tests.SocketDemo;

import java.io.*;
import java.net.*;

/**
 * 复用连接的echo服务器

 * 功能：将客户端发送的内容反馈给客户端
 *
 * @author DannyBlue
 * @create 2018/9/22
 * @since 1.0.0
 */

public class MulSocketServer {

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

            System.out.println("服务器已启动：");

            //获得连接

            socket = serverSocket.accept();

            //初始化流

            is = socket.getInputStream();

            os = socket.getOutputStream();

            byte[] b = new byte[1024];

            for(int i = 0;i < 3;i++){

                int n = is.read(b);

                //输出

                System.out.println("客户端发送内容为：" + new String(b,0,n));

                //向客户端发送反馈内容

                os.write(b, 0, n);

            }

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