package id.technow.reservasiklinik.API;

import java.util.ArrayList;

import id.technow.reservasiklinik.Model.ResponseCorona;
import id.technow.reservasiklinik.Model.ResponseListPasien;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiCorona {
    @GET("indonesia")
    Call<ArrayList<ResponseCorona>> coronaIndo(
            @Header("Accept") String accept
    );

    @GET("indonesia/{provinsi}")
    Call<ArrayList<ResponseCorona>> coronaProv(
            @Header("Accept") String accept,
            @Path("provinsi") String provinsi
    );
}
