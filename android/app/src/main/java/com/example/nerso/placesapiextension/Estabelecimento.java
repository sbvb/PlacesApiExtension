package com.example.nerso.placesapiextension;

public class Estabelecimento {
    private int id = 0;
    private String nome = "";
    private int tempoDoTreino = 0;
    private String sensei = "";
    private float mensalidade = 0.0f;

    public Estabelecimento(int id, String nome, int tempoDoTreino, String sensei, float mensalidade) {
        this.id = id;
        this.nome = nome;
        this.tempoDoTreino = tempoDoTreino;
        this.sensei = sensei;
        this.mensalidade = mensalidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getTempoDoTreino() {
        return tempoDoTreino;
    }

    public String getSensei() {
        return sensei;
    }

    public float getMensalidade() {
        return mensalidade;
    }
}
