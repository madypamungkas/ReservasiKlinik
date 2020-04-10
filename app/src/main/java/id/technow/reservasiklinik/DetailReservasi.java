package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Model.PostScreeningModel;
import id.technow.reservasiklinik.Model.ResponsePostScreening;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailReservasi extends AppCompatActivity {
    String idReservasi;
    TextView txtIdReservasi, txtNama, txtTanggal, txtJamSesi, txtStatus, txtnoAntrian, txtSkor, txtHasil, txtPoli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reservasi);
        idReservasi = getIntent().getStringExtra("idReservasi");
        txtIdReservasi = findViewById(R.id.txtIdReservasi);
        txtNama = findViewById(R.id.txtNama);
        txtTanggal = findViewById(R.id.txtTanggal);
        txtJamSesi = findViewById(R.id.txtJamSesi);
        txtStatus = findViewById(R.id.txtStatus);
        txtnoAntrian = findViewById(R.id.txtnoAntrian);
        txtPoli = findViewById(R.id.txtPoli);
        txtSkor = findViewById(R.id.txtSkor);
        txtHasil = findViewById(R.id.txtHasil);
        loadDetail();
    }

    public void loadDetail() {
        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();
        Call<ResponsePostScreening> call = RetrofitClient.getInstance().getApi().getReservasiDetail("application/json", token, idReservasi);
        call.enqueue(new Callback<ResponsePostScreening>() {
            @Override
            public void onResponse(Call<ResponsePostScreening> call, Response<ResponsePostScreening> response) {
                PostScreeningModel reservasiModel = response.body().getReservasi();
                if (response.isSuccessful()) {
                    txtIdReservasi.setText("ID Reservasi Anda " + reservasiModel.getKode());
                    txtnoAntrian.setText(reservasiModel.getAntrian());
                    txtStatus.setText(reservasiModel.getStatus());
                    txtTanggal.setText(reservasiModel.getTanggal());
                    txtPoli.setText(response.body().getReservasi().getPoli().getPoli());
                    txtJamSesi.setText(reservasiModel.getJam());
                    txtNama.setText(reservasiModel.getCalon_pasien().getNama());
                    txtSkor.setText("Screening Score Anda" + " " + response.body().getScreening().getSkor());
                    txtHasil.setText(response.body().getScreening().getHasil());

                } else {
                    Toast.makeText(DetailReservasi.this,
                            response.message() + " ",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePostScreening> call, Throwable t) {

                Toast.makeText(DetailReservasi.this,
                        t.toString() + " ",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
}
