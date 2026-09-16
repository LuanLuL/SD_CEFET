package pacote;

public class Threads {
    public static void main(String[] args) {
        try{
            for(int i=0; i<10; i++){
                System.out.println("Impresso pela thread principal");
            }

            Thread t1 = new Thread(new MultiExecucao(1));
            Thread t2 = new Thread(new MultiExecucao(2));

            MultiExecucao exec3 = new MultiExecucao(3);
            Thread t3 = new Thread(exec3);

            MultiExecucao exec5 = new MultiExecucao(5);        
            MultiExecucao exec6 = new MultiExecucao(6);        
            MultiExecucao exec7 = new MultiExecucao(7);

            Thread t6 = Thread.ofVirtual().unstarted(exec6);
            Thread t7 = Thread.ofVirtual().unstarted(exec7);            
            Thread t8 = new Thread(() -> {
                for(int i=0; i<10; i++){
                    System.out.println("Impressao da thread: 8 syntaax lambda");
                }
            });

            t1.start();
            t2.start();
            t3.start();
            Thread.ofVirtual().start(new MultiExecucao(4));        
            Thread.ofVirtual().start(exec5);
            t6.start();
            t7.join();
            t8.start();
        } catch(Exception e){
            System.out.println("Error = " + e.getMessage());
        }
    }
}
