package trabalhoii.trabalhoii.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import trabalhoii.trabalhoii.model.mongo.Pessoa;

@Repository
public interface PessoaMongoRepository extends MongoRepository<Pessoa, String> {

}
