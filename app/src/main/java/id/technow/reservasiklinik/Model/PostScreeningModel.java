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
    @SerializedName("poli_id")
    @Expose
    private String poli_id;
    @SerializedName("antrian")
    @Expose
    private String antrian;
    @SerializedName("perkiraan_jam_pelayanan")
    @Expose
    private String jam;
    @SerializedName("counter")
    @Expose
    private String counter;
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

    @SerializedName("kekhawatiran")
    @Expose
    private String kekhawatiran;
    @SerializedName("riwayat_penyakit_menahun")
    @Expose
    private String riwayat_penyakit_menahun;

    @SerializedName("upaya_pengobatan")
    @Expose
    private String upaya_pengobatan;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("status_updated_by")
    @Expose
    private String status_updated_by;
    @SerializedName("calon_pasien")
    @Expose
    private PasienModel calon_pasien;
    @SerializedName("poli")
    @Expose
    private PoliModel poli;

    public PostScreeningModel(String id, String kode, String poli_id, String antrian, String jam, String counter, String pasien_id, String keluhan, String status, String tanggal, String kekhawatiran, String riwayat_penyakit_menahun, String upaya_pengobatan, String created_at, String updated_at, String status_updated_by, PasienModel calon_pasien, PoliModel poli) {
        this.id = id;
        this.kode = kode;
        this.poli_id = poli_id;
        this.antrian = antrian;
        this.jam = jam;
        this.counter = counter;
        this.pasien_id = pasien_id;
        this.keluhan = keluhan;
        this.status = status;
        this.tanggal = tanggal;
        this.kekhawatiran = kekhawatiran;
        this.riwayat_penyakit_menahun = riwayat_penyakit_menahun;
        this.upaya_pengobatan = upaya_pengobatan;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status_updated_by = status_updated_by;
        this.calon_pasien = calon_pasien;
        this.poli = poli;
    }

    public PoliModel getPoli() {
        return poli;
    }

    public String getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public String getPoli_id() {
        return poli_id;
    }

    public String getAntrian() {
        return antrian;
    }

    public String getJam() {
        return jam;
    }

    public String getCounter() {
        return counter;
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

    public String getKekhawatiran() {
        return kekhawatiran;
    }

    public String getRiwayat_penyakit_menahun() {
        return riwayat_penyakit_menahun;
    }

    public String getUpaya_pengobatan() {
        return upaya_pengobatan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getStatus_updated_by() {
        return status_updated_by;
    }

    public PasienModel getCalon_pasien() {
        return calon_pasien;
    }
}
