package network;

import java.net.InetAddress;

public class SimpleDNS {

    public static void main(String[] args) {
        //getAllDNS();
        getDNS();
    }

    private static void getAllDNS() {
        try {
            InetAddress[] address = InetAddress.getAllByName("2271h47c40.iask.in");//www.baidu.com
            for (int i = 0; i < address.length; i++) {
                System.out.println(address[i].getHostAddress());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }

    private static void getDNS() {
        try {
            InetAddress address = InetAddress.getByName("2271h47c40.iask.in");//mdm.zte.com.cn
            System.out.println(address.getHostAddress());
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }

}