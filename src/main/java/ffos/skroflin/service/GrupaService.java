/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.service;

import ffos.skroflin.model.Grupa;
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
}
