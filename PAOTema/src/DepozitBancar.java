import java.time.LocalDate;

public class DepozitBancar {
    private LocalDate termen;
    private float dobanda;

    public DepozitBancar() {
    }

    public DepozitBancar(LocalDate termen, float dobanda) {
        this.termen = termen;
        this.dobanda = dobanda;
    }

    public LocalDate getTermen() {
        return termen;
    }

    public void setTermen(LocalDate termen) {
        this.termen = termen;
    }

    public float getDobanda() {
        return dobanda;
    }

    public void setDobanda(float dobanda) {
        this.dobanda = dobanda;
    }
}
