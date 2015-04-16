package com.channingzhou.karpool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class createEvent extends Activity {

    //progress dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    EditText createEventName;
    EditText createEventDestZip;
    EditText createEventCreator;

    Button saveEvent;

    private static final String url_create_event = "http://192.168.1.81/karpool/createEvent.php";

    //JSON node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        createEventName = (EditText) findViewById(R.id.createEventName);
        createEventDestZip = (EditText) findViewById(R.id.createEventDestZip);
        createEventCreator = (EditText) findViewById(R.id.eventCreator);

        Button saveEvent = (Button) findViewById(R.id.saveEvent);

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register new rider
                new createNewEvent().execute();
            }
        });
    }

    class createNewEvent extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            String eventName = createEventName.getText().toString();
            String eventDestZip = createEventDestZip.getText().toString();
            String eventCreator = createEventCreator.getText().toString();
            //random number for event code
            Random rnd = new Random();
            int n = 1000 + rnd.nextInt(9000);
            String eventCode = String.valueOf(n);

            //building params
            List<NameValuePair> paramsEvent = new ArrayList<NameValuePair>();
            paramsEvent.add(new BasicNameValuePair("event_name", eventName));
            paramsEvent.add(new BasicNameValuePair("event_code", eventCode));
            paramsEvent.add(new BasicNameValuePair("dest_zip", eventDestZip));
            paramsEvent.add(new BasicNameValuePair("creator", eventCreator));

            System.out.println(paramsEvent);
            //getting JSON object
            JSONObject jsonCreateEvent = jsonParser.makeHttpRequest(url_create_event, "POST", paramsEvent);

            //check JSON object
            Log.d("Create Response", jsonCreateEvent.toString());

            try{
                int success = jsonCreateEvent.getInt(TAG_SUCCESS);
                if (success == 1) {
                    //successfully created rider
                    Intent i = new Intent(getApplicationContext(),driverAndRider.class);
                    i.putExtra("event","Event name: " + eventName + ", Event code: " + eventCode);
                    i.putExtra("event_code",eventCode);
                    startActivity(i);
                    //closing
                    finish();
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
