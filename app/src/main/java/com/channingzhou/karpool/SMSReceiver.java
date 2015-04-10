package com.channingzhou.karpool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Channing on 4/9/2015.
 */
public class SMSReceiver extends BroadcastReceiver {

    /*当收到短信时，就会触发此方法*/
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];
        for (int n = 0; n < messages.length; n++)
        {
            smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
        }

        Boolean yes;
        Boolean no;

        for(int i = 0; i < smsMessage[0].toString().length(); i++){
            if(smsMessage[0].toString().charAt(i) == 'Y' || smsMessage[0].toString().charAt(i) == 'y'){
                if("5556".equals(smsMessage[0].getDisplayOriginatingAddress())){
                    System.out.println(" abort ");
                    abortBroadcast();
                }
                Toast toast = Toast.makeText(context, "Driver said yes, Welcome aboard!", Toast.LENGTH_LONG);
                System.out.println(smsMessage[0]);
                toast.show();
            } else{
                if("5554".equals(smsMessage[0].getDisplayOriginatingAddress())){
                    System.out.println(" abort ");
                    abortBroadcast();
                }
                Toast toast = Toast.makeText(context, "Go and check out other drivers", Toast.LENGTH_LONG);
                System.out.println(smsMessage[0]);
                toast.show();
            }

        }




    }

}
