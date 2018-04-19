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
            byte[] sendData;
            byte[] receiveData;
            int Porta = 9877;
            InetAddress IPAddress = InetAddress.getByName("localhost");
            DatagramSocket clientSocket;
            while(true){
                BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
                
                clientSocket = new DatagramSocket();                

                sendData    = new byte[1024];
                receiveData = new byte[1024];

                String sentence = inFromUser.readLine();
                
               
                System.out.println("sentence: " + sentence);
                sendData = sentence.getBytes();

                EnviaPacote(sendData, clientSocket, IPAddress, Porta);
                                
                String modifiedSentence = RecebePacote(receiveData, clientSocket);
                
                System.out.println("FROM SERVER:" + modifiedSentence);
                clientSocket.close();
                clientSocket.disconnect();
            }
        }
        catch(Exception e){
            System.out.println("FALTAL ERROR!");
        }
    }
    
    public static void EnviaPacote(byte[] sendData, DatagramSocket clientSocket, InetAddress IPAddress, int Porta) throws IOException{
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Porta);
        clientSocket.send(sendPacket);
    }
    
    public static String RecebePacote(byte[] receiveData, DatagramSocket clientSocket) throws IOException{
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("\nMostra: " + modifiedSentence);
        return modifiedSentence + "\nTamanho: " + receivePacket.getData().length;
    }
    
}
