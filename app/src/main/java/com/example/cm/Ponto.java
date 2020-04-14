package com.example.cm;


public class Ponto {
    private int IdPonto;
    private String Tema;
    private String Descricao;
    private double Longitude;
    private double Latitude;
    private String Imagem;
    private int IdUtilizador;

    public Ponto() {
    }

    public Ponto(int IdPonto, String Tema, String Descricao, double Longitude, double Latitude, String Imagem, int IdUtilizador) {
        this.IdPonto = IdPonto;
        this.Tema = Tema;
        this.Descricao = Descricao;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Imagem = Imagem;
        this.IdUtilizador = IdUtilizador;
    }

    public int getIdPonto() {
        return IdPonto;
    }

    public void setIdPonto(int idPonto) {
        IdPonto = idPonto;
    }

    public String getTema() {
        return Tema;
    }

    public void setTema(String tema) {
        Tema = tema;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        Imagem = imagem;
    }

    public int getIdUtilizador() {
        return IdUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        IdUtilizador = idUtilizador;
    }

}


