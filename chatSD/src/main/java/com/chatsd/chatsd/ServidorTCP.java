package com.chatsd.chatsd;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ServidorTCP {
    public void execute() {
        try{
            ServerSocket server = new ServerSocket(3322);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() Servidor iniciado na porta 3322"); // TODO: Alteração futura
            
            Socket client = server.accept();
            
            ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());
            writer.flush();
            
            String msg = JOptionPane.showInputDialog("Olá, você está conectado ao servidor");
            
            writer.writeUTF(msg); // TODO: Alteração futura
            
            writer.close();
            client.close();
            
        } catch(HeadlessException | IOException error) {
            System.out.println(error);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() " + error.getMessage());
        }
    }
}
