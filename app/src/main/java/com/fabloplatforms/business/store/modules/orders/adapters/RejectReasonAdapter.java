package com.fabloplatforms.business.store.modules.orders.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.ReasonListBinding;
import com.fabloplatforms.business.store.models.acceptorder.RejectReasonResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RejectReasonAdapter extends RecyclerView.Adapter<RejectReasonAdapter.ReasonViewHolder> {
    ReasonListBinding binding;
    private Context context;
    private int selectedItem;
    private List<RejectReasonResponse.Item> reasonList;

    public RejectReasonAdapter(Context context, List<RejectReasonResponse.Item> reasonList) {
        this.context = context;
        this.reasonList = reasonList;
        selectedItem=-1;
    }

    @NonNull
    @NotNull
    @Override
    public ReasonViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reason_list,parent,false);

        return new ReasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReasonViewHolder holder, int position) {
        RejectReasonResponse.Item item = reasonList.get(position);
        if(item!=null)
        {


            holder.reason.setText(item.getMessage());
            holder.reason.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String reason = item.getMessage();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Reason",reason);
                    editor.apply();
                    int previousItem = selectedItem;
                    selectedItem = holder.getAdapterPosition();

                    notifyItemChanged(previousItem);
                    notifyItemChanged(holder.getAdapterPosition());
                 //   holder.reason.setTextColor(Color.parseColor("#A0A4A8"));


                }
            });
            if(selectedItem==position){
                holder.reason.setTextColor(Color.parseColor("#A0A4A8"));

            }
            else
            {
                holder.reason.setTextColor(Color.parseColor("#25282B"));
            }
        }


    }


    @Override
    public int getItemCount() {
        return reasonList.size();
    }

    public class ReasonViewHolder extends RecyclerView.ViewHolder {
        private TextView reason;
        public ReasonViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            reason = itemView.findViewById(R.id.tvReason);
        }
    }
}
