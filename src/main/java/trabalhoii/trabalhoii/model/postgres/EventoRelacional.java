package trabalhoii.trabalhoii.model.postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "eventos_exportados")
public class EventoRelacional {

    @Id
    private String id;
    private String titulo;
    private String local;
    private LocalDate data;

    public EventoRelacional() {}

    public EventoRelacional(String id, String titulo, String local, LocalDate data) {
        this.id = id;
        this.titulo = titulo;
        this.local = local;
        this.data = data;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}