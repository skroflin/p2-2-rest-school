/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 *
 * @author svenk
 */
@Entity(name = "skroflin_godina")
public class Godina extends Entitet{
    @Column(nullable = false)
    private String naziv;
    @Column(columnDefinition = "bit", nullable = false)
    private boolean aktivna;

    public Godina() {
    }

    public Godina(String naziv, boolean aktivna) {
        this.naziv = naziv;
        this.aktivna = aktivna;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isAktivna() {
        return aktivna;
    }

    public void setAktivna(boolean aktivna) {
        this.aktivna = aktivna;
    }
    
}
