package com.chatsd.udp_issuer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

public class UDP_Issuer {

    public static void main(String[] args) {
        try{
            int port = 6666;
            InetAddress addr = InetAddress.getByName("200.128.141.162");
            DatagramSocket ds = new DatagramSocket(port);

            
            byte[] msg = JOptionPane.showInputDialog("Mensagem a enviar: ").getBytes();
            
            DatagramPacket pckt = new DatagramPacket(msg, msg.length, addr, port); 
            ds.send(pckt);
            
            System.out.println("Mensagem enviada para porta: " + addr.getCanonicalHostName());
        
        } catch (Exception error){
            System.out.println(error);

            JOptionPane.showMessageDialog(
                null,
                "UDP_Issuer: " + error.getMessage()
            );
        }
    }
}
