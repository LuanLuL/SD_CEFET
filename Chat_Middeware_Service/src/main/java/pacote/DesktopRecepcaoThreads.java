
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
    private volatile boolean statusServidor = false;
    private ServerSocket receptor;
    private Socket client;
    
    public DesktopRecepcaoThreads(){
        this.utils = new Util();
    }

    @Override
    public void run() {
        try{
            while(!this.statusServidor){
                this.receptor = new ServerSocket(utils.getPortaRecepcaoDesktop());
                this.receptor.setReuseAddress(true);
                this.client = receptor.accept();
                //ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
                //String mensagem = reader.readUTF();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String mensagem = reader.readLine();
                reader.close();
                this.client.close();
                this.receptor.close();
                String arquivo = utils.getPathDesktopTxt();
                FileWriter fwriter = new FileWriter(arquivo, true);
                fwriter.write(mensagem);
                fwriter.close();
            }
        } catch(Exception error){
            if(!this.statusServidor){
                System.out.println("DesktopRecepcaoThreads::run: Error " + error.getMessage());
                JOptionPane.showMessageDialog(null, "DesktopRecepcaoThreads::run: Error " + error.getMessage());
            }
        }
    }
    
    public void fecharServidor(){
        this.statusServidor = true;
        try{
            if(this.client != null && !this.client.isClosed()){
                this.client.close();
            }
            if(this.receptor != null && !this.receptor.isClosed()){
                this.receptor.close();
            }
        } catch(Exception error){
            if(this.statusServidor){
                System.out.println("DesktopRecepcaoThreads::fecharServidor: Error " + error.getMessage());
                JOptionPane.showMessageDialog(null, "DesktopRecepcaoThreads::fecharServidor: Error " + error.getMessage());
            }
        }
    }
    
}
