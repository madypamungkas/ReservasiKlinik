package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Adapter.DataPasienAdapter;
import id.technow.reservasiklinik.Model.DataPasienModel;
import id.technow.reservasiklinik.Model.PasienModel;
import id.technow.reservasiklinik.Model.ResponseListPasien;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPasien extends AppCompatActivity {
    private RecyclerView rvPasien;
    ArrayList<PasienModel> model;
    DataPasienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pasien);
        rvPasien = findViewById(R.id.rvPasien);
        loadData();
    }

    private void loadData() {
        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();
        Call<ResponseListPasien> call = RetrofitClient.getInstance().getApi().pasien( "application/json", token);
        call.enqueue(new Callback<ResponseListPasien>() {
            @Override
            public void onResponse(Call<ResponseListPasien> call, Response<ResponseListPasien> response) {
                ResponseListPasien listPasien = response.body();
                if (response.isSuccessful()) {
                    int size = listPasien.getPasien().size();
                    model = response.body().getPasien();
                    if (model.isEmpty()) {
                        Toast.makeText(DataPasien.this, "Pasien Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    } else {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
                        adapter = new DataPasienAdapter(DataPasien.this, model);
                        rvPasien.setLayoutManager(new LinearLayoutManager(DataPasien.this));
                        rvPasien.setLayoutManager(staggeredGridLayoutManager);
                        rvPasien.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(DataPasien.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListPasien> call, Throwable t) {

                Toast.makeText(DataPasien.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void tambahAnggota(View view) {
        Intent intent = new Intent(DataPasien.this, TambahPasienActivity.class);
        startActivity(intent);
    }
}
