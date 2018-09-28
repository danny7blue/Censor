/**
 * FileName: LogicThread
 * Author:   DannyBlue
 * Date:     2018/9/22 17:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package tests;

import java.io.*;
import java.net.*;

/**
 * 服务器端逻辑线程
 *
 * @author DannyBlue
 * @create 2018/9/22
 * @since 1.0.0
 */


public class LogicThread extends Thread {

    Socket socket;

    InputStream is;

    OutputStream os;

    public LogicThread(Socket socket){

        this.socket = socket;

        start(); //启动线程

    }



    public void run(){

        byte[] b = new byte[1024];

        try{

            //初始化流

            os = socket.getOutputStream();

            is = socket.getInputStream();

            for(int i = 0;i < 3;i++){

                //读取数据

                int n = is.read(b);

                //逻辑处理

                byte[] response = logic(b,0,n);

                //反馈数据

                os.write(response);

            }

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            close();

        }

    }



    /**

     * 关闭流和连接

     */

    private void close(){

        try{

            //关闭流和连接

            os.close();

            is.close();

            socket.close();

        }catch(Exception e){}

    }



    /**

     * 逻辑处理方法,实现echo逻辑

     * @param b 客户端发送数据缓冲区

     * @param off 起始下标

     * @param len 有效数据长度

     * @return

     */

    private byte[] logic(byte[] b,int off,int len){

        byte[] response = new byte[len];

        //将有效数据拷贝到数组response中

        System.arraycopy(b, 0, response, 0, len);

        return response;

    }

}