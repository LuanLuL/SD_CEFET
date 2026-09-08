package com.chatsd.eleitor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;

public class UrnaCliente {
    private int portaTCP;    
    private int portaUDP;
    private Socket cliente;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Painel screenVotacao;
    private Results screenResultado;
    private InetSocketAddress group;
    private NetworkInterface netInf;
    private MulticastSocket multicastSocket;
    
    // Construtor
    public UrnaCliente() {
        try {
            this.portaTCP = 3322;
            this.portaUDP = 2233;
            this.cliente = new Socket("127.0.0.1", this.portaTCP);
            this.writer = new ObjectOutputStream(this.cliente.getOutputStream());
            this.reader = new ObjectInputStream(this.cliente.getInputStream());
            this.screenVotacao = new Painel();
            this.screenResultado = new Results();
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            this.group = new InetSocketAddress(addr, this.portaUDP);
            this.netInf = NetworkInterface.getByName("Wi-Fi");
            this.multicastSocket = new MulticastSocket(group.getPort());
            System.out.println("Cliente::Cliente(): online");
        } catch (Exception e) {
            System.out.println("Cliente::Cliente(): Error " + e.getMessage());
        }
    }
    
    public void confirmConnection(){
        try{
            this.writer.writeUTF("Cliente::confirmConnection(): REQUEST_ACCESS");
            this.writer.flush();
            
            String resposta = this.reader.readUTF();
            System.out.println("Cliente::confirmConnection(): " + resposta);
            
            this.vote();
        } catch (Exception e) {
            System.out.println("Cliente::confirmConnection(): Error " + e.getMessage());
        }
    }
    
    private void vote() {
        this.screenVotacao.setOnVotar(() -> {
            try {
                int voto = this.screenVotacao.getVotoValue();

                this.writer.writeInt(voto);
                this.writer.flush();

                System.out.println("Cliente::vote(): Voto enviado");

                new Thread(() -> {
                    this.showResults();
                }).start();

            } catch (Exception e) {
                System.out.println(
                    "Cliente::vote(): Error " + e.getMessage()
                );
            }
        });

        this.screenVotacao.setVisible(true);
    }
    
    private String[] transformarResultados(DatagramPacket pckt) {
        try {
            String mensagem = new String(
                pckt.getData(),
                0,
                pckt.getLength()
            );

            return mensagem.split("\n");

        } catch (Exception e) {
            System.out.println(
                "Cliente::deserializador(): Error " + e.getMessage()
            );
            return null;
        }
    }
    
    public void showResults(){
        try {
            this.multicastSocket.joinGroup(this.group, this.netInf);
            byte[] msg = new byte[4096];
            DatagramPacket pckt = new DatagramPacket(msg, msg.length);
            this.multicastSocket.receive(pckt);
            this.screenVotacao.dispose();
            this.screenResultado.setResults(this.transformarResultados(pckt));
            this.screenResultado.setVisible(true);
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
