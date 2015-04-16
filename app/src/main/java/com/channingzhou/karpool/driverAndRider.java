package com.channingzhou.karpool;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class driverAndRider extends Activity {

    Button btnDriver;
    Button btnRider;
    TextView txtEventInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_rider);

        Toast.makeText(driverAndRider.this,
                "Login Sucessfull", Toast.LENGTH_LONG).show();

        //buttons
        btnDriver = (Button) findViewById(R.id.btnDriver);
        btnRider = (Button) findViewById(R.id.btnRider);

        txtEventInfo = (TextView)findViewById(R.id.txtEventInfo);
        Intent intent = getIntent();
        final String eventInfo = intent.getStringExtra("event");
        final String event_code = intent.getStringExtra("event_code");
        txtEventInfo.setText(eventInfo);


        //rider click event
        btnRider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), riderActivity.class);
                i.putExtra("event_code", event_code);
                startActivity(i);
            }
        });



        //driver click event
        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), driverActivity.class);
                i.putExtra("event_code",event_code);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
