package com.fabloplatforms.business.store;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreLogoutAlert;
import com.fabloplatforms.business.store.utils.StoreOutletPref;


import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class MyForeground extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private Context context;
    MqttAndroidClient client;
    private StoreFabloAlert storeFabloAlert;
    String tmsg;
    private StoreLogoutAlert fabloAlert;
    String serverURL = "tcp://broker.hivemq.com:1883";
    String topic = "";
    String sTopic = "mqtt/sensorData";
    public static final String ACTION_START_SERVICE = "ACTION_START_SERVICE";
    public static final String ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";
    boolean connectionFlag = false;
    private final static String TAG = "MyForegroundService";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        storeFabloAlert = new StoreFabloAlert();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(isOnline()) {
            if (intent != null) {
                String action = intent.getAction();
                StoreOutletPref pref = new StoreOutletPref(context);
                topic = pref.getId();

                switch (action) {

                    case ACTION_START_SERVICE:
                        tmsg = "on";
                        Toast.makeText(getApplicationContext(), "Foreground service is started.", Toast.LENGTH_LONG).show();
                        serviceStart();
                        break;
                    case ACTION_STOP_SERVICE:
                        tmsg = "off";
                        Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                        serviceStop();
                        break;
                }
            }


        }
        else {
            networkMsg();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    private void serviceStop() {
        Log.d(TAG, "Stop foreground service.");
        unsubscribeToTopic(topic);
        // Stop foreground service and remove the notification.
        stopForeground(true);
        // Stop the foreground service.
        stopSelf();

       // disconectMqtt();
    }

    private void networkMsg() {
        try {
            storeFabloAlert.showAlert(context,StoreConstant.ALERT_ERROR,false,"Network Alert","Internet not available, Cross check your internet connectivity and try again",TAG);

        } catch (Exception e) {
            Toast.makeText(this, "Error :" + e, Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){

            return false;
        }
        return true;
    }

    private void unsubscribeToTopic(String topic) {
        try {
            if (!client.isConnected()) {
                client.unsubscribe(topic);
                client.close();
               // Toast.makeText(getApplicationContext(), "Un-Subscribed", Toast.LENGTH_SHORT).show();

            }
            else {
                client.unsubscribe(topic);
                client.disconnect();
                client.close();

            }
        } catch (Exception ignored) {
        }
    }

    private void serviceStart() {
        createchannel();
        connectToBroker();
        Intent notificationIntent = new Intent(this, StoreMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Service")
                .setSmallIcon(R.drawable.fablo_icon)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void connectToBroker() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), serverURL, clientId);

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
                    Toast.makeText(getApplicationContext(), "Failed"+exception, Toast.LENGTH_SHORT).show();
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
                        //  connectionStatus.setText("Connection Failed");\
//                        connectionFlag = false;
//
//                        connectToBroker();//just for retry
                        if(tmsg.equals("on")) {
                            connectionFlag = false;

                            connectToBroker();//just for retry
                        }
                        else{

                            client.close();
                            unsubscribeToTopic(topic);
                        }
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

                        JSONObject msg = new JSONObject(new String(message.getPayload()));
                        String id = msg.getString("resourceId");

                        Toast.makeText(getApplicationContext(), "New Msg  "+ msg.getString("resourceId"), Toast.LENGTH_LONG).show();
                        Intent notificationIntent1 = new Intent(context, StoreMainActivity.class);
                        notificationIntent1.putExtra("resourceId", id);
                        notificationIntent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent1,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setContentTitle("hello")
                                .setContentText("new Order")
                                .setSmallIcon(R.drawable.fablo_icon);



                        builder.setContentIntent(contentIntent);


                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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
    public Notification getNotification(String message) {
        Intent notifyIntent = new Intent(getApplicationContext(), StoreMainActivity.class);
// Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        return new NotificationCompat.Builder(getApplicationContext(), "fabloid")
                .setContentTitle("New Order")
                .setContentText((CharSequence) message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(notifyPendingIntent).build();


    }
    public void showMsg(String title, String description) {
        fabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
    private void createchannel() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "fablo", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }


        }
    }
}
