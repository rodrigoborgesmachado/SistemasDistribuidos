package projeto_servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                    
                    // Comando que e para exibir o menu novamente ao cliente nao precisa ser processado
                    if(!co.contains("9")){
                        procTrd = new ProcessaThread(cmd, receivePacket, serverSocket, mapa);
                    }
                    
                    /* Comando que e para sair, exibir o menu novamente e 
                    listar ao cliente nao precisa ser processado*/
                    if(!co.contains("7") && !co.contains("6") && !co.contains("5") && !co.contains("4")){
                        log = new Log(cmd);
                    }
                    
                    c.remove();
                    
                    if(procTrd != null){
                        this.exec.execute(procTrd);
                    }
                    
                    if(log != null){
                        this.exec.execute(log);
                    }
                }
                break;
            }
        }
    }
}
