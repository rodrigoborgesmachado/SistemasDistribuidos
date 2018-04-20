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
    InetAddress IPAddress;
    int Porta;
    String resposta;
    
    public ClienteApresentaDados(InetAddress IP, int Port){
        this.IPAddress = IP;
        this.Porta = Port;
        this.resposta = resposta;
    }
    
    @Override
    public void run() {
        try{
            while(true){
                
                while(resposta== null);
                
                System.out.println("Resposta: " + resposta);
            }
        }
        catch(Exception e){
            System.out.println("FUDEU Apresenta!");
        }
        
    }
}
