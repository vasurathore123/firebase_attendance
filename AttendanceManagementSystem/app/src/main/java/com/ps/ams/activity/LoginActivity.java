package com.ps.ams.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.ps.ams.databinding.ActivityLoginBinding;
import com.ps.ams.dto.FCM_UserDTO;
import com.ps.ams.util.SessionManager;

import java.util.Calendar;
import java.util.TimeZone;

public class LoginActivity extends AppCompatActivity {
    private String LOG_TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    SessionManager sessionManager;
    Context mContext;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initValues();
        setClickEvent();
        checkCon();
    }

    private void initValues() {
        mContext = this;
        sessionManager = new SessionManager(mContext);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void checkCon() {
        if (auth.getCurrentUser() != null) {
            if (sessionManager.isLogin()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }
    }

    private void setClickEvent() {

        binding.loginRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();

            }
        });


        binding.createaccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.forgotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);

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
                    binding.loginRL.setBackgroundResource(R.drawable.curculer_view_green);
                } else {
                    binding.loginRL.setBackgroundResource(R.drawable.curculer_view);
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
                    binding.loginRL.setBackgroundResource(R.drawable.curculer_view_green);
                } else {
                    binding.loginRL.setBackgroundResource(R.drawable.curculer_view);
                }
            }
        });
    }

    private void checkValidation() {

        String email = binding.emailET.getText().toString();
        final String password = binding.passwordET.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {

            binding.progressBar.setVisibility(View.VISIBLE);

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    binding.progressBar.setVisibility(View.GONE);

                   /* if (!task.isSuccessful()) {
                        if (password.length() < 6) {
                            binding.passwordET.setError(getString(R.string.password));
                        } else {
                            alertDialogDialog();
                        }
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        sessionManager.login();
                        startActivity(intent);
                        finish();
                    }*/
                    FCM_UserDTO fcmUserDTO = new FCM_UserDTO(
                            "01",
                            binding.emailET.getText().toString(),
                            "Vasu",
                            "", "1", "",
                            "1", "abc12345"
                    );
                    signInFirebase(binding.emailET.getText().toString(), binding.passwordET.getText().toString());


                }
            });

        }


    }

    public void alertDialogDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        final Dialog dialog = new Dialog(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_view, null);
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView okayTV = dialogView.findViewById(R.id.okayTV);
        LinearLayout okayLL = dialogView.findViewById(R.id.okayLL);
        okayLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                finish();
            }
        });
        dialog.show();
    }

    private DatabaseReference dbFirebase;

    private void signInFirebase(final String vEmail, final String vPassword/*, final String vUserType,
                                final FCM_UserDTO _fcmUserDTO*/) {
        Log.d(LOG_TAG, "signInFirebase");
        auth.signInWithEmailAndPassword(vEmail, vPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(LOG_TAG, "===INSIDE===");
                            final FirebaseUser user = auth.getCurrentUser();
                            dbFirebase = FirebaseDatabase.getInstance().getReference("user");
                            dbFirebase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    try {
                                        Log.d(LOG_TAG, "===INSIDE CHILD===");
                                        FCM_UserDTO fcm_userDTO = dataSnapshot.getValue(FCM_UserDTO.class);
                                        fcm_userDTO.setUser_id(auth.getUid());
                                        dbFirebase.child(fcm_userDTO.getUser_id()).setValue(fcm_userDTO);
                                        sessionManager.setUserId(fcm_userDTO.getUser_id());
                                        sessionManager.login();
                                        startActivity(new Intent(mContext, MainActivity.class));
                                    } catch (Exception e) {
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    dialogProgress.dismiss();
                                    Log.d(LOG_TAG, "===INSIDE CHILD ERROR===");
                                    Log.d(LOG_TAG, "@@firebase db@@" + databaseError.getDetails() + "\n message" + databaseError.getMessage());
                                    Toast.makeText(mContext, "Password Not Matched.", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            Toast.makeText(mContext, "Register First", Toast.LENGTH_SHORT).show();
                        }


                    }

                });
    }


}