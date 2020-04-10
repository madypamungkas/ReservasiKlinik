package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePostReservasi {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("reservasi")
    @Expose
    private PostReservasiModel reservasi;

    public ResponsePostReservasi(String status, PostReservasiModel reservasi) {
        this.status = status;
        this.reservasi = reservasi;
    }

    public String getStatus() {
        return status;
    }

    public PostReservasiModel getReservasi() {
        return reservasi;
    }
}
