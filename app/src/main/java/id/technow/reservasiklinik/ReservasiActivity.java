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
import id.technow.reservasiklinik.Adapter.JamAdapter;
import id.technow.reservasiklinik.Adapter.SesiAdapter;
import id.technow.reservasiklinik.Model.JamModel;
import id.technow.reservasiklinik.Model.ResponseEditPasien;
import id.technow.reservasiklinik.Model.ResponseJam;
import id.technow.reservasiklinik.Model.ResponseSesi;
import id.technow.reservasiklinik.Model.SesiModel;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasiActivity extends AppCompatActivity {
    private TextView txthp, txtnama, txtnik, txtnoBpjs, txtTanggal, txtJam, txtSesi;
    String txtNamaSt, txthpSt, txtnikSt, txtnoBpjsSt;
    String id, tanggal, idJam, idSesi;
    NestedScrollView nestedJam, nestedSesi;
    MaterialButton btnJam, btnSesi, btnSave;
    DatePickerDialog.OnDateSetListener mDateListener;
    RecyclerView RVJam, RVSesi;
    ProgressDialog loading;
    ArrayList<JamModel> jamModel;
    ArrayList<SesiModel> sesiModel;
    JamAdapter jamAdapter;
    SesiAdapter sesiAdapter;
    TextInputEditText inputKeluhan;
    TextInputLayout layoutKeluhan;

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
        txtJam = findViewById(R.id.txtJam);
        txtSesi = findViewById(R.id.txtSesi);
        layoutKeluhan = findViewById(R.id.layoutKeluhan);
        inputKeluhan = findViewById(R.id.inputKeluhan);
        nestedJam = findViewById(R.id.nestedJam);
        nestedSesi = findViewById(R.id.nestedSesi);
        btnSesi = findViewById(R.id.btnSesi);
        btnJam = findViewById(R.id.btnJam);
        btnSave = findViewById(R.id.btnSave);
        RVJam = findViewById(R.id.RVJam);
        RVSesi = findViewById(R.id.RVSesi);

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
    }


    public void pickTanggal(View view) {
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
                    btnJam.setEnabled(true);
                }
            }
        };
    }


    public void pickSesi(View view) {
        nestedJam.setVisibility(View.GONE);
        nestedSesi.setVisibility(View.VISIBLE);
        viewSesi();
    }

    public void pickJam(View view) {
        nestedJam.setVisibility(View.VISIBLE);
        nestedSesi.setVisibility(View.GONE);
        viewJam();

    }

    private void viewSesi() {
        UserModel user = SharedPrefManager.getInstance(ReservasiActivity.this).getUser();

        String token = "Bearer " + user.getToken();
        Call<ResponseSesi> call = RetrofitClient.getInstance().getApi().getSesi("application/json", token, tanggal, idJam);
        call.enqueue(new Callback<ResponseSesi>() {
            @Override
            public void onResponse(Call<ResponseSesi> call, final Response<ResponseSesi> response) {
                ResponseSesi responseJam = response.body();
                if (response.isSuccessful()) {
                    sesiModel = responseJam.getSesi();
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    sesiAdapter = new SesiAdapter(ReservasiActivity.this, sesiModel);
                    RVSesi.setLayoutManager(new LinearLayoutManager(ReservasiActivity.this));
                    RVSesi.setLayoutManager(staggeredGridLayoutManager);
                    RVSesi.setAdapter(sesiAdapter);
                } else {
                    Log.i("debug", "onResponse : FAILED");
                    Toast.makeText(ReservasiActivity.this, response.code() + " ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseSesi> call, Throwable t) {
                Log.i("debug", "onResponse : FAILED");
                Toast.makeText(ReservasiActivity.this, t.toString() + R.string.something_wrong + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void viewJam() {
        UserModel user = SharedPrefManager.getInstance(ReservasiActivity.this).getUser();

        String token = "Bearer " + user.getToken();
        Call<ResponseJam> call = RetrofitClient.getInstance().getApi().getJam("application/json", token, tanggal);
        call.enqueue(new Callback<ResponseJam>() {
            @Override
            public void onResponse(Call<ResponseJam> call, final Response<ResponseJam> response) {
                ResponseJam responseJam = response.body();
                if (response.isSuccessful()) {
                    jamModel = responseJam.getJam();
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
                    jamAdapter = new JamAdapter(ReservasiActivity.this, jamModel);
                    RVJam.setLayoutManager(new LinearLayoutManager(ReservasiActivity.this));
                    RVJam.setLayoutManager(staggeredGridLayoutManager);
                    RVJam.setAdapter(jamAdapter);
                } else {
                    Log.i("debug", "onResponse : FAILED");
                    Toast.makeText(ReservasiActivity.this, response.code() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseJam> call, Throwable t) {
                Log.i("debug", "onResponse : FAILED");
                Toast.makeText(ReservasiActivity.this, t.toString() + R.string.something_wrong + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void jamSetting(String jam, String id) {
        txtJam.setText(jam);
        btnSesi.setEnabled(true);
        idJam = id;
        nestedSesi.setVisibility(View.GONE);
    }

    public void sesiSetting(String sesi, String id) {
        txtSesi.setText(sesi);
        btnSesi.setEnabled(true);
        idSesi = id;
        nestedSesi.setVisibility(View.GONE);
        btnSave.setEnabled(true);
    }

    public void saveReservasi(View view) {
        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        loading = ProgressDialog.show(ReservasiActivity.this, null, getString(R.string.please_wait), true, false);
        String keluhan = inputKeluhan.getText().toString().trim();


        if (keluhan.isEmpty()) {
            loading.dismiss();
            layoutKeluhan.setError("No HP Tidak Boleh Kosong");
            inputKeluhan.requestFocus();
            return;
        }

        Call<ResponseEditPasien> call = RetrofitClient
                .getInstance()
                .getApi()
                .addReservasi("application/json", token, id, keluhan, tanggal, idJam, idSesi);

        call.enqueue(new Callback<ResponseEditPasien>() {
            @Override
            public void onResponse(Call<ResponseEditPasien> call, Response<ResponseEditPasien> response) {
                ResponseEditPasien responseEditPasien = response.body();
                loading.dismiss();
                if (response.isSuccessful()) {
                    if (responseEditPasien.getStatus().equals("success")) {
                        Log.i("debug", "onResponse: SUCCESS");
                        loading.dismiss();
                        Intent intent = new Intent(ReservasiActivity.this, DataPasien.class);
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
                        Toast.makeText(ReservasiActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ReservasiActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseEditPasien> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                Toast.makeText(ReservasiActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        });

    }
}

