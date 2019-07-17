/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reta2;
    //Lya15032009
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author killito
 */
public class EscanearRed extends Thread{
   public void checkHosts(String subnet) {
        try{
            InetAddress inAdd;
            for (int i = 0; i < 115; i++) {
                inAdd = InetAddress.getByName("192.168.0." + i);
                if (inAdd.isReachable(150)) {
                    System.out.println("IP: " + inAdd.getHostAddress());
                    System.out.println("HOST: " + inAdd.getHostName());
                    System.out.println();
                }
            }
            System.out.println("Todas las ips escaneadas");
        }
        catch (Exception e){}
   
   }

    public void run(){
        checkHosts("192.168.0");;
    }
}
