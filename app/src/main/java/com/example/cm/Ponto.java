package com.example.cm;

public class Ponto {
    public int IdPonto;
    public String Tema;
    public String Descricao;
    public float Lat;
    public float Lng;
    public String Imagem;
    public int IdUtilizador;

    public Ponto() {

    }

    public Ponto(int idPonto, String tema, String descricao, float lat, float lng, String imagem, int idUtilizador) {
        IdPonto = idPonto;
        Tema = tema;
        Descricao = descricao;
        Lat = lat;
        Lng = lng;
        Imagem = imagem;
        IdUtilizador = idUtilizador;
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

    public float getLat() {
        return Lat;
    }

    public void setLat(float lat) {
        Lat = lat;
    }

    public float getLng() {
        return Lng;
    }

    public void setLng(float lng) {
        Lng = lng;
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