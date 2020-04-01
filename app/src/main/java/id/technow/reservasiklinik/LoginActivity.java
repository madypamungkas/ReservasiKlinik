package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView btnSignUp;
    Context mCtx = LoginActivity.this;
    Button btnLogin;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, RegisterActivity.class);
                startActivity(intent);
            }
        });
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);

    }

    public void login() {
     /*   Intent intent = new Intent(mCtx, MainActivity.class);
        startActivity(intent);
*/
      /*  String email = textInputEmail.getEditText().
                getText().toString().trim();
        String password = textInputPassword.getEditText().
                getText().toString().trim();

        if (email.isEmpty()) {
            textInputEmail.setError("Username Harus Diisi!");
            textInputEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            textInputPassword.setError("Password Harus Diisi!");
            textInputPassword.requestFocus();
            return;
        }*/
       /* loading = ProgressDialog.show(LoginActivity.this, null,
                "Sedang Memuat...", true, false);
*/
/*
        Call<ResponseLogin> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(email, password, "Klien");
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                //  loading.dismiss();
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance(LoginActivity.this)
                            .saveToken(response.body().getToken());
                    Toast.makeText(LoginActivity.this, "Loged In", Toast.LENGTH_LONG).show();*/

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // loadDetails();
        startActivity(intent);
            /*    } else {
                    Toast.makeText(LoginActivity.this, response.code()+" ",
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.toString(),
                        Toast.LENGTH_LONG).show();
                // loading.dismiss();
            }
        });
    }

    public void loadDetails() {
        final String accept = "application/json";

        final String token = SharedPrefManager.getInstance(LoginActivity.this).getToken().getToken();
        retrofit2.Call<ResponseDetailKlien> call = RetrofitClient
                .getInstance()
                .getApi()
                .getKlien("Bearer "+token, accept);
        call.enqueue(new retrofit2.Callback<ResponseDetailKlien>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseDetailKlien> call, retrofit2.Response<ResponseDetailKlien> response) {
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance(LoginActivity.this).saveDetails(response.body().getDetails());


                } else {
                    Toast.makeText(LoginActivity.this, "Error, Periksa Kembali Jaringan Anda" + response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseDetailKlien> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error, Periksa Kembali Jaringan Anda" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }*/
    }
}
