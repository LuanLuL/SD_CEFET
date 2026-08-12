package com.chatsd.chatsd;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ServidorTCP {
    public void execute(int numberClients) {
        try{
            int clientsLeft = numberClients;
            ServerSocket server = new ServerSocket(3322);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() Servidor iniciado na porta 3322"); // TODO: Alteração futura
            
           
            
            while(clientsLeft > 0){
                Socket client = server.accept();
                
                ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());
                writer.flush();
                String msg = JOptionPane.showInputDialog("Olá, você está conectado ao servidor");
                writer.writeUTF(msg + "\n\nSeu IP de cliente é: " + client.getInetAddress().getHostAddress());
                writer.close();
                
                ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
            
                JOptionPane.showMessageDialog(null, "ServidorTCP::execute(): " + reader.readUTF());
                reader.close();

                client.close();
                clientsLeft--;
                System.out.println("Cliente conectou! Restante " + clientsLeft);
                
            }
            
        } catch(HeadlessException | IOException error) {
            System.out.println(error);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() " + error.getMessage());
        }
    }
}
