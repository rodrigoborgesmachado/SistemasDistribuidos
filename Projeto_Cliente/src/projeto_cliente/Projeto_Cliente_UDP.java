/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_cliente;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static jdk.nashorn.tools.ShellFunctions.input;
/**
 *
 * @author Rodrigo Machado - Rodrigo Nogueira - Robertta Loise
 */
public class Projeto_Cliente_UDP {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        try{
            DatagramSocket clientSocket = new DatagramSocket();
            int Porta = 9877;
            
            ExecutorService exec = Executors.newCachedThreadPool();
            
            InetAddress IPAddress = InetAddress.getByName("localhost");          
            
            ClienteEnviaDados cliente = new ClienteEnviaDados(IPAddress, Porta, clientSocket);
            Thread thread = new Thread(cliente);
            
            ClienteApresentaDados res = new ClienteApresentaDados(clientSocket);
            Thread thread2 = new Thread(res);
            
            
            exec.execute(thread);
            exec.execute(thread2);
            
            exec.shutdown();
            
        }
        catch(Exception e){
            System.out.println("FALTAL ERROR!Erro: " + e.getMessage());
        }
    }
    
}
