package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseJam {
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("result")
    @Expose
    private ArrayList<JamModel> jam;

    public ResponseJam(String status, ArrayList<JamModel> jam) {
        this.status = status;
        this.jam = jam;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<JamModel> getJam() {
        return jam;
    }
}
