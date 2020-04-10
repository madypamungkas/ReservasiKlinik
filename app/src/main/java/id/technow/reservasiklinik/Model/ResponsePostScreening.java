package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePostScreening {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("reservasi")
    @Expose
    private PostScreeningModel reservasi;

    @SerializedName("screening")
    @Expose
    private ResultScreeningModel screening;

    public ResponsePostScreening(String status, PostScreeningModel reservasi, ResultScreeningModel screening) {
        this.status = status;
        this.reservasi = reservasi;
        this.screening = screening;
    }

    public String getStatus() {
        return status;
    }

    public PostScreeningModel getReservasi() {
        return reservasi;
    }

    public ResultScreeningModel getScreening() {
        return screening;
    }
}
