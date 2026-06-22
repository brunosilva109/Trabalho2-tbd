package trabalhoii.trabalhoii.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trabalhoii.trabalhoii.model.mongo.Pessoa;
import trabalhoii.trabalhoii.model.neo4j.PessoaNode;
import trabalhoii.trabalhoii.service.PessoaService;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;


    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.criarPessoa(pessoa));
    }

    @PostMapping("/{pessoaId}/participa/{eventoId}")
    public ResponseEntity<String> registrarParticipacao(
            @PathVariable String pessoaId,
            @PathVariable String eventoId,
            @RequestParam(defaultValue = "false") boolean organizador) {
        
        pessoaService.registrarParticipacao(pessoaId, eventoId, organizador);
        String papel = organizador ? "organizador" : "participante";
        return ResponseEntity.ok("Pessoa registrada como " + papel + " no evento com sucesso!");
    }

    @PutMapping("/{pessoaId}/promove/{eventoId}")
    public ResponseEntity<String> promoverParaOrganizador(
            @PathVariable String pessoaId,
            @PathVariable String eventoId) {
        
        pessoaService.promoverParaOrganizador(pessoaId, eventoId);
        return ResponseEntity.ok("Pessoa promovida a organizadora do evento com sucesso!");
    }

    @GetMapping("/evento/{eventoId}/participantes")
    public ResponseEntity<List<PessoaNode>> listarParticipantes(@PathVariable String eventoId) {
        return ResponseEntity.ok(pessoaService.listarParticipantesDeEvento(eventoId));
    }


    @GetMapping("/evento/{eventoId}/organizadores")
    public ResponseEntity<List<PessoaNode>> listarOrganizadores(@PathVariable String eventoId) {
        return ResponseEntity.ok(pessoaService.listarOrganizadoresDeEvento(eventoId));
    }
}