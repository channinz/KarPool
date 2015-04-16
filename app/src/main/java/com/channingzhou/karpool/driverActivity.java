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


public class driverActivity extends Activity {
    //progress dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText driverName;
    EditText driverCell;
    EditText avaSeats;
    EditText carType;



    //url to create new rider
    private static String url_create_driver = "http://192.168.1.81/karpool/driverRegister.php";

    //JSON node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        //edit text
        driverName = (EditText) findViewById(R.id.etxDriverName);
        driverCell = (EditText) findViewById(R.id.etxDriverCell);
        avaSeats = (EditText) findViewById(R.id.etxAvaSeats);
        carType = (EditText) findViewById(R.id.etxCarType);

        //save button
        Button btnCreateDriver = (Button) findViewById(R.id.save_driver);


        btnCreateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register new rider
                new RegisterNewDriver().execute();
            }
        });
    }

    //register new rider
    class RegisterNewDriver extends AsyncTask<String, String, String> {

        //register new rider
        protected String doInBackground(String... args) {
            String name = driverName.getText().toString();
            String cell = driverCell.getText().toString();
            String seats = avaSeats.getText().toString();
            String car = carType.getText().toString();

            Intent intent = getIntent();
            String event_code = intent.getStringExtra("event_code");
            System.out.println("event_code" + event_code);
            //building parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("cellphone", cell));
            params.add(new BasicNameValuePair("car_type", car));
            params.add(new BasicNameValuePair("ava_seats", seats));
            params.add(new BasicNameValuePair("event", event_code));

            //getting JSON object
            JSONObject json = jsonParser.makeHttpRequest(url_create_driver, "POST", params);

            //check log cat for response
            Log.d("Create Driver", json.toString());

            //check for success tag
            try{
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    //successfully created rider
                    Intent i = new Intent(getApplicationContext(),driverAndRider.class);
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
