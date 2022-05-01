package ServiciiPentruCSV;

import Tranzactii.Tranzactie;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class ScriereCSV {
    //singleton => constructor privat
    private static ScriereCSV single_instance = null;
    private ScriereCSV(){}
    public static ScriereCSV getInstance(){
        if(single_instance == null){
            single_instance = new ScriereCSV();
        }
        return single_instance;
    }
    public static ScriereCSV getSingle_instance() {
        return single_instance;
    }
    public void adaugare_in_fisier(int operationNumber, String stringDeAdaugat,String file_location){
        try{
            if(operationNumber==1) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file_location, false));
                writer.write(stringDeAdaugat);
                writer.close();
            }
            else{
                BufferedWriter writer = new BufferedWriter(new FileWriter(file_location, true));
                writer.append('\n');
                writer.append(stringDeAdaugat);
                writer.close();
            }
        }catch(IOException e){
            System.out.println("Exceptie I/O");
        }
    }
}
