package com.api.handler;

import com.api.model.Resposta;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StatusHandler  implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

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
                jsonResposta = new Resposta("sucesso", "Dados recebidos com sucesso!").toJson();
                statusCode = 201;
            } else{
                jsonResposta = new Resposta("erro", "Payload invalido. O campo 'nome' eh obrigatorio.").toJson();
                statusCode = 400;
            }
        } else {
            jsonResposta = new Resposta("erro", "Metodo nao permitido").toJson();
            statusCode = 405;
        }

        byte[] respostaBytes = jsonResposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, respostaBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(respostaBytes);
            os.flush();
        }
    }
}