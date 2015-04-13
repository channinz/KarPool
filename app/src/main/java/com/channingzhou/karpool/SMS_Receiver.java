package com.channingzhou.karpool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMS_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("收到短信");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Object[] pdus=(Object[])intent.getExtras().get("pdus");

            SmsMessage[] message=new SmsMessage[pdus.length];
            StringBuilder sb=new StringBuilder();
            System.out.println("pdus长度"+pdus.length);
            for(int i=0;i<pdus.length;i++){
                //虽然是循环，其实pdus长度一般都是1
                message[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                sb.append("接收到短信来自:\n");
                sb.append(message[i].getDisplayOriginatingAddress()+"\n");
                sb.append("内容:"+message[i].getDisplayMessageBody());

                if(message[i].getDisplayOriginatingAddress().equals("15555215556")){
                    Toast toast = Toast.makeText(context, "Driver said yes, Welcome aboard!", Toast.LENGTH_LONG);
                    //System.out.println(smsMessage[0]);
                    toast.show();
                }

            }
            System.out.println(sb.toString());
        }
    }

}
