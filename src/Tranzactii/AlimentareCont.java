package Tranzactii;

import Tranzactii.Tranzactie;

import java.time.LocalDateTime;

public class AlimentareCont extends Tranzactie {
    private int suma_depusa;

    public AlimentareCont() {
        super();
    }

    public AlimentareCont(LocalDateTime data, int suma_depusa) {
        super(data);
        this.suma_depusa = suma_depusa;
    }

    public int getSuma_depusa() {
        return suma_depusa;
    }

    public void setSuma_depusa(int suma_depusa) {
        this.suma_depusa = suma_depusa;
    }

    @Override
    public String toString() {
        return "AlimentareCont{" +
                "suma_depusa=" + suma_depusa +
                ", data=" + data +
                '}';
    }
}
