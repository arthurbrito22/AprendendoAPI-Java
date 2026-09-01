package com.api;

import com.api.config.ServidorConfig;

public class App {
    public static void main(String[] args) {
        try {
            ServidorConfig.iniciar(8080);
        }catch (Exception e){
            System.out.println("Erro ao configurar Servidor" + e.getMessage());
        }
    }
}
