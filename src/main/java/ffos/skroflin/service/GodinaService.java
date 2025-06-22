/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.service;

import ffos.skroflin.model.Godina;
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
}
