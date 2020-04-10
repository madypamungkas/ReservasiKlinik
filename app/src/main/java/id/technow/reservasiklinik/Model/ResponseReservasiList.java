package id.technow.reservasiklinik.Model;

import android.widget.ArrayAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseReservasiList {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("reservasi")
    @Expose
    private ArrayList< PostScreeningModel> reservasi;

    public ResponseReservasiList(String status, ArrayList<PostScreeningModel> reservasi) {
        this.status = status;
        this.reservasi = reservasi;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<PostScreeningModel> getReservasi() {
        return reservasi;
    }
}
