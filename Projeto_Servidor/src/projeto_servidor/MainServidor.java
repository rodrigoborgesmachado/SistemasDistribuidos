package projeto_servidor;

import java.net.DatagramSocket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainServidor {
    public static void main(String args[]) throws Exception 
    {
        
        Properties propriedade = UDPServer.getProp();
        Properties arquivo;
        Map<BigInteger, String> mapa;
        List<String> inst = new ArrayList<>();
        Mapa crud = new Mapa();
        
        RecebeThread rcvThread;
        
        String porta = propriedade.getProperty("prop.server.port");
        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(porta));
        byte[] receiveData = new byte[1401];
        byte[] sendData = new byte[1401];

        try
        {
            ProcessaThread pt = new ProcessaThread();        
            ExecutorService executor = Executors.newCachedThreadPool();
            
            File file = new File("./properties/log.properties");
            
            if(file.exists())
            {
                arquivo = ManFileLog.getProp();
                mapa = new HashMap<BigInteger, String>((Map) arquivo);
                                
                Set propertySet = mapa.entrySet();
                for(Object o: propertySet)
                {
                    Map.Entry entry = (Map.Entry) o;
                    inst = Arrays.asList(o.toString().split("\\["));
                    crud = pt.processaComando(inst, crud);
                }
            }
            System.out.println("Server Iniciated!");
            rcvThread = new RecebeThread(serverSocket, crud);
            executor.execute(rcvThread);

            executor.shutdown();
            while (!executor.awaitTermination(24L, TimeUnit.HOURS)) 
            {
                System.out.println("Processing.");
            }
            
        } 
        catch(Exception e)
        {
            System.out.println("FUCKED. ERRO: " +  e.getMessage());
        }        
    }
}
