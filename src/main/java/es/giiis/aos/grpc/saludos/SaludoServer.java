package es.giiis.aos.grpc.saludos;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class SaludoServer {
    private static final Logger logger = Logger.getLogger(SaludoServer.class.getName());

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new SaludarImpl())
                .build()
                .start();
        logger.info("Servidor arrancado, escuchando en el puerto " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** Apagando servidor gRPC desde JVM");
                SaludoServer.this.stop();
                System.err.println("*** Servidor apagado");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final SaludoServer server = new SaludoServer();
        server.start();
        server.blockUntilShutdown();
    }


    static class SaludarImpl extends SaludarGrpc.SaludarImplBase {

        @Override
        public void saludo(SaludoRequest req, StreamObserver<SaludoResponse> responseObserver) {
            SaludoResponse response = SaludoResponse.newBuilder().setMessage("Hola " + req.getNombre()+" "+ req.getApellido1()).build();
            logger.info("Servidor ha recibido un saludo de " + req.getNombre()+" "+req.getApellido1()+" "+req.getApellido2()+", con DNI: "+ req.getDni());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void saludoOtraVez(SaludoRequest req, StreamObserver<SaludoResponse> responseObserver) {
            SaludoResponse response = SaludoResponse.newBuilder().setMessage("Hola de nuevo, " + req.getNombre()+" "+ req.getApellido1()).build();
            logger.info("Servidor ha vuelto a recibir un saludo de " + req.getNombre()+" "+req.getApellido1()+" "+req.getApellido2()+", con DNI: "+ req.getDni());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}

