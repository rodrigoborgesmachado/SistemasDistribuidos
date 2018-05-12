/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class RecebeThread implements Runnable{
    private DatagramSocket serverSocket;
    private ConsumirThread conTrd;
    private Mapa crud;
    
    public RecebeThread(ConsumirThread conTrd, DatagramSocket serverSocket, Mapa crud)
    {
        this.serverSocket = serverSocket;
        this.conTrd = conTrd;
        this.crud = crud;
    }
    
    @Override
    public void run() {        
        while(true)
        {
            try
            {
                byte[] receiveData = new byte[1450];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                
                serverSocket.receive(receivePacket);
                String comando="";
                comando = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Comando: " + comando);
               
                // Cria thread de consumir da fila e enviar para log e processador do comando.
                if(!comando.isEmpty() && !(comando.equalsIgnoreCase("")))
                {
                    conTrd.setReceivePacket(receivePacket);
                    conTrd.setServerSocket(serverSocket);
                    conTrd.setMapa(crud);
                    conTrd.addComando(comando);
                }
                                
            }
            catch(Exception e)
            {
                System.out.println("ERROR ERROR: " + e.getMessage());
            }
        }
    }    
}
