import javafx.util.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ServiciiClient {
    //1.Depunere
    public Pair<Integer, List<Tranzactie>> depunere(Scanner scanner,Scanner scannerString, ContBancar cont){

        System.out.println("Introduceti suma");
        int suma_de_adaugat = scanner.nextInt();
        int sold_nou = cont.getSold()+suma_de_adaugat;
        List<Tranzactie> tranzactii = cont.getTranzactii();
        AlimentareCont alimentare_noua = new AlimentareCont(LocalDate.now(),suma_de_adaugat);
        tranzactii.add(alimentare_noua);
        Pair<Integer, List<Tranzactie>> pereche = new Pair<Integer, List<Tranzactie>> (sold_nou, tranzactii);
        return pereche;
    }
    //2.Retragere
    public Pair<Integer, List<Tranzactie>> retragere(Scanner scanner,Scanner scannerString, ContBancar cont){

        System.out.println("Introduceti suma");
        int suma_de_scos = scanner.nextInt();
        int sold_nou = cont.getSold();
        List<Tranzactie> tranzactii = cont.getTranzactii();
        if(suma_de_scos > cont.getSold()){
            System.out.println("Sold insuficient");
        }
        else {
            sold_nou -= suma_de_scos;
            Retragere retragere_noua = new Retragere(LocalDate.now(), suma_de_scos);
            tranzactii.add(retragere_noua);
        }
        Pair<Integer, List<Tranzactie>> pereche = new Pair<Integer, List<Tranzactie>>(sold_nou, tranzactii);
        return pereche;
    }
    //4.PlataFactura
    public Pair<Integer, List<Tranzactie>> plataFactura(Scanner scanner,Scanner scannerString, ContBancar cont){
        System.out.println("Introduceti numarul facturii");
        int numar_factura = scanner.nextInt();
        System.out.println("Introduceti suma");
        int suma_de_plata = scanner.nextInt();
        System.out.println("Introduceti cif-ul firmei");
        String cif = scannerString.nextLine();
        System.out.println("Introduceti numele firmei");
        String nume_firma = scannerString.nextLine();
        int sold_nou = cont.getSold();
        List<Tranzactie> tranzactii = cont.getTranzactii();
        if(suma_de_plata > cont.getSold()){
            System.out.println("Sold insuficient");
        }
        else {
            sold_nou -= suma_de_plata;
            PlataFactura plata_noua = new PlataFactura(LocalDate.now(), numar_factura, suma_de_plata, cif, nume_firma);
            tranzactii.add(plata_noua);
        }
        Pair<Integer, List<Tranzactie>> pereche = new Pair<Integer, List<Tranzactie>>(sold_nou, tranzactii);
        return pereche;
    }
    //5.Afisare extras de cont
    public void afisareExtrasCont(ContBancar cont){
        for(Tranzactie tranzactie : cont.getTranzactii()){
            System.out.println(tranzactie);
        }
    }
    //6.Afisare Sold
    public void afisareSold(ContBancar cont){
        System.out.println(cont.getSold());
    }
}
