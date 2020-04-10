package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoliModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("poli")
    @Expose
    private String poli;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    public PoliModel(String id, String poli, String kode, String created_at, String updated_at) {
        this.id = id;
        this.poli = poli;
        this.kode = kode;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public String getPoli() {
        return poli;
    }

    public String getKode() {
        return kode;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
