package trabalhoii.trabalhoii.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trabalhoii.trabalhoii.model.mongo.Evento;
import trabalhoii.trabalhoii.service.EventoService;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody Evento evento) {
        Evento novoEvento = eventoService.criarEvento(evento);
        return ResponseEntity.ok(novoEvento);
    }

    @GetMapping
    public ResponseEntity<List<Evento>> listarEventos(
            @RequestParam(required = false) String local,
            @RequestParam(required = false) String palavraChave,
            @RequestParam(required = false) java.time.LocalDate data) { // <-- Recebe a data
        
        List<Evento> eventos = eventoService.buscarPorFiltros(local, palavraChave, data);
        return ResponseEntity.ok(eventos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizarEvento(@PathVariable String id, @RequestBody Evento eventoAtualizado) {
        Evento evento = eventoService.atualizarEvento(id, eventoAtualizado);
        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEvento(@PathVariable String id) {
        eventoService.deletarEvento(id);
        return ResponseEntity.ok("Evento e suas relações foram apagados com sucesso!");
    }
}