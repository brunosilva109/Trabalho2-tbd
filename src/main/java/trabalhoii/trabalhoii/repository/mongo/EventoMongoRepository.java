package trabalhoii.trabalhoii.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import trabalhoii.trabalhoii.model.mongo.Evento;
import java.util.List;

@Repository
public interface EventoMongoRepository extends MongoRepository<Evento, String> {

    List<Evento> findByLocal(String local);

    @Query("{ 'titulo' : { $regex: ?0, $options: 'i' } }")
    List<Evento> buscarPorPalavraChave(String palavraChave);

    List<Evento> findByData(java.time.LocalDate data);
}