package Tranzactii;

import java.time.LocalDateTime;

public class Tranzactie {
    protected LocalDateTime data;

    public Tranzactie() {
    }

    public Tranzactie(LocalDateTime data) {
        this.data = data;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "data=" + data +
                '}';
    }
}
