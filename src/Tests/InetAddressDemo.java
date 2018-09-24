/**
 * FileName: asda
 * Author:   DannyBlue
 * Date:     2018/9/22 17:10
 * Description: InetAddressDemo
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package Tests;

import java.net.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈演示InetAddress类的基本使用〉
 *
 * @author DannyBlue
 * @create 2018/9/22
 * @since 1.0.0
 */

public class InetAddressDemo {

    public static void main(String[] args) {

        try{

            //使用域名创建对象

            InetAddress inet1 = InetAddress.getByName("www.163.com");

            System.out.println(inet1);

            //使用IP创建对象

            InetAddress inet2 = InetAddress.getByName("127.0.0.1");

            System.out.println(inet2);

            //获得本机地址对象

            InetAddress inet3 = InetAddress.getLocalHost();

            System.out.println(inet3);

            //获得对象中存储的域名

            String host = inet3.getHostName();

            System.out.println("域名：" + host);

            //获得对象中存储的IP

            String ip = inet3.getHostAddress();

            System.out.println("IP:" + ip);

        }catch(Exception e){}

    }

}