
package pacote;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class DesktopRecepcaoThreads implements Runnable {
    private Util utils;
    
    public DesktopRecepcaoThreads(){
        this.utils = new Util();
    }

    @Override
    public void run() {
        try{
            while(true){
                ServerSocket receptor = new ServerSocket(utils.getPortaRecepcaoDesktop());
                receptor.setReuseAddress(true);
                Socket client = receptor.accept();
                //ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
                //String mensagem = reader.readUTF();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String mensagem = reader.readLine();
                
                
                reader.close();
                client.close();
                receptor.close();
                String arquivo = utils.getPathDesktopTxt();
                FileWriter fwriter = new FileWriter(arquivo, true);
                fwriter.write(mensagem);
                fwriter.close();
            }
        } catch(Exception error){
           System.out.println("DesktopRecepcaoThreads::run: Error " + error.getMessage());
           JOptionPane.showMessageDialog(null, "DesktopRecepcaoThreads::run: Error " + error.getMessage());
        }
    }
    
}
