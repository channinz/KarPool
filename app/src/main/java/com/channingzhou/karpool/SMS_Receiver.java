package com.channingzhou.karpool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMS_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent inte) {
        System.out.println("receive");
        System.out.println(inte);
        if(inte.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Object[] pdus=(Object[])inte.getExtras().get("pdus");

            SmsMessage[] message=new SmsMessage[pdus.length];
            StringBuilder sb=new StringBuilder();
            System.out.println("pdus's length"+pdus.length);
            for(int i=0;i<pdus.length;i++){
                //the length of pdus usually is 1
                message[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                sb.append("get a message:\n");
                sb.append(message[i].getDisplayOriginatingAddress()+"\n");
                sb.append("content:"+message[i].getDisplayMessageBody());


                /*String sender = inte.getStringExtra("sender");
                sender = sender.substring(sender.length()-5,sender.length()-1);*/

                if((message[i].getDisplayOriginatingAddress().equals("15555215556") )){
                    if(message[i].getDisplayMessageBody().equals("y")||message[i].getDisplayMessageBody().equals("Y")) {
                        Toast toast = Toast.makeText(context, "Driver said yes, Welcome aboard!", Toast.LENGTH_LONG);
                        //System.out.println(smsMessage[0]);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(context, "Too bad, this driver is not available.", Toast.LENGTH_LONG);
                        //System.out.println(smsMessage[0]);
                        toast.show();
                    }
                }
                /*if(true){
                    Toast toast = Toast.makeText(context, "Driver said yes, Welcome aboard!", Toast.LENGTH_LONG);
                    //System.out.println(smsMessage[0]);
                    toast.show();
                }*/
            }
            System.out.println(sb.toString());
        }
    }

}
