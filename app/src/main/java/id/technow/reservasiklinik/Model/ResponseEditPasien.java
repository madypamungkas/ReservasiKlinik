package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseEditPasien {
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("result")
    @Expose
    private PasienModel pasien;

    public ResponseEditPasien(String status, PasienModel pasien) {
        this.status = status;
        this.pasien = pasien;
    }

    public String getStatus() {
        return status;
    }

    public PasienModel getPasien() {
        return pasien;
    }
}
