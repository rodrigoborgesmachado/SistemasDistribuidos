/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_cliente;

import java.io.*;
import java.net.*;
import static jdk.nashorn.tools.ShellFunctions.input;
/**
 *
 * @author Rodrigo Machado - Rodrigo Nogueira - Robertta Loise
 */
public class Projeto_Cliente_UDP {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        try{
            
            int Porta = 9877;
            InetAddress IPAddress = InetAddress.getByName("localhost");          
            ClienteEnviaDados cliente = new ClienteEnviaDados(IPAddress, Porta);
            
            Thread thread = new Thread(cliente);
            thread.start();
                                
            ///ClienteApresentaDados res = new ClienteApresentaDados(IPAddress, Porta, resposta);
            ///Thread thread2 = new Thread(res);
            ///thread2.start();
            while(true){
                
                while(cliente.resposta == null);
                
                System.out.println("Resposta: " + cliente.resposta);
                cliente.resposta = null;
            }    
            
        }
        catch(Exception e){
            System.out.println("FALTAL ERROR!");
        }
    }
    
}
