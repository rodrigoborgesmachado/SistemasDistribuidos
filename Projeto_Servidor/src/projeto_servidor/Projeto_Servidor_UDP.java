/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;
import java.io.*;
import java.net.*;

/**
 *
 * @author Rodrigo Machado - Rodrigo Nogueira - Robertta Loise
 */
public class Projeto_Servidor_UDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            DatagramSocket serverSocket = new DatagramSocket(9877);
            serverSocket.setReceiveBufferSize(1024);
            while(true)
            {
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String( receivePacket.getData());
                System.out.println("RECEIVED: " + sentence);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String capitalizedSentence = sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);  
            }
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage().toString());
        }
    }
}
