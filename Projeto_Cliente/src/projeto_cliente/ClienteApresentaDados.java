/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author root
 */
public class ClienteApresentaDados implements Runnable {
    DatagramSocket clientSocket;
    
    public ClienteApresentaDados(DatagramSocket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run() {
        try
        {
            while(true)
            {
                byte[] receiveData = new byte[1450];
               
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String comando = new String(receivePacket.getData(), 0, receivePacket.getLength());
                
                if(comando.charAt(0) == '9')
                {
                    System.out.println("Finished!");
                    break;
                }     
                
                if(!comando.isEmpty())
                {
                    System.out.println("Result: " +  comando);
                    System.out.println("New request: ");
                }
            }           
        }
        catch(Exception e)
        {
            System.out.println("FUCKED Show!");
        }
        
    }
}
