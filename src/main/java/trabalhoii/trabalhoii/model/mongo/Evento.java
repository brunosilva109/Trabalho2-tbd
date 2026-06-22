package trabalhoii.trabalhoii.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.Map;

@Document(collection = "eventos")
public class Evento {

    @Id
    private String id;
    private String titulo;
    private String local;
    private LocalDate data;
    private Map<String, Object> detalhesExtras;

    public Evento() {}
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Map<String, Object> getDetalhesExtras() { return detalhesExtras; }
    public void setDetalhesExtras(Map<String, Object> detalhesExtras) { this.detalhesExtras = detalhesExtras; }
}