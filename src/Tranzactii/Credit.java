package Tranzactii;

public class Credit {
    private int suma;
    private int durata;
    private float dobanda;
    private int valoare_achitata;

    public Credit() {
    }

    public Credit(int suma, int durata, float dobanda, int valoare_achitata) {
        this.suma = suma;
        this.durata = durata;
        this.dobanda = dobanda;
        this.valoare_achitata = valoare_achitata;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public float getDobanda() {
        return dobanda;
    }

    public void setDobanda(float dobanda) {
        this.dobanda = dobanda;
    }

    public int getValoare_achitata() {
        return valoare_achitata;
    }

    public void setValoare_achitata(int valoare_achitata) {
        this.valoare_achitata = valoare_achitata;
    }
}
