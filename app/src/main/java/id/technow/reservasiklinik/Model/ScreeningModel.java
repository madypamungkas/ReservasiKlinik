package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScreeningModel {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("kategori")
    @Expose
    private String kategori;

    @SerializedName("pertanyaan")
    @Expose
    private String pertanyaan;

    @SerializedName("skor_ya")
    @Expose
    private int skor_ya;

    @SerializedName("skor_tidak")
    @Expose
    private int skor_tidak;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("jawaban")
    @Expose
    private String jawaban;

    public ScreeningModel(int id, String kategori, String pertanyaan, int skor_ya, int skor_tidak, String created_at, String updated_at, String jawaban) {
        this.id = id;
        this.kategori = kategori;
        this.pertanyaan = pertanyaan;
        this.skor_ya = skor_ya;
        this.skor_tidak = skor_tidak;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.jawaban = jawaban;
    }

    public int getId() {
        return id;
    }

    public String getKategori() {
        return kategori;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public int getSkor_ya() {
        return skor_ya;
    }

    public int getSkor_tidak() {
        return skor_tidak;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public void setSkor_ya(int skor_ya) {
        this.skor_ya = skor_ya;
    }

    public void setSkor_tidak(int skor_tidak) {
        this.skor_tidak = skor_tidak;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
