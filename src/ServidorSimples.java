import com.sun.net.httpserver.HttpServer;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;



public class ServidorSimples {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/status", exchange -> {
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");

            String metodo = exchange.getRequestMethod();
            String jsonResposta;
            int statusCode;

            if("GET".equalsIgnoreCase(metodo)) {

                jsonResposta = "{\"status\": \"sucesso\",\"mensagem\":\"Minha primeira API Java puro!\"}";
                statusCode = 200;
            } else if("POST".equalsIgnoreCase(metodo)) {

                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                System.out.println("Corpo do body recebido: " + body);

                if (body.contains("nome")) {
                    jsonResposta = "{\"status\": \"sucesso\", \"mensagem\": \"Dados recebidos com sucesso!\"}";
                    statusCode = 201;
                } else{
                    jsonResposta = "{\"status\": \"erro\", \"mensagem\": \"Payload invalido. O campo 'nome' eh obrigatorio.\"}";
                    statusCode = 400;
                }
            } else {
                jsonResposta = "{\"status\": \"erro\", \"mensagem\": \"Metodo nao permitido\"}";
                statusCode = 405;
            }

            byte[] respostaBytes = jsonResposta.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(statusCode, respostaBytes.length);

            try (var os = exchange.getResponseBody()) {
                os.write(respostaBytes);
            }
        });

        server.start();
        System.out.println("Servidor rodando em http://localhost:8080/");
    }
}
