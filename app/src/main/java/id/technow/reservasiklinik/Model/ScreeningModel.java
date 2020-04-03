package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScreeningModel {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("kategori")
    @Expose
    private String kategori;

    @SerializedName("pertanyaan")
    @Expose
    private String pertanyaan;

    @SerializedName("skor_ya")
    @Expose
    private String skor_ya;

    @SerializedName("skor_tidak")
    @Expose
    private String skor_tidak;

    @SerializedName("jawaban")
    @Expose
    private String jawaban;

    public ScreeningModel(String id, String kategori, String pertanyaan, String skor_ya, String skor_tidak, String jawaban) {
        this.id = id;
        this.kategori = kategori;
        this.pertanyaan = pertanyaan;
        this.skor_ya = skor_ya;
        this.skor_tidak = skor_tidak;
        this.jawaban = jawaban;
    }

    public String getId() {
        return id;
    }

    public String getKategori() {
        return kategori;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public String getSkor_ya() {
        return skor_ya;
    }

    public String getSkor_tidak() {
        return skor_tidak;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
