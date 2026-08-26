
package com.chatsd.udp_multicast_issuer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

public class UDP_MULTICAST_Issuer {

    public static void main(String[] args) {
        try{
            
            byte[] msg = JOptionPane.showInputDialog("Mensagem a enviar: ").getBytes();
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            DatagramSocket ds = new DatagramSocket();
            DatagramPacket pckt = new DatagramPacket(msg, msg.length, addr, 6667);
            
            ds.send(pckt);
            ds.close();
            
        } catch (Exception error){
            System.out.println(error);

            JOptionPane.showMessageDialog(
                null,
                "UDP_Issuer: " + error.getMessage()
            );
        }
    }
}
