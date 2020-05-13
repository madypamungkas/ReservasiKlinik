package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import com.google.android.material.button.MaterialButton;

import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.Storage.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {
    private Handler mHandler;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mHandler = new Handler();

        mContext = this;
        mHandler = new Handler();
        mStatusChecker.run();

        checkConnection();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {

            mHandler.postDelayed(mStatusChecker, 270);
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void checkConnection() {
        if (isNetworkAvailable()) {
            checkLogin();
        } else {

        }
    }

    private void checkLogin() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (SharedPrefManager.getInstance(SplashScreen.this).isLoggedIn()) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                finish();
            }
        }, 2500L);
    }

}
