package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePostScreeningUmum {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("screening")
    @Expose
    private ResultScreeningModel screening;

    public ResponsePostScreeningUmum(String status, ResultScreeningModel screening) {
        this.status = status;
        this.screening = screening;
    }

    public String getStatus() {
        return status;
    }

    public ResultScreeningModel getScreening() {
        return screening;
    }
}
