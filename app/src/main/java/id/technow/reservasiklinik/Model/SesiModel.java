package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SesiModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sesi")
    @Expose
    private String sesi;

    public SesiModel(String id, String sesi) {
        this.id = id;
        this.sesi = sesi;
    }

    public String getId() {
        return id;
    }

    public String getSesi() {
        return sesi;
    }
}
