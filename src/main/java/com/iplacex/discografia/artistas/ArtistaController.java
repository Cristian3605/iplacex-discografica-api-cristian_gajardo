package com.iplacex.discografia.artistas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(
        value = "/artista", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista nuevoArtista = artistaRepository.save(artista);
        return new ResponseEntity<>(nuevoArtista, HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/artistas", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        List<Artista> artistas = artistaRepository.findAll();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
        List<Artista> artistas = artistaRepository.findAll();
        for (Artista a : artistas) {
            if (a._id != null && a._id.equals(id)) {
                return new ResponseEntity<>(a, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(
        value = "/artista/{id}", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        List<Artista> artistas = artistaRepository.findAll();
        boolean existe = false;
        for (Artista a : artistas) {
            if (a._id != null && a._id.equals(id)) {
                existe = true;
                break;
            }
        }

        if (existe) {
            artista._id = id;
            Artista artistaActualizado = artistaRepository.save(artista);
            return new ResponseEntity<>(artistaActualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(
        value = "/artista/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
        List<Artista> artistas = artistaRepository.findAll();
        Artista artistaEncontrado = null;
        for (Artista a : artistas) {
            if (a._id != null && a._id.equals(id)) {
                artistaEncontrado = a;
                break;
            }
        }
        
        if (artistaEncontrado != null) {
            artistaRepository.delete(artistaEncontrado);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}