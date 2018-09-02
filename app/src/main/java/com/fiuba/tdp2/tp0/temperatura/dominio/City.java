package com.fiuba.tdp2.tp0.temperatura.dominio;

public class City {
    private int id;
    private String name;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
