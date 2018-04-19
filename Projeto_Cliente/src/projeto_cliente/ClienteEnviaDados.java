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
public class ClienteEnviaDados implements Runnable {
    
    static DatagramSocket clientSocket;
    InetAddress IPAddress;
    int Porta;
    
    public ClienteEnviaDados(DatagramSocket client, InetAddress IP, int Port){
        clientSocket = client;
        IPAddress = IP;
        Porta = Port;
    }
    
    @Override
    public void run() {
        try{
            BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
        
            byte[] sendData;
            
            sendData    = new byte[1024];   
            String sentence = inFromUser.readLine();
            
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Porta);
            clientSocket.send(sendPacket);
        }
        catch(Exception e){
            System.out.println("FUDEU!");
        }
        
    }
}
