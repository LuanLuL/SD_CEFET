package com.chatsd.chatsd;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ServidorTCP {
    public void execute() {
        try{
            ServerSocket server = new ServerSocket(3322);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() Servidor iniciado na porta 3322");
            
            Socket client = server.accept();

            ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(client.getInputStream());

            System.out.println("Escrevendo mensagem!");
            String msg = JOptionPane.showInputDialog("Servidor::execute(): Olá, você está conectado ao servidor");
            writer.writeUTF(msg);
            writer.flush();
            System.out.println("Mensagem enviada!");
            
            System.out.println("Aguardando leitura!");
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute(): " + reader.readUTF());
            System.out.println("Mensagem recebida!");

            writer.close();
            reader.close();
            client.close();
            server.close();
        } catch(HeadlessException | IOException error) {
            System.out.println(error);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() " + error.getMessage());
        }
    }
}
