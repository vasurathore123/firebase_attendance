package com.ps.ams.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ps.ams.dto.TaskListDTO;
import com.ps.ams.R;
import com.ps.ams.activity.TaskDetailsActivity;
import com.ps.ams.databinding.AdapterTaskListViewBinding;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private AdapterTaskListViewBinding binding;
    private ArrayList<TaskListDTO> mList;
    private LayoutInflater inflater;
    private Context mContext;

    public TaskListAdapter(ArrayList<TaskListDTO> list, Context context) {
        this.mList = list;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_task_list_view, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        binding.dateTV.setText(mList.get(position).getDate());
        binding.tasknameTV.setText(mList.get(position).getTitle());
        binding.descriptionTV.setText(mList.get(position).getDescription());

        binding.taskLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, TaskDetailsActivity.class);
                intent.putExtra("Date", mList.get(position).getDate());
                intent.putExtra("Name", mList.get(position).getTitle());
                intent.putExtra("Description", mList.get(position).getDescription());
                intent.putExtra("status", mList.get(position).getStatus());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterTaskListViewBinding binding;
        public ViewHolder(@NonNull AdapterTaskListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}