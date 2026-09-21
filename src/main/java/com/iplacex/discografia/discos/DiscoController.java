package com.iplacex.discografia.discos;
import com.iplacex.discografia.artistas.Artista;
import com.iplacex.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;
    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(
        value = "/disco", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (disco.idArtista != null) {
            List<Artista> artistas = artistaRepository.findAll();
            boolean existeArtista = false;

            for (Artista a : artistas) {
                if (a._id != null && a._id.equals(disco.idArtista)) {
                    existeArtista = true;
                    break;
                }
            }
            if (existeArtista) {
                Disco nuevoDisco = discoRepository.save(disco);
                return new ResponseEntity<>(nuevoDisco, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>("El artista no existe.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(
        value = "/discos", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> discos = discoRepository.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    @GetMapping(
        value = "/disco/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {
        List<Disco> discos = discoRepository.findAll();
        for (Disco d : discos) {
            if (d._id != null && d._id.equals(id)) {
                return new ResponseEntity<>(d, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(
        value = "/artista/{id}/discos", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        List<Disco> discos = discoRepository.findDiscosByIdArtista(id);
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}