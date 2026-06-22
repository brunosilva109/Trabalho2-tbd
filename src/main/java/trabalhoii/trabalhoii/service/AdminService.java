package trabalhoii.trabalhoii.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trabalhoii.trabalhoii.model.mongo.Evento;
import trabalhoii.trabalhoii.model.postgres.EventoRelacional;
import trabalhoii.trabalhoii.repository.mongo.EventoMongoRepository;
import trabalhoii.trabalhoii.repository.postgres.EventoPostgresRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private EventoMongoRepository eventoMongoRepository;

    @Autowired
    private EventoPostgresRepository eventoPostgresRepository;

    @Autowired
    private Driver neo4jDriver; 

    @Transactional
    public String exportarParaSQL() {
        List<Evento> eventosMongo = eventoMongoRepository.findAll();
        int count = 0;

        for (Evento e : eventosMongo) {
            EventoRelacional er = new EventoRelacional(e.getId(), e.getTitulo(), e.getLocal(), e.getData());
            eventoPostgresRepository.save(er);
            count++;
        }

        return "Exportação concluída! " + count + " eventos foram migrados para o PostgreSQL.";
    }

    public Map<String, Object> gerarDashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        long totalEventosMongo = eventoMongoRepository.count();
        dashboard.put("mongodb_total_eventos", totalEventosMongo);

        long totalPessoasNeo4j = 0;
        try (Session session = neo4jDriver.session()) {
            totalPessoasNeo4j = session.run("MATCH (p:Pessoa) RETURN count(p) AS total")
                                       .single()
                                       .get("total")
                                       .asLong();
        }
        dashboard.put("neo4j_total_pessoas_rede", totalPessoasNeo4j);

        long totalEventosExportados = eventoPostgresRepository.count();
        dashboard.put("postgresql_eventos_sincronizados", totalEventosExportados);

        dashboard.put("status", "Sistemas operacionais e integrados");
        
        return dashboard;
    }
}