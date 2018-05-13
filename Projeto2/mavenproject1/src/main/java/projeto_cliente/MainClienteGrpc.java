package projeto_cliente;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainClienteGrpc {
    
    public static void main(String args[]) throws Exception 
    {    
        ExecutorService executor = Executors.newCachedThreadPool();
        
        Projeto_Cliente_gRCP cmdCliRpc = new Projeto_Cliente_gRCP();
                
        executor.execute(cmdCliRpc);
        executor.shutdown();
        
        while (!executor.awaitTermination(24L, TimeUnit.HOURS)) 
        {
            System.out.println("Not yet. Still waiting for termination");
        }
   }
}
