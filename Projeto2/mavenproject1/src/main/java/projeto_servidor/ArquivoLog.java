/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class ArquivoLog {
    
    public static Properties getProp(String s) throws IOException 
    {
	Properties props = new Properties();
	FileInputStream file = new FileInputStream("./properties/" + s);
	props.load(file);
	return props;
    }
   
    public static Long timeStamp(){
        Timestamp timestamp = new Timestamp(System.nanoTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        return timestamp.getTime();
    } 
}
