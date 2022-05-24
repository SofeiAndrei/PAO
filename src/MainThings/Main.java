package MainThings;

import Jdbc.JdbcConnection;
import ServiciiPentruCSV.ScriereCSV;
import Tranzactii.*;
import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args){
        JdbcConnection jdbcConnection = new JdbcConnection();

        int numarAlimentari=0;
        int numarDepozite=0;
        int numarPlatiFacturi=0;
        int numarRetrageri=0;
        int numarTransferuri=0;
        int numarCredite=0;
        int actionNumber=0;
        int numarCarduri=0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



        List<ContBancar> conturi_deschise = new ArrayList<>();
        ///AICI IN LOC DE CHESTIA CU CSV TREBUIE SA DAU UN select * from contbancar si sa le introduc in lista de conturi_deschise
        String queryStart = "select * from contbancar";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                int Id_client = rs.getInt("id_client");
                String Nume = rs.getString("nume");
                String Prenume = rs.getString("prenume");
                int Sold = rs.getInt("sold");
                String Iban = rs.getString("iban");
                ContBancar contCitit = new ContBancar(Id_client,Nume,Prenume,Sold,Iban);
                conturi_deschise.add(contCitit);
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        ///TREBUIE PENTRU FIECARE ALT TABEL SA IAU CATE ELEMENTE SUNT DEJA ACOLO, CA SA NU INCERC SA SUPRASCRIU
        queryStart = "select * from actiuni";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                actionNumber+=1;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        queryStart = "select * from alimentare_cont";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                numarAlimentari+=1;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        queryStart = "select * from card";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                numarCarduri+=1;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        queryStart = "select * from credit";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                numarCredite+=1;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        queryStart = "select * from depozit";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                numarDepozite+=1;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        queryStart = "select * from plata_factura";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                numarPlatiFacturi+=1;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        queryStart = "select * from retragere";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                numarRetrageri+=1;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        queryStart = "select * from transfer";
        try{
            ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
            while(rs.next()){
                numarTransferuri+=1;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }



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
                                            String timeForQuerry = LocalDateTime.now().format(formatter);
                                            try{
                                                String query = "insert into alimentare_cont values("+numarAlimentari+",'"+timeForQuerry+"',"+alimentareCont.getSuma_depusa()+",'"+iban+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            try{
                                                int suma_noua = conturi_deschise.get(i).getSold();
                                                String query = "update contbancar set sold = "+suma_noua+" where (contbancar.iban='"+iban+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            actionNumber++;
                                            try{
                                                String query = "insert into actiuni values("+actionNumber+",'Alimentare cont',"+"'"+timeForQuerry+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            break;
                                        case 2:
                                            pereche = serviciiClient.retragere(keyboard,conturi_deschise.get(i));
                                            conturi_deschise.get(i).setSold(pereche.getKey());
                                            conturi_deschise.get(i).setTranzactii(pereche.getValue());
                                            Retragere retragere = (Retragere) conturi_deschise.get(i).getTranzactii().get(conturi_deschise.get(i).getTranzactii().size()-1);
                                            numarRetrageri++;
                                            timeForQuerry = LocalDateTime.now().format(formatter);
                                            try{
                                                String query = "insert into retragere values("+numarRetrageri+",'"+timeForQuerry+"',"+retragere.getSuma_retrasa()+",'"+iban+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            try{
                                                int suma_noua = conturi_deschise.get(i).getSold();
                                                String query = "update contbancar set sold = "+suma_noua+" where (contbancar.iban='"+iban+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            actionNumber++;
                                            try{
                                                String query = "insert into actiuni values("+actionNumber+",'Retragere',"+"'"+timeForQuerry+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
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
                                                    timeForQuerry = LocalDateTime.now().format(formatter);
                                                    try{
                                                        String query = "insert into transfer values("+numarTransferuri+",'"+timeForQuerry+"',"+transferulFacut.getSuma_transferata()+",'"+transferulFacut.getIBAN_destinatar()+"','"+iban+"')";
                                                        jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                                    } catch (SQLException e) {
                                                        System.out.println("Error");
                                                        e.printStackTrace();
                                                    }
                                                    try{
                                                        int suma_noua = conturi_deschise.get(i).getSold();
                                                        String query = "update contbancar set sold = "+suma_noua+" where (contbancar.iban='"+iban+"')";
                                                        jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                                    } catch (SQLException e) {
                                                        System.out.println("Error");
                                                        e.printStackTrace();
                                                    }
                                                    try{
                                                        int suma_noua = conturi_deschise.get(j).getSold();
                                                        String query = "update contbancar set sold = "+suma_noua+" where (contbancar.iban='"+transferulFacut.getIBAN_destinatar()+"')";
                                                        jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                                    } catch (SQLException e) {
                                                        System.out.println("Error");
                                                        e.printStackTrace();
                                                    }
                                                    actionNumber++;
                                                    try{
                                                        String query = "insert into actiuni values("+actionNumber+",'Transfer',"+"'"+timeForQuerry+"')";
                                                        jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                                    } catch (SQLException e) {
                                                        System.out.println("Error");
                                                        e.printStackTrace();
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
                                            PlataFactura plataFactura = (PlataFactura) conturi_deschise.get(i).getTranzactii().get(conturi_deschise.get(i).getTranzactii().size()-1);
                                            numarPlatiFacturi++;
                                            actionNumber++;
                                            timeForQuerry = LocalDateTime.now().format(formatter);
                                            try{
                                                String query = "insert into plata_factura values("+numarPlatiFacturi+",'"+timeForQuerry+"',"+plataFactura.getNumar_factura()+","+plataFactura.getSuma()+",'"+plataFactura.getCIF()+"','"+plataFactura.getNume_firma()+"','"+iban+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            try{
                                                int suma_noua = conturi_deschise.get(i).getSold();
                                                String query = "update contbancar set sold = "+suma_noua+" where (contbancar.iban='"+iban+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            try{
                                                String query = "insert into actiuni values("+actionNumber+",'Plata Factura',"+"'"+timeForQuerry+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            break;
                                        case 5:
                                            serviciiClient.afisareExtrasCont(conturi_deschise.get(i));

                                            queryStart = "select * from alimentare_cont where (alimentare_cont.iban='"+iban+"')";
                                            System.out.println("Alimentari de cont:");
                                            try{
                                                ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
                                                while(rs.next()){
                                                    String data_din_query = rs.getString("data");
                                                    LocalDateTime data = LocalDateTime.parse(data_din_query,formatter);
                                                    int suma_depusa = rs.getInt("suma_depusa");
                                                    AlimentareCont alimentare = new AlimentareCont(data,suma_depusa);
                                                    System.out.println(alimentare);
                                                }
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }

                                            queryStart = "select * from retragere where (retragere.iban='"+iban+"')";
                                            System.out.println("Retrageri:");
                                            try{
                                                ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
                                                while(rs.next()){
                                                    String data_din_query = rs.getString("data");
                                                    LocalDateTime data = LocalDateTime.parse(data_din_query,formatter);
                                                    int suma_retrasa = rs.getInt("suma_retrasa");
                                                    Retragere retragere1 = new Retragere(data,suma_retrasa);
                                                    System.out.println(retragere1);
                                                }
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }

                                            queryStart = "select * from plata_factura where (plata_factura.iban='"+iban+"')";
                                            System.out.println("Plati de Facturi:");
                                            try{
                                                ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
                                                while(rs.next()){
                                                    String data_din_query = rs.getString("data");
                                                    LocalDateTime data = LocalDateTime.parse(data_din_query,formatter);
                                                    int numar_factura = rs.getInt("numar_factura");
                                                    int suma_depusa = rs.getInt("suma_depusa");
                                                    String cif = rs.getString("cif");
                                                    String nume_firma = rs.getString("nume_firma");
                                                    PlataFactura plataFactura1 = new PlataFactura(data,numar_factura,suma_depusa,cif, nume_firma);
                                                    System.out.println(plataFactura1);
                                                }
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }

                                            queryStart = "select * from transfer where (transfer.iban='"+iban+"')";
                                            System.out.println("Transferuri:");
                                            try{
                                                ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
                                                while(rs.next()){
                                                    String data_din_query = rs.getString("data");
                                                    LocalDateTime data = LocalDateTime.parse(data_din_query,formatter);
                                                    int suma_transferata = rs.getInt("suma_transferata");
                                                    String iban_destinatar_querry = rs.getString("iban_destinatar");
                                                    Transfer transfer1 = new Transfer(data,suma_transferata,iban_destinatar_querry);
                                                    System.out.println(transfer1);
                                                }
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }

                                            actionNumber++;
                                            timeForQuerry = LocalDateTime.now().format(formatter);
                                            try{
                                                String query = "insert into actiuni values("+actionNumber+",'Afisare Extras De Cont',"+"'"+timeForQuerry+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            break;
                                        case 6:
                                            queryStart = "select sold from contbancar where (contbancar.iban='"+iban+"')";
                                            try{
                                                ResultSet rs = jdbcConnection.ExecuteSelectQuery(queryStart);
                                                while(rs.next()){
                                                    int sold=rs.getInt("sold");
                                                    System.out.println(sold);
                                                }
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
                                            actionNumber++;
                                            timeForQuerry = LocalDateTime.now().format(formatter);
                                            try{
                                                String query = "insert into actiuni values("+actionNumber+",'Afisare Sold',"+"'"+timeForQuerry+"')";
                                                jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                            } catch (SQLException e) {
                                                System.out.println("Error");
                                                e.printStackTrace();
                                            }
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

                                String timeForQuerry = LocalDateTime.now().format(formatter);

                                ContBancar cont = conturi_deschise.get(conturi_deschise.size()-1);
                                try{
                                    String querry = "insert into contbancar values("+cont.getId_client()+",'"+cont.getNume()+"','"+cont.getPrenume()+"',"+cont.getSold()+",'"+cont.getIBAN()+"')";
                                    jdbcConnection.ExecuteCreateUpdateDeleteQuery(querry);
                                } catch (SQLException e) {
                                    System.out.println("Error");
                                    e.printStackTrace();
                                }

                                try{
                                    String querry = "insert into actiuni values("+actionNumber+",'Creare Cont Bancar',"+"'"+timeForQuerry+"')";
                                    jdbcConnection.ExecuteCreateUpdateDeleteQuery(querry);
                                } catch (SQLException e) {
                                    System.out.println("Error");
                                    e.printStackTrace();
                                }

                                break;
                            case 2:
                                System.out.println("Introdu IBAN-ul contului pe care vrei sa il stergi:");
                                iban = keyboardString.nextLine();
                                OK=false;
                                for(int i=0;i<conturi_deschise.size();i++){
                                    if(conturi_deschise.get(i).getIBAN().equals(iban)){
                                        OK = true;
                                        conturi_deschise.remove(conturi_deschise.get(i));
                                        try{
                                            String query = "delete from contbancar where (contbancar.iban='"+iban+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }

                                        actionNumber++;
                                        timeForQuerry = LocalDateTime.now().format(formatter);
                                        try{
                                            String query = "insert into actiuni values("+actionNumber+",'Stergere Cont Bancar',"+"'"+timeForQuerry+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }

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
                                        try{
                                            String query = "update card set blocat = 1 where (card.iban='"+iban+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }
                                        actionNumber++;
                                        timeForQuerry = LocalDateTime.now().format(formatter);
                                        try{
                                            String query = "insert into actiuni values("+actionNumber+",'Blocare Card',"+"'"+timeForQuerry+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }

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
                                        DepozitBancar depozitBancar = conturi_deschise.get(i).getDepozite().get(conturi_deschise.get(i).getDepozite().size()-1);
                                        try{
                                            String querry = "insert into depozit values("+numarDepozite+",'"+depozitBancar.getTermen().format(formatter)+"',"+depozitBancar.getDobanda()+",'"+iban+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(querry);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }
                                        actionNumber++;
                                        timeForQuerry = LocalDateTime.now().format(formatter);
                                        try{
                                            String query = "insert into actiuni values("+actionNumber+",'Creare Depozit Bancar',"+"'"+timeForQuerry+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }
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
                                        numarCarduri+=1;
                                        Pair<SortedSet<Card>,Card> result = serviciiAdmin.creareCard(keyboard,keyboardString, conturi_deschise.get(i),numarCarduri);
                                        conturi_deschise.get(i).setCarduri(result.getKey());
                                        Card card = result.getValue();
                                        int adv;
                                        if(card.isBlocat()){
                                            adv = 1;
                                        }
                                        else{
                                            adv = 0;
                                        }
                                        try{
                                            String querry = "insert into card values("+card.getNumar()+",'"+card.getNume_titular()+"','"+card.getData_expirare()+"',"+adv+",'"+iban+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(querry);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }
                                        actionNumber++;
                                        timeForQuerry = LocalDateTime.now().format(formatter);
                                        try{
                                            String query = "insert into actiuni values("+actionNumber+",'Creare Card Bancar',"+"'"+timeForQuerry+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }
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
                                        numarCredite+=1;
                                        Credit credit = conturi_deschise.get(i).getCredite().get(conturi_deschise.get(i).getCredite().size()-1);
                                        try{
                                            String querry = "insert into credit values("+numarCredite+","+credit.getSuma()+","+credit.getDurata()+","+credit.getDobanda()+","+credit.getValoare_achitata()+",'"+iban+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(querry);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }
                                        try{
                                            int suma_anterioare = conturi_deschise.get(i).getSold();
                                            int suma_noua = suma_anterioare + credit.getSuma();
                                            String query = "update contbancar set sold = "+suma_noua+" where (contbancar.iban='"+iban+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }
                                        actionNumber++;
                                        timeForQuerry = LocalDateTime.now().format(formatter);
                                        try{
                                            String query = "insert into actiuni values("+actionNumber+",'Creare Credit',"+"'"+timeForQuerry+"')";
                                            jdbcConnection.ExecuteCreateUpdateDeleteQuery(query);
                                        } catch (SQLException e) {
                                            System.out.println("Error");
                                            e.printStackTrace();
                                        }
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
