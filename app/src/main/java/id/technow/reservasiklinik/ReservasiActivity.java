package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Adapter.PoliAdapter;
import id.technow.reservasiklinik.Adapter.TanggalAdapter;
import id.technow.reservasiklinik.Model.PoliModel;
import id.technow.reservasiklinik.Model.ResponseEditPasien;
import id.technow.reservasiklinik.Model.ResponsePoli;
import id.technow.reservasiklinik.Model.ResponsePostReservasi;
import id.technow.reservasiklinik.Model.ResponseTanggal;
import id.technow.reservasiklinik.Model.TanggalModel;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasiActivity extends AppCompatActivity {
    private TextView txthp, txtnama, txtnik, txtnoBpjs, txtTanggal, txtPoli;
    String txtNamaSt, txthpSt, txtnikSt, txtnoBpjsSt;
    String id, tanggal, idPoli, idTanggal;
    NestedScrollView nestedPoli, nestedTanggal;
    MaterialButton btnPoli, btnTanggal, btnSave;
    DatePickerDialog.OnDateSetListener mDateListener;
    RecyclerView RVPoli, RVTanggal;
    ProgressDialog loading;
    ArrayList<PoliModel> poliModel;
    ArrayList<TanggalModel> tanggalModel;
    PoliAdapter poliAdapter;
    TanggalAdapter tanggalAdapter;
    TextInputEditText inputKeluhan, inputKhawatir, inputPenyakit;
    TextInputLayout layoutKeluhan, layoutkhawatir, layoutPenyakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);
        id = getIntent().getStringExtra("idPasien");
        txthp = findViewById(R.id.txtValue1A);
        txtnama = findViewById(R.id.txtValue2);
        txtnik = findViewById(R.id.txtValue3);
        txtnoBpjs = findViewById(R.id.txtValue4);
        txtTanggal = findViewById(R.id.txtTanggal);
        txtPoli = findViewById(R.id.txtPoli);
        layoutKeluhan = findViewById(R.id.layoutKeluhan);
        inputKeluhan = findViewById(R.id.inputKeluhan);
        inputKhawatir = findViewById(R.id.inputKhawatir);
        inputPenyakit = findViewById(R.id.inputPenyakit);
        layoutPenyakit = findViewById(R.id.layoutPenyakit);
        layoutkhawatir = findViewById(R.id.layoutkhawatir);
        nestedPoli = findViewById(R.id.nestedPoli);
        nestedTanggal = findViewById(R.id.nestedTanggal);
        btnTanggal = findViewById(R.id.btnTanggal);
        btnPoli = findViewById(R.id.btnPoli);
        btnSave = findViewById(R.id.btnSave);
        RVPoli = findViewById(R.id.RVPoli);
        RVTanggal = findViewById(R.id.RVTanggal);

        txtNamaSt = getIntent().getStringExtra("namaPasien");
        txtnikSt = getIntent().getStringExtra("nikPasien");
        txthpSt = getIntent().getStringExtra("hpPasien");
        txtnoBpjsSt = getIntent().getStringExtra("bpjsPasien");

        if (txtNamaSt == null) {
            txtnama.setText("-");
        } else {
            txtnama.setText(txtNamaSt);
        }
        if (txtnikSt == null) {
            txtnik.setText("-");
        } else {
            txtnik.setText(txtnikSt);
        }
        if (txthpSt == null) {
            txthp.setText("-");
        } else {
            txthp.setText(txthpSt);
        }
        if (txtnoBpjsSt == null) {
            txtnoBpjs.setText("-");
        } else {
            txtnoBpjs.setText(txtnoBpjsSt);
        }

        btnPoli.setEnabled(true);
    }


    /*public void pickTanggal(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                ReservasiActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateListener,
                year, month, day
        );
        dialog.show();

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String dayS, monthS, yearS;
                if (day < 10) {
                    dayS = "0" + Integer.toString(day);
                } else {
                    dayS = Integer.toString(day);
                }
                if (month < 10) {
                    monthS = "0" + Integer.toString(month);
                } else {
                    monthS = Integer.toString(month);
                }

                Log.d("onDateSet", month + "/" + day + "/" + year);
                txtTanggal.setText(dayS + "-" + monthS + "-" + year);
                tanggal = year + "-" + monthS + "-" + dayS;
                if (tanggal != null) {
                    btnPoli.setEnabled(true);
                }
            }
        };
    }*/

    public void pickTanggal(View view) {
        nestedPoli.setVisibility(View.GONE);
        nestedTanggal.setVisibility(View.VISIBLE);
        viewTanggal();
    }

    public void pickPoli(View view) {
        nestedPoli.setVisibility(View.VISIBLE);
        nestedTanggal.setVisibility(View.GONE);
        viewPoli();

    }

    private void viewTanggal() {
        UserModel user = SharedPrefManager.getInstance(ReservasiActivity.this).getUser();

        String token = "Bearer " + user.getToken();
        Call<ResponseTanggal> call = RetrofitClient.getInstance().getApi().getTanggal("application/json", token, idPoli);
        call.enqueue(new Callback<ResponseTanggal>() {
            @Override
            public void onResponse(Call<ResponseTanggal> call, final Response<ResponseTanggal> response) {
                if (response.isSuccessful()) {
                    tanggalModel = response.body().getResult();
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    tanggalAdapter = new TanggalAdapter(ReservasiActivity.this, tanggalModel);
                    RVTanggal.setLayoutManager(new LinearLayoutManager(ReservasiActivity.this));
                    RVTanggal.setLayoutManager(staggeredGridLayoutManager);
                    RVTanggal.setAdapter(tanggalAdapter);
                } else {
                    Log.i("debug", "onResponse : FAILED");
                    Toast.makeText(ReservasiActivity.this, response.code() + " ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseTanggal> call, Throwable t) {
                Log.i("debug", "onResponse : FAILED");
                Toast.makeText(ReservasiActivity.this, t.toString() + R.string.something_wrong + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void viewPoli() {
        UserModel user = SharedPrefManager.getInstance(ReservasiActivity.this).getUser();

        String token = "Bearer " + user.getToken();
        Call<ResponsePoli> call = RetrofitClient.getInstance().getApi().getPoli("application/json", token);
        call.enqueue(new Callback<ResponsePoli>() {
            @Override
            public void onResponse(Call<ResponsePoli> call, final Response<ResponsePoli> response) {
                ResponsePoli responsePoli = response.body();
                if (response.isSuccessful()) {
                    poliModel = responsePoli.getResult();
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
                    poliAdapter = new PoliAdapter(ReservasiActivity.this, poliModel);
                    RVPoli.setLayoutManager(new LinearLayoutManager(ReservasiActivity.this));
                    RVPoli.setLayoutManager(staggeredGridLayoutManager);
                    RVPoli.setAdapter(poliAdapter);
                } else {
                    Log.i("debug", "onResponse : FAILED");
                    Toast.makeText(ReservasiActivity.this, response.code() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePoli> call, Throwable t) {
                Log.i("debug", "onResponse : FAILED");
                Toast.makeText(ReservasiActivity.this, t.toString() + R.string.something_wrong + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void PoliSetting(String Poli, String id) {
        txtPoli.setText(Poli);
        btnTanggal.setEnabled(true);
        idPoli = id;
    }

    public void TanggalSetting(String Tanggal, String tanggalStore) {
        txtTanggal.setText(Tanggal);
        btnTanggal.setEnabled(true);
        //  idTanggal = id;
        tanggal = tanggalStore;
        nestedTanggal.setVisibility(View.GONE);
        btnSave.setEnabled(true);
    }

    public void saveReservasi(View view) {
        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        loading = ProgressDialog.show(ReservasiActivity.this, null, getString(R.string.please_wait), true, false);
        final String keluhan = inputKeluhan.getText().toString().trim();
        final String khawatir = inputKhawatir.getText().toString().trim();
        final String penyakit = inputPenyakit.getText().toString().trim();


        if (keluhan.isEmpty()) {
            loading.dismiss();
            layoutKeluhan.setError("Keluhan Tidak Boleh Kosong");
            inputKeluhan.requestFocus();
            return;
        }
        if (khawatir.isEmpty()) {
            loading.dismiss();
            layoutkhawatir.setError("Kekhawatiran Tidak Boleh Kosong,  Jika Tidak Ada tambahkan tanda -");
            inputKhawatir.requestFocus();
            return;
        }
        if (penyakit.isEmpty()) {
            loading.dismiss();
            layoutPenyakit.setError("Riwayat Tidak Boleh Kosong, Jika Tidak Ada tambahkan tanda -");
            inputPenyakit.requestFocus();
            return;
        }

        Call<ResponsePostReservasi> call = RetrofitClient
                .getInstance()
                .getApi()
                .addReservasi("application/json", token, id, keluhan + "", penyakit + "", khawatir + "", "-", tanggal + "", idPoli + "");

        call.enqueue(new Callback<ResponsePostReservasi>() {
            @Override
            public void onResponse(Call<ResponsePostReservasi> call, Response<ResponsePostReservasi> response) {
                ResponsePostReservasi responseEditPasien = response.body();
                loading.dismiss();
                if (response.isSuccessful()) {
                    if (responseEditPasien.getStatus().equals("success")) {
                        Log.i("debug", "onResponse: SUCCESS");
                        loading.dismiss();
                        Intent intent = new Intent(ReservasiActivity.this, ScreeningActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("idReservasi", response.body().getReservasi().getId());
                        startActivity(intent);
                    } else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(ReservasiActivity.this, response.code() + " " + responseEditPasien.getStatus(), Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());

                            Toast.makeText(ReservasiActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            //   Toast.makeText(ReservasiActivity.this, id+" - "+ keluhan+" - "+ penyakit +" - "+khawatir+" - "+ "- upaya"+" - "+ tanggal+" - "+ idPoli+"", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(ReservasiActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    loading.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(ReservasiActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        //   Toast.makeText(ReservasiActivity.this, id+" - "+ keluhan+" - "+ penyakit +" - "+khawatir+" - "+ "- upaya"+" - "+ tanggal+" - "+ idPoli+"", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ReservasiActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostReservasi> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                Toast.makeText(ReservasiActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        });

    }
}

