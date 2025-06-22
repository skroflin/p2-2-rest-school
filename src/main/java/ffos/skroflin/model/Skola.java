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
@Entity(name = "skroflin_skola")
public class Skola extends Entitet{
    @Column(nullable = false)
    private String naziv;
    @Column(columnDefinition = "tinyint", nullable = false, name = "podrucna_skola")
    private boolean podrucnaSkola;

    public Skola() {
    }

    public Skola(String naziv, boolean podrucnaSkola) {
        this.naziv = naziv;
        this.podrucnaSkola = podrucnaSkola;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isPodrucnaSkola() {
        return podrucnaSkola;
    }

    public void setPodrucnaSkola(boolean podrucnaSkola) {
        this.podrucnaSkola = podrucnaSkola;
    }
    
    
}
