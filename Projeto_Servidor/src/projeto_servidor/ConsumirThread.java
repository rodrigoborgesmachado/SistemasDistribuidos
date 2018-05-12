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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class ConsumirThread implements Runnable {
    
    private Log logTrd;
    private ProcessaThread procTrd;
    private Mapa mapa;
    private BlockingQueue<String> comandos = new LinkedBlockingQueue<String>();
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    
    public ConsumirThread(Log logTrd, ProcessaThread procTrd)
    {
        this.logTrd = logTrd;
        this.procTrd = procTrd;
    }
    
    @Override
    public void run() {
        while(true)
        {
            if(getComandos() == null || comandos.isEmpty())
            {
                continue;
            }
            else
            {
                Iterator<String> com = getComandos().iterator();
                while(com.hasNext())
                {
                    String cmd = com.next();
                    String co = "" + cmd.charAt(0);
                    if(!co.contains("9")){
                        procTrd.setReceivePacket(receivePacket);
                        procTrd.setServerSocket(serverSocket);
                        procTrd.setMapa(mapa);
                        procTrd.addComando(cmd);
                    }
                    
                    /// Comandos para sair, procurar e listar ao cliente nao precisam ser processados
                    if(!co.contains("4") && !co.contains("9") && !co.contains("5"))
                    {
                        logTrd.addComando(cmd);
                    }
                    
                    com.remove();
                }
            }
        }
    }

    public void addComando(String comando){
        getComandos().add(comando);
    }
    
    /**
     * @return the logTrd
     */
    public Log getLogTrd() {
        return logTrd;
    }

    /**
     * @param logTrd the logTrd to set
     */
    public void setLogTrd(Log logTrd) {
        this.logTrd = logTrd;
    }

    /**
     * @return the procTrd
     */
    public ProcessaThread getProcTrd() {
        return procTrd;
    }

    /**
     * @param procTrd the procTrd to set
     */
    public void setProcTrd(ProcessaThread procTrd) {
        this.procTrd = procTrd;
    }

    /**
     * @return the mapa
     */
    public Mapa getMapa() {
        return mapa;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    /**
     * @return the comandos
     */
    public BlockingQueue<String> getComandos() {
        return comandos;
    }

    /**
     * @param comandos the comandos to set
     */
    public void setComandos(BlockingQueue<String> comandos) {
        this.comandos = comandos;
    }

    /**
     * @return the serverSocket
     */
    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * @param serverSocket the serverSocket to set
     */
    public void setServerSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * @return the receivePacket
     */
    public DatagramPacket getReceivePacket() {
        return receivePacket;
    }

    /**
     * @param receivePacket the receivePacket to set
     */
    public void setReceivePacket(DatagramPacket receivePacket) {
        this.receivePacket = receivePacket;
    }
}
