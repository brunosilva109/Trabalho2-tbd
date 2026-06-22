package trabalhoii.trabalhoii.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Evento")
public class EventoNode {
    
    @Id
    private String id; 
    private String titulo; 

    public EventoNode() {}

    public EventoNode(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
}