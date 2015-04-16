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

/**
 * Created by Channing on 4/6/2015.
 */
public class riderActivity extends Activity {
    //progress dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText riderName;
    EditText riderCell;

    //url to create new rider
    private static String url_create_rider = "http://192.168.1.81/karpool/riderRegister.php";

    //JSON node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        //edit text
        riderName = (EditText) findViewById(R.id.etxRiderName);
        riderCell = (EditText) findViewById(R.id.etxRiderCell);

        //save button
        Button btnCreateRider = (Button) findViewById(R.id.btnSubmit);

        btnCreateRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register new rider
                new RegisterNewRider().execute();
            }
        });
    }

    //register new rider
    class RegisterNewRider extends AsyncTask<String, String, String>{
        //register new rider
        protected String doInBackground(String... args) {
            String name = riderName.getText().toString();
            String cell = riderCell.getText().toString();

            //building parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("rider_name", name));
            params.add(new BasicNameValuePair("rider_cell", cell));

            //getting JSON object
            JSONObject json = jsonParser.makeHttpRequest(url_create_rider, "POST", params);

            //check log cat for response
            Log.d("Create Response", json.toString());

            //check for success tag
            try{
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = getIntent();
                    String event_code = intent.getStringExtra("event_code");
                    //successfully created rider
                    Intent i = new Intent(getApplicationContext(),AllDriversActivity.class);

                    i.putExtra("name", riderName.getText().toString());
                    i.putExtra("cell", riderCell.getText().toString());
                    i.putExtra("event_code",event_code);
                    System.out.print("rider: "+name);
                    System.out.println(" " + cell);

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

