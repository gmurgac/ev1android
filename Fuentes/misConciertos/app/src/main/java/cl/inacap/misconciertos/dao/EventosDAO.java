package cl.inacap.misconciertos.dao;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.misconciertos.dto.Evento;

public class EventosDAO {
    public static List<Evento> eventos = new ArrayList<Evento>();

    public void add(Evento e){
        eventos.add(e);
    }


}
