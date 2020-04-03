package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Adapter.DataPasienAdapter;
import id.technow.reservasiklinik.Adapter.ScreeningAdapter;
import id.technow.reservasiklinik.Model.PasienModel;
import id.technow.reservasiklinik.Model.ResponseListPasien;
import id.technow.reservasiklinik.Model.ResponseScreening;
import id.technow.reservasiklinik.Model.ScreeningModel;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreeningActivity extends AppCompatActivity {
    private RecyclerView rvPasien;
    ArrayList<ScreeningModel> model;
    ScreeningAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);
        rvPasien = findViewById(R.id.rvPasien);
        loadData();
    }
    private void loadData() {
        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();
        Call<ResponseScreening> call = RetrofitClient.getInstance().getApi().screening( "application/json", token);
        call.enqueue(new Callback<ResponseScreening>() {
            @Override
            public void onResponse(Call<ResponseScreening> call, Response<ResponseScreening> response) {
                ResponseScreening listPasien = response.body();
                if (response.isSuccessful()) {
                    int size = listPasien.getScreening().size();
                    model = response.body().getScreening();
                    if (model.isEmpty()) {
                        Toast.makeText(ScreeningActivity.this, "Pasien Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    } else {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
                        adapter = new ScreeningAdapter(ScreeningActivity.this, model);
                        rvPasien.setLayoutManager(new LinearLayoutManager(ScreeningActivity.this));
                        rvPasien.setLayoutManager(staggeredGridLayoutManager);
                        rvPasien.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(ScreeningActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseScreening> call, Throwable t) {

                Toast.makeText(ScreeningActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();

            }
        });

    }
}
