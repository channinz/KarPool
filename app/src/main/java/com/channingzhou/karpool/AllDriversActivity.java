package com.channingzhou.karpool;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AllDriversActivity extends ListActivity {

    //progress dialog
    private ProgressDialog pDialog;

    //creating JSON parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> driversList;

    //url to get all drivers list
    private static String url_all_drivers = "http://192.168.0.21/karpool/getAllDrivers.php";

    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DRIVERS = "name";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    //drivers JSONArray
    JSONArray drivers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_drivers);

        //Hashmap for ListView
        driversList = new ArrayList<HashMap<String, String>>();

        //loading drivers in background thread
        new LoadAllDrivers().execute();

        //get listview
        ListView lv = getListView();

        //on selecting single driver
        //launching SEND SMS SCREEN
        /*lv.setOnClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //getting values from selected listitem
                String pid = ((TextView) view.findViewById(R.id.id)).getText().toString();

                //starting new intent to send SMS
                Intent in = new Intent(getApplicationContext(),
                        EditProductActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_PID, pid);


                //starting new activity and expecting some response back
                //startActivityForResult(in, 100);
            }
        });*/
    }

    //for SMS sending
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    }*/

    class LoadAllDrivers extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllDriversActivity.this);
            pDialog.setMessage("Loading drivers. Please wait...");
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
            JSONObject json = jParser.makeHttpRequest(url_all_drivers, "GET", params);

            //Check log cat for JSON response
            Log.d("All Drivers: ", json.toString());
            try{
                //check for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if(success == 1){
                    //drivers found
                    //getting array of drivers
                    drivers = json.getJSONArray(TAG_NAME);

                    //looping through all drivers
                    for (int i = 0; i < drivers.length(); i++){
                        JSONObject c = drivers.getJSONObject(i);

                        //store each JSON item in variable
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);

                        //create new hashmap
                        HashMap<String, String> map = new HashMap<String, String>();

                        //add each child node to hashmap key=> key
                        map.put(TAG_ID, id);
                        map.put(TAG_NAME, name);

                        //adding hashlist to arrayList
                        driversList.add(map);
                    }
                } else {
                    //no driver found
                    /*pDialog = new ProgressDialog(AllDriversActivity.this);
                    pDialog.setMessage("no driver available yet...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();*/
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        /*
        * after all, dismiss the progress dialog
        * */
        protected  void onPostExecute(String file_url){
            //dismiss the dialog
            pDialog.dismiss();
            //update UI from background thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /*
                    * update parsed JSON data into ListView
                    * */

                    ListAdapter adapter = new SimpleAdapter(
                            AllDriversActivity.this, driversList, R.layout.list_item, new String[]{TAG_ID, TAG_NAME},
                            new int[] {R.id.id, R.id.drivername});
                    //update listview
                    setListAdapter(adapter);

                }
            });
        }

     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_drivers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
