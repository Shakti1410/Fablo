package com.fabloplatforms.business.modules.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabloplatforms.business.auth.model.OutletLoginRequest;
import com.fabloplatforms.business.auth.model.OutletLoginResponse;
import com.fabloplatforms.business.databinding.ListItemCityBinding;
import com.fabloplatforms.business.databinding.StorelistitemBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.modules.model.StoreListResponse;
import com.fabloplatforms.business.onboard.adapter.CityRecyclerAdapter;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.store.StoreMainActivity;
import com.fabloplatforms.business.store.error.ClientErrorBody;
import com.fabloplatforms.business.store.interfaces.OutletInterface;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.FabloLoading;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreListRecyclerAdapter extends RecyclerView.Adapter<StoreListRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<StoreListResponse.Item> storeList;
    StorelistitemBinding binding;
    StoreFabloAlert storeFabloAlert;
    private FabloLoading fabloLoading;

    public StoreListRecyclerAdapter(Context context, List<StoreListResponse.Item> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = StorelistitemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View view = binding.getRoot();
        fabloLoading = FabloLoading.getInstance();
        storeFabloAlert = new StoreFabloAlert();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        StoreListResponse.Item store = storeList.get(position);
        if (store != null) {
            binding.tvStorelist.setText(store.getStoreName());
            binding.tvStorelist.setOnClickListener(v -> {
                   String id = store.getStoreUsername();
                   String pass = store.getPassword();


//                StoreOutletPref storeOutletPref = new StoreOutletPref(context);
//                storeOutletPref.setOutletDetails(response);

                   storeLogin(id,pass);
            //    Toast.makeText(context, "Store Selected"+ store.getStoreName(), Toast.LENGTH_LONG).show();
                //Store Intent
               // EventBus.getDefault().post(store);
            });
        }

    }

   private void storeLogin(String phone, String pass) {
        fabloLoading.showProgress(context);
        String restId = phone;
        String password = pass;

        if (!restId.isEmpty() && !password.isEmpty()) {
            OutletLoginRequest outletLoginRequest = new OutletLoginRequest();
            outletLoginRequest.setStoreUsername(restId);
            outletLoginRequest.setPassword(password);

            getRestaurantByIdAndPassword(outletLoginRequest);
        }
    }
    private void getRestaurantByIdAndPassword(OutletLoginRequest outletLoginRequest) {
        OutletInterface outletInterface = RestClientStore.getRetrofitServiceInstance(context).create(OutletInterface.class);
        Call<OutletLoginResponse> call = outletInterface.outLogin(outletLoginRequest);
        call.enqueue(new Callback<OutletLoginResponse>() {
            @Override
            public void onResponse(Call<OutletLoginResponse> call, Response<OutletLoginResponse> response) {


                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {

                    if (response.body() != null) {
                        fabloLoading.hideProgress();
                        StoreOutletPref storeOutletPref = new StoreOutletPref(context);
                        storeOutletPref.setOutletDetails(response.body());
                        launchMain();

                    }
                } else if (response.code() == StoreConstant.HTTP_CLIENT_ERROR) {
                    fabloLoading.hideProgress();
                    Gson gson = new GsonBuilder().create();
                    try {
                        ClientErrorBody clientErrorBody = gson.fromJson(response.errorBody().string(), ClientErrorBody.class);
                        if (clientErrorBody.getMessage().equals(StoreConstant.ERROR_INVALID_CREDENTIALS)) {
                            showError("Login error", "Seams you have entered wrong Id or password.");
                        }
                    } catch (IOException e) {
                        showError("Incorrect details", "Seems there is some issue with the details you have entered.");
                    }
                }
            }

            @Override
            public void onFailure(Call<OutletLoginResponse> call, Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException) {
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");
//                    StoreGlobalError storeGlobalError = new StoreGlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", StoreConstant.STATUS_NO_ERROR);
//                    EventBus.getDefault().post(storeGlobalError);
                }
            }
        });
    }
    private void launchMain() {
        fabloLoading.hideProgress();
        Intent intent = new Intent(context, StoreMainActivity.class);
        intent.putExtra("LoginType","business");
        context.startActivity(intent);
    }

    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
