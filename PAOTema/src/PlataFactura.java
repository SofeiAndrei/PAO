import java.time.LocalDate;

public class PlataFactura extends Tranzactie{
    private int numar_factura;
    private int suma;
    private String CIF;
    private String nume_firma;

    public PlataFactura() {
    }

    public PlataFactura(LocalDate data, int numar_factura, int suma, String CIF, String nume_firma) {
        super(data);
        this.numar_factura = numar_factura;
        this.suma = suma;
        this.CIF = CIF;
        this.nume_firma = nume_firma;
    }

    public int getNumar_factura() {
        return numar_factura;
    }

    public void setNumar_factura(int numar_factura) {
        this.numar_factura = numar_factura;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public String getCIF() {
        return CIF;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public String getNume_firma() {
        return nume_firma;
    }

    public void setNume_firma(String nume_firma) {
        this.nume_firma = nume_firma;
    }

    @Override
    public String toString() {
        return "PlataFactura{" +
                "numar_factura=" + numar_factura +
                ", suma=" + suma +
                ", CIF='" + CIF + '\'' +
                ", nume_firma='" + nume_firma + '\'' +
                ", data=" + data +
                '}';
    }
}
