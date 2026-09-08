package com.chatsd.eleicao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class Central {
    private int portaTCP;    
    private int portaUDP;
    private ServerSocket servidorTCP;
    private Socket cliente;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private UrnaServidor urna;

    public Central() {
        try {
            this.portaTCP = 3322;            
            this.portaUDP = 2233;
            this.servidorTCP = new ServerSocket(this.portaTCP);
            this.servidorTCP.setReuseAddress(true);
            this.urna = new UrnaServidor();
            
            System.out.println("Central::Central(): online");            
        } catch (Exception e) {
            System.out.println("Central::Central(): Error " + e.getMessage());
        }
    }
    
    public void startVotacao(int quantidadeVotos) {
        try{
            for (int i = 0; i < quantidadeVotos; i++) {
                this.cliente = servidorTCP.accept();
                this.writer = new ObjectOutputStream(this.cliente.getOutputStream());
                this.reader = new ObjectInputStream(this.cliente.getInputStream());
                this.confirmConnection(i + 1);
            }
            this.showResults();
        } catch (Exception e) {
            System.out.println("Central::iniciarVotacao() Error: " + e.getMessage());
        }
    }
    
    private void confirmConnection(int numberClient){
         try{
            String resposta = this.reader.readUTF();
            System.out.println("Central::confirmConnection(): " + resposta + " " + numberClient);
            this.writer.writeUTF("Central::confirmConnection(): REQUEST_ACCEPTED");
            this.writer.flush();
            this.saveVoto();
        } catch (Exception e) {
            System.out.println("Central::confirmConnection() Error: " + e.getMessage());
        }
    }
    
    private void saveVoto() {
        try {
            int valueVoto = this.reader.readInt();
            CandidatoEnum candidato = CandidatoEnum.fromValor(valueVoto);
            if (candidato == null) {
                System.out.println("Central::saveVoto(): OPÇÃO INVÁLIDA");
                throw new Exception("Não foi possível identificar o candidato");
            }
            this.urna.computaVoto(candidato);
            System.out.println("Central::saveVoto(): Voto computado");
        } catch (Exception e) {
            System.out.println( "Central::saveVoto(): Error " + e.getMessage());
        }
    }
    
    public void showResults() {
        try {
            Thread.sleep(5000);
            String mensagem = String.join("\n", this.urna.calcularResultado());
            byte[] msg = mensagem.getBytes();
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            DatagramSocket ds = new DatagramSocket();
            DatagramPacket pckt = new DatagramPacket(msg, msg.length, addr, this.portaUDP);
            ds.send(pckt);
            ds.close();
        } catch (Exception e) {
            System.out.println("Central::showResults() Error: " + e.getMessage());
        }
    }
    
    public void close() {
        try {
            if (this.writer != null) {
                this.writer.close();
            }
            if (this.cliente != null) {
                this.cliente.close();
            }
            if (this.servidorTCP != null) {
                this.servidorTCP.close();
            }
            System.out.println("Central::close() ServidorTCP fechado.");
        } catch (Exception e) {
            System.out.println("Central::close() Error: " + e.getMessage());
        }
    }
}