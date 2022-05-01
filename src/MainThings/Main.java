package MainThings;

import ServiciiPentruCSV.CitireCSV;
import ServiciiPentruCSV.ScriereCSV;
import Tranzactii.*;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args){
        CitireCSV singletonCitireConturiBancare =  CitireCSV.getInstance();
        int numarAlimentari=0;
        int numarDepozite=0;
        int numarPlatiFacturi=0;
        int numarRetrageri=0;
        int numarTransferuri=0;
        int numarCredite=0;
        int actionNumber=0;
        ScriereCSV singletonScriereInFisiere =  ScriereCSV.getInstance();
        List<ContBancar> conturi_deschise = singletonCitireConturiBancare.citireConturi("ConturiBancare.csv");
        System.out.println(conturi_deschise.size());
        Scanner keyboard = new Scanner(System.in);
        Scanner keyboardString = new Scanner(System.in);
        System.out.println("Hello, this is our bank's app, what can we help you with");
        while(true){
            System.out.println("If you want to exit the program press 0");
            System.out.println("If you are a Client press 1");
            System.out.println("If you are an Employee press 2");
            int input = keyboard.nextInt();
            switch (input){
                case 1:
                    if(conturi_deschise.isEmpty()){
                        System.out.println("We are sorry, our bank is the worst and doesn't have any customers yet, contact an employee");
                    }
                    else {
                        ServiciiClient serviciiClient = new ServiciiClient();
                        System.out.println("Enter the IBAN");
                        String iban = keyboardString.nextLine();
                        boolean OK = false;
                        //Iti cere datele contului de la inceput ca sa stie ce cont interogheaza(IBAN)
                        for(int i=0;i<conturi_deschise.size();i++){
                            if(conturi_deschise.get(i).getIBAN().equals(iban)){
                                OK = true;
                                boolean keepDoing = true;
                                while (keepDoing) {
                                    System.out.println("If you want to exit press 0");
                                    System.out.println("If you want to deposit money in the account press 1");
                                    System.out.println("If you want to withdraw money press 2");
                                    System.out.println("If you want to transfer money press 3");
                                    System.out.println("If you want to pay a bill press 4");
                                    System.out.println("If you want to see all the transactions press 5");
                                    System.out.println("If you want to see the account balance press 6");
                                    int serviciu = keyboard.nextInt();
                                    String actiune;
                                    Pair<Integer, List<Tranzactie>> pereche;
                                    switch(serviciu){
                                        case 1:
                                            pereche = serviciiClient.depunere(keyboard,conturi_deschise.get(i));
                                            conturi_deschise.get(i).setSold(pereche.getKey());
                                            conturi_deschise.get(i).setTranzactii(pereche.getValue());
                                            AlimentareCont alimentareCont = (AlimentareCont) conturi_deschise.get(i).getTranzactii().get(conturi_deschise.get(i).getTranzactii().size()-1);
                                            numarAlimentari++;
                                            String alimentareString = iban + "," + alimentareCont.getSuma_depusa();
                                            singletonScriereInFisiere.adaugare_in_fisier(numarAlimentari,alimentareString,"AlimentariCont.csv");
                                            actionNumber++;
                                            actiune = "Alimentare cont " + LocalDateTime.now();
                                            singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                            break;
                                        case 2:
                                            pereche = serviciiClient.retragere(keyboard,conturi_deschise.get(i));
                                            conturi_deschise.get(i).setSold(pereche.getKey());
                                            conturi_deschise.get(i).setTranzactii(pereche.getValue());
                                            Retragere retragere = (Retragere) conturi_deschise.get(i).getTranzactii().get(conturi_deschise.get(i).getTranzactii().size()-1);
                                            numarRetrageri++;
                                            String retragereString = iban + "," + retragere.getSuma_retrasa();
                                            singletonScriereInFisiere.adaugare_in_fisier(numarRetrageri,retragereString,"Retrageri.csv");
                                            actionNumber++;
                                            actiune = "Retragere " + LocalDateTime.now();
                                            singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                            break;
                                        case 3:
                                            System.out.println("Introdu IBAN-ul destinatarului");
                                            String iban_destinatar = keyboardString.nextLine();
                                            boolean OK2=false;
                                            for(int j=0;j<conturi_deschise.size();j++) {
                                                if (conturi_deschise.get(j).getIBAN().equals(iban_destinatar)) {
                                                    OK2 = true;
                                                    List<Tranzactie> tranzactii_nou = serviciiClient.transfer(keyboard,conturi_deschise.get(i),conturi_deschise.get(j));
                                                    conturi_deschise.get(i).setTranzactii(tranzactii_nou);
                                                    conturi_deschise.get(j).setTranzactii(tranzactii_nou);
                                                    Transfer transferulFacut = (Transfer) tranzactii_nou.get(tranzactii_nou.size()-1);
                                                    conturi_deschise.get(i).setSold(conturi_deschise.get(i).getSold()-transferulFacut.getSuma_transferata());
                                                    conturi_deschise.get(j).setSold(conturi_deschise.get(j).getSold()+transferulFacut.getSuma_transferata());
                                                    numarTransferuri++;
                                                    String transferString = iban + "," + transferulFacut.getSuma_transferata() + ","+ iban_destinatar;
                                                    singletonScriereInFisiere.adaugare_in_fisier(numarTransferuri,transferString,"Transferuri.csv");
                                                    actionNumber++;
                                                    actiune = "Transfer " + LocalDateTime.now();
                                                    singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                                }
                                            }
                                            if(!OK2){
                                                System.out.println("Nu exista destinatarul");
                                            }
                                            break;
                                        case 4:
                                            pereche = serviciiClient.plataFactura(keyboard,keyboardString,conturi_deschise.get(i));
                                            conturi_deschise.get(i).setSold(pereche.getKey());
                                            conturi_deschise.get(i).setTranzactii(pereche.getValue());
                                            PlataFactura plataFactura = (PlataFactura) conturi_deschise.get(i).getTranzactii().get(conturi_deschise.get(i).getTranzactii().size()-1);
                                            numarPlatiFacturi++;
                                            String plataFacturaString = iban + ","+plataFactura.getNumar_factura()+","+plataFactura.getSuma()+","+plataFactura.getCIF()+","+plataFactura.getNume_firma();
                                            singletonScriereInFisiere.adaugare_in_fisier(numarPlatiFacturi,plataFacturaString,"PlatiFacturi.csv");
                                            actionNumber++;
                                            actiune = "Plata Factura " + LocalDateTime.now();
                                            singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                            break;
                                        case 5:
                                            serviciiClient.afisareExtrasCont(conturi_deschise.get(i));
                                            actionNumber++;
                                            actiune = "Afisare Extras De Cont " + LocalDateTime.now();
                                            singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                            break;
                                        case 6:
                                            serviciiClient.afisareSold(conturi_deschise.get(i));
                                            actionNumber++;
                                            actiune = "Afisare Sold " + LocalDateTime.now();
                                            singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                            break;
                                        case 0:
                                            keepDoing=false;
                                            break;
                                        default:
                                            System.out.println("It's not a valid option");
                                    }
                                }
                            }
                        }
                        if (!OK) {
                            System.out.println("The IBAN you have entered does not have an associated account");
                        }
                    }
                    break;
                case 2:
                    ServiciiAdmin serviciiAdmin = new ServiciiAdmin();
                    boolean OK = false;
                    boolean keepDoing = true;
                    while (keepDoing) {
                        System.out.println("If you want to exit press 0");
                        System.out.println("If you want to create an account press 1");
                        System.out.println("If you want to delete an account press 2");
                        System.out.println("If you want to freeze a credit card press 3");
                        System.out.println("If you want to create a deposit for a customer press 4");
                        System.out.println("If you want to make another credit card for a customer press 5");
                        System.out.println("If you want to give a loan to a customer press 6");
                        int serviciu = keyboard.nextInt();
                        String iban;
                        String actiune;
                        switch(serviciu){
                            case 1:
                                conturi_deschise.add(serviciiAdmin.creareCont(keyboard,keyboardString,conturi_deschise.size()));
                                actionNumber++;
                                actiune = "Creare Cont Bancar " + LocalDateTime.now();
                                singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                break;
                            case 2:
                                System.out.println("Introdu IBAN-ul contului pe care vrei sa il stergi:");
                                iban = keyboardString.nextLine();
                                OK=false;
                                for(int i=0;i<conturi_deschise.size();i++){
                                    if(conturi_deschise.get(i).getIBAN().equals(iban)){
                                        OK = true;
                                        conturi_deschise.remove(conturi_deschise.get(i));
                                        actionNumber++;
                                        actiune = "Stergere Cont Bancar " + LocalDateTime.now();
                                        singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                        break;
                                    }
                                }
                                if (!OK) {
                                    System.out.println("The IBAN you have entered does not have an associated account");
                                }
                                break;
                            case 3:
                                System.out.println("Introdu IBAN-ul:");
                                iban = keyboardString.nextLine();
                                for(int i=0;i<conturi_deschise.size();i++){
                                    if(conturi_deschise.get(i).getIBAN().equals(iban)){
                                        OK = true;
                                        conturi_deschise.get(i).setCarduri(serviciiAdmin.blocheazaCard(keyboard,keyboardString, conturi_deschise.get(i)));
                                        actionNumber++;
                                        actiune = "Blocare Card " + LocalDateTime.now();
                                        singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                    }
                                }
                                if (!OK) {
                                    System.out.println("The IBAN you have entered does not have an associated account");
                                }
                                break;
                            case 4:
                                System.out.println("Introdu IBAN-ul:");
                                iban = keyboardString.nextLine();
                                for(int i=0;i<conturi_deschise.size();i++){
                                    if(conturi_deschise.get(i).getIBAN().equals(iban)){
                                        OK = true;
                                        conturi_deschise.get(i).setDepozite(serviciiAdmin.creareDepozit(keyboard,keyboardString, conturi_deschise.get(i)));
                                        numarDepozite++;
                                        DepozitBancar depozitBancar = conturi_deschise.get(i).getDepozite().get(conturi_deschise.get(i).getDepozite().size());
                                        String depozitBancarString = iban+","+depozitBancar.getTermen()+","+depozitBancar.getDobanda();
                                        singletonScriereInFisiere.adaugare_in_fisier(numarDepozite,depozitBancarString,"DepoziteBancare.csv");
                                        actionNumber++;
                                        actiune = "Creare Depozit Bancar " + LocalDateTime.now();
                                        singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                    }
                                }
                                if (!OK) {
                                    System.out.println("The IBAN you have entered does not have an associated account");
                                }
                                break;
                            case 5:
                                System.out.println("Introdu IBAN-ul:");
                                iban = keyboardString.nextLine();
                                for(int i=0;i<conturi_deschise.size();i++){
                                    if(conturi_deschise.get(i).getIBAN().equals(iban)){
                                        OK = true;
                                        conturi_deschise.get(i).setCarduri(serviciiAdmin.creareCard(keyboard,keyboardString, conturi_deschise.get(i)));
                                        actionNumber++;
                                        actiune = "Creare Card Bancar " + LocalDateTime.now();
                                        singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                    }
                                }
                                if (!OK) {
                                    System.out.println("The IBAN you have entered does not have an associated account");
                                }
                                break;
                            case 6:
                                System.out.println("Introdu IBAN-ul:");
                                iban = keyboardString.nextLine();
                                for(int i=0;i<conturi_deschise.size();i++){
                                    if(conturi_deschise.get(i).getIBAN().equals(iban)){
                                        OK = true;
                                        conturi_deschise.get(i).setCredite(serviciiAdmin.acordareCredit(keyboard,keyboardString, conturi_deschise.get(i)));
                                        numarCredite++;
                                        Credit credit = conturi_deschise.get(i).getCredite().get(conturi_deschise.get(i).getCredite().size());
                                        String creditString = iban+","+credit.getSuma()+","+credit.getDurata()+","+credit.getDobanda()+","+credit.getValoare_achitata();
                                        singletonScriereInFisiere.adaugare_in_fisier(numarCredite,creditString,"DepoziteBancare.csv");
                                        actionNumber++;
                                        actiune = "Creare Credit " + LocalDateTime.now();
                                        singletonScriereInFisiere.adaugare_in_fisier(actionNumber,actiune,"Actiuni.csv");
                                    }
                                }
                                if (!OK) {
                                    System.out.println("The IBAN you have entered does not have an associated account");
                                }
                                break;
                            case 0:
                                keepDoing=false;
                                break;
                            default:
                                System.out.println("It's not a valid option");
                        }
                    }
                    break;
                case 0:
                    for(int i=0;i<conturi_deschise.size();i++){
                        ContBancar contBancar = conturi_deschise.get(i);
                        String contBancarString = contBancar.getNume()+","+contBancar.getPrenume()+","+contBancar.getSold()+","+contBancar.getIBAN();
                        singletonScriereInFisiere.adaugare_in_fisier(i+1,contBancarString,"ConturiBancare.csv");
                    }
                    return;
                default:
                    System.out.println("This is not a valid command");
            }
        }
    }
}
