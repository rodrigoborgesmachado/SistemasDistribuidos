/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_cliente;

import java.io.*;
import java.net.*;
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
            
            int Porta = 9877;
            DatagramSocket clientSocket;
            InetAddress IPAddress = InetAddress.getByName("localhost");
                
            clientSocket = new DatagramSocket();                

            Thread thread = new Thread(new ClienteEnviaDados(clientSocket, IPAddress, Porta));
            thread.start();
                                
            Thread thread2 = new Thread(new ClienteApresentaDados(clientSocket, IPAddress, Porta));
            thread2.start();
                
            clientSocket.close();
            clientSocket.disconnect();
        }
        catch(Exception e){
            System.out.println("FALTAL ERROR!");
        }
    }
    
}
