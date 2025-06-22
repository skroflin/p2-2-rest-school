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
public class NastavaService extends MainService{
    public List<NastavaService> getAll(){
        return session.createQuery("from skroflin_nastava", NastavaService.class).list();
    }
    
    public NastavaService getBySifra(int sifra){
        return session.get(NastavaService.class, sifra);
    }
}
