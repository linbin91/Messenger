package com.linbin.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MessengerService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Log.e("linbin","msg.getData()="+msg.getData().toString() );
                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain();
                    replyMessage.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("relpy","i get it");
                    replyMessage.setData(bundle);
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;

            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());
}
