package com.ps.ams.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.ps.ams.MainActivity;
import com.ps.ams.R;
import com.ps.ams.dto.FCM_UserDTO;
import com.ps.ams.util.SessionManager;

public class SplashActivity extends AppCompatActivity {
    public static FCM_UserDTO fcmUserDTO= new FCM_UserDTO();
    protected int SPLASH_TIME_OUT = 5000;
    SessionManager sessionManager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        intValues();
        setDelayScreen();

    }

    private void intValues() {
        mContext = this;
        sessionManager = new SessionManager(mContext);
    }

    private void setDelayScreen() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager .isLogin()){
                    Intent  intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent  intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }
        }, SPLASH_TIME_OUT);
    }
}