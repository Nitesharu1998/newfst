package com.example.trial_fst0763;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SmsBroadCastReceiver extends BroadcastReceiver {
    public SmsBroadcastinterface smsBroadcastinterface;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == SmsRetriever.SMS_RETRIEVED_ACTION) {
            Bundle bundle = intent.getExtras();
            Status smsRetrieveStatus = (Status) bundle.get(SmsRetriever.EXTRA_STATUS);
            switch (smsRetrieveStatus.getStatusCode()) {

                case CommonStatusCodes
                        .SUCCESS:
                    Intent messageIntent = bundle.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                    smsBroadcastinterface.onSuccess(messageIntent);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    smsBroadcastinterface.onFailure();
                    break;

            }
        }
    }


    public interface SmsBroadcastinterface {

        void onSuccess(Intent intent);

        void onFailure();
    }
}


