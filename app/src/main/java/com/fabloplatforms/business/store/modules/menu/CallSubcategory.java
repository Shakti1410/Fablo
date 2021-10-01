package com.fabloplatforms.business.store.modules.menu;

import android.content.Context;
import android.widget.Toast;


import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.MenuCategoryInterface;
import com.fabloplatforms.business.store.models.MenuSubCategoryResponse;
import com.fabloplatforms.business.store.modules.menu.adapter.SubcategoryAdapter;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallSubcategory {
  String categoryid;
  private Context context;
  private StoreFabloAlert storeFabloAlert;

  public CallSubcategory(String categoryid, Context context) {
    this.categoryid = categoryid;
    this.context = context;
    storeFabloAlert = new StoreFabloAlert();
  }

  public void callApi(String categoryid, Context context)
    {

      StoreOutletPref pref = new StoreOutletPref(context);
      String store_id = pref.getId();
      MenuCategoryInterface categoryInterface = RestClientStore.getRetrofitServiceInstance(context).create(MenuCategoryInterface.class);
      Call<MenuSubCategoryResponse> call = categoryInterface.getSubCategories(store_id,categoryid);
      call.enqueue(new Callback<MenuSubCategoryResponse>() {
        @Override
        public void onResponse(Call<MenuSubCategoryResponse> call, Response<MenuSubCategoryResponse> response) {
          if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
            if (response.body() != null) {
              SubcategoryAdapter adapter = new SubcategoryAdapter(context,response.body().getItems());
              adapter.notifyDataSetChanged();

            }
          } else {
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
          }
        }

        @Override
        public void onFailure(Call<MenuSubCategoryResponse> call, Throwable t) {
          if (t instanceof NoConnectivityException){
            showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

          }
        }
      });

    }
  public void showError(String title, String description) {
    storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
  }
}
