
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
            
            JOptionPane.showMessageDialog(null, "ClientTCP::execute(): " + reader.readUTF());
            reader.close();
            
            ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());
            writer.flush();
            String msg = JOptionPane.showInputDialog("Qual sua mensagem de resposta");
            writer.close();
            
            client.close();
            
        } catch(HeadlessException | IOException error) {
            System.out.println(error);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() " + error.getMessage());
        }

    }
}
