package com.channingzhou.karpool;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class allEvents extends ListActivity {
    //progress dialog
    private ProgressDialog pDialog;

    //create JSON parser project
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> eventsList;

    private static String url_all_events = "http://192.168.0.21/karpool/getAllEvents.php";

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
        setContentView(R.layout.activity_all_events);


        //hash map for list view
        eventsList = new ArrayList<HashMap<String, String>>();

        //loading events
        new LoadAllEvents().execute();

        //get list view
        ListView lv = getListView();

        //on selecting single event
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(getApplicationContext(), driverAndRider.class);

                startActivity(in);
                Toast.makeText(allEvents.this, "Event selected!",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    class LoadAllEvents extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(allEvents.this);
            pDialog.setMessage("Loading Events. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /*
        * getting all drivers from url
        * */
        protected String doInBackground(String... args) {
            //building parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_events, "GET", params);

            //Check log cat for JSON response
            Log.d("All Events: ", json.toString());
            try {
                //check for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    //drivers found
                    //getting array of drivers
                    events = json.getJSONArray(TAG_EVENT);

                    //looping through all drivers
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject c = events.getJSONObject(i);

                        //store each JSON item in variable
                        String id = c.getString(TAG_ID);
                        String eventcode = c.getString(TAG_EVENTCODE);
                        String destzip = c.getString(TAG_DESTZIP);
                        String creator = c.getString(TAG_CREATOR);

                        //create new hashmap
                        HashMap<String, String> map = new HashMap<String, String>();

                        //add each child node to hashmap key=> key
                        map.put(TAG_ID, id);
                        map.put(TAG_EVENTCODE, eventcode);
                        map.put(TAG_DESTZIP, destzip);
                        map.put(TAG_CREATOR, creator);

                        //adding hashlist to arrayList
                        eventsList.add(map);
                    }
                } else {
                    //no event found
                    /*pDialog = new ProgressDialog(AllDriversActivity.this);
                    pDialog.setMessage("no driver available yet...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    protected  void onPostExecute(String file_url){
        //dismiss the dialog
        pDialog.dismiss();

        //final String temp_seat = "Available seats: " + TAG_SEAT;
        //update UI from background thread
        runOnUiThread(new Runnable() {
            public void run() {
                    /*
                    * update parsed JSON data into ListView
                    * */

                /*ListAdapter adapter = new SimpleAdapter(
                        allEvents.this, eventsList, R.layout.event_item, new String[]{TAG_ID, TAG_EVENT, TAG_DESTZIP, TAG_CREATOR},
                        new int[] {R.id.event_id, R.id.event_code, R.id.destination, R.id.creator});
                //update listview
                setListAdapter(adapter);*/

            }
        });
    }

}