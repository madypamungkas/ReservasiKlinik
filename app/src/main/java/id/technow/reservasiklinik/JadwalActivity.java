package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
    ProgressDialog loading;
    LinearLayout layoutAda, layoutTidakAda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        rvReservasi = findViewById(R.id.rvReservasi);
        layoutAda = findViewById(R.id.layoutAda);
        layoutTidakAda = findViewById(R.id.layoutTidakAda);
        loadData();
    }

    private void loadData() {
        loading = ProgressDialog.show(JadwalActivity.this, null, getString(R.string.please_wait), true, false);

        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();
        Call<ResponseReservasiList> call = RetrofitClient.getInstance().getApi().getReservasi("application/json", token);
        call.enqueue(new Callback<ResponseReservasiList>() {
            @Override
            public void onResponse(Call<ResponseReservasiList> call, Response<ResponseReservasiList> response) {
                ResponseReservasiList listPasien = response.body();
                loading.dismiss();
                if (response.isSuccessful()) {
                    model = response.body().getReservasi();
                    if (model.size() == 0) {
                        Toast.makeText(JadwalActivity.this, "Anda Belum Melakukan Rervasi", Toast.LENGTH_SHORT).show();
                    } else {
                        layoutTidakAda.setVisibility(View.GONE);
                        layoutAda.setVisibility(View.VISIBLE);
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
                loading.dismiss();

                Toast.makeText(JadwalActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
