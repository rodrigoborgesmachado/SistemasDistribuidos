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
import java.util.Properties;
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
    private ComandServiceGrpc.ComandServiceImplBase ComandServiceImpl;
    
    public RecebeThread_gRCP(final ConsumirThread conTrd, final Mapa crud, final ProcessaThread procTrd)
    {
        this.conTrd = conTrd;
        this.crud = crud;
        this.executor = Executors.newCachedThreadPool();
        this.procTrd = procTrd;
        
        this.ComandServiceImpl = new ComandServiceGrpc.ComandServiceImplBase() {
            @Override
            public StreamObserver<ComandRequest> cmd(final StreamObserver<ComandResponse> responseObserver) {
                final ServerCallStreamObserver<ComandResponse> serverCallStreamObserver =
                    (ServerCallStreamObserver<ComandResponse>) responseObserver;
                serverCallStreamObserver.disableAutoInboundFlowControl();

                final AtomicBoolean wasReady = new AtomicBoolean(false);

                serverCallStreamObserver.setOnReadyHandler(new Runnable() {
                    public void run() {
                        if (serverCallStreamObserver.isReady() && wasReady.compareAndSet(false, true)) {
                            System.out.println("READY");
                            serverCallStreamObserver.request(1);
                        }
                    }
                });

                return new StreamObserver<ComandRequest>() {
                    @Override
                    public void onNext(ComandRequest request) {
                        try {
                            String name = request.getComm();
                            procTrd.setResponseObserverGrpc(responseObserver);
                            conTrd.setMapa(crud);
                            conTrd.addComando(name);
                            
                            if (serverCallStreamObserver.isReady()) {
                                serverCallStreamObserver.request(1);
                            } else {
                                wasReady.set(false);
                            }
                        } catch (Exception ex) {
                            System.out.println("ERROR ERROR TerecebThread_gRCP: "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        responseObserver.onCompleted();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("COMPLETED");
                        responseObserver.onCompleted();
                    }
                };
            }
        };
    }

    @Override
    public void run() 
    {
        String port="";
        String ip = "";
        
        try 
        {
            Properties prop = ArquivoLog.getProp("config.properties");
            port = prop.getProperty("prop.server.GRPCport");
            ip = prop.getProperty("prop.server.GRPChost");
            
            server = ServerBuilder.forPort(Integer.parseInt(port))
                .addService(ComandServiceImpl)
                .build()
                .start();
        } 
        catch (Exception e) 
        {
            System.out.println("ERROR ERROR TerecebThread_gRCP: " + e.getMessage());
            return;
        }        
        
        System.out.println("Server started, listening on " + port);
        
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
}

