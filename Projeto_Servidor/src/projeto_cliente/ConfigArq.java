/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_cliente;

import java.io.*;
import java.util.Properties;


/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class ConfigArq
{
    
   public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./properties/config.properties");
		props.load(file);
		return props;
   }
    
}