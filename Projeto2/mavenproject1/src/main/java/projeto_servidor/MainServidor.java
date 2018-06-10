/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;

import java.net.DatagramSocket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class MainServidor {
    public static void main(String args[]) throws Exception 
    {
        Properties prop = null;
        Properties arq = null;
        
        try
        {
            prop = ArquivoLog.getProp("config.properties");
        }
        catch(Exception e)
        {
            System.out.println("There is no file of configuration! Error: " + e.getMessage());
            return;
        }
        
        try
        {
            File file = new File("log.properties");
            if(!file.exists())
                file.createNewFile();
            arq = ArquivoLog.getProp("log.properties");
        }
        catch(Exception e)
        {
            System.out.println("There is no file of logs!");
            return;
        }
        
        Map<BigInteger, String> mapa;
        List<String> lista = new ArrayList<String>();
        Mapa map = new Mapa();
        
        RecebeThread rcvThread;
        
        String porta = prop.getProperty("server.port");
        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(porta));
        ArrayList<File> arquivos = new ArrayList<File>();
        File logFile = new File("./properties/log.properties");
        File diretorio = new File("./properties/SnapShot/");
        File snapFile = new File("");
        
        if(diretorio.exists())
        {
            File files[] = diretorio.listFiles();   
            if(files.length > 0)
                snapFile = files[files.length-1];
        }
            
        if(logFile.exists())
        {
            arquivos.add(logFile);
        }
            
        if(snapFile.exists())
        {
            arquivos.add(snapFile);
        }
        
        byte[] receiveData = new byte[1401];
        byte[] sendData = new byte[1401];
        
        try
        {
            
            ExecutorService exec = Executors.newCachedThreadPool();
            ProcessaThread pt = new ProcessaThread();        
            
            mapa = new HashMap<BigInteger, String>((Map) arq);
            TreeMap<BigInteger, String> mapOrdenado = new TreeMap<BigInteger, String>();
            ArrayList<Dados> registros = new ArrayList<Dados>();
            
            for(File f : arquivos){
                Set propertySet = mapa.entrySet();
                for(Object o: propertySet)
                {
                    Map.Entry entry = (Map.Entry) o;
                    String entrada = entry.getValue().toString();
                    String partes[] = entrada.split(" ");
                    String chave = partes[1];
                    String comando = partes[0].replace("[", "");
                    String valor = "";
                    
                    for(int i = 2; i < partes.length; i++)
                        valor += partes[i] + " ";
                    
                    valor = valor.substring(0, valor.length() - 2);
                    
                    lista = Arrays.asList(o.toString().split("\\["));
                    
                    map = pt.CarregaDados(lista.get(1), map);
                }
            }
            
            Collections.sort(registros, new Comparator<Dados>() {
                public int compare(Dados r1, Dados r2) {
                    Long s1 = r1.getDataCriacao();
                    Long s2 = r2.getDataCriacao();
                    return (s1 < s2 ? -1 : (s1 == s2 ? 1 : 0));
                }
            });
            
            for(Dados r: registros)
            {   
                String frase = r.getComando().toString() + " " 
                             + r.getChave().toString() + " " 
                             + r.getValor();
                map = pt.CarregaDados(frase, map);
            }
            
            Log logTrd = new Log();
            ConsumirThread conTrd = new ConsumirThread(logTrd, pt);
            rcvThread = new RecebeThread(conTrd, serverSocket, map);
            RecebeThread_gRCP grpcRcv = new RecebeThread_gRCP(conTrd, map, pt);
            SnapShot ss = new SnapShot(map);
            // Inicia as threads
            exec.execute(rcvThread);
            exec.execute(conTrd);
            exec.execute(logTrd);
            exec.execute(pt);
            exec.execute(grpcRcv);
            exec.execute(ss);

            
            exec.shutdown();
            System.out.println("Server Initialized!");
            while (!exec.awaitTermination(24L, TimeUnit.HOURS)) 
            {
                System.out.println("Processing.");
            }
            
        } 
        catch(Exception e)
        {
            System.out.println("ERROR ERROR aqui: " +  e.getMessage());
        }        
    }
}
