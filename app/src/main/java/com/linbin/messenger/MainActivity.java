package com.linbin.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private  MessengerConnect mMessengerConnect;
    private Messenger mServiceMessenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessengerConnect = new MessengerConnect();
        Intent intent = new Intent(this,MessengerService.class);
        startService(intent);
        bindService(intent,mMessengerConnect,BIND_IMPORTANT);

    }

    private class MessengerConnect implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceMessenger = new Messenger(service);
            Message msg = Message.obtain();
            Bundle data = new Bundle();
            msg.what = 0;
            data.putString("msg","hello, world");
            msg.replyTo =mMessenger;
            msg.setData(data);

            try {
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.e("linbin","msg.getData()="+msg.getData().toString() );

                    break;
                default:
                    super.handleMessage(msg);
                    break;

            }
        }
    }
}
