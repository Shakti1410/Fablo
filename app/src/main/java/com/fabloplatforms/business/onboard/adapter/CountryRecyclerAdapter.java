package com.fabloplatforms.business.onboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabloplatforms.business.databinding.ListItemCityBinding;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.onboard.model.Country;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.ViewHolder>{
    ListItemCityBinding binding;
    private Context context;
    private List<Country.Item> countryList;


    public CountryRecyclerAdapter(Context context, List<Country.Item> countryList) {
        this.context = context;
        this.countryList = countryList;
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
        Country.Item country = countryList.get(position);
        if (country != null) {
            binding.tvCity.setText(country.getName());
            binding.tvCity.setOnClickListener(v -> {
                EventBus.getDefault().post(country);
            });
        }
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
