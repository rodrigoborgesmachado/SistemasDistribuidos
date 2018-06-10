/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;

import io.grpc.SistemasDistruidos.message.ComandResponse;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class ProcessaThread implements Runnable{
    private BlockingQueue<String> comandos = new LinkedBlockingQueue<String>();
    private DatagramPacket receivePacket;
    private DatagramSocket serverSocket;
    private String busca;
    private ArrayList<String> lista =  new ArrayList<String>();
    private boolean cria, deleta, atualiza;
    private Mapa mapa;
    private List<String> list = new ArrayList<String>();
    private String monChave = " ";
    private io.grpc.stub.StreamObserver<ComandResponse> responseObserverGrpc;
    private List<MonitorObject> monitorObject = new ArrayList<MonitorObject>();
        
    public ProcessaThread()
    {
        System.out.println("-----Loading-----");
    }
    
    @Override
    public void run() 
    {
        byte[] sendData = new byte[1401];
        String dados="";
        String c;
        while(true)
        {
            try
            {
                Iterator<String> cmd = getComandos().iterator();
                /// Para cada comandos da lista
                while(cmd.hasNext())
                {
                    
                    c = cmd.next();
                    cmd.remove();
                    sendData = c.getBytes();
                    setList(Arrays.asList(c.split(" "))); 
                    System.out.println("ProcessaThread: " + c);   
                    switch(RetiraLixo(getList().get(0)).charAt(0))
                    {
                        /// Cria dados
                        case '1':
                            if(getMapa().Search(new BigInteger(RetiraLixo(getList().get(1)))) != "")
                            {
                                dados = "The key already exists\n";
                            }
                            else
                            {
                                setCria(getMapa().Create(new BigInteger(RetiraLixo(getList().get(1))), Value(getList())));
                                if(isCria())
                                    dados = "Created!\n";
                                else
                                    dados = "Was not possible to complete the operation\n";
                            }
                            break;

                        /// Atualiza dados a partir da chave
                        case '2':
                            setAtualiza(getMapa().Update(new BigInteger(RetiraLixo(getList().get(1))), Value(getList())));
                            if(isAtualiza())
                                dados = "Updated!\n";
                            else
                                dados = "Was not possible to complete the operation\n";
                            break;


                        /// Delete chave
                        case '3':
                            setDeleta(getMapa().Delete(new BigInteger(RetiraLixo(getList().get(1)))));
                            if(isDeleta())
                                dados = "Deleted!\n";
                            else
                                dados = "The key doesn't exist\n";
                            break;

                        /// Busca dado no mapa
                        case '4':
                            setBusca(getMapa().Search(new BigInteger(RetiraLixo(getList().get(1)))));
                            if(getBusca() != null && !busca.isEmpty())
                                dados = getBusca()+"\n";
                            else
                                dados = "The key doesn't exist\n";
                            break;

                        /// Retorna todos os dados
                        case '5':
                            setLista(getMapa().Read());
                            if(getLista() != null && !lista.isEmpty())
                                dados = getLista() + "\n";
                            else
                                dados = "There is no register in database\n";
                            break;

                        case '6':
                                monChave = getList().get(1);
                                dados = "Start the search\n";
                                break;
                            
                        /// Envia 9 para matar cliente 
                        case '9':
                            dados = "9";
                            break;

                        default:
                            break;
                        }

                    if(getReceivePacket() != null)
                    {
                        sendData = dados.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, getReceivePacket().getAddress(), getReceivePacket().getPort());
                        getServerSocket().send(sendPacket);
                        
                        if(getList().get(0).equalsIgnoreCase("9")){
                            MonitorObject monObj = new MonitorObject();
                            monObj.setMonChave(monChave);
                            monObj.setPacoteUdp(sendPacket);
                            monObj.setSocketUdp(serverSocket);
                            monitorObject.add(monObj);
                        }
                        
                    }
                    
                    if(responseObserverGrpc != null)
                    {
                        if(!getList().get(0).equalsIgnoreCase("6"))
                        {
                            ComandResponse rspGrpc = ComandResponse.newBuilder().setCmd(dados + " " + getList()).build();
                            this.responseObserverGrpc.onNext(rspGrpc);
                            this.responseObserverGrpc.onCompleted();
                        }
                        else
                        {
                            MonitorObject monObj = new MonitorObject();
                            monObj.setMonChave(monChave);
                            monObj.setResponseObserverGrpc(responseObserverGrpc);
                            monitorObject.add(monObj);
                            ComandResponse rspGrpc = ComandResponse.newBuilder().setCmd(dados + " ").build();
                            this.responseObserverGrpc.onNext(rspGrpc);
                        }
                    }
                }
                
                if(monitorObject.size() > 0 && !getList().get(0).equalsIgnoreCase("5") && !getList().get(0).equalsIgnoreCase("6"))
                {
                    for(MonitorObject mo : monitorObject)
                    {
                        if(mo.getSocketUdp() != null && mo.getMonChave().equalsIgnoreCase(getList().get(1)))
                        {
                            dados = "Callback from the key - " +mo.getMonChave()+ " Charges: " + getList().toString() +"\n";
                            sendData = dados.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, mo.getPacoteUdp().getAddress(), mo.getPacoteUdp().getPort());
                            DatagramSocket ss = mo.getSocketUdp();
                            ss.send(sendPacket);
                        }
                                
                        if(mo.getResponseObserverGrpc() != null && mo.getMonChave().equalsIgnoreCase(getList().get(1))
                                        && !getList().get(0).equalsIgnoreCase("5") && !getList().get(0).equalsIgnoreCase("7"))
                        {
                            try{
                                dados = "Callback from the key - " +mo.getMonChave()+ "  Charges: " + getList().toString() +"\n";
                                ComandResponse rspGrpc = ComandResponse.newBuilder().setCmd(dados + " ").build();
                                mo.getResponseObserverGrpc().onNext(rspGrpc);
                            } 
                            catch(Exception e)
                            {
                                System.out.println("ERROR ERROR: " + e.getMessage());
                            }
                        }
                    }
                    cmd.remove();
                    receivePacket = null;
                    responseObserverGrpc = null;
                }
            } 
            catch(Exception e)
            {
                System.out.println("ERROR ERROR ProcessaThread.java: "+e.getMessage());
            }   
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
        //System.out.println("Inserindo no mapa: " + valor);
        valor = RetiraLixo(valor);
        
        Lista = Arrays.asList(valor.split(" "));
        
        switch(RetiraLixo(Lista.get(0)).charAt(0))
        {
            case '1':
                setCria(mapa.Create(new BigInteger(Lista.get(1)), Value(Lista)));
                break;

            case '2':
                setAtualiza(mapa.Update(new BigInteger(Lista.get(1)), Value(Lista)));
                break;

            case '3':
                setDeleta(mapa.Delete(new BigInteger(Lista.get(1))));
                break;
                
            default:
                break;
        }
        return mapa;
    }

    /**
     * @return the comandos
     */
    public BlockingQueue<String> getComandos() 
    {
        return comandos;
    }

    /**
     * @param comandos the comandos to set
     */
    public void setComandos(BlockingQueue<String> comandos) 
    {
        this.comandos = comandos;
    }

    /**
     * @return the receivePacket
     */
    public DatagramPacket getReceivePacket() 
    {
        return receivePacket;
    }

    /**
     * @param receivePacket the receivePacket to set
     */
    public void setReceivePacket(DatagramPacket receivePacket) 
    {
        this.receivePacket = receivePacket;
    }

    /**
     * @return the serverSocket
     */
    public DatagramSocket getServerSocket() 
    {
        return serverSocket;
    }

    /**
     * @param serverSocket the serverSocket to set
     */
    public void setServerSocket(DatagramSocket serverSocket) 
    {
        this.serverSocket = serverSocket;
    }

    /**
     * @return the busca
     */
    public String getBusca() 
    {
        return busca;
    }

    /**
     * @param busca the busca to set
     */
    public void setBusca(String busca) 
    {
        this.busca = busca;
    }

    /**
     * @return the lista
     */
    public ArrayList<String> getLista() 
    {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(ArrayList<String> lista)
    {
        this.lista = lista;
    }

    /**
     * @return the cria
     */
    public boolean isCria() 
    {
        return cria;
    }

    /**
     * @param cria the cria to set
     */
    public void setCria(boolean cria) 
    {
        this.cria = cria;
    }

    /**
     * @return the deleta
     */
    public boolean isDeleta() 
    {
        return deleta;
    }

    /**
     * @param deleta the deleta to set
     */
    public void setDeleta(boolean deleta) 
    {
        this.deleta = deleta;
    }

    /**
     * @return the atualiza
     */
    public boolean isAtualiza() 
    {
        return atualiza;
    }

    /**
     * @param atualiza the atualiza to set
     */
    public void setAtualiza(boolean atualiza) 
    {
        this.atualiza = atualiza;
    }

    /**
     * @return the mapa
     */
    public Mapa getMapa() 
    {
        return mapa;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapa(Mapa mapa) 
    {
        this.mapa = mapa;
    }

    /**
     * @return the list
     */
    public List<String> getList() 
    {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<String> list) 
    {
        this.list = list;
    }

    /**
     * @return the monChave
     */
    public String getMonChave() 
    {
        return monChave;
    }

    /**
     * @param monChave the monChave to set
     */
    public void setMonChave(String monChave) 
    {
        this.monChave = monChave;
    }
    
    public void addComando(String comando){
        comandos.add(comando);
    }
    
    public io.grpc.stub.StreamObserver<ComandResponse> getResponseObserverGrpc() 
    {
        return responseObserverGrpc;
    }
    
    public void setResponseObserverGrpc(io.grpc.stub.StreamObserver<ComandResponse> responseObserverGrpc) 
    {
        this.responseObserverGrpc = responseObserverGrpc;
    }
    
}
