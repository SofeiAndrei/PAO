import java.time.LocalDate;
import java.util.Comparator;

public class ComparatorCustom implements Comparator<Tranzactie> {
    @Override
    public int compare(Tranzactie tranzactie1, Tranzactie tranzactie2){
        return tranzactie1.data.compareTo(tranzactie2.data);
    }
}
