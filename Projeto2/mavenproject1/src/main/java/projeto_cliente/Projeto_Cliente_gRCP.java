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
    
    int porta = 9876;
    String host = "localhost";
    
    final CountDownLatch done = new CountDownLatch(1);
     ManagedChannel channel = ManagedChannelBuilder
        .forAddress(host, porta)
        .usePlaintext()
        .build();
    ComandServiceGrpc.ComandServiceStub stub = ComandServiceGrpc.newStub(channel);
    
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
                            // Start generating values from where we left off on a non-gRPC thread.   
                            EscreveMenuCompleto();
                            Scanner sc = new Scanner(System.in);
                            String name = "";
                            while(!name.equalsIgnoreCase("9") && requestStream.isReady())
                            {
                                // Send more messages if there are more messages to send.
                                name = sc.nextLine();

                                if(name.equalsIgnoreCase("7"))
                                {
                                    EscreveMenuCompleto();
                                    continue;
                                }

                                if(name.equalsIgnoreCase("9"))
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
                                ComandRequest request = ComandRequest.newBuilder().setComm(name).build();
                                requestStream.onNext(request);
                            }
                        }
                    }
                );
            }

            @Override
            public void onNext(ComandResponse v) 
            {
                System.out.println("Response: " + v.getCmd());
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
                System.out.println("All Done");
            }
        };
        stub.cmd(clientResponseObserver);
        try 
        {
            done.await();
            channel.shutdown();
            channel.awaitTermination(1, TimeUnit.SECONDS);
        } 
        catch (InterruptedException e) 
        {
            System.out.println("ERROR ERROR: " + e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println("ERROR ERROR: " + e.getMessage());
        }
            
    }
}