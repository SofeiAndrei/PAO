package MainThings;

import Tranzactii.Credit;
import Tranzactii.DepozitBancar;
import Tranzactii.Tranzactie;

import java.util.*;

public class ContBancar {
    private int id_client;
    private String nume;
    private String prenume;
    private int sold;
    private String IBAN;
    private SortedSet<Card> carduri;
    private List<Credit> credite;
    private List<Tranzactie> tranzactii;
    private List<DepozitBancar> depozite;

    public ContBancar() {
        this.id_client = 0;
        this.nume = "";
        this.prenume = "";
        this.sold = 0;
        this.IBAN = "";
        this.carduri = new TreeSet<Card>(Comparator.comparing(Card::getNumar));
        this.credite = new ArrayList<>();
        this.tranzactii = new ArrayList<>();
        this.depozite = new ArrayList<>();
    }

    public ContBancar(int id_client, String nume, String prenume, int sold, String IBAN, SortedSet<Card> carduri, List<Credit> credite, List<Tranzactie> tranzactii, List<DepozitBancar> depozite) {
        this.id_client = id_client;
        this.nume = nume;
        this.prenume = prenume;
        this.sold = sold;
        this.IBAN = IBAN;
        this.carduri = carduri;
        this.credite = credite;
        this.tranzactii = tranzactii;
        this.depozite = depozite;
    }
    public ContBancar(ContBancar contBancar){
        this.id_client = contBancar.id_client;
        this.nume = contBancar.nume;
        this.prenume = contBancar.prenume;
        this.sold = contBancar.sold;
        this.IBAN = contBancar.IBAN;
        this.carduri = contBancar.carduri;
        this.credite = contBancar.credite;
        this.tranzactii = contBancar.tranzactii;
        this.depozite = contBancar.depozite;
    }
    public ContBancar(int id_client, String nume, String prenume, String IBAN) {
        this.id_client = id_client;
        this.nume = nume;
        this.prenume = prenume;
        this.sold = 0;
        this.IBAN = IBAN;
        this.carduri = new TreeSet<Card>(Comparator.comparing(Card::getNumar));
        this.credite = new ArrayList<>();
        this.tranzactii = new ArrayList<>();
        this.depozite = new ArrayList<>();
    }
    public ContBancar(int id_client, String nume, String prenume, int sold, String IBAN) {
        this.id_client = id_client;
        this.nume = nume;
        this.prenume = prenume;
        this.sold = sold;
        this.IBAN = IBAN;
        this.carduri = new TreeSet<Card>(Comparator.comparing(Card::getNumar));
        this.credite = new ArrayList<>();
        this.tranzactii = new ArrayList<>();
        this.depozite = new ArrayList<>();
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public SortedSet<Card> getCarduri() {
        return carduri;
    }

    public void setCarduri(SortedSet<Card> carduri) {
        this.carduri = carduri;
    }

    public List<Credit> getCredite() {
        return credite;
    }

    public void setCredite(List<Credit> credite) {
        this.credite = credite;
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    public void setTranzactii(List<Tranzactie> tranzactii) {
        this.tranzactii = tranzactii;
    }

    public List<DepozitBancar> getDepozite() {
        return depozite;
    }

    public void setDepozite(List<DepozitBancar> depozite) {
        this.depozite = depozite;
    }
}
