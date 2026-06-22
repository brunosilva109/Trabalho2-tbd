package trabalhoii.trabalhoii.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trabalhoii.trabalhoii.model.neo4j.EventoNode;

@Repository
public interface EventoNeo4jRepository extends Neo4jRepository<EventoNode, String> {

    @Query("MERGE (e:Evento {id: $id}) SET e.titulo = $titulo")
    void salvarEvento(@Param("id") String id, @Param("titulo") String titulo);

    @Query("MATCH (e:Evento {id: $id}) DETACH DELETE e")
    void deletarEvento(@Param("id") String id);
}