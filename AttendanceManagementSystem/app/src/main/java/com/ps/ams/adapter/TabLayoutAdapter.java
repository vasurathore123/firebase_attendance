package com.ps.ams.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.ams.dto.TablayoutDTO;
import com.ps.ams.R;
import com.ps.ams.databinding.AdapterTablayoutViewBinding;

import java.util.ArrayList;

public class TabLayoutAdapter extends RecyclerView.Adapter<TabLayoutAdapter.ViewHolder> {
    public static int selectedPosition = -1;
    private AdapterTablayoutViewBinding binding;
    private ArrayList<TablayoutDTO> mList;
    private LayoutInflater inflater;
    private Context mContext;

    public TabLayoutAdapter(ArrayList<TablayoutDTO> list, Context context) {
        mList = list;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_tablayout_view, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.binding.digitTV.setText(mList.get(position).getmOne());

        if (mList.get(position).isSelected()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.binding.digitRL.setBackground(mContext.getDrawable(R.drawable.icon_true));
                holder.binding.digitTV.setVisibility(View.GONE);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.binding.digitTV.setTextColor(mContext.getResources().getColor(R.color.black));

            }
        }


        holder.binding.digitRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition != -1) {
                    if (selectedPosition == position) {
                        if (mList.get(selectedPosition).isSelected()) {
                            mList.get(selectedPosition).setSelected(false);
                        } else {
                            mList.get(selectedPosition).setSelected(true);
                        }
                        selectedPosition = -1;
                    } else {
                        mList.get(selectedPosition).setSelected(false);
                        selectedPosition = position;
                        mList.get(selectedPosition).setSelected(true);
                    }
                } else {
                    selectedPosition = position;
                    mList.get(selectedPosition).setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterTablayoutViewBinding binding;

        public ViewHolder(@NonNull AdapterTablayoutViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}