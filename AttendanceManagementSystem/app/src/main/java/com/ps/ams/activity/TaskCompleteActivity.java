package com.ps.ams.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ps.ams.MainActivity;
import com.ps.ams.R;
import com.ps.ams.databinding.ActivityTaskCompleteBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import pub.devrel.easypermissions.EasyPermissions;

public class TaskCompleteActivity extends AppCompatActivity {
    private ActivityTaskCompleteBinding binding;
    Context mContext;
    private Uri image_profile_uri;
    private Bitmap bitmapProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_complete);
        mContext = this;
        setClickEvent();
    }

    private void setClickEvent() {

        binding.backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TaskCompleteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.imguploadTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
        
        binding.saveRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "Successfuly Save", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void checkPermission() {
        String[] perms = {/*Manifest.permission.ACCESS_FINE_LOCATION,*/ Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON).start(this);
        } else {
            EasyPermissions.requestPermissions(this, "We need permissions",
                    123, perms);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                image_profile_uri = result.getUri();
                binding.showIV.setImageURI(image_profile_uri);
                try {
                    bitmapProfilePic = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), image_profile_uri);
                    Glide.with(mContext).load(bitmapProfilePic).into(binding.showIV);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}