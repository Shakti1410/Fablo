package com.fabloplatforms.business.store.modules.menu;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.fabloplatforms.business.databinding.FragmentMenuBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.MenuCategoryInterface;
import com.fabloplatforms.business.store.models.MenuCategoryResponse;
import com.fabloplatforms.business.store.modules.menu.adapter.CategoryAdapter;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {
    FragmentMenuBinding binding;
    private Context context;
    private StoreFabloLoading loading;
    private StoreFabloAlert storeFabloAlert;

    private static final String TAG = "MenuFragment";

    //Extra



    //Extra
    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {

        loading = new StoreFabloLoading();
        storeFabloAlert = new StoreFabloAlert();
      //  repository = new Repository(getActivity());
        context = getContext();



     //  viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MenuFragmentViewModel.class);



/* *****For room connection8***** */
//        repository.getAllCategory().observe((LifecycleOwner) context, new Observer<List<CategoryRoomModel>>() {
//            @Override
//            public void onChanged(List<CategoryRoomModel> sources) {
//
//                CategoryAdapter adapter = new CategoryAdapter(getContext(),sources);
//                binding.rvMenu.setAdapter(adapter);
//
//            }
//        });
//        repository.getCategoryListTodb();



        fetchCategory();
    }

    private void fetchCategory() {
        loading.showProgress(context);
        StoreOutletPref pref = new StoreOutletPref(context);
        String store_id = pref.getId();
        MenuCategoryInterface categoryInterface = RestClientStore.getRetrofitServiceInstance(context).create(MenuCategoryInterface.class);
        Call<MenuCategoryResponse> call = categoryInterface.getCategories(store_id);
        call.enqueue(new Callback<MenuCategoryResponse>() {
            @Override
            public void onResponse(Call<MenuCategoryResponse> call, Response<MenuCategoryResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                         loading.hideProgress();
                         if(!response.body().getItems().isEmpty()) {
                             binding.rvMenu.setLayoutManager(new LinearLayoutManager(getContext()));
                             CategoryAdapter adapter = new CategoryAdapter(getContext(), response.body().getItems());
                             binding.rvMenu.setAdapter(adapter);
                         }

                    }
                } else {
                    loading.hideProgress();
                    Log.d(TAG, "onResponse: "+response.message());
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MenuCategoryResponse> call, Throwable t) {
                loading.hideProgress();
                if (t instanceof NoConnectivityException){
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });

    }
    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}