package projeto_servidor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


public class SnapShot implements Runnable{
    
    int segundos;
    Mapa crud;
   
    public SnapShot(Mapa crud){
        this.crud = crud;
    }
    public void run() {
        boolean criado=false;
        try{
            segundos = intervaloSnapShot();
            System.out.println("SEGUNDOS: "+segundos);
            while(true){
                trataQtdSnaps();
                String caminho = "./properties/SnapShot/SnapShot"+horario()+".properties";

                FileOutputStream fileOut = new FileOutputStream(caminho);
                Properties prop = ArquivoLog.getProp(caminho);
                prop.clear();

                Iterator<Map.Entry<BigInteger, String>> map = crud.getMapa().entrySet().iterator();

                while(map.hasNext()){                        
                    Map.Entry<BigInteger, String> par = map.next();
                    //coloca 1 porque é o comando de inserção na hash
                    prop.put(ArquivoLog.timeStamp().toString(), "[1 "+par.getKey() + " " + par.getValue()+"]");
                }
                
                prop.store(fileOut, "SnapShot");
                fileOut.flush();
                fileOut.close();
                
                Thread.sleep(segundos*1000);

                excluiLog();
            }
        }catch(Exception e){
            System.out.println("ERROR ERROR SnapShot: " + e.getMessage());
        }
    }
    
    private int intervaloSnapShot() throws IOException{
        Properties propSegundos = ArquivoLog.getProp("config.properties");
        return Integer.parseInt(propSegundos.getProperty("prop.snap.shot.segundos"));
    }
    
    private boolean trataQtdSnaps(){
        boolean deletado = false;
        File diretorio = new File("./properties/SnapShot/");
        
        if(diretorio.exists()){
            File files[] = diretorio.listFiles();
            if(files.length > 3){
                deletado = files[0].delete();
            }
        }
        return deletado;
    }
    
    private boolean excluiLog() throws IOException{
        boolean excluido = false;
        Path path= Paths.get("");
        try {
            path = Paths.get("./properties/log.properties");
            if(path != null){
                Files.delete(path);
                excluido = true;
            }            
        } catch (Exception x) {            
            System.err.format("%s: no such" + " file or directory%n", path);
        } 
        return excluido;
    }
    
   protected static String horario(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); 
        String horario = sdf.format(hora);
        return horario.replaceAll(":", "-");
   }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public Mapa getProc() {
        return crud;
    }

    public void setProc(Mapa proc) {
        this.crud = proc;
    }
    
    
}
