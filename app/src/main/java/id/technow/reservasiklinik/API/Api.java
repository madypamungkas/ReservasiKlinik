package id.technow.reservasiklinik.API;

import id.technow.reservasiklinik.Model.ResponseEditPasien;
import id.technow.reservasiklinik.Model.ResponsePoli;
import id.technow.reservasiklinik.Model.ResponseListPasien;
import id.technow.reservasiklinik.Model.ResponseLogin;
import id.technow.reservasiklinik.Model.ResponsePostReservasi;
import id.technow.reservasiklinik.Model.ResponsePostScreening;
import id.technow.reservasiklinik.Model.ResponsePostScreeningUmum;
import id.technow.reservasiklinik.Model.ResponseReservasiList;
import id.technow.reservasiklinik.Model.ResponseScreening;
import id.technow.reservasiklinik.Model.ResponseTanggal;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @FormUrlEncoded
    @POST("register")
    Call<ResponseLogin> registerUser(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("password") String password,
            @Field("nomor_bpjs_atau_kartu_sehat") String nomor_bpjs_atau_kartu_sehat
    );

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
    @POST("reservasi/store/{pasien_id}")
    Call<ResponsePostReservasi> addReservasi(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("pasien_id") String id_pasien,
            @Field("keluhan") String keluhan_fisik,
            @Field("riwayat_penyakit_menahun") String riwayat_penyakit_menahun,
            @Field("kekhawatiran") String kekhawatiran,
            @Field("upaya_pengobatan") String upaya_pengobatan,
            @Field("tanggal") String tanggal,
            @Field("poli") String poli_id
    );

    @POST("screening/tambah/{id_reservasi}")
    Call<ResponsePostScreening> addScreenig(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("id_reservasi") String id_reservasi,
            @Body ResponseScreening file
    );

    @POST("screening-umum")
    Call<ResponsePostScreeningUmum> addScreenigUmum(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Body ResponseScreening file
    );

    @GET("poli")
    Call<ResponsePoli> getPoli(
            @Header("Accept") String accept,
            @Header("Authorization") String token
    );

    @GET("reservasi-list")
    Call<ResponseReservasiList> getReservasi(
            @Header("Accept") String accept,
            @Header("Authorization") String token
    );

    @GET("reservasi-detail/{id_reservasi}")
    Call<ResponsePostScreening> getReservasiDetail(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("id_reservasi") String idReservasi
    );

    @GET("reservasi/{idPoli}")
    Call<ResponseTanggal> getTanggal(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("idPoli") String idPoli
    );

}
