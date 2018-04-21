package projeto_servidor;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
                    
                    FileOutputStream fileout = new FileOutputStream(
                                    "./properties/log.properties", true);
                     Properties prop = ManFileLog.getProp();
                     prop.clear();
                    for (String comando : comandos) {
                        prop.put("comando"+java.util.UUID.randomUUID(), ("[" + comando + "]")
                                .replaceAll("\u0000", "") /* removes NUL chars */
                                .replaceAll("\\u0000", "") /* removes backslash+u0000 */);
                    }
                    //System.out.println("log: "+comandos.toString());
                    prop.store(fileout, "Log dos comandos enviados pelo cliente");
                    fileout.flush();
                    
                } catch(Exception ex){
                    ex.printStackTrace();
                }
                
               
                break;
            }
        }
    }
    
}
