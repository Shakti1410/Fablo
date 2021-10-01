package com.fabloplatforms.business.onboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.databinding.ListItemCityBinding;
import com.fabloplatforms.business.onboard.model.City;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> {

    ListItemCityBinding binding;
    private Context context;
    private List<City.Item> cityList;

    public CityRecyclerAdapter(Context context, List<City.Item> cityList) {
        this.context = context;
        this.cityList = cityList;
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
        City.Item city = cityList.get(position);
        if (city != null) {
            binding.tvCity.setText(city.getName());
            binding.tvCity.setOnClickListener(v -> {
                EventBus.getDefault().post(city);
            });
        }
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
