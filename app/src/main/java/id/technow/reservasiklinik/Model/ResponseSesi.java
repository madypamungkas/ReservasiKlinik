package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSesi {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private ArrayList<SesiModel> sesi;

    public ResponseSesi(String status, ArrayList<SesiModel> sesi) {
        this.status = status;
        this.sesi = sesi;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<SesiModel> getSesi() {
        return sesi;
    }


}
