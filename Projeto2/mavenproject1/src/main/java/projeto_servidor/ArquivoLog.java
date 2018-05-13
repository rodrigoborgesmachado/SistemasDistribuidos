/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;

import java.io.*;
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
    
}
