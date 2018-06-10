/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto_servidor;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class Log implements Runnable {
    private BlockingQueue<String> comandos = new LinkedBlockingQueue<String>();
    int i=0;
    
    @Override
    public void run() 
    {
        while(true)
        {
            if(comandos == null || comandos.isEmpty())
            {
                continue;
            }
            else
            {
                try
                { 
                    Iterator<String> comand = comandos.iterator();
                    FileOutputStream fileout = new FileOutputStream("./properties/log.properties", true);
                    Properties prop = ArquivoLog.getProp("log.properties");
                    prop.clear();
                    String cm = "0";
                    
                    while(comand.hasNext())
                    {
                        cm = comand.next();
                        prop.put("command:"+java.util.UUID.randomUUID(), RetiraLixo("[" + cm + "]"));
                        System.out.println("command:"+java.util.UUID.randomUUID() + " " + RetiraLixo("[" + cm + "]"));
                        comand.remove();
                    }
                    
                    prop.store(fileout, "Logs sent by client");
                    System.out.println("Logs sent by client");
                    fileout.flush();
                    
                } 
                catch(Exception e)
                {
                    System.out.println("ERROR ERROR log.java: " + e.getMessage());
                }
            }
        }   
    }
    
    ///Método para retirar o lixo da string
    public static String RetiraLixo(String x)
    {
        return x.replaceAll("\u0000", "").replaceAll("\\u0000", "");
    }
    
    public void addComando(String comando)
    {
        comandos.add(comando);
    }
}
