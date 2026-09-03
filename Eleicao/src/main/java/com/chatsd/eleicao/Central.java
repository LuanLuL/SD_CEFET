package com.chatsd.eleicao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

class Central {
    // variáveis para armazenar portas de comunicação
    private int portaTCP;    
    private int portaUDP;
    
    // variáveis para comunicação UTP Servidor <--> Cliente
    private ServerSocket servidorTCP;
    private Socket cliente;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    
    // variável para contabilizar a votação
    private UrnaServidor urna;

    // Construtor
    public Central() {
        try {
            // Configurando variáveis para armazenar portas de comunicação
            this.portaTCP = 3322;            
            this.portaUDP = 2233;
            
            // Configurando para comunicação UTP Servidor <--> Cliente
            this.servidorTCP = new ServerSocket(this.portaTCP);
            
            // Iniciando variável para controlar os votos
            this.urna = new UrnaServidor();
            
            System.out.println("Central::Central(): online");            
        } catch (Exception e) {
            System.out.println("Central::Central(): Error " + e.getMessage());
        }
    }
    
    public void perguntaVoto() {
        try {
            
            CandidatoEnum candidato = null;
            while (candidato == null) {
                String votoInput = JOptionPane.showInputDialog(
                    "Em qual candidato você deseja votar?\n" +
                    "1 - Candidato 1\n" +
                    "2 - Candidato 2\n" +
                    "3 - Candidato 3"
                );
                if (votoInput == null) {
                    continue;
                }
                int voto = Integer.parseInt(votoInput);
                candidato = CandidatoEnum.fromValor(voto);
                if (candidato == null) {
                    System.out.println("Central::perguntaVoto() Error: OPÇÃO INVÁLIDA");
                    continue;
                }
                this.urna.computaVoto(candidato);
                this.writer = new ObjectOutputStream(cliente.getOutputStream());
                this.writer.writeUTF("VOTO_RECEBIDO");
                this.writer.flush();
            }
            System.out.println("Voto computado: " + candidato);
        } catch (Exception e) {
            System.out.println("Central::iniciarVotacao() Error: " + e.getMessage());
        }
        
    }
    
    // Responsável pela recepção e contabilidadea de Clientes
    public void startVotacao(int quantidadeVotos) {
        try{
            for (int i = 0; i < quantidadeVotos; i++) {
                this.cliente = servidorTCP.accept();
                
                // Configurando para comunicação UTP Servidor <--> Cliente
                this.writer = new ObjectOutputStream(this.cliente.getOutputStream());
                this.reader = new ObjectInputStream(this.cliente.getInputStream());
                
                this.confirmConnection(i + 1);
            }
        } catch (Exception e) {
            System.out.println("Central::iniciarVotacao() Error: " + e.getMessage());
        }
        
    }
    
    // Responsável por aguardar a conexão e aceitar confirmação do servidor
    private void confirmConnection(int numberClient){
         try{
            String resposta = this.reader.readUTF();
            System.out.println("Servidor::confirmConnection(): " + resposta + " " + numberClient);
            
            this.writer.writeUTF("Servidor::confirmConnection(): REQUEST_ACCEPTED");
            this.writer.flush();
            
        } catch (Exception e) {
            System.out.println("Central::confirmConnection() Error: " + e.getMessage());
        }
    }
    
    public void showResults() {
        try {
            Thread.sleep(5000);
            CandidatoEnum vencedor = this.urna.calcularVencedor();
            String mensagem;
            if (vencedor == null) {
                mensagem = "Houve um empate entre os candidatos!";
                System.out.println("Central::showResults() Houve um empate entre os candidatos!");
            } else {
               mensagem = "O ganhador da eleição foi o Candidato " + vencedor.getValor() + "!";
               System.out.println("Central::showResults() O ganhador da eleiçao foi o Candidato " + vencedor.getValor() + "!");
            }
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