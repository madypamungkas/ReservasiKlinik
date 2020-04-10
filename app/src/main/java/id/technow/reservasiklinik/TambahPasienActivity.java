package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Model.ResponseEditPasien;
import id.technow.reservasiklinik.Model.ResponseLogin;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPasienActivity extends AppCompatActivity {
    private TextInputLayout layoutNIK, layoutBpjs, layoutNomorHp, layoutNama;
    private TextInputEditText inputHp, inputNama, inputNIK, inputBPJS;
    ProgressDialog loading;
    RadioButton rBYes, rBNo;
    LinearLayout textInputBPJS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pasien);
        inputHp = findViewById(R.id.inputHp);
        inputNama = findViewById(R.id.inputNama);
        inputNIK = findViewById(R.id.inputNIK);
        inputBPJS = findViewById(R.id.inputBPJS);
        layoutNIK = findViewById(R.id.layoutNIK);
        layoutBpjs = findViewById(R.id.layoutBpjs);
        layoutNomorHp = findViewById(R.id.layoutNomorHp);
        layoutNama = findViewById(R.id.layoutNama);
        rBYes = findViewById(R.id.rBYes);
        rBNo = findViewById(R.id.rbNo);
        textInputBPJS = findViewById(R.id.textInputBPJS);

        rBYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rBYes.isChecked()) {
                    textInputBPJS.setVisibility(View.VISIBLE);
                } else {
                    textInputBPJS.setVisibility(View.GONE);
                }
            }
        });
        rBNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rBNo.isChecked()) {
                    textInputBPJS.setVisibility(View.GONE);
                } else {
                    textInputBPJS.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void simpanPasien(View view) {
        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        loading = ProgressDialog.show(TambahPasienActivity.this, null, getString(R.string.please_wait), true, false);
        String noHp = inputHp.getText().toString().trim();
        String nama = inputNama.getText().toString().trim();
        String nik = inputNIK.getText().toString().trim();
        String bpjs = inputBPJS.getText().toString().trim();

        if ( noHp.length()< 10 ||  noHp.length()> 12) {
            loading.dismiss();
            layoutNomorHp.setError("No HP Terdiri Dari 10 - 12 Karakter");
            inputHp.requestFocus();
            return;
        }
        if (noHp.isEmpty()) {
            loading.dismiss();
            layoutNomorHp.setError("No HP Tidak Boleh Kosong");
            inputHp.requestFocus();
            return;
        }
        if (nama.isEmpty()) {
            loading.dismiss();
            layoutNama.setError("Nama Tidak Boleh Kosong");
            inputNama.requestFocus();
            return;
        }
        if (nik.isEmpty()) {
            loading.dismiss();
            layoutNIK.setError("No NIK Tidak Boleh Kosong");
            inputNIK.requestFocus();
            return;
        }
        if (nik.length() != 16 ) {
            loading.dismiss();
            layoutNIK.setError("No NIK Harus Berisi 16");
            inputNIK.requestFocus();
            return;
        }
        if (bpjs.isEmpty()) {
            loading.dismiss();
            layoutBpjs.setError("No BPJS Tidak Boleh Kosong");
            inputBPJS.requestFocus();
            return;
        }

        Call<ResponseEditPasien> call = RetrofitClient
                .getInstance()
                .getApi()
                .tambahPasien("application/json", token, nama, noHp, nik, bpjs);

        call.enqueue(new Callback<ResponseEditPasien>() {
            @Override
            public void onResponse(Call<ResponseEditPasien> call, Response<ResponseEditPasien> response) {
                ResponseEditPasien responseEditPasien = response.body();
                loading.dismiss();
                if (response.isSuccessful()) {
                    if (responseEditPasien.getStatus().equals("success")) {
                        Log.i("debug", "onResponse: SUCCESS");
                        loading.dismiss();
                        Intent intent = new Intent(TambahPasienActivity.this, DataPasien.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        //   Toast.makeText(TambahPasienActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    loading.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TambahPasienActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TambahPasienActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseEditPasien> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                Toast.makeText(TambahPasienActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
