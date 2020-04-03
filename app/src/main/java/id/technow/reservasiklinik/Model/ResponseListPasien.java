package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseListPasien {
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("result")
    @Expose
    private ArrayList<PasienModel> pasien;

    public ResponseListPasien(String status, ArrayList<PasienModel> pasien) {
        this.status = status;
        this.pasien = pasien;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<PasienModel> getPasien() {
        return pasien;
    }
}
