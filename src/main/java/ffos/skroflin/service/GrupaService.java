/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.service;

import ffos.skroflin.model.Godina;
import ffos.skroflin.model.Grupa;
import ffos.skroflin.model.dto.GrupaDTO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author svenk
 */
@Service
public class GrupaService extends MainService{
    public List<Grupa> getAll(){
        return session.createQuery("from skroflin_grupa", Grupa.class).list();
    }
    
    public Grupa getBySifra(int sifra){
        return session.get(Grupa.class, sifra);
    }
    
    public Grupa post(GrupaDTO o){
        Godina godina = session.get(Godina.class, o.godinaSifra());
        Grupa g = new Grupa(o.brojRedovnih(), o.brojIzvanrednih(), godina);
        session.beginTransaction();
        session.persist(g);
        session.getTransaction().commit();
        return g;
    }
    
    public void put(GrupaDTO o, int sifra){
        session.beginTransaction();
        Grupa g = (Grupa) session.get(Grupa.class, sifra);
        Godina god = session.get(Godina.class, o.godinaSifra());
        g.setBrojRedovnih(o.brojRedovnih());
        g.setBrojIzvanrednih(o.brojIzvanrednih());
        g.setGodina(god);
        session.persist(g);
    }
}
