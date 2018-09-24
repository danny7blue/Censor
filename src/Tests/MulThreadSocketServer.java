/**
 * FileName: MulThreadSocketServer
 * Author:   DannyBlue
 * Date:     2018/9/22 17:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package Tests;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 支持多客户端的服务器端实现
 *
 * @author DannyBlue
 * @create 2018/9/22
 * @since 1.0.0
 */


public class MulThreadSocketServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        Socket socket = null;

        ExecutorService executor = Executors.newCachedThreadPool();

        //监听端口号

        int port = 10000;

        try {

            //建立连接

            serverSocket = new ServerSocket(port);

            System.out.println("服务器已启动：");

            while(true){

                //获得连接

                socket = serverSocket.accept();

                //启动线程

//                new LogicThread(socket);
                executor.execute(new LogicThread(socket));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }finally{

            try{

                //关闭连接

                serverSocket.close();

            }catch(Exception e){}

        }

    }

}
