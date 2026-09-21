package com.iplacex.discografia.artistas;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Document(collection = "artistas")
public class Artista {

    @Id
    @Field("_id")
    public String _id;
    public String nombre;
    public List<String> estilos;
    public int anioFundacion;
    public boolean estaActivo;
    public Artista() {
    }

    public Artista(String _id, String nombre, List<String> estilos, int anioFundacion, boolean estaActivo) {
        this._id = _id;
        this.nombre = nombre;
        this.estilos = estilos;
        this.anioFundacion = anioFundacion;
        this.estaActivo = estaActivo;
    }
}