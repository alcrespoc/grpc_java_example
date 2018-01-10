package es.giiis.aos.grpc.saludos;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple client that requests a greeting from the {@link SaludoServer}.
 */
public class SaludoClient {
    private static final Logger logger = Logger.getLogger(SaludoClient.class.getName());

    private final ManagedChannel channel;
    private final SaludarGrpc.SaludarBlockingStub blockingStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public SaludoClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true)
                .build());
    }

    /** Construct client for accessing RouteGuide server using the existing channel. */
    SaludoClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = SaludarGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Say hello to server. */
    public void saludar(String nombre, String apellido1, String apellido2, String dni) {
        logger.info("Intentando saludar como " + nombre + " ...");
        SaludoRequest request = SaludoRequest.newBuilder().setNombre(nombre).setApellido1(apellido1).setApellido2(apellido2).setDni(dni).build();
        SaludoResponse response;
        try {
            response = blockingStub.saludo(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC fallido: {0}", e.getStatus());
            return;
        }
        logger.info("Saludo: " + response.getMessage());
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        SaludoClient client = new SaludoClient("localhost", 50051);
        try {
            /* Access a service running on the local machine on port 50051 */
            String nombre = "mundo";
            String apellido1 = "ejemplo";
            String apellido2 = "saludo";
            String dni = "01234567Z";
            if (args.length > 0 && args.length < 2) {
                nombre = args[0];
            }
            if (args.length > 0 && args.length < 3) {
                nombre = args[0];
                apellido1 = args[1];
            }
            if (args.length > 0 && args.length < 4) {
                nombre = args[0];
                apellido1 = args[1];
                apellido2 = args[2];
            }
            if (args.length > 0 && args.length < 5) {
                nombre = args[0];
                apellido1 = args[1];
                apellido2 = args[2];
                dni = args[3];
            }
            client.saludar(nombre, apellido1, apellido2, dni);
        } finally {
            client.shutdown();
        }
    }
}

