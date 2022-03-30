import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;

public class ServiciiAdmin {
    //1.Creare Cont(dupa IBAN)
    public ContBancar creareCont(Scanner scanner,Scanner scannerString, int nr_conturi){
        System.out.println("Introdu numele clientului:");
        String nume = scannerString.nextLine();
        System.out.println("Introdu prenumele clientului:");
        String prenume = scannerString.nextLine();
        System.out.println("Introdu un IBAN pentru contul creat: /stiu ca ar trebui sa fie automata chestia asta dar nu am timp acum :)/");
        String iban = scannerString.nextLine();
        ContBancar cont = new ContBancar(nr_conturi+1,nume,prenume,iban);
        return cont;
    }
    //3.Blocare card la un cont
    public SortedSet<Card> blocheazaCard(Scanner scanner,Scanner scannerString, ContBancar cont){
        System.out.println("Introdu numarul cardului pe care vrei sa il blochezi");
        int numar_card = scanner.nextInt();
        SortedSet<Card> carduri = cont.getCarduri();
        boolean OK = false;
        for(Card card : carduri){
            if(card.getNumar() == numar_card){
                OK = true;
                card.setBlocat(true);
            }
        }
        return carduri;
    }
    //4.Creare Depozit
    public List<DepozitBancar> creareDepozit(Scanner scanner,Scanner scannerString, ContBancar cont){
        List<DepozitBancar> depozite = cont.getDepozite();
        System.out.println("Introdu dobanda");
        float dobanda = scanner.nextFloat();
        System.out.println("Introdu numarul de ani");
        int nr_ani = scanner.nextInt();
        DepozitBancar depozit_nou = new DepozitBancar(LocalDate.now().plusYears(nr_ani),dobanda);
        depozite.add(depozit_nou);
        return depozite;
    }
    //5.Creare inca un card pentru un cont
    public SortedSet<Card> creareCard(Scanner scanner,Scanner scannerString, ContBancar cont){
        SortedSet<Card> carduri = cont.getCarduri();
        System.out.println("Introdu numarul de card");
        int numar_card = scanner.nextInt();
        System.out.println("Introdu numele titularului");
        String nume_titular = scannerString.nextLine();
        Card card_nou = new Card(numar_card,nume_titular,LocalDate.now().plusYears(5),false);
        carduri.add(card_nou);
        return carduri;
    }
    //6.Acordare credit
    public List<Credit> acordareCredit(Scanner scanner,Scanner scannerString, ContBancar cont){
        List<Credit> credite = cont.getCredite();
        System.out.println("Introdu suma");
        int suma = scanner.nextInt();
        System.out.println("Introdu durata in ani");
        int durata = scanner.nextInt();
        System.out.println("Introdu dobanda");
        float dobanda = scanner.nextFloat();
        Credit credit_nou = new Credit(suma,durata,dobanda,0);
        credite.add(credit_nou);
        return credite;
    }
}
