
package com.chatsd.chatsd_issuer;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ClientTCP {
    public void execute() {
        try {
            Socket client = new Socket("127.0.0.1", 3322);
            
            ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());
            
            System.out.println("Aguardando leitura!");
            JOptionPane.showMessageDialog(null, "ClientTCP::execute(): " + reader.readUTF());
            System.out.println("Mensagem recebida!");

            System.out.println("Escrevendo mensagem!");
            String msg = JOptionPane.showInputDialog("Cliente::execute(): Resposta para o servidor");
            writer.writeUTF(msg);
            writer.flush();
            System.out.println("Mensagem enviada!");
            
            writer.close();
            reader.close();
            client.close();           
        } catch(HeadlessException | IOException error) {
            System.out.println(error);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() " + error.getMessage());
        }

    }
}
