package id.technow.reservasiklinik.API;

import java.util.ArrayList;

import id.technow.reservasiklinik.Model.ResponseCorona;
import id.technow.reservasiklinik.Model.ResponseListPasien;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiCorona {
    @GET("indonesia")
    Call<ArrayList<ResponseCorona>> coronaIndo(
            @Header("Accept") String accept
    );
}
