import java.time.LocalDate;

public class Tranzactie {
    protected LocalDate data;

    public Tranzactie() {
    }

    public Tranzactie(LocalDate data) {
        this.data = data;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "data=" + data +
                '}';
    }
}
