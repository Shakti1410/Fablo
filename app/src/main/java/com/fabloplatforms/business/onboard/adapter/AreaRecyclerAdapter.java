package com.fabloplatforms.business.onboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabloplatforms.business.databinding.ListItemCityBinding;
import com.fabloplatforms.business.onboard.model.AreaResponse;
import com.fabloplatforms.business.onboard.model.StateResponse;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class AreaRecyclerAdapter extends RecyclerView.Adapter<AreaRecyclerAdapter.ViewHolder> {
    ListItemCityBinding binding;
    private Context context;
    private List<AreaResponse.Item> areaList;

    public AreaRecyclerAdapter(Context context, List<AreaResponse.Item> areaList) {
        this.context = context;
        this.areaList = areaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ListItemCityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AreaResponse.Item area = areaList.get(position);
        if (area != null) {
            binding.tvCity.setText(area.getAreaName());
            binding.tvCity.setOnClickListener(v -> {
                EventBus.getDefault().post(area);
            });
        }
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
