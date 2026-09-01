import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class ServidorSimples {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", exchange -> {
            String mensagem = "Meu primeiro servidor funcionou!";
            exchange.sendResponseHeaders(200, mensagem.length());
            exchange.getResponseBody().write(mensagem.getBytes());
            exchange.getResponseBody().close();
        });

        server.start();
        System.out.println("Servidor rodando em http://localhost:8080/");
    }
}
