/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto_servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class ConsumirThread implements Runnable {
    
    private ExecutorService exec;
    private Mapa mapa;
    private List<String> comandos;
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    
    public ConsumirThread(String comando, DatagramPacket receivePacket, DatagramSocket serverSocket, Mapa mapa)
    {
        this.receivePacket = receivePacket;
        this.serverSocket = serverSocket;
        this.mapa = mapa;
        comandos = new ArrayList<>();
        comandos.add(comando);
        this.exec = Executors.newCachedThreadPool();
    }
    
    @Override
    public void run() {
        Log log = null;
        ProcessaThread procTrd = null;
        
        while(true)
        {
            if(comandos != null && !comandos.isEmpty())
            {
                for(Iterator<String> c = comandos.listIterator(); c.hasNext();)
                {
                    String cmd = c.next();
                    String co = "" + cmd.charAt(0);
                        
                    if(!co.contains("9")){
                        procTrd = new ProcessaThread(cmd, receivePacket, serverSocket, mapa);
                    }
                    
                    /// Comandos para sair, procurar e listar ao cliente nao precisam ser processados
                    if(!co.contains("4") && !co.contains("9") && !co.contains("5"))
                    {
                        log = new Log(cmd);
                    }
                    
                    c.remove();
                    
                    if(procTrd != null)
                    {
                        this.exec.execute(procTrd);
                    }
                    
                    if(log != null)
                    {
                        this.exec.execute(log);
                    }
                }
                break;
            }
        }
    }
}
