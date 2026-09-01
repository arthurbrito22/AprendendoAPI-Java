package com.api.config;

import com.api.handler.StatusHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServidorConfig {
    public static void iniciar(int porta) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);
        server.createContext("/api/status", new StatusHandler());
        server.start();
        System.out.println("Servidor rodando em http://localhost:" + porta + "/");
    }
}
