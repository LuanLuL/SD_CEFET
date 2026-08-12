
package com.chatsd.chatsd_issuer;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ClientTCP {
    public void execute() {
        try {
            Socket client = new Socket("127.0.0.1", 3322);
            ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
            
            JOptionPane.showMessageDialog(null, "ClientTCP::execute(): " + reader.readUTF());
            reader.close();
            client.close();
            
        } catch(HeadlessException | IOException error) {
            System.out.println(error);
            JOptionPane.showMessageDialog(null, "ServidorTCP::execute() " + error.getMessage());
        }

    }
}
