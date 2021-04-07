package com.ps.ams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps.ams.activity.LoginActivity;
import com.ps.ams.activity.NoticeActivity;
import com.ps.ams.activity.TaskCompleteActivity;
import com.ps.ams.adapter.TaskListAdapter;
import com.ps.ams.databinding.ActivityMainBinding;
import com.ps.ams.dto.FCM_UserDTO;
import com.ps.ams.dto.TaskListDTO;
import com.ps.ams.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    String cal_Date;
    private ActivityMainBinding binding;
    private ArrayList<TaskListDTO> mList;
    private TaskListAdapter adapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initValues();
        setClickEvent();
        setFirebaseValue();
    }

    private void setAdapter() {
        binding.tasklistRV.setHasFixedSize(true);
        binding.tasklistRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskListAdapter(mList, mContext);
        binding.tasklistRV.setAdapter(adapter);
    }

    private void initValues() {
        mContext = this;
        sessionManager = new SessionManager(mContext);
        mList = new ArrayList<>();

    }

    private void setClickEvent() {

        binding.calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                cal_Date = dayOfMonth + "/" + (month + 1) + "/" + year;
                binding.dateTV.setText(cal_Date);
                setFirebaseValue();
            }
        });

        binding.viewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, TaskCompleteActivity.class);
                startActivity(intent);
            }
        });

        binding.noticeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        binding.logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog().show();
            }
        });
    }

    private AlertDialog showLogoutDialog() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you want to LogOut")
                .setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        sessionManager.logOut();
                        Intent i = new Intent(mContext, LoginActivity.class);
                        startActivity(i);
                        finishAffinity();
                        finish();

                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }

    private void setFirebaseValue() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("task");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList.clear();
                Log.d("MainActivity", "onDataChange: getUserId--> " + sessionManager.getUserId());
                if (dataSnapshot.exists()) {
                    mList.clear();
                    if (adapter != null)
                        adapter.notifyDataSetChanged();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        TaskListDTO taskListDTO = (TaskListDTO) npsnapshot.getValue(TaskListDTO.class);

                        if (taskListDTO.getDate().equals(cal_Date) && taskListDTO.getUser_id().equals(sessionManager.getUserId())) {
                            mList.add(taskListDTO);
                        }
                    }
                    if (adapter != null)
                        adapter.notifyDataSetChanged();
                    else
                        setAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}