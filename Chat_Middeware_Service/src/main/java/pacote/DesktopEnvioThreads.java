package pacote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class DesktopEnvioThreads implements Runnable {
    private ServerSocket emissor;
    private Socket client;
    private Util utils;

      
    public DesktopEnvioThreads(){
        this.utils = new Util();
    }
    
    @Override
    public void run() {
        try{
            while(true){
                FileReader fReader = new FileReader(utils.getPathDesktopTxt());
                BufferedReader buffer = new BufferedReader(fReader);
                String msgs = "";
                while(buffer.ready()){
                    msgs += buffer.readLine();
                }
                buffer.close();
                fReader.close();
                emissor = new ServerSocket(utils.getPortaEnvioDesktop());
                emissor.setReuseAddress(true);
                client = emissor.accept();
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                output.writeUTF(msgs);
                output.close();
                client.close();
                emissor.close();
            }
        } catch(Exception error){
            System.out.println("DesktopEnvioThreads::run: Error " + error.getMessage());
            JOptionPane.showMessageDialog(null, "DesktopEnvioThreads::run: Error " + error.getMessage());
        }
    }
    
}
