import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lote {
    private String id;
    private LocalDate data;
    private List<Garrafa> garrafa;
    private double filamentoProd;

    public Lote(String id, LocalDate data) {
        this.id = id;
        this.data = data;
        this.garrafa = new ArrayList<>();
        this.filamentoProd = 0.0;
    }

    public Lote(String id, LocalDate data, List<Garrafa> garrafa, double filamentoProd) {
        this.id = id;
        this.data = data;
        this.garrafa = garrafa != null ? garrafa : new ArrayList<>();
        this.filamentoProd = filamentoProd;
    }

    public void adicionarGarrafa(Garrafa g) {
        this.garrafa.add(g);
    }

    public int getTotalGarrafas() {
        return garrafa.size();
    }

    public double getPesoTotal() {
        return garrafa.stream().mapToDouble(Garrafa::getPeso).sum();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public List<Garrafa> getGarrafa() { return garrafa; }
    public void setGarrafa(List<Garrafa> garrafa) { this.garrafa = garrafa; }

    public double getFilamentoProd() { return filamentoProd; }
    public void setFilamentoProd(double filamentoProd) { this.filamentoProd = filamentoProd; }

    @Override
    public String toString() {
        return "Lote{id='" + id + "', data=" + data + ", garrafas=" + garrafa.size() + ", filamentoProd=" + filamentoProd + "m}";
    }
}
