package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import id.technow.reservasiklinik.R;

public class SplashScreen extends AppCompatActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mHandler = new Handler();
    checkLogin();
    }

    private void checkLogin() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //   if (SharedPrefManager.getInstance(SplashScreen.this).isLoggedIn()) {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
              /*  } else {
                    Intent intent = new Intent(SplashscreenActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }*/
                finish();
            }
        }, 3000L);
    }
}
