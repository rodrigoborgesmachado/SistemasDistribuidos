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
    static DatagramSocket clientSocket;
    InetAddress IPAddress;
    int Porta;
    
    public ClienteApresentaDados(DatagramSocket client, InetAddress IP, int Port){
        clientSocket = client;
        IPAddress = IP;
        Porta = Port;
    }
    
    @Override
    public void run() {
        try{
            byte[] receiveData;
            receiveData = new byte[1024];
            
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("Resposta: "+ modifiedSentence);
        }
        catch(Exception e){
            System.out.println("FUDEU!");
        }
        
    }
}
