/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto_servidor;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class Log implements Runnable {
    private List<String> comandos;
    int i=0;
            
    public Log(String comando){
        this.comandos = new ArrayList<>();
        this.comandos.add(comando);
    }
    
    @Override
    public void run() {
        while(true){
            if(comandos != null && !comandos.isEmpty()){
                
                try{ 
                    
                    FileOutputStream fileout = new FileOutputStream("./properties/log.properties", true);
                    Properties prop = ArquivoLog.getProp("log.properties");
                    prop.clear();
                    
                    for (String comando : comandos) 
                    {
                        prop.put("command:"+java.util.UUID.randomUUID(), RetiraLixo("[" + comando + "]"));
                    }
                    
                    prop.store(fileout, "Logs sent by client");
                    fileout.flush();
                    
                } catch(Exception e){
                    System.out.println("ERROR ERROR: " + e.getMessage());
                }
                
               
                break;
            }
        }   
        
    }
    
    ///Método para retirar o lixo da string
    public static String RetiraLixo(String x)
    {
        return x.replaceAll("\u0000", "").replaceAll("\\u0000", "");
    }
    
}
