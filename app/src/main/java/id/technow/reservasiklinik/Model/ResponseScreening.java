package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseScreening {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private ArrayList<ScreeningModel> screening;

    public ResponseScreening(String status, ArrayList<ScreeningModel> screening) {
        this.status = status;
        this.screening = screening;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<ScreeningModel> getScreening() {
        return screening;
    }

    public void setScreening(ArrayList<ScreeningModel> screening) {
        this.screening = screening;
    }
}
