package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("user")
    @Expose
    private UserModel user;

    public ResponseLogin(String status, UserModel user) {
        this.status = status;
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public UserModel getUser() {
        return user;
    }
}
