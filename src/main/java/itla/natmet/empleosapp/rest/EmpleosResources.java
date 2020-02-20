package itla.natmet.empleosapp.rest;

import itla.natmet.empleosapp.domain.Empleo;
import itla.natmet.empleosapp.repository.EmpleosRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmpleosResources {
    
    private EmpleosRepository empleosRepository;
    
    public EmpleosResources(EmpleosRepository empleosRepository) {
        this.empleosRepository = empleosRepository;
    }
    
    @GetMapping("/empleos")
    public ResponseEntity<List<Empleo>> obtenerEmpletos(Pageable pageable) {
        Page<Empleo> result = empleosRepository.findAll(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(result.getTotalElements()));
        return ResponseEntity.status(HttpStatus.OK).headers(HttpHeaders.EMPTY).body(result.getContent());
    }
    
    @PostMapping("/empleos")
    public ResponseEntity<Empleo> crearEmpleo(@RequestBody Empleo empleo) throws URISyntaxException {
        if (empleo.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Empleo result = empleosRepository.save(empleo);
        return ResponseEntity.created(new URI("/api/empleos" + result.getId())).body(result);
    }
    
    @GetMapping("/empleos/{id}")
    public ResponseEntity<Empleo> buscarPorId(@PathVariable Long id) {
        Optional<Empleo> result = empleosRepository.findById(id);
        return result.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/empleos/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        empleosRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/empleos")
    public ResponseEntity<Empleo> actualizarEmpleo(@RequestBody Empleo empleo) {
        if (empleo.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Empleo result = empleosRepository.save(empleo);
        return ResponseEntity.ok().body(result);
    }
    
}
