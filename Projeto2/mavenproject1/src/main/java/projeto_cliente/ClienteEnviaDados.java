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
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class ClienteEnviaDados implements Runnable {
    InetAddress IPAddress;
    int Porta;
    String resposta = null;
    DatagramSocket clientSocket;
    
    public ClienteEnviaDados(InetAddress IP, int Port,DatagramSocket clientSocket)
    {
        IPAddress = IP;
        Porta = Port;
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run() 
    {
        int op=0;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        try
        {    
            EscreveMenuCompleto();
            while(op != 9)
            {
                
                String sentence = inFromUser.readLine();
                try
                {
                    op = Integer.parseInt(RetiraLixo(sentence.charAt(0) + ""));
                }
                catch(Exception e)
                {
                    op = 0;
                    EscreveMenuCompleto();
                    continue;
                }
                
                if(op == 7)
                {
                    EscreveMenuCompleto();
                    continue;
                }
                
                byte[] sendData;
                sendData = new byte[1450];   
                
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Porta);
                clientSocket.send(sendPacket);   
                System.out.println("Sent: " + sentence);
            }
        }
        catch(Exception e)
        {
            System.out.println("ERROR ERROR : " + e.getMessage());
        }
        
    }
    
    public static void EscreveMenuCompleto()
    {
        System.out.println("------- ROYALE TOPS - JONAS -------");
        System.out.println("|Choose your option (just numbers): ");
        System.out.println("|1. Create <key> <value>");
        System.out.println("|2. Update <key> <value>");
        System.out.println("|3. Drop <key>");
        System.out.println("|4. Search <Key>");
        System.out.println("|5. Show");
        System.out.println("|6. Search");
        System.out.println("|7. Menu of Options");
        System.out.println("|9. Exit");
        System.out.print("\n\nOption:  ");
    }
    
    ///Método para retirar o lixo da string
    public static String RetiraLixo(String x)
    {
        return x.replaceAll("\u0000", "").replaceAll("\\u0000", "").replaceAll("\\]","");
    }
}
