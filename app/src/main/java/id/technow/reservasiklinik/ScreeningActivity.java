package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Adapter.DataPasienAdapter;
import id.technow.reservasiklinik.Adapter.ScreeningAdapter;
import id.technow.reservasiklinik.Model.PasienModel;
import id.technow.reservasiklinik.Model.ResponseListPasien;
import id.technow.reservasiklinik.Model.ResponsePostScreening;
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
    String idReservasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);
        rvPasien = findViewById(R.id.rvPasien);
        idReservasi = getIntent().getStringExtra("idReservasi");
        loadData();
    }

    private void loadData() {
        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();
        Call<ResponseScreening> call = RetrofitClient.getInstance().getApi().screening("application/json", token);
        call.enqueue(new Callback<ResponseScreening>() {
            @Override
            public void onResponse(Call<ResponseScreening> call, Response<ResponseScreening> response) {
                ResponseScreening listPasien = response.body();
                if (response.isSuccessful()) {
                    int size = listPasien.getScreening().size();
                    model = response.body().getScreening();
                    if (model.isEmpty()) {
                        Toast.makeText(ScreeningActivity.this, "Data Screening Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    } else {
                        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningActivity.this);
                        SharedPreferences.Editor editorList = sharedPrefs.edit();
                        Gson gson = new Gson();

                        String responseQuiz = gson.toJson(response.body());
                        editorList.putString("response", responseQuiz);

                        String json = gson.toJson(model);
                        editorList.putString("screeningModel", json);
                        editorList.apply();

                        getLocalData();
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

    public void getLocalData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningActivity.this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("response", "response");
        Type type = new TypeToken<ResponseScreening>() {
        }.getType();
        ResponseScreening responseScreening = gson.fromJson(json, type);
        ArrayList<ScreeningModel> screeningModels = responseScreening.getScreening();


        model = screeningModels;

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        adapter = new ScreeningAdapter(ScreeningActivity.this, model);
        rvPasien.setLayoutManager(new LinearLayoutManager(ScreeningActivity.this));
        rvPasien.setLayoutManager(staggeredGridLayoutManager);
        rvPasien.setAdapter(adapter);
    }

    public void postDataScreening() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningActivity.this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("response", "response");
        Type type = new TypeToken<ResponseScreening>() {
        }.getType();

        ResponseScreening responseScreening = gson.fromJson(json, type);
        ArrayList<ScreeningModel> screeningModels = responseScreening.getScreening();

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("answer2.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();

            //    Toast.makeText(getApplicationContext(), "Composition saved", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("Exception", "File write failed: " + e.toString());
        }

        model = screeningModels;


        UserModel user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();
        Call<ResponsePostScreening> call = RetrofitClient.getInstance().getApi().addScreenig("application/json", token, idReservasi, responseScreening);
        call.enqueue(new Callback<ResponsePostScreening>() {
            @Override
            public void onResponse(Call<ResponsePostScreening> call, Response<ResponsePostScreening> response) {
                ResponsePostScreening listPasien = response.body();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ScreeningActivity.this, ReservasiResultActivity.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {

                 /*   Intent intent = new Intent(ScreeningActivity.this, ReservasiResultActivity.class);
                 //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);*/
                    Toast.makeText(ScreeningActivity.this,
                            //R.string.something_wrong,
                            response.message() + " ",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePostScreening> call, Throwable t) {

                Toast.makeText(ScreeningActivity.this,
                        //R.string.something_wrong,
                        t.toString() + " ",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void tambahScreening(View view) {
        postDataScreening();
    }
}
