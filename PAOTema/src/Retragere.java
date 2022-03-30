import java.time.LocalDate;

public class Retragere extends Tranzactie{
    private int suma_retrasa;

    public Retragere() {
        super();
    }

    public Retragere(LocalDate data, int suma_retrasa) {
        super(data);
        this.suma_retrasa = suma_retrasa;
    }

    public int getSuma_retrasa() {
        return suma_retrasa;
    }

    public void setSuma_retrasa(int suma_depusa) {
        this.suma_retrasa = suma_depusa;
    }

    @Override
    public String toString() {
        return "Retragere{" +
                "suma_retrasa=" + suma_retrasa +
                ", data=" + data +
                '}';
    }
}
