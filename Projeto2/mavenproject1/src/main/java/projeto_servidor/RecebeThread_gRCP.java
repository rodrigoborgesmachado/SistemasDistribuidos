/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;


import io.grpc.SistemasDistruidos.message.ComandRequest;
import io.grpc.SistemasDistruidos.message.ComandResponse;
import io.grpc.SistemasDistruidos.message.ComandServiceGrpc;
import io.grpc.ServerBuilder;
import io.grpc.Server;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
    public class RecebeThread_gRCP  implements Runnable {
    private BlockingQueue<String> comandos = new LinkedBlockingQueue<String>();
    private ConsumirThread conTrd;
    private Server server;
    private ExecutorService executor;
    private Mapa crud;
    private ProcessaThread procTrd;
    private int porta;
    
    public RecebeThread_gRCP(ConsumirThread conTrd, Mapa crud, ProcessaThread procTrd, int porta)
    {
        this.conTrd = conTrd;
        this.crud = crud;
        this.executor = Executors.newCachedThreadPool();
        this.procTrd = procTrd;
        this.porta = porta;
    }

    @Override
    public void run() 
    {
        try 
        {
            server = ServerBuilder.forPort(porta)
                .addService(new ComandServiceImpl())
                .build()
                .start();
            System.out.println("Server started, listening on " + porta);
        } 
        catch (Exception e) 
        {
            System.out.println("ERROR ERROR: " + e.getMessage());
            return;
        }        
        
        Runtime.getRuntime().addShutdownHook(new Thread() 
        {
          @Override
          public void run() 
          {
            System.err.println("*** shutting down gRPC server");
            this.stop();
            System.err.println("*** server shut down");
          }
        });
    }
    
    class ComandServiceImpl extends ComandServiceGrpc.ComandServiceImplBase 
    {
        @Override
        public StreamObserver<ComandRequest> cmd(final StreamObserver<ComandResponse> responseObserver) 
        {
            final ServerCallStreamObserver<ComandResponse> serverCallStreamObserver =
                (ServerCallStreamObserver<ComandResponse>) responseObserver;
            serverCallStreamObserver.disableAutoInboundFlowControl();
            
            final AtomicBoolean wasReady = new AtomicBoolean(false);
            
            serverCallStreamObserver.setOnReadyHandler(new Runnable() 
            {
                public void run() 
                {
                    if (serverCallStreamObserver.isReady() && wasReady.compareAndSet(false, true)) 
                    {
                        System.out.println("READY");
                        serverCallStreamObserver.request(1);
                    }
                    else
                    {
                        System.out.println("Finished");
                    }
                }
            });
            
            // Give gRPC a StreamObserver that can observe and process incoming requests.
            return new StreamObserver<ComandRequest>() 
            {
                @Override
                public void onNext(ComandRequest request) 
                {
                    // Process the request and send a response or an error.
                    try 
                    {
                        String name = request.getComm();
                        
                        procTrd.setResponseObserverGrpc(responseObserver);
                        conTrd.setMapa(crud);
                        conTrd.addComando(name);
                        System.out.println("Enviei comando: " + name);
                        
                        if (serverCallStreamObserver.isReady()) {
                            serverCallStreamObserver.request(1);
                            System.out.println("Requisitei");
                        } else {
                            // If not, note that back-pressure has begun.
                            wasReady.set(false);
                        }
                    } 
                    catch (Exception e)
                    {
                        System.out.println("ERROR ERROR: " + e.getMessage());
                    }
                }
                
                @Override
                public void onError(Throwable t) 
                {
                    t.printStackTrace();
                    responseObserver.onCompleted();
                    System.out.println("ERROR ERROR: " + t.getMessage());
                }

                @Override
                public void onCompleted() 
                {
                    System.out.println("COMPLETED");
                    responseObserver.onCompleted();
                }
            };
        }
    }
}

