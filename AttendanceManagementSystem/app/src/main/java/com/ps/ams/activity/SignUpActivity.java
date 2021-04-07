package com.ps.ams.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ps.ams.MainActivity;
import com.ps.ams.R;
import com.ps.ams.databinding.ActivitySignUpBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ps.ams.MainActivity;
import com.ps.ams.R;
import com.ps.ams.databinding.ActivitySignUpBinding;
import com.ps.ams.dto.FCM_UserDTO;

import java.util.TimeZone;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private String LOG_TAG = "SignUpActivity";
    private DatabaseReference dbFirebase;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        initi();
    }

    private void initi() {
        mContext = this;
        setClickEvent();
    }

    private void setClickEvent() {

        binding.loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.createaccountRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();


            }
        });

        binding.nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.emailET.getText().toString().trim().length() > 0 && binding.passwordET.getText().toString().trim().length() > 0) {
                    binding.createaccountRL.setBackgroundResource(R.drawable.curculer_view_green);
                } else {
                    binding.createaccountRL.setBackgroundResource(R.drawable.curculer_view);
                }
            }
        });
        binding.emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.emailET.getText().toString().trim().length() > 0 && binding.passwordET.getText().toString().trim().length() > 0) {
                    binding.createaccountRL.setBackgroundResource(R.drawable.curculer_view_green);
                } else {
                    binding.createaccountRL.setBackgroundResource(R.drawable.curculer_view);
                }
            }
        });

        binding.mobileET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.mobileET.getText().toString().trim().length() > 0 && binding.passwordET.getText().toString().trim().length() > 0) {
                    binding.createaccountRL.setBackgroundResource(R.drawable.curculer_view_green);
                } else {
                    binding.createaccountRL.setBackgroundResource(R.drawable.curculer_view);
                }
            }
        });

        binding.passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.emailET.getText().toString().trim().length() > 0 && binding.passwordET.getText().toString().trim().length() > 0) {
                    binding.createaccountRL.setBackgroundResource(R.drawable.curculer_view_green);
                } else {
                    binding.createaccountRL.setBackgroundResource(R.drawable.curculer_view);
                }
            }
        });


    }

    private void checkValidation() {
        final String name = binding.nameET.getText().toString().trim();
        String email = binding.emailET.getText().toString().trim();
        String mobile = binding.mobileET.getText().toString().trim();
        String password = binding.passwordET.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(SignUpActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignUpActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(SignUpActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignUpActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(SignUpActivity.this, "Password Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
        } else {

            signInFirebase(binding.emailET.getText().toString(), binding.passwordET.getText().toString());


        }
    }


//        binding.progressBar.setVisibility(View.GONE);
//
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
//                SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        Toast.makeText(SignUpActivity.this, "Successfully Registered" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                        binding.progressBar.setVisibility(View.GONE);
//
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(SignUpActivity.this, "Registration Failed" + task.getException(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
//                            finish();
//                        }
//                    }
//                });


    @Override
    protected void onResume() {
        super.onResume();
        binding.progressBar.setVisibility(View.GONE);
    }

    private void signUpUsingFirebase(final String vEmail, final String vPassword, final FCM_UserDTO fcm_userDTO) {

        auth.createUserWithEmailAndPassword(vEmail, vPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    String vUid = auth.getUid();
                    SplashActivity.fcmUserDTO = new FCM_UserDTO();
                    SplashActivity.fcmUserDTO.setUser_id(vUid);
                    SplashActivity.fcmUserDTO.setUsername(fcm_userDTO.getUsername());
                    SplashActivity.fcmUserDTO.setEmail(fcm_userDTO.getEmail());
                    SplashActivity.fcmUserDTO.setDevice_token("");
                    SplashActivity.fcmUserDTO.setDevice_type("1");
                    SplashActivity.fcmUserDTO.setImage("");
                    SplashActivity.fcmUserDTO.setTimeZone(TimeZone.getDefault().getID());
                    dbFirebase = FirebaseDatabase.getInstance().getReference("user");
                    dbFirebase.child(vUid).setValue(SplashActivity.fcmUserDTO);
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }

            }

        });
    }

    private void signInFirebase(final String vEmail, final String vPassword/*, final String vUserType,
                                final FCM_UserDTO _fcmUserDTO*/) {
        Log.d(LOG_TAG, "signInFirebase");
        auth.signInWithEmailAndPassword(vEmail, vPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "User already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            FCM_UserDTO fcmUserDTO = new FCM_UserDTO(
                                    "", binding.emailET.getText().toString().trim(),
                                    binding.nameET.getText().toString().trim(),
                                    TimeZone.getDefault().getID(),
                                    "", "",
                                    "1", "abc12345"
                            );
                            signUpUsingFirebase(vEmail, vPassword, fcmUserDTO);
                        }


                    }

                });
    }

}