package com.fabloplatforms.business.onboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabloplatforms.business.databinding.ListItemCityBinding;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.onboard.model.StateResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class StateRecyclerAdapter extends RecyclerView.Adapter<StateRecyclerAdapter.ViewHolder> {
    ListItemCityBinding binding;
    private Context context;
    private List<StateResponse.Item> stateList;


    public StateRecyclerAdapter(Context context, List<StateResponse.Item> stateList) {
        this.context = context;
        this.stateList = stateList;
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
        StateResponse.Item state = stateList.get(position);
        if (state != null) {
            binding.tvCity.setText(state.getName());
            binding.tvCity.setOnClickListener(v -> {
                EventBus.getDefault().post(state);
            });
        }
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
