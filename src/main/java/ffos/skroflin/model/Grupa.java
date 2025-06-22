/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author svenk
 */
@Entity(name = "skroflin_grupa")
public class Grupa extends Entitet{
    @Column(nullable = false, name = "broj_redovnih")
    private int brojRedovnih;
    @Column(nullable = false, name = "broj_izvanrednih")
    private int brojIzvanrednih;
    @ManyToOne
    private Godina godina;

    public Grupa() {
    }

    public Grupa(int brojRedovnih, int brojIzvanrednih, Godina godina) {
        this.brojRedovnih = brojRedovnih;
        this.brojIzvanrednih = brojIzvanrednih;
        this.godina = godina;
    }

    public int getBrojRedovnih() {
        return brojRedovnih;
    }

    public void setBrojRedovnih(int brojRedovnih) {
        this.brojRedovnih = brojRedovnih;
    }

    public int getBrojIzvanrednih() {
        return brojIzvanrednih;
    }

    public void setBrojIzvanrednih(int brojIzvanrednih) {
        this.brojIzvanrednih = brojIzvanrednih;
    }

    public Godina getGodina() {
        return godina;
    }

    public void setGodina(Godina godina) {
        this.godina = godina;
    }
    
}
