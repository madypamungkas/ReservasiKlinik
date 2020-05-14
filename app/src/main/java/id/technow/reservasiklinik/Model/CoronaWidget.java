package id.technow.reservasiklinik.Model;

public class CoronaWidget {
    public String indPos;
    public String indSembuh;
    public String indMen;
    public String provPos;
    public String provSembuh;
    public String provMen;
    public String provinsi;

    public CoronaWidget(String indPos, String indSembuh, String indMen, String provPos, String provSembuh, String provMen, String provinsi) {
        this.indPos = indPos;
        this.indSembuh = indSembuh;
        this.indMen = indMen;
        this.provPos = provPos;
        this.provSembuh = provSembuh;
        this.provMen = provMen;
        this.provinsi = provinsi;
    }

    public String getIndPos() {
        return indPos;
    }

    public void setIndPos(String indPos) {
        this.indPos = indPos;
    }

    public String getIndSembuh() {
        return indSembuh;
    }

    public void setIndSembuh(String indSembuh) {
        this.indSembuh = indSembuh;
    }

    public String getIndMen() {
        return indMen;
    }

    public void setIndMen(String indMen) {
        this.indMen = indMen;
    }

    public String getProvPos() {
        return provPos;
    }

    public void setProvPos(String provPos) {
        this.provPos = provPos;
    }

    public String getProvSembuh() {
        return provSembuh;
    }

    public void setProvSembuh(String provSembuh) {
        this.provSembuh = provSembuh;
    }

    public String getProvMen() {
        return provMen;
    }

    public void setProvMen(String provMen) {
        this.provMen = provMen;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }
}
