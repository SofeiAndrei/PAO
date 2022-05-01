package MainThings;

import Tranzactii.Tranzactie;

import java.util.Comparator;

public class ComparatorCustom implements Comparator<Tranzactie> {
    @Override
    public int compare(Tranzactie tranzactie1, Tranzactie tranzactie2){
        return (tranzactie1.getData()).compareTo(tranzactie2.getData());
    }
}
