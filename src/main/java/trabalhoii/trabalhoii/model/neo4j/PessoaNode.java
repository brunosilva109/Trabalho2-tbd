package trabalhoii.trabalhoii.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.ArrayList;
import java.util.List;

@Node("Pessoa")
public class PessoaNode {

    @Id
    private String id; 
    private String nome;

    @Relationship(type = "PARTICIPOU", direction = Relationship.Direction.OUTGOING)
    private List<EventoNode> eventosParticipados = new ArrayList<>();

    @Relationship(type = "ORGANIZOU", direction = Relationship.Direction.OUTGOING)
    private List<EventoNode> eventosOrganizados = new ArrayList<>();

    public PessoaNode() {}

    public PessoaNode(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void participar(EventoNode evento) {
        this.eventosParticipados.add(evento);
    }

    public void organizar(EventoNode evento) {
        this.eventosOrganizados.add(evento);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<EventoNode> getEventosParticipados() { return eventosParticipados; }
    public List<EventoNode> getEventosOrganizados() { return eventosOrganizados; }
}