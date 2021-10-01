package com.fabloplatforms.business.store.modules.orders.fragments;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentPreparingOrderBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.StoreMainActivity;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;
import com.fabloplatforms.business.store.modules.orders.adapters.PreparingOrdersAdapter;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.GlobalError;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PreparingOrderFragment extends Fragment {
    FragmentPreparingOrderBinding binding;
    private static final String TAG = "PreparingOrderFragment";
    private final String KEY_RECYCLER_STATE = "recycler_state";

    private Context context;
    MqttAndroidClient client;
    private StoreFabloLoading loading;
    private FabloAlert fabloAlert;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    String serverURL = "tcp://broker.hivemq.com:1883";
    String topic = "",storeId="";
    String sTopic = "mqtt/sensorData";
    boolean connectionFlag = false;
    //Only for Checking
    PreparingOrdersAdapter preparingAdapter;
    private static Bundle mBundleRecyclerViewState;

    public PreparingOrderFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPreparingOrderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();

        return view;
    }

    private void initView() {
        //checkOutletStatus();
        context = getContext();

        loading = new StoreFabloLoading();
        fabloAlert = new FabloAlert();

        getItem(context);

    }

    private void connectToBroker() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, serverURL, clientId);

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);// just try for continue msg
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            IMqttToken token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //  connectionStatus.setText("Connected To " + serverURL);
                    connectionFlag = true;

                    //  Toast.makeText(MyForeground.this, "Connection  "+ asyncActionToken, Toast.LENGTH_SHORT).show();

                    subscribeToTopic(topic);
                    //     sendButton.setEnabled(true);
                    //   subscribeButton.setEnabled(true);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(context, "Failed"+exception, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribeToTopic(String topic1) {
        try {
            if (client.isConnected()) {
                client.subscribe(topic1, 0);
                //  Toast.makeText(getApplicationContext(), "Subscribed", Toast.LENGTH_SHORT).show();
                client.setCallback(new MqttCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void connectionLost(Throwable cause) {

                        connectionFlag = false;
                        connectToBroker();

                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.ting);
                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        Log.d(TAG, "messageArrived: "+ message);
                         getItem(context);
                        JSONObject msg = new JSONObject(new String(message.getPayload()));
                        String id = msg.getString("resourceId");

                        Toast.makeText(context, "New Msg  "+ msg.getString("resourceId"), Toast.LENGTH_LONG).show();
                        Intent notificationIntent1 = new Intent(context, StoreMainActivity.class);
                        notificationIntent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent1,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setContentTitle("Delivery Assign")
                                .setContentText("XYZ")
                                .setSmallIcon(R.drawable.fablo_icon);


                        builder.setContentIntent(contentIntent);


                        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(id.hashCode(), builder.build());
//
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {

                    }
                });
            }
        } catch (Exception ignored) {
        }
    }
//    private void checkOutletStatus() {
//        OutletStatusPref outletStatusPref = new OutletStatusPref(context);
//        if (outletStatusPref.getOutletStatus()) {
//              binding.recyclePreparing.setVisibility(View.VISIBLE);
//              binding.storeOffline.setVisibility(View.GONE);
//        } else {
//            binding.recyclePreparing.setVisibility(View.GONE);
//            binding.storeOffline.setVisibility(View.VISIBLE);
//
//        }
//    }
    private void getItem(Context context) {
        loading.showProgress(context);
        StoreOutletPref pref = new StoreOutletPref(context);
        storeId = pref.getId();
        topic = storeId;//.concat("-order")

          //  connectToBroker();
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<PendingOrderResponse> call = orderInterface.preparingOrder(storeId);
        call.enqueue(new Callback<PendingOrderResponse>() {
            @Override
            public void onResponse(Call<PendingOrderResponse> call, Response<PendingOrderResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        loading.hideProgress();
                        if(!response.body().getItems().isEmpty()) {
                            preparingAdapter = new PreparingOrdersAdapter(getContext(), response.body().getItems());
                            DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                            Drawable drawable = context.getResources().getDrawable(R.drawable.divider);
                            decoration.setDrawable(drawable);
                            binding.recyclePreparing.setLayoutManager(new LinearLayoutManager(context));
                            binding.recyclePreparing.addItemDecoration(decoration);
                            binding.recyclePreparing.setAdapter(preparingAdapter);


                        }

                    }
                } else {
                    loading.hideProgress();
                    Log.d(TAG, "onResponse: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<PendingOrderResponse> call, Throwable t) {
                loading.hideProgress();
                Log.d(TAG, "onFailure: " + t.getMessage());
                if (t instanceof NoConnectivityException) {
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });

    }

    public void showError(String title, String description) {
        fabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
    private void networkMsg() {
        try {
            fabloAlert.showAlert(context,StoreConstant.ALERT_ERROR,false,"Network Alert","Internet not available, Cross check your internet connectivity and try again",TAG);

        } catch (Exception e) {
            Toast.makeText(context, "Error :" + e, Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){

            return false;
        }
        return true;
    }
    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        preparingAdapter = null;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}