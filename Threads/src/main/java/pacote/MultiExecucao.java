package pacote;

public class MultiExecucao implements Runnable{
    private int valor = 0;
    
    public MultiExecucao(int dado){
        this.valor = dado;
    }

    @Override
    public void run() {
       for(int i=0; i<10; i++){
           System.out.println("Impressao da thread: " + this.valor);
       } 
    }
    
}
