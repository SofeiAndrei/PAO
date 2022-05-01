package ServiciiPentruCSV;
import MainThings.ContBancar;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CitireCSV {
    //singleton => constructor privat
    private static CitireCSV single_instance = null;
    private CitireCSV(){}
    public static CitireCSV getInstance(){
        if(single_instance == null){
            single_instance = new CitireCSV();
        }
        return single_instance;
    }

    public static CitireCSV getSingle_instance() {
        return single_instance;
    }
    public List<ContBancar> citireConturi(String file_location){
        List<ContBancar> conturi_citite = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file_location));
            String line = reader.readLine();
            int i=0;
            while(line != null){
                    i++;
                    String[] fields =line.split(",");
                    ContBancar cont_citit = new ContBancar(i,fields[0],fields[1],Integer.parseInt(fields[2]),fields[3]);
                    conturi_citite.add(cont_citit);
                    line = reader.readLine();
            }
            reader.close();
        } catch(IOException e){
            System.out.println("Exceptie I/O");
        }
        return conturi_citite;
    }
}
