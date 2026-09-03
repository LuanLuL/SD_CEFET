
package com.chatsd.eleitor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import javax.swing.JOptionPane;

public class UrnaCliente {
    // variáveis para armazenar portas de comunicação
    private int portaTCP;    
    private int portaUDP;
    
    // variáveis para comunicação UTP Cliente <--> Servidor
    private Socket cliente;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    
    // variáveis para controlar os JFrame Form
    private Painel screenVotacao;
    
    // variáveis para comunicação UDP Cliente <--> Servidor
    private InetSocketAddress group;
    private NetworkInterface netInf;
    private MulticastSocket multicastSocket;
    
    // Construtor
    public UrnaCliente() {
        try {
            // Configurando variáveis para armazenar portas de comunicação
            this.portaTCP = 3322;
            this.portaUDP = 2233;
            
            // Configurando para comunicação UTP Cliente <--> Servidor
            this.cliente = new Socket("127.0.0.1", this.portaTCP);
            this.writer = new ObjectOutputStream(this.cliente.getOutputStream());
            this.reader = new ObjectInputStream(this.cliente.getInputStream());
            
            // Iniciando variáveis para controlar os JFrame Form
            this.screenVotacao = new Painel();
            
            // Configurando variáveis para comunicação UDP Cliente <--> Servidor
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            this.group = new InetSocketAddress(addr, this.portaUDP);
            this.netInf = NetworkInterface.getByName("Wi-Fi");
            this.multicastSocket = new MulticastSocket(group.getPort());
            
            System.out.println("Cliente::Cliente(): online");
        } catch (Exception e) {
            System.out.println("Cliente::Cliente(): Error " + e.getMessage());
        }
    }
    
    // Responsável por solicitar conexão e aguardar confirmação do servidor
    public void confirmConnection(){
        try{
            this.writer.writeUTF("Cliente::confirmConnection(): REQUEST_ACCESS");
            this.writer.flush();
            
            String resposta = this.reader.readUTF();
            System.out.println("Cliente::confirmConnection(): " + resposta);
            
            this.screenVotacao.setVisible(true);
        } catch (Exception e) {
            System.out.println("Cliente::confirmConnection(): Error " + e.getMessage());
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
            System.out.println("Cliente::results(): Error " + e.getMessage());
        }
    }
    
    public void close() {
        try {
            if (this.reader != null) {
                this.reader.close();
            }
            if(this.writer != null){
                this.writer.close();
            }
            if (this.cliente != null) {
                this.cliente.close();
            }
            if (this.multicastSocket != null) {
                this.multicastSocket.close();
            }
            System.out.println("Cliente::close() Cliente fechado.");
        } catch (Exception e) {
            System.out.println("Cliente::close() Error " + e.getMessage());
        }
    }
}
