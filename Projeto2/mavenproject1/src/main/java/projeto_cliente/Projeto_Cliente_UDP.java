/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_cliente;

import java.net.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class Projeto_Cliente_UDP {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Properties prop = null;
        try
        {
            prop = ConfigArq.getProp();
        }
        catch(Exception e)
        {
            System.out.println("There is no file of configuration!");
            return;
        }
        
        
        try
        {
            DatagramSocket clientSocket = new DatagramSocket();
            int Porta =  Integer.parseInt(prop.getProperty("client.port"));
            
            ExecutorService exec = Executors.newCachedThreadPool();
            
            InetAddress IPAddress = InetAddress.getByName(prop.getProperty("client.host"));          
            
            ClienteEnviaDados cliente = new ClienteEnviaDados(IPAddress, Porta, clientSocket);
            Thread thread = new Thread(cliente);
            
            ClienteApresentaDados res = new ClienteApresentaDados(clientSocket);
            Thread thread2 = new Thread(res);
            
            
            exec.execute(thread);
            exec.execute(thread2);
            
            exec.shutdown();
            
        }
        catch(Exception e)
        {
            System.out.println("ERROR ERROR: " + e.getMessage());
        }
    }
    
}
