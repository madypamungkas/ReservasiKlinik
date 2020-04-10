package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Adapter.ListPasienAdapter;
import id.technow.reservasiklinik.Adapter.ReservasiAdapter;
import id.technow.reservasiklinik.Model.PasienModel;
import id.technow.reservasiklinik.Model.PostScreeningModel;
import id.technow.reservasiklinik.Model.ResponseListPasien;
import id.technow.reservasiklinik.Model.ResponseReservasiList;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalActivity extends AppCompatActivity {
    private RecyclerView rvReservasi;
    ArrayList<PostScreeningModel> model;
    ReservasiAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        rvReservasi = findViewById(R.id.rvReservasi);
        loadData();
    }

    private void loadData() {
        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();
        Call<ResponseReservasiList> call = RetrofitClient.getInstance().getApi().getReservasi("application/json", token);
        call.enqueue(new Callback<ResponseReservasiList>() {
            @Override
            public void onResponse(Call<ResponseReservasiList> call, Response<ResponseReservasiList> response) {
                ResponseReservasiList listPasien = response.body();
                if (response.isSuccessful()) {
                    model = response.body().getReservasi();
                    if (model == null) {
                        Toast.makeText(JadwalActivity.this, "Reservasi Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    } else {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
                        adapter = new ReservasiAdapter(model,JadwalActivity.this);
                        rvReservasi.setLayoutManager(new LinearLayoutManager(JadwalActivity.this));
                        rvReservasi.setLayoutManager(staggeredGridLayoutManager);
                        rvReservasi.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(JadwalActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseReservasiList> call, Throwable t) {

                Toast.makeText(JadwalActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
