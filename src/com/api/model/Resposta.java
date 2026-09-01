package com.api.model;

public class Resposta {
    private String status;
    private String mensagem;

    public Resposta(String status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    public String toJson(){

        return String.format("{\"status\":\"%s\",\"mensagem\":\"%s\"}", status, mensagem);
    }
}
