package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.Model.ResponseLogin;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Context mCtx = RegisterActivity.this;
    TextView btnLogin;
    Button loginButton;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputNama, textInputBPJS;
    TextInputEditText inputEmail, inputPass, inputNama, inputBPJS;
    ProgressDialog loading;
    RadioButton rBYes, rBNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, LoginActivity.class);
                startActivity(intent);
            }
        });
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputNama = findViewById(R.id.textInputNama);
        inputEmail = findViewById(R.id.inputEmail);
        textInputBPJS = findViewById(R.id.textInputBPJS);
        inputBPJS = findViewById(R.id.inputBPJS);
        inputPass = findViewById(R.id.inputPass);
        inputNama = findViewById(R.id.inputNama);
        rBYes = findViewById(R.id.rBYes);
        rBNo = findViewById(R.id.rbNo);

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


        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    public void registerUser() {
        loading = ProgressDialog.show(RegisterActivity.this, null, getString(R.string.please_wait), true, false);
        String email = inputEmail.getText().toString().trim();
        String password = inputPass.getText().toString().trim();
        String nama = inputNama.getText().toString().trim();
        String bpjs = inputBPJS.getText().toString().trim();

        if (nama.isEmpty()) {
            loading.dismiss();
            textInputNama.setError("Nama tidak boleh kosong");
            inputNama.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            loading.dismiss();
            textInputEmail.setError("Email tidak boleh kosong");
            inputEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loading.dismiss();
            textInputEmail.setError("Masukkan email yang sesuai");
            inputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            loading.dismiss();
            textInputPassword.setError("Password tidak boleh kosong");
            inputPass.requestFocus();
            return;
        }

        if (password.length() < 8) {
            loading.dismiss();
            textInputPassword.setError("Password minimal terdiri 8 karakter");
            inputPass.requestFocus();
            return;
        }

        Call<ResponseLogin> call = RetrofitClient
                .getInstance()
                .getApi()
                .registerUser(nama, email, password, bpjs);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                ResponseLogin responseLogin = response.body();
                loading.dismiss();
                if (response.isSuccessful()) {
                    if (responseLogin.getStatus().equals("success")) {
                        Log.i("debug", "onResponse: SUCCESS");
                        loading.dismiss();
                        Toast.makeText(RegisterActivity.this, "Regiter Berhasil", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                    }
                } else {
                    loading.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(RegisterActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                Toast.makeText(RegisterActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
