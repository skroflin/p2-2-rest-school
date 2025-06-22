/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author svenk
 */
@Entity(name = "skroflin_nastava")
public class Nastava extends Entitet{
    @Column(nullable = false, name = "datum_nastave")
    private Date datumNastave;
    @Column(nullable = false, name = "naslov_predavanja")
    private String naslovPredavanja;
    @Column(columnDefinition = "float")
    private BigDecimal cijena;
    @ManyToOne
    private Grupa grupa;

    public Nastava() {
    }

    public Nastava(Date datumNastave, String naslovPredavanja, BigDecimal cijena, Grupa grupa) {
        this.datumNastave = datumNastave;
        this.naslovPredavanja = naslovPredavanja;
        this.cijena = cijena;
        this.grupa = grupa;
    }

    public Date getDatumNastave() {
        return datumNastave;
    }

    public void setDatumNastave(Date datumNastave) {
        this.datumNastave = datumNastave;
    }

    public String getNaslovPredavanja() {
        return naslovPredavanja;
    }

    public void setNaslovPredavanja(String naslovPredavanja) {
        this.naslovPredavanja = naslovPredavanja;
    }

    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    public Grupa getGrupa() {
        return grupa;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }
    
}
