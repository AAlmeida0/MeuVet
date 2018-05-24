package com.example.gabriel.myapplication.modelo;

public class Locais {
    private String Nome ="";
    private double Latitude =0;
    private double Longitude =0;
    private String imagem = "";
    private String icone = "";
    private String Telefone = "";
    private String Descricao = "";

    public Locais() {

    }

    public Locais(String Nome, double Latitude, double Longitude, String imagem, String icone, String Telefone, String Descricao) {
        this.Nome = Nome;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.imagem = imagem;
        this.icone = icone;
        this.Telefone = Telefone;
        this.Descricao = Descricao;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }
}
