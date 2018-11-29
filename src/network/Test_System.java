package network;

import database.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import database.TestMain;
import network.*;
public class Test_System {
    public static void main(String[] args) {
        int[] heavydata=new int[100];
        boolean value = false;
        Server_Xian soc = new Server_Xian();
        value = soc.socket_listen();
        System.out.println(value);
        soc.SocketDeal();

    }

}
