/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.service;

import ffos.skroflin.model.Skola;
import ffos.skroflin.model.dto.SkolaDTO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author svenk
 */
@Service
public class SkolaService extends MainService{
    public List<Skola> getAll(){
        return session.createQuery("from skroflin_skola", Skola.class).list();
    }
    
    public Skola getBySifra(int sifra){
        return session.get(Skola.class, sifra);
    }
    
    public Skola post(SkolaDTO o){
        Skola s = new Skola(o.naziv(), o.podrucnaSkola());
        session.beginTransaction();
        session.persist(s);
        session.getTransaction().commit();
        return s;
    }
    
    public void put(SkolaDTO o, int sifra){
        session.beginTransaction();
        Skola s = (Skola) session.get(Skola.class, sifra);
        s.setNaziv(o.naziv());
        s.setPodrucnaSkola(o.podrucnaSkola());
        session.persist(s);
        session.getTransaction().commit();
    }
}
