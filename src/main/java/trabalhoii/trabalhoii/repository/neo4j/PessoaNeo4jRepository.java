package trabalhoii.trabalhoii.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trabalhoii.trabalhoii.model.neo4j.PessoaNode;
import java.util.List;

@Repository
public interface PessoaNeo4jRepository extends Neo4jRepository<PessoaNode, String> {

    @Query("MATCH (p:Pessoa)-[:PARTICIPOU]->(e:Evento {id: $eventoId}) RETURN p")
    List<PessoaNode> buscarParticipantesDoEvento(@Param("eventoId") String eventoId);

    @Query("MATCH (p:Pessoa)-[:ORGANIZOU]->(e:Evento {id: $eventoId}) RETURN p")
    List<PessoaNode> buscarOrganizadoresDoEvento(@Param("eventoId") String eventoId);

    @Query("MATCH (p:Pessoa {id: $pessoaId}), (e:Evento {id: $eventoId}) " +
           "MERGE (p)-[:ORGANIZOU]->(e)")
    void tornarOrganizador(@Param("pessoaId") String pessoaId, @Param("eventoId") String eventoId);

    @Query("MATCH (p:Pessoa) RETURN count(p)")
    long contarPessoas();
}