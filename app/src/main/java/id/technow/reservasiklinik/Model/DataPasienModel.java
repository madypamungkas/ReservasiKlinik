package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPasienModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("no_telepon")
    @Expose
    private String no_telepon;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("no_bpjs")
    @Expose
    private String no_bpjs;
    @SerializedName("created_by")
    @Expose
    private String created_by;
    @SerializedName("tipe")
    @Expose
    private String tipe;

    public DataPasienModel(int id, String no_telepon, String nama, String nik, String no_bpjs, String created_by, String tipe) {
        this.id = id;
        this.no_telepon = no_telepon;
        this.nama = nama;
        this.nik = nik;
        this.no_bpjs = no_bpjs;
        this.created_by = created_by;
        this.tipe = tipe;
    }

    public int getId() {
        return id;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public String getNama() {
        return nama;
    }

    public String getNik() {
        return nik;
    }

    public String getNo_bpjs() {
        return no_bpjs;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getTipe() {
        return tipe;
    }
}
