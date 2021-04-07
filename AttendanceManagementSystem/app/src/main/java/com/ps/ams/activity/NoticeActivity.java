package com.ps.ams.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.ps.ams.MainActivity;
import com.ps.ams.R;
import com.ps.ams.databinding.ActivityNoticeBinding;

public class NoticeActivity extends AppCompatActivity {
    ActivityNoticeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice);
        initi();
    }

    private void initi() {

        setClickEvent();
    }

    private void setClickEvent() {

        binding.backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NoticeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}