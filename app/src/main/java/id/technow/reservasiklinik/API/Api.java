package id.technow.reservasiklinik.API;

import id.technow.reservasiklinik.Model.ResponseEditPasien;
import id.technow.reservasiklinik.Model.ResponseJam;
import id.technow.reservasiklinik.Model.ResponseListPasien;
import id.technow.reservasiklinik.Model.ResponseLogin;
import id.technow.reservasiklinik.Model.ResponseScreening;
import id.technow.reservasiklinik.Model.ResponseSesi;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> loginUser(
            @Field("email") String email,
            @Field("password") String password);

    @GET("list-pasien")
    Call<ResponseListPasien> pasien(
            @Header("Accept") String accept,
            @Header("Authorization") String token
    );

    @GET("screening")
    Call<ResponseScreening> screening(
            @Header("Accept") String accept,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @PUT("pasien/ubah/{id}")
    Call<ResponseEditPasien> updatePasien(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("id") String id,
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("nik") String nik,
            @Field("nomor_bpjs") String nomor_bpjs
    );

    @FormUrlEncoded
    @POST("pasien/tambah")
    Call<ResponseEditPasien> tambahPasien(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("nik") String nik,
            @Field("nomor_bpjs") String nomor_bpjs
    );

    @FormUrlEncoded
    @POST("reservasi/store/{id_pasien}")
    Call<ResponseEditPasien> addReservasi(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("id_pasien") String id_pasien,
            @Field("keluhan") String keluhan,
            @Field("tanggal") String tanggal,
            @Field("jam") String jam,
            @Field("sesi") String sesi
    );

    @GET("reservasi/{tanggal}")
    Call<ResponseJam> getJam(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("tanggal") String tanggal

    );

    @GET("reservasi/{tanggal}/{id_jam}")
    Call<ResponseSesi> getSesi(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("tanggal") String tanggal,
            @Path("id_jam") String id_jam
    );

}
