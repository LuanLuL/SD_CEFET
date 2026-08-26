package com.chatsd.udp_multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import javax.swing.JOptionPane;

public class UDP_MULTICAST {

    public static void main(String[] args) {
        try{
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            InetSocketAddress group = new InetSocketAddress(addr, 6667);
            NetworkInterface netInf = NetworkInterface.getByName("Wi-Fi");
            MulticastSocket s = new MulticastSocket(group.getPort());
            s.joinGroup(group, netInf);
            
            byte[] msg = new byte[1000];
            DatagramPacket pckt = new DatagramPacket(msg, msg.length);
            
            s.receive(pckt);
            JOptionPane.showMessageDialog(null, new String(msg));
        
        } catch (Exception error){
            System.out.println(error);

            JOptionPane.showMessageDialog(
                null,
                "UDP_Issuer: " + error.getMessage()
            );
        }
    }
}
