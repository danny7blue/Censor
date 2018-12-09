package network;

import database.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.TestMain;
import network.*;

import ui.Myclass;

import javax.swing.*;

//public class Test_System extends Server_Xian{
//    public static void main(String[] args) {
//        boolean a=true;
//        Myclass  m1 = new Myclass ();
//        Server_Xian soc = new Server_Xian(m1);
//        a=soc.socket_listen();
////        ReceData rec = new ReceData();
////        SendData sen = new SendData();
////        Thread t1 = new Thread(rec);
////        Thread t2 = new Thread(sen);
////        t1.start();
////        t2.start();
//    }
//}




//class ReceData extends Server_Xian implements Runnable{
//
//    public void run(){
////        int[] heavydata=new int[100];
////        boolean value = false;
//////        Server_Xian soc = new Server_Xian();
////        System.out.println(value);
////        SocketDeal();
//            socket_listen();
//        for(int i = 0;i <4;i++) {//for(int i = 0;i <4;i++){
////                Thread.sleep(2000);
////                sendData();
//                  SocketDeal();//处理socket数据流
//        }
//    }
//}
//class SendData extends ReceData implements Runnable{
//
//
//    public void run(){
//
//    }
//}