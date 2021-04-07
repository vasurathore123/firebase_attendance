package com.ps.ams.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ps.ams.MainActivity;
import com.ps.ams.dto.TablayoutDTO;
import com.ps.ams.R;
import com.ps.ams.adapter.TabLayoutAdapter;
import com.ps.ams.databinding.ActivityTaskDetailsBinding;
import com.ps.ams.dto.TaskListDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskDetailsActivity extends AppCompatActivity {
    private ActivityTaskDetailsBinding binding;
    private ArrayList<TablayoutDTO> mList;
    TabLayoutAdapter tabLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_details);
        init();
        setArrayList();
    }

    private void init() {
        mList = new ArrayList<>();
        setClickeEvent();
        setAdapter();
        Intent intent = getIntent();
        final String taskDate = intent.getExtras().getString("Date");
        final String taskName = intent.getExtras().getString("Name");
        final String taskDescription = intent.getExtras().getString("Description");
        final String taskStatus = intent.getExtras().getString("status");

        binding.dateTV.setText(taskDate);
        binding.tasknameTV.setText(taskName);
        binding.descriptionTV.setText(taskDescription);
        binding.statusTV.setText(taskStatus);

    }

    private void setAdapter() {
        binding.tablayoutRV.setHasFixedSize(true);
        tabLayoutAdapter = new TabLayoutAdapter(mList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.tablayoutRV.setLayoutManager(linearLayoutManager);
        binding.tablayoutRV.setAdapter(tabLayoutAdapter);
    }

    private void setArrayList() {
        if (mList.size() < 1) {
            mList.clear();
            mList.add(new TablayoutDTO("1", false));
            mList.add(new TablayoutDTO("2", false));
            mList.add(new TablayoutDTO("3", false));
            mList.add(new TablayoutDTO("4", false));
            mList.add(new TablayoutDTO("5", false));
            mList.add(new TablayoutDTO("6", false));
            mList.add(new TablayoutDTO("7", false));
            mList.add(new TablayoutDTO("8", false));
            mList.add(new TablayoutDTO("9", false));
            mList.add(new TablayoutDTO("10", false));
            tabLayoutAdapter.notifyDataSetChanged();
        }


    }

    private void setClickeEvent() {

        binding.backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TaskDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.completeRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialogDialog();
            }
        });

    }

    public void alertDialogDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        final Dialog dialog = new Dialog(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.popop_layout, null);
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView closeIV = dialogView.findViewById(R.id.closeIV);
        LinearLayout rl = dialogView.findViewById(R.id.rl);
        RelativeLayout submittedRL = dialogView.findViewById(R.id.submittedRL);
        closeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                finish();
            }
        });

        submittedRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog();

            }
        });


        dialog.show();
    }


    public void showUpdateDialog() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(TaskDetailsActivity.this);
        alertDialogBuilder.setTitle("Are you sure Edit Profile");
//        alertDialogBuilder.setMessage("Complete");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(TaskDetailsActivity.this.getString(R.string.update_btn_title), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

}