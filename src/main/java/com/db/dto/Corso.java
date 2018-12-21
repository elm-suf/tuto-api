package com.db.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Corso {
    private int id;
    private String titolo;

    public Corso(){}

    public Corso(String titolo) {
        this.titolo = titolo;
    }

    public Corso(int id, String titolo) {
        this.id = id;
        this.titolo = titolo;
    }

    public int getId(){
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
