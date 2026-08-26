package com.chatsd.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;

public class UDP {

    public static void main(String[] args) {
        try{
            int port = 6666;
            DatagramSocket ds = new DatagramSocket(port);
            byte[] msg = new byte[256];
            DatagramPacket pckt = new DatagramPacket(msg, msg.length);

            System.out.println("Ouvindo porta: " + port);
            
            ds.receive(pckt);
            ds.close();
        
            JOptionPane.showMessageDialog(
                null,
                "UDP: " + new String(pckt.getData())
            );
        
        } catch (Exception error){
            System.out.println(error);

            JOptionPane.showMessageDialog(
                null,
                "UDP: " + error.getMessage()
            );
        }       
    }
}
