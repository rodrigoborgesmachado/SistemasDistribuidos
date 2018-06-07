/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_cliente;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.SistemasDistruidos.message.ComandRequest;
import io.grpc.SistemasDistruidos.message.ComandResponse;
import io.grpc.SistemasDistruidos.message.ComandServiceGrpc;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class Projeto_Cliente_gRCP implements Runnable {
    
    private static final Logger logger =
        Logger.getLogger(Projeto_Cliente_gRCP.class.getName());
    
    final CountDownLatch done = new CountDownLatch(1);
    
    ManagedChannel channel;
    
    ComandServiceGrpc.ComandServiceStub stub = ComandServiceGrpc.newStub(channel);
    
    String comando = "5";
    
    public static void EscreveMenuCompleto()
    {
        System.out.println("------- ROYALE TOPS - JONAS -------");
        System.out.println("|Choose your option (just numbers): ");
        System.out.println("|1. Create <key> <value>");
        System.out.println("|2. Update <key> <value>");
        System.out.println("|3. Drop <key>");
        System.out.println("|4. Search <Key>");
        System.out.println("|5. Show");
        System.out.println("|6. Search");
        System.out.println("|7. Menu of Options");
        System.out.println("|9. Exit");
        System.out.print("\n\nOption:  ");
    }

    public void run() 
    {
        try{
            Properties prop = ConfigArq.getProp();
            String porta = prop.getProperty("prop.server.GRPCport");
            String ip = prop.getProperty("prop.server.GRPChost");
            channel = ManagedChannelBuilder
                .forAddress(ip, Integer.parseInt(porta))
                .usePlaintext()
                .build();
        } catch(Exception e){
            e.printStackTrace();
        }
        
        Scanner sc = new Scanner(System.in);
        this.EscreveMenuCompleto();
        
        while(true){
            ComandServiceGrpc.ComandServiceStub stub = ComandServiceGrpc.newStub(channel);
            
            comando = sc.nextLine();
            
            if(comando.equals("7"))
            {
                this.EscreveMenuCompleto();
                continue;
            }
            if(comando.equals("9"))
            {
                break;
            }
            
            ClientResponseObserver<ComandRequest, ComandResponse> clientResponseObserver =
            new ClientResponseObserver<ComandRequest, ComandResponse>() 
            {
                ClientCallStreamObserver<ComandRequest> requestStream;

                @Override
                public void beforeStart(final ClientCallStreamObserver<ComandRequest> requestStream) 
                {
                    this.requestStream = requestStream;
                    requestStream.disableAutoInboundFlowControl();
                    
                    requestStream.setOnReadyHandler(
                        new Runnable() 
                        {
                            @Override
                            public void run() 
                            {
                                if(requestStream.isReady())
                                {
                                    if(comando.equalsIgnoreCase("9"))
                                    {
                                        System.out.println("Finishing!");
                                        try 
                                        {
                                            channel.shutdown();
                                        } 
                                        catch (Exception e) 
                                        {
                                            System.out.println("ERROR ERROR: " + e.getMessage());
                                        }
                                    }
                                    ComandRequest request = ComandRequest.newBuilder().setComm(comando).build();
                                    requestStream.onNext(request);
                                    
                                    try 
                                    {
                                        Thread.sleep(300);
                                    } 
                                    catch (InterruptedException e) 
                                    {
                                        System.out.println("ERRO ERRO: " + e.getMessage());
                                    }
                                    if(comando.charAt(0) != '6')
                                        requestStream.onCompleted();
                                }
                            }
                        }
                    );
                }

                @Override
                public void onNext(ComandResponse respost) 
                {
                    System.out.println("Response: " + respost.getCmd());
                    requestStream.request(1);
                }

                @Override
                public void onError(Throwable thrwbl) 
                {
                    System.out.println("ERROR ERROR: " + thrwbl.getMessage());
                }

                @Override
                public void onCompleted() 
                {
                    System.out.println("Search Done");
                }
            };
            stub.cmd(clientResponseObserver);
            
            System.out.println("New request: ");
        
            if(comando.charAt(0) == '9'){
                break;
            }
        }   
    }
}