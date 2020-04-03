package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JamModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jam")
    @Expose
    private String jam;

    public JamModel(String id, String jam) {
        this.id = id;
        this.jam = jam;
    }

    public String getId() {
        return id;
    }

    public String getJam() {
        return jam;
    }
}
