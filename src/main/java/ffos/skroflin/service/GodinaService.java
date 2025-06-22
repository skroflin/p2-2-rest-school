/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.service;

import ffos.skroflin.model.Godina;
import ffos.skroflin.model.dto.GodinaDTO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author svenk
 */
@Service
public class GodinaService extends MainService{
    public List<Godina> getAll(){
        return session.createQuery("from skroflin_godina", Godina.class).list();
    }
    
    public Godina getBySifra(int sifra){
        return session.get(Godina.class, sifra);
    }
    
    public Godina post(GodinaDTO o){
        Godina g = new Godina(o.naziv(), o.aktivna());
        session.beginTransaction();
        session.persist(g);
        session.getTransaction().commit();
        return g;
    }
    
    public void put(GodinaDTO o, int sifra){
        session.beginTransaction();
        Godina g = (Godina) session.get(Godina.class, sifra);
        g.setNaziv(o.naziv());
        g.setAktivna(o.aktivna());
        session.persist(g);
        session.getTransaction().commit();
    }
    
    public List<Godina> getGodinePoAktivnosti(boolean aktivna){
        return session.createQuery(
                "from skroflin_godina g where g.aktivna = :aktivna", Godina.class)
                .setParameter("aktivna", aktivna)
                .list();
    }
}
