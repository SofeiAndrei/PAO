import javafx.util.Pair;

import java.util.*;

public class Main {
    public static void main(String[] args){
        List<ContBancar> conturi_deschise = new ArrayList<>();
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
                                    Pair<Integer, List<Tranzactie>> pereche;
                                    switch(serviciu){
                                        case 1:
                                            pereche = serviciiClient.depunere(keyboard,keyboardString,conturi_deschise.get(i));
                                            conturi_deschise.get(i).setSold(pereche.getKey());
                                            conturi_deschise.get(i).setTranzactii(pereche.getValue());
                                            break;
                                        case 2:
                                            pereche = serviciiClient.retragere(keyboard,keyboardString,conturi_deschise.get(i));
                                            conturi_deschise.get(i).setSold(pereche.getKey());
                                            conturi_deschise.get(i).setTranzactii(pereche.getValue());
                                            break;
                                        case 3:
                                            System.out.println("Introdu IBAN-ul destinatarului");
                                            String iban_destinatar = keyboardString.nextLine();
                                            boolean OK2=false;
                                            for(int j=0;j<conturi_deschise.size();j++) {
                                                if (conturi_deschise.get(j).getIBAN().equals(iban_destinatar)) {
                                                    OK2 = true;
                                                    int sold_vechi = conturi_deschise.get(i).getSold();
                                                    pereche = serviciiClient.retragere(keyboard,keyboardString,conturi_deschise.get(i));
                                                    conturi_deschise.get(i).setSold(pereche.getKey());
                                                    conturi_deschise.get(i).setTranzactii(pereche.getValue());
                                                    if(sold_vechi != pereche.getKey()){
                                                        pereche = serviciiClient.depunere(keyboard,keyboardString,conturi_deschise.get(j));
                                                        conturi_deschise.get(j).setSold(pereche.getKey());
                                                        conturi_deschise.get(j).setTranzactii(pereche.getValue());
                                                    }
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
                                            break;
                                        case 5:
                                            serviciiClient.afisareExtrasCont(conturi_deschise.get(i));
                                            break;
                                        case 6:
                                            serviciiClient.afisareSold(conturi_deschise.get(i));
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
                        switch(serviciu){
                            case 1:
                                conturi_deschise.add(serviciiAdmin.creareCont(keyboard,keyboardString,conturi_deschise.size()));
                                break;
                            case 2:
                                System.out.println("Introdu IBAN-ul contului pe care vrei sa il stergi:");
                                iban = keyboardString.nextLine();
                                for(int i=0;i<conturi_deschise.size();i++){
                                    if(conturi_deschise.get(i).getIBAN().equals(iban)){
                                        OK = true;
                                        conturi_deschise.remove(conturi_deschise.get(i));
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
                    return;
                default:
                    System.out.println("This is not a valid command");
            }
        }
    }
}
