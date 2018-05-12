/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class MainServidor {
    public static void main(String args[]) throws Exception 
    {
        Properties prop = null;
        Properties arq = null;
        
        try
        {
            prop = ArquivoLog.getProp("config.properties");
        }
        catch(Exception e)
        {
            System.out.println("There is no file of configuration!");
            return;
        }
        
        try
        {
            File file = new File("./properties/log.properties");
            if(!file.exists())
                file.createNewFile();
            arq = ArquivoLog.getProp("log.properties");
        }
        catch(Exception e)
        {
            System.out.println("There is no file of logs!");
            return;
        }
        
        Map<BigInteger, String> mapa;
        List<String> lista = new ArrayList<>();
        Mapa map = new Mapa();
        
        RecebeThread rcvThread;
        
        String porta = prop.getProperty("server.port");
        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(porta));
        
        byte[] receiveData = new byte[1401];
        byte[] sendData = new byte[1401];
        
        try
        {
            ExecutorService exec = Executors.newCachedThreadPool();
            ProcessaThread pt = new ProcessaThread();        
            
            mapa = new HashMap<BigInteger, String>((Map) arq);
                                
            Set propertySet = mapa.entrySet();
            for(Object o: propertySet)
            {
                Map.Entry entry = (Map.Entry) o;
                lista = Arrays.asList(o.toString().split("\\["));
                map = pt.CarregaDados(lista.get(1), map);
            }
            
            Log logTrd = new Log();
            ConsumirThread conTrd = new ConsumirThread(logTrd, pt);
            rcvThread = new RecebeThread(conTrd, serverSocket, map);
            
            // Inicia as threads
            exec.execute(rcvThread);
            exec.execute(conTrd);
            exec.execute(logTrd);
            exec.execute(pt);

            System.out.println("Server Initialized!");
            
            exec.shutdown();
            while (!exec.awaitTermination(24L, TimeUnit.HOURS)) 
            {
                System.out.println("Processing.");
            }
            
        } 
        catch(Exception e)
        {
            System.out.println("ERROR ERROR: " +  e.getMessage());
        }        
    }
}
