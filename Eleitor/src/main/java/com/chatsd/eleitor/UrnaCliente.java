
package com.chatsd.eleitor;

import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import javax.swing.JOptionPane;

public class UrnaCliente {
    private int portaTCP;    
    private int portaUDP;
    private Socket cliente;
    private ObjectInputStream reader;
    
    private InetSocketAddress group;
    private NetworkInterface netInf;
    private MulticastSocket multicastSocket;
    
    public UrnaCliente() {
        try {
            this.portaTCP = 3322;
            this.portaUDP = 2233;
            this.cliente = new Socket("127.0.0.1", this.portaTCP);
            this.reader = new ObjectInputStream(this.cliente.getInputStream());
            
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            this.group = new InetSocketAddress(addr, this.portaUDP);
            this.netInf = NetworkInterface.getByName("Wi-Fi");
            this.multicastSocket = new MulticastSocket(group.getPort());
                    
        } catch (Exception e) {
            System.out.println("Cliente::Cliente() Error: " + e.getMessage());
        }
    }
    
    public void confirmVoto(){
        try{
            String resposta = this.reader.readUTF();
            System.out.println("Cliente::isAnswered(): " + resposta);
        } catch (Exception e) {
            System.out.println("Cliente::confirmVoto() Error: " + e.getMessage());
        }
    }
    
    public void showResults(){
        try {
            this.multicastSocket.joinGroup(this.group, this.netInf);
            byte[] msg = new byte[1000];
            DatagramPacket pckt = new DatagramPacket(msg, msg.length);
            this.multicastSocket.receive(pckt);
            JOptionPane.showMessageDialog(null, new String(msg));
        } catch (Exception e) {
            System.out.println("Cliente::results() Error: " + e.getMessage());
        }
    }
    
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (cliente != null) {
                cliente.close();
            }
            if (this.multicastSocket != null) {
                this.multicastSocket.close();
            }
            System.out.println("Cliente::close() Cliente fechado.");
        } catch (Exception e) {
            System.out.println("Cliente::close() Error: " + e.getMessage());
        }
    }
}
