/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ffos.skroflin.service;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author svenk
 */
@Service
public class SkolaService extends MainService{
    public List<SkolaService> getAll(){
        return session.createQuery("from skroflin_skola", SkolaService.class).list();
    }
    
    public SkolaService getBySifra(int sifra){
        return session.get(SkolaService.class, sifra);
    }
}
