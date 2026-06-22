package trabalhoii.trabalhoii.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trabalhoii.trabalhoii.model.postgres.EventoRelacional;

@Repository
public interface EventoPostgresRepository extends JpaRepository<EventoRelacional, String> {
}