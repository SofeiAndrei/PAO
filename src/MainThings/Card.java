package MainThings;

import java.time.LocalDate;

public class Card {
    private int numar;
    private String nume_titular;
    private LocalDate data_expirare;
    private boolean blocat;

    public Card() {
        data_expirare = LocalDate.now();
    }

    public Card(int numar, String nume_titular, LocalDate data_expirare, boolean blocat) {
        this.numar = numar;
        this.nume_titular = nume_titular;
        this.data_expirare = data_expirare;
        this.blocat = blocat;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getNume_titular() {
        return nume_titular;
    }

    public void setNume_titular(String nume_titular) {
        this.nume_titular = nume_titular;
    }

    public LocalDate getData_expirare() {
        return data_expirare;
    }

    public void setData_expirare(LocalDate data_expirare) {
        this.data_expirare = data_expirare;
    }

    public boolean isBlocat() {
        return blocat;
    }

    public void setBlocat(boolean blocat) {
        this.blocat = blocat;
    }
}
