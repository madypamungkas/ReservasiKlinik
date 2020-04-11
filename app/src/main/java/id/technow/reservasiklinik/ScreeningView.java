package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Adapter.ScreeningAdapter;
import id.technow.reservasiklinik.Model.ResponsePostScreeningUmum;
import id.technow.reservasiklinik.Model.ResponseScreening;
import id.technow.reservasiklinik.Model.ScreeningModel;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreeningView extends AppCompatActivity {
    LinearLayout soalLayout, readyLayout, layaoutRemainChance;
    private TextView timer, soal, number, sum, namaSoal, gameName, tvIsian, txtChance, txtRemainTime;
    ProgressDialog progress;

    private int currentQusetionId = 0;
    int idsoal, sumQues;
    String token;
    ImageView prevSoal, nextSoal;
    MaterialButton readyBtn;
    int status = 0;
    RecyclerView optionRV;
    NestedScrollView scrollOption;
    public int time;
    TextInputLayout layoutAnswer;
    LinearLayout layoutAns;
    TextInputEditText inputAnswer;
    String namaQuiz, codeQuiz;
    MaterialButton btnYa, btnTidak;

    ArrayList<ScreeningModel> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_view);
        soalLayout = findViewById(R.id.soalLayout);

        readyLayout = findViewById(R.id.readyLayout);
        timer = findViewById(R.id.timer);
        number = findViewById(R.id.number);
        sum = findViewById(R.id.sum);
        soal = findViewById(R.id.soal);
        nextSoal = findViewById(R.id.nextSoal);
        prevSoal = findViewById(R.id.prevSoal);
        //readyBtn = findViewById(R.id.readyBtn);
        scrollOption = findViewById(R.id.scrollOption);
        gameName = findViewById(R.id.gameName);
        btnYa = findViewById(R.id.btnYa);
        btnTidak = findViewById(R.id.btnTidak);

        loadData();
        prevSoal.setVisibility(View.INVISIBLE);

        nextSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSoal();
            }
        });
        prevSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevSoal();
            }
        });

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOptionTidak();
            }
        });
        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOptionYa();
            }
        });
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
                        Toast.makeText(ScreeningView.this, "Data Screening Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    } else {
                        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningView.this);
                        SharedPreferences.Editor editorList = sharedPrefs.edit();
                        Gson gson = new Gson();

                        String responseQuiz = gson.toJson(response.body());
                        editorList.putString("response", responseQuiz);

                        String json = gson.toJson(model);
                        editorList.putString("screeningModel", json);
                        editorList.apply();

                        showQuestion();
                        sum.setText("/" + model.size());
                        sumQues = model.size();
                        //getLocalData();
                    }

                } else {
                    Toast.makeText(ScreeningView.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseScreening> call, Throwable t) {

                Toast.makeText(ScreeningView.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void getLocalData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningView.this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("response", "response");
        Type type = new TypeToken<ResponseScreening>() {
        }.getType();
        ResponseScreening responseScreening = gson.fromJson(json, type);
        ArrayList<ScreeningModel> screeningModels = responseScreening.getScreening();


        model = screeningModels;

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
      /*  adapter = new ScreeningAdapter(ScreeningView.this, model);
        rvPasien.setLayoutManager(new LinearLayoutManager(ScreeningView.this));
        rvPasien.setLayoutManager(staggeredGridLayoutManager);
        rvPasien.setAdapter(adapter);*/
    }

    public void showQuestion() {
        ScreeningModel questions = model.get(currentQusetionId);
        soal.setText(questions.getPertanyaan());
        if (questions.getPertanyaan().length() > 120) {
            soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._14ssp));
        } else if (questions.getPertanyaan().length() > 200) {
            soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._13ssp));
        } else {
            soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._17ssp));
        }


        if (currentQusetionId == 0) {
            prevSoal.setVisibility(View.INVISIBLE);
        }
    }

    public void saveOptionYa() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningView.this);
        Gson gson = new Gson();

        String json = sharedPrefs.getString("response", "response");
        Type type = new TypeToken<ResponseScreening>() {
        }.getType();
        ResponseScreening responseQuestion = gson.fromJson(json, type);

        String json2 = sharedPrefs.getString("screeningModel", "screeningModel");
        Type type2 = new TypeToken<ArrayList<ScreeningModel>>() {
        }.getType();
        ArrayList<ScreeningModel> questionSave = gson.fromJson(json2, type2);


        ArrayList<ScreeningModel> que = questionSave;

        que.get(currentQusetionId).setJawaban("ya");

        SharedPreferences.Editor editorList = sharedPrefs.edit();

        String questionSt = gson.toJson(questionSave);
        editorList.putString("screeningModel", questionSt);

        responseQuestion.setScreening(questionSave);
        String responseQuiz = gson.toJson(responseQuestion);
        editorList.putString("response", responseQuiz);

        editorList.apply();
        btnYa.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.primaryTextColor));
        btnYa.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
        btnTidak.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
        btnTidak.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.dark_red));
    }

    public void saveOptionTidak() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningView.this);
        Gson gson = new Gson();

        String json = sharedPrefs.getString("response", "response");
        Type type = new TypeToken<ResponseScreening>() {
        }.getType();
        ResponseScreening responseQuestion = gson.fromJson(json, type);

        String json2 = sharedPrefs.getString("screeningModel", "screeningModel");
        Type type2 = new TypeToken<ArrayList<ScreeningModel>>() {
        }.getType();
        ArrayList<ScreeningModel> questionSave = gson.fromJson(json2, type2);


        ArrayList<ScreeningModel> que = questionSave;

        que.get(currentQusetionId).setJawaban("tidak");

        SharedPreferences.Editor editorList = sharedPrefs.edit();

        String questionSt = gson.toJson(questionSave);
        editorList.putString("screeningModel", questionSt);

        responseQuestion.setScreening(questionSave);
        String responseQuiz = gson.toJson(responseQuestion);
        editorList.putString("response", responseQuiz);

        editorList.apply();
        btnYa.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
        btnYa.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.primaryTextColor));
        btnTidak.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.dark_red));
        btnTidak.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));

    }


    @SuppressLint("RestrictedApi")
    public void nextSoal() {
        prevSoal.setVisibility(View.VISIBLE);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningView.this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("screeningModel", "screeningModel");
        Type type = new TypeToken<ArrayList<ScreeningModel>>() {
        }.getType();
        ArrayList<ScreeningModel> questionSave = gson.fromJson(json, type);

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt("num", currentQusetionId);
        editor.apply();

        if (currentQusetionId + 1 == questionSave.size()) {
            nextSoal.setVisibility(View.INVISIBLE);
        }

        if (currentQusetionId + 1 == questionSave.size()) {
            if (status == 1) {
            } else {
            }

        } else {
            currentQusetionId++;
            final ScreeningModel questions = questionSave.get(currentQusetionId);

            if (questions.getJawaban().equals("tidak")) {
                btnYa.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                btnYa.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.primaryTextColor));
                btnTidak.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.dark_red));
                btnTidak.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
            }
            if (questions.getJawaban().equals("ya")) {
                btnYa.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.primaryTextColor));
                btnYa.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                btnTidak.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                btnTidak.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.dark_red));
            }
            if (questions.getJawaban().equals("**")) {
                btnYa.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                btnYa.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.primaryTextColor));
                btnTidak.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                btnTidak.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.dark_red));
            }


            if (currentQusetionId > questionSave.size()) {

            } else {
                soal.setText(questions.getPertanyaan());
                if (questions.getPertanyaan().length() > 120) {
                    soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._14ssp));
                } else if (questions.getPertanyaan().length() > 200) {
                    soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._13ssp));
                } else {
                    soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._17ssp));
                }
                number.setText(currentQusetionId + 1 + "");

                nextSoal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextSoal();
                    }
                });
                prevSoal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prevSoal();
                    }
                });
            }
        }
    }

    public void prevSoal() {
        nextSoal.setVisibility(View.VISIBLE);
        prevSoal.setVisibility(View.VISIBLE);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningView.this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("screeningModel", "screeningModel");
        Type type = new TypeToken<ArrayList<ScreeningModel>>() {
        }.getType();
        ArrayList<ScreeningModel> questionSave = gson.fromJson(json, type);

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt("num", currentQusetionId);
        editor.apply();

        if (currentQusetionId == 0) {
        } else {
            if (currentQusetionId == 1) {
                prevSoal.setVisibility(View.INVISIBLE);
            }
            if (currentQusetionId > 0) {
                currentQusetionId--;
                final ScreeningModel questions = questionSave.get(currentQusetionId);

                if (questions.getJawaban().equals("tidak")) {
                    btnYa.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                    btnYa.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.primaryTextColor));
                    btnTidak.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.dark_red));
                    btnTidak.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                }
                if (questions.getJawaban().equals("ya")) {
                    btnYa.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.primaryTextColor));
                    btnYa.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                    btnTidak.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                    btnTidak.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.dark_red));
                }
                if (questions.getJawaban().equals("**")) {
                    btnYa.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                    btnYa.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.primaryTextColor));
                    btnTidak.setBackgroundTintList(ContextCompat.getColorStateList(ScreeningView.this, R.color.white));
                    btnTidak.setTextColor(ContextCompat.getColorStateList(ScreeningView.this, R.color.dark_red));
                }

                soal.setText(questions.getPertanyaan());
                if (questions.getPertanyaan().length() > 120) {
                    soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._14ssp));
                } else if (questions.getPertanyaan().length() > 200) {
                    soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._13ssp));
                } else {
                    soal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreeningView.this.getResources().getDimension(R.dimen._17ssp));
                }
                number.setText(currentQusetionId + 1 + "");

                nextSoal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextSoal();
                    }
                });
                prevSoal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prevSoal();
                    }
                });
            }
        }
    }

    public void postDataScreening() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ScreeningView.this);
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
        Call<ResponsePostScreeningUmum> call = RetrofitClient.getInstance().getApi().addScreenigUmum("application/json", token, responseScreening);
        call.enqueue(new Callback<ResponsePostScreeningUmum>() {
            @Override
            public void onResponse(Call<ResponsePostScreeningUmum> call, Response<ResponsePostScreeningUmum> response) {
                ResponsePostScreeningUmum listPasien = response.body();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ScreeningView.this, ScreeningResultActivity.class);
                    intent.putExtra("skorScreening", response.body().getScreening().getSkor());
                    intent.putExtra("hasilScreening", response.body().getScreening().getHasil());
                    startActivity(intent);

                } else {
                    Toast.makeText(ScreeningView.this,
                            response.message() + " ",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePostScreeningUmum> call, Throwable t) {

                Toast.makeText(ScreeningView.this,
                        //R.string.something_wrong,
                        t.toString() + " ",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void postData(View view) {
        postDataScreening();
    }
}
