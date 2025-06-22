/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.service;

import ffos.skroflin.model.Grupa;
import ffos.skroflin.model.Nastava;
import ffos.skroflin.model.dto.NastavaDTO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author svenk
 */
@Service
public class NastavaService extends MainService{
    public List<Nastava> getAll(){
        return session.createQuery("from skroflin_nastava", Nastava.class).list();
    }
    
    public Nastava getBySifra(int sifra){
        return session.get(Nastava.class, sifra);
    }
    
    public Nastava post(NastavaDTO o){
        Grupa g = session.get(Grupa.class, o.grupaSifra());
        Nastava n = new Nastava(o.datumNastave(), o.naslovPredavanje(), o.cijena(),g);
        session.beginTransaction();
        session.persist(n);
        session.getTransaction().commit();
        return n;
    }
    
    public void put(NastavaDTO o, int sifra){
        session.beginTransaction();
        Grupa g = session.get(Grupa.class, o.grupaSifra());
        Nastava n = (Nastava) session.get(Nastava.class, sifra);
        n.setDatumNastave(o.datumNastave());
        n.setNaslovPredavanja(o.naslovPredavanje());
        n.setCijena(o.cijena());
        n.setGrupa(g);
        session.persist(n);
        session.getTransaction().commit();
    }
    
    public boolean dodajGrupuNaNastavu(int nastavaSifra, int grupaSifra){
        session.beginTransaction();
        Nastava n = session.get(Nastava.class, nastavaSifra);
        Grupa g = session.get(Grupa.class, grupaSifra);
        n.setGrupa(g);
        session.getTransaction().commit();
        return true;
    }
    
    public boolean makniGrupuSaNastave(int sifra){
        session.beginTransaction();
        Nastava n = session.get(Nastava.class, sifra);
        n.setGrupa(null);
        session.getTransaction().commit();
        return true;
    }
    
    public List<Nastava> getNastaveZaGrupu(int sifra){
        return session.createQuery(
                "from skroflin_nastava n where n.grupa.sifra = :sifra", Nastava.class)
                .setParameter("sifra", sifra)
                .list();
    }
}
