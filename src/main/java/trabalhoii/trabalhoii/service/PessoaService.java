package trabalhoii.trabalhoii.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trabalhoii.trabalhoii.model.mongo.Pessoa;
import trabalhoii.trabalhoii.model.neo4j.PessoaNode;
import trabalhoii.trabalhoii.repository.mongo.PessoaMongoRepository;
import trabalhoii.trabalhoii.repository.neo4j.PessoaNeo4jRepository;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaMongoRepository pessoaMongoRepository;

    @Autowired
    private PessoaNeo4jRepository pessoaNeo4jRepository;

    @Autowired
    private Driver neo4jDriver;

    @Transactional
    public Pessoa criarPessoa(Pessoa pessoa) {
        Pessoa pessoaSalva = pessoaMongoRepository.save(pessoa);

        try (Session session = neo4jDriver.session()) {
            session.run("MERGE (p:Pessoa {id: $id}) SET p.nome = $nome",
                Values.parameters("id", pessoaSalva.getId(), "nome", pessoaSalva.getNome()));
        }
        
        return pessoaSalva;
    }

    @Transactional
    public void registrarParticipacao(String pessoaId, String eventoId, boolean comoOrganizador) {
        String relacao = comoOrganizador ? "ORGANIZOU" : "PARTICIPOU";
        String query = String.format("MATCH (p:Pessoa {id: $pessoaId}), (e:Evento {id: $eventoId}) MERGE (p)-[:%s]->(e)", relacao);

        try (Session session = neo4jDriver.session()) {
            session.run(query, Values.parameters("pessoaId", pessoaId, "eventoId", eventoId));
        }
    }

    public void promoverParaOrganizador(String pessoaId, String eventoId) {
        try (Session session = neo4jDriver.session()) {
            session.run("MATCH (p:Pessoa {id: $pessoaId}), (e:Evento {id: $eventoId}) MERGE (p)-[:ORGANIZOU]->(e)", 
                Values.parameters("pessoaId", pessoaId, "eventoId", eventoId));
        }
    }
    
    public List<PessoaNode> listarParticipantesDeEvento(String eventoId) {
        try (Session session = neo4jDriver.session()) {
            return session.run("MATCH (p:Pessoa)-[:PARTICIPOU]->(e:Evento {id: $eventoId}) RETURN p.id AS id, p.nome AS nome",
                    Values.parameters("eventoId", eventoId))
                .list(record -> new PessoaNode(record.get("id").asString(), record.get("nome").asString()));
        }
    }
    
    public List<PessoaNode> listarOrganizadoresDeEvento(String eventoId) {
        try (Session session = neo4jDriver.session()) {
            return session.run("MATCH (p:Pessoa)-[:ORGANIZOU]->(e:Evento {id: $eventoId}) RETURN p.id AS id, p.nome AS nome",
                    Values.parameters("eventoId", eventoId))
                .list(record -> new PessoaNode(record.get("id").asString(), record.get("nome").asString()));
        }
    }
}