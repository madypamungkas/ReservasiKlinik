package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponsePoli {
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("result")
    @Expose
    private ArrayList<PoliModel> result;

    public ResponsePoli(String status, ArrayList<PoliModel> result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<PoliModel> getResult() {
        return result;
    }
}
