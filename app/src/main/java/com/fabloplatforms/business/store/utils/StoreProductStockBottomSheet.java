package com.fabloplatforms.business.store.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;


import com.fabloplatforms.business.databinding.FragmentRestartTimeBottomSheetBinding;
import com.fabloplatforms.business.store.interfaces.MenuCategoryInterface;
import com.fabloplatforms.business.store.models.ProductStatus;
import com.fabloplatforms.business.store.models.storeavailability.StoreScheduleRequest;
import com.fabloplatforms.business.store.models.storeavailability.StoreScheduleResponse;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreProductStockBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    FragmentRestartTimeBottomSheetBinding binding;
    private Context context;
    private String offType ="2 hours",pId;

    private static final String TAG = "ProductStockBottomSheet";
    String dD,tT,y,dateTime;
    public StoreProductStockBottomSheet() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestartTimeBottomSheetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Bundle mArgs = getArguments();
        assert mArgs != null;
        pId = mArgs.getString("productId");

        initView();
        return view;
    }

    private void initView() {
        context = getContext();
        binding.btnDate.setOnClickListener(this);
        binding.radioSelf.setOnClickListener(this);
        binding.btnSetSchedule.setOnClickListener(this);
        binding.imgCancle.setOnClickListener(this);
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {

               checkRadio(checkedId);
           }
       });




    }
    private void checkRadio(int checkedId) {
        if(checkedId == binding.radio2hrs.getId())
        {

            if( binding.radioSelf.isChecked())
            {
                binding.radioSelf.setChecked(false);
            }
            binding.dateLayout.setVisibility(View.GONE);
            offType = "2 hours";

        }
        else if(checkedId == binding.radio4hrs.getId())
        {
            if( binding.radioSelf.isChecked())
            {
                binding.radioSelf.setChecked(false);
            }
            binding.dateLayout.setVisibility(View.GONE);
            offType = "4 hours";

        }
        else if(checkedId == binding.radioTomorrow.getId())
        {
            if( binding.radioSelf.isChecked())
            {
                binding.radioSelf.setChecked(false);
            }
            binding.dateLayout.setVisibility(View.GONE);
            offType = "tomorrow";

        }
        else if(checkedId == binding.radioSpecific.getId())
        {

            binding.tiTime.setVisibility(View.GONE);
            binding.tiDate.setVisibility(View.GONE);
            binding.tvMsgDate.setVisibility(View.GONE);
            binding.etTime.setText("");
            binding.etDate.setText("");
            binding.dateLayout.setVisibility(View.VISIBLE);
            if( binding.radioSelf.isChecked())
            {
                binding.radioSelf.setChecked(false);
            }
            offType = "specific";

        }
    }
    @Override
    public void onClick(View v) {
       if(v== binding.btnDate)
       {
           handleDateButton();
       }
       if(v== binding.radioSelf)
       {
           manuallyFun();
       }
        if(v== binding.btnSetSchedule)
        {
            setSchedule();
        }
        if(v== binding.imgCancle)
        {
            dismissBottom();
        }


    }

    private void manuallyFun() {

        if( binding.radio2hrs.isChecked())
        {
            binding.radio2hrs.setChecked(false);
        }
        else if( binding.radio4hrs.isChecked())
        {
            binding.radio4hrs.setChecked(false);
        }
        else if( binding.radioTomorrow.isChecked())
        {
            binding.radioTomorrow.setChecked(false);
        }
        else if( binding.radioSpecific.isChecked())
        {
            binding.radioSpecific.setChecked(false);
            binding.tiTime.setVisibility(View.GONE);
            binding.tiDate.setVisibility(View.GONE);
            binding.etTime.setText("");
            binding.etDate.setText("");
            binding.dateLayout.setVisibility(View.GONE);
        }
        binding.radioSelf.setChecked(true);
        offType = "manual";

    }
    private void setSchedule() {
        switch(offType) {
            case "manual":
                callManualApi();
                break;
            case "2 hours":
                setTwo();
                break;
            case "4 hours":
                setFour();
                break;
            case "tomorrow":
                setTomorrow();
                break;
            case "specific":
                setSpecific();
                break;
        }



    }
    private void setSpecific() {
        if (binding.tiDate.getVisibility()==View.VISIBLE && binding.tiTime.getVisibility()==View.VISIBLE) {
            dateTime = dD +" "+tT+" "+ "GMT+05:30"+" "+ y;
            Log.d(TAG,"Date/Time "+ dateTime);
            StoreOutletPref pref = new StoreOutletPref(context);
            String store_id = pref.getId();
            StoreScheduleRequest request = new StoreScheduleRequest();
            request.setStore_id(store_id);
            request.setHours("");
            request.setTimestamp(dateTime);
            request.setTommorow("");
            callSchedule(request);
        }
        else
        {
            binding.tvMsgDate.setVisibility(View.VISIBLE);
        }


    }

    private void setTomorrow() {
        StoreOutletPref pref = new StoreOutletPref(context);
        String store_id = pref.getId();
        StoreScheduleRequest request = new StoreScheduleRequest();
        request.setStore_id(store_id);
        request.setHours("");
        request.setTimestamp("");
        request.setTommorow("1");
        callSchedule(request);
    }

    private void setFour() {
        StoreOutletPref pref = new StoreOutletPref(context);
        String store_id = pref.getId();
        StoreScheduleRequest request = new StoreScheduleRequest();
        request.setStore_id(store_id);
        request.setHours("4");
        request.setTimestamp("");
        request.setTommorow("");
        callSchedule(request);
    }

    private void setTwo() {
        StoreOutletPref pref = new StoreOutletPref(context);
        String store_id = pref.getId();
        StoreScheduleRequest request = new StoreScheduleRequest();
        request.setStore_id(store_id);
        request.setHours("2");
        request.setTimestamp("");
        request.setTommorow("");
        callSchedule(request);
    }
    private void dismissBottom() {

        ProductStatus status = new ProductStatus();
        status.isOnline();
//       anInterface = (BottomCloseInterface) context;
//        dismiss();
        EventBus.getDefault().post(status);
//        final Random myRandom = new Random();
//        int k = myRandom.nextInt();
//        SharedPreferences preferences = context.getSharedPreferences("ProductState",0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("product",k);
//        editor.apply();
        dismiss();
    }
    private void callSchedule(StoreScheduleRequest request) {

        MenuCategoryInterface menuInterface = RestClientStore.getRetrofitServiceInstance(context).create(MenuCategoryInterface.class);
        Call<StoreScheduleResponse> call = menuInterface.stockUpdateSchedule(pId,1,request);
        call.enqueue(new Callback<StoreScheduleResponse>() {
            @Override
            public void onResponse(Call<StoreScheduleResponse> call, Response<StoreScheduleResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {

                    Toast.makeText(context, "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+ response.message());
                    dismiss();

                }
                else {
                    Log.d(TAG, "onResponse: "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<StoreScheduleResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }


    private void callManualApi() {

        MenuCategoryInterface categoryInterface = RestClientStore.getRetrofitServiceInstance(context).create(MenuCategoryInterface.class);
        Call<ResponseBody> call = categoryInterface.stockUpdateManual(pId,0);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    Toast.makeText(context, "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error","error"+t);
                Toast.makeText(context, "Status Not Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                dD = DateFormat.format("EEE MMM dd",calendar1).toString();
                y = DateFormat.format("yyyy",calendar1).toString();
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();
                binding.tiDate.setVisibility(View.VISIBLE);
                binding.etDate.setText(dateText);
                handleTimeButton();
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR_OF_DAY);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(context);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
             //   Log.i(TAG, "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                tT = DateFormat.format("HH:mm:ss ", calendar1).toString();
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                binding.tiTime.setVisibility(View.VISIBLE);
                binding.tvMsgDate.setVisibility(View.GONE);
                binding.etTime.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();
    }


}