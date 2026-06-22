package trabalhoii.trabalhoii.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trabalhoii.trabalhoii.model.mongo.Evento;
import trabalhoii.trabalhoii.repository.mongo.EventoMongoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoMongoRepository eventoMongoRepository;

    @Autowired
    private Driver neo4jDriver;

    @Transactional
    public Evento criarEvento(Evento evento) {
        Evento eventoSalvo = eventoMongoRepository.save(evento);

        try (Session session = neo4jDriver.session()) {
            session.run("MERGE (e:Evento {id: $id}) SET e.titulo = $titulo", 
                Values.parameters("id", eventoSalvo.getId(), "titulo", eventoSalvo.getTitulo()));
        }
        
        return eventoSalvo;
    }

    public List<Evento> listarTodos() {
        return eventoMongoRepository.findAll();
    }

    public List<Evento> buscarPorFiltros(String local, String palavraChave, java.time.LocalDate data) {
        if (local != null && !local.isEmpty()) {
             return eventoMongoRepository.findByLocal(local);
        } else if (palavraChave != null && !palavraChave.isEmpty()) {
             return eventoMongoRepository.buscarPorPalavraChave(palavraChave);
        } else if (data != null) {
             return eventoMongoRepository.findByData(data); // <-- Novo filtro por data!
        }
        return listarTodos();
    }

    @Transactional
    public Evento atualizarEvento(String id, Evento eventoAtualizado) {
        Optional<Evento> eventoExistente = eventoMongoRepository.findById(id);
        
        if (eventoExistente.isPresent()) {
            Evento evento = eventoExistente.get();
            evento.setTitulo(eventoAtualizado.getTitulo());
            evento.setLocal(eventoAtualizado.getLocal());
            evento.setData(eventoAtualizado.getData());
            evento.setDetalhesExtras(eventoAtualizado.getDetalhesExtras());
            
            Evento eventoSalvo = eventoMongoRepository.save(evento);

            try (Session session = neo4jDriver.session()) {
                session.run("MERGE (e:Evento {id: $id}) SET e.titulo = $titulo", 
                    Values.parameters("id", id, "titulo", eventoSalvo.getTitulo()));
            }
            
            return eventoSalvo;
        } else {
            throw new RuntimeException("Evento com ID " + id + " não foi encontrado.");
        }
    }

    @Transactional
    public void deletarEvento(String id) {

        eventoMongoRepository.deleteById(id);

        try (Session session = neo4jDriver.session()) {
            session.run("MATCH (e:Evento {id: $id}) DETACH DELETE e", 
                Values.parameters("id", id));
        }
    }
}