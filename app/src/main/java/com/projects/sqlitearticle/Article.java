package com.projects.sqlitearticle;

public class Article {
    private int id;
    private String libelle;
    private int pu; // Prix unitaire

    public Article(int id, String libelle, int pu) {
        this.id = id;
        this.libelle = libelle;
        this.pu = pu;
    }

    // Getters et setters pour chaque attribut de l'article
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getPu() {
        return pu;
    }

    public void setPu(int pu) {
        this.pu = pu;
    }
}
