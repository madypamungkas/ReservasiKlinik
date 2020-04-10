package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostScreeningModel {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("pasien_id")
    @Expose
    private String pasien_id;

    @SerializedName("keluhan")
    @Expose
    private String keluhan;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("waktu")
    @Expose
    private String waktu;

    @SerializedName("status_updated_by")
    @Expose
    private String status_updated_by;

    @SerializedName("calon_pasien")
    @Expose
    private PasienModel calon_pasien;

    public PostScreeningModel(String id, String kode, String pasien_id, String keluhan, String status, String tanggal, String waktu, String status_updated_by, PasienModel calon_pasien) {
        this.id = id;
        this.kode = kode;
        this.pasien_id = pasien_id;
        this.keluhan = keluhan;
        this.status = status;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.status_updated_by = status_updated_by;
        this.calon_pasien = calon_pasien;
    }

    public String getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public String getPasien_id() {
        return pasien_id;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getStatus_updated_by() {
        return status_updated_by;
    }

    public PasienModel getCalon_pasien() {
        return calon_pasien;
    }
}
