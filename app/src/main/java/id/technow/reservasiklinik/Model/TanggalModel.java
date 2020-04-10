package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TanggalModel {
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    public TanggalModel(String tanggal, String keterangan) {
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
