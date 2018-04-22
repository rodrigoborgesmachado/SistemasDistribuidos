/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class ProcessaThread implements Runnable{
    private List<String> comandos;
    private DatagramPacket receivePacket;
    private DatagramSocket serverSocket;
    private String busca;
    private ArrayList<String> lista =  new ArrayList<>();
    private boolean cria, deleta, atualiza;
    private Mapa mapa;
    private List<String> list = new ArrayList<>();

    
    public ProcessaThread()
    {
        System.out.println("-----Loading-----");
    }
    
    public ProcessaThread(String comando, DatagramPacket receivePacket, DatagramSocket serverSocket, Mapa crud)
    {
        this.comandos = new ArrayList<>();
        this.comandos.add(comando);
        this.receivePacket = receivePacket;
        this.serverSocket = serverSocket;
        this.mapa = crud;
    }
    
    @Override
    public void run() {
        byte[] sendData = new byte[1401];
        String dados="";
        
        try
        {
            /// Para cada comandos da lista
            for(String c : comandos)
            {
                sendData = c.getBytes();
                list = Arrays.asList(c.split(" ")); 
                   
                switch(RetiraLixo(list.get(0)).charAt(0))
                {
                    /// Cria dados
                    case '1':
                        cria = mapa.Create(new BigInteger(RetiraLixo(list.get(1))), Value(list));
                        if(cria)
                            dados = "Created!\n";
                        else
                            dados = "Was not possible to complete the operation\n";
                        break;
                        
                    /// Atualiza dados a partir da chave
                    case '2':
                        atualiza = mapa.Update(new BigInteger(RetiraLixo(list.get(1))), Value(list));
                        if(atualiza)
                            dados = "Updated!\n";
                        else
                            dados = "Was not possible to complete the operation\n";
                        break;
                        

                    /// Delete chave
                    case '3':
                        deleta = mapa.Delete(new BigInteger(RetiraLixo(list.get(1))));
                        if(deleta)
                            dados = "Deleted!\n";
                        else
                            dados = "Was not possible to complete the operation\n";
                        break;

                    /// Busca dado no mapa
                    case '4':
                        busca = mapa.Search(new BigInteger(RetiraLixo(list.get(1))));
                        if(busca != null && !busca.isEmpty())
                            dados = busca+"\n";
                        else
                            dados = "There is no key\n";
                        break;

                    /// Retorna todos os dados
                    case '5':
                        lista = mapa.Read();
                        if(lista != null && !lista.isEmpty())
                            dados = lista + "\n";
                        else
                            dados = "There is no register on database\n";
                        break;
                        
                    /// Envia 9 para matar cliente 
                    case '9':
                        dados = "9";
                        break;
                        
                    default:
                        break;
                    }
                        
                    sendData = dados.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    serverSocket.send(sendPacket);
                    
                break;
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }   
    }
    
    ///Método para retirar o lixo da string
    public static String RetiraLixo(String x)
    {
        return x.replaceAll("\u0000", "").replaceAll("\\u0000", "").replaceAll("\\]","");
    }
    
    ///Concatena as strings passadas por referência na lista
    public String Value(List<String> lista)
    {
        String value = "";
        for(int i=2; i < lista.size(); i++)value += lista.get(i)+" ";
        return RetiraLixo(value);
    }
    
    /// Carrega os dados da lista para o mapa processando os comandos
    public Mapa CarregaDados(String valor, Mapa mapa)
    {
        List<String> Lista = null;
        String dados = "";
        
        valor = RetiraLixo(valor);
        
        Lista = Arrays.asList(valor.split(" "));
        
        switch(RetiraLixo(Lista.get(0)).charAt(0))
        {
            case '1':
                cria = mapa.Create(new BigInteger(Lista.get(1)), Value(Lista));
                break;

            case '2':
                atualiza = mapa.Update(new BigInteger(Lista.get(1)), Value(Lista));
                break;

            case '3':
                deleta = mapa.Delete(new BigInteger(Lista.get(1)));
                break;
                
            default:
                break;
        }
        return mapa;
    }
    
}
