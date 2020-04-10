package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultScreeningModel {
    @SerializedName("skor")
    @Expose
    private String skor;

    @SerializedName("hasil")
    @Expose
    private String hasil;

    public ResultScreeningModel(String skor, String hasil) {
        this.skor = skor;
        this.hasil = hasil;
    }

    public String getSkor() {
        return skor;
    }

    public String getHasil() {
        return hasil;
    }
}
