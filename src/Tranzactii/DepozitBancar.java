package Tranzactii;

import java.time.LocalDateTime;

public class DepozitBancar {
    private LocalDateTime termen;
    private float dobanda;

    public DepozitBancar() {
    }

    public DepozitBancar(LocalDateTime termen, float dobanda) {
        this.termen = termen;
        this.dobanda = dobanda;
    }

    public LocalDateTime getTermen() {
        return termen;
    }

    public void setTermen(LocalDateTime termen) {
        this.termen = termen;
    }

    public float getDobanda() {
        return dobanda;
    }

    public void setDobanda(float dobanda) {
        this.dobanda = dobanda;
    }
}
