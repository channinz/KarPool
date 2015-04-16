package com.channingzhou.karpool;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class event extends Activity implements View.OnClickListener {
    Button allEvents;
    Button createEvent;
    EditText existEventCode;
    TextView result;

    JSONParser jParserEvent = new JSONParser();

    private static String url_all_events = "http://192.168.1.81/karpool/getAllEvents.php";

    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ID = "id";
    private static final String TAG_EVENT = "event";
    private static final String TAG_EVENTCODE = "event_code";
    private static final String TAG_DESTZIP = "dest_zip";
    private static final String TAG_CREATOR = "creator";

    //event array
    JSONArray events = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        allEvents = (Button) findViewById(R.id.searchEvent);
        allEvents.setOnClickListener(this);

        createEvent = (Button) findViewById(R.id.createEvent);
        createEvent.setOnClickListener(this);

        existEventCode = (EditText) findViewById(R.id.editTextEventCode);

    }

    public void onClick(View v){
        if(v == allEvents){

            new searchEvents().execute();

        }else if(v == createEvent){
           Intent i = new Intent(event.this, createEvent.class);
            startActivity(i);
        }
    }

    class searchEvents extends AsyncTask<String, String, String>  {

        @Override
        protected String doInBackground(String... args) {
            //building parameters
            List<NameValuePair> paramsGetEvent = new ArrayList<NameValuePair>();
            //getting JSON string from URL
            paramsGetEvent.add(new BasicNameValuePair("event_code",
                    existEventCode.getText().toString()));
            JSONObject jsonGetEvent = jParserEvent.makeHttpRequest(url_all_events, "GET", paramsGetEvent);
            System.out.println(paramsGetEvent);
            System.out.println(jsonGetEvent);

            //Check log cat for JSON response
            Log.d("Event: ", jsonGetEvent.toString());
            try {
                //check for SUCCESS TAG
                int success = jsonGetEvent.getInt(TAG_SUCCESS);
                if (success == 1) {
                    //drivers found
                    //getting array of drivers
                    events = jsonGetEvent.getJSONArray(TAG_EVENT);
                    //looping through all drivers
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject c = events.getJSONObject(i);

                        //store each JSON item in variable
                        String id = c.getString(TAG_ID);
                        String eventcode = c.getString(TAG_EVENTCODE);
                        String eventname = c.getString("event_name");
                        String destzip = c.getString(TAG_DESTZIP);
                        String creator = c.getString(TAG_CREATOR);

                        //if (existEventCode.getText().toString().equals(eventcode)){
                            String eventInfo = "Event name: " + eventname +
                                    ", Event code: " + eventcode + ", Destination's zip code: " +
                                    destzip + ", Creator: " + creator + "   Send the Event Code to the people you want to invite!!";
                            System.out.println(eventInfo);
                            Intent in = new Intent(event.this, driverAndRider.class);
                            in.putExtra("event", eventInfo);
                            in.putExtra("event_code", eventcode);
                            startActivity(in);
                       // }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
