package Tranzactii;

import Tranzactii.Tranzactie;

import java.time.LocalDateTime;

public class Transfer extends Tranzactie {
    private int suma_transferata;
    private String IBAN_destinatar;

    public Transfer() {
        super();
    }

    public Transfer(LocalDateTime data, int suma_transferata, String IBAN_destinatar) {
        super(data);
        this.suma_transferata = suma_transferata;
        this.IBAN_destinatar = IBAN_destinatar;
    }

    public int getSuma_transferata() {
        return suma_transferata;
    }

    public void setSuma_transferata(int suma_transferata) {
        this.suma_transferata = suma_transferata;
    }

    public String getIBAN_destinatar() {
        return IBAN_destinatar;
    }

    public void setIBAN_destinatar(String IBAN_destinatar) {
        this.IBAN_destinatar = IBAN_destinatar;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "suma_transferata=" + suma_transferata +
                ", IBAN_destinatar='" + IBAN_destinatar + '\'' +
                ", data=" + data +
                '}';
    }
}
