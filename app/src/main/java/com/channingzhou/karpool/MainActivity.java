package com.channingzhou.karpool;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class mainActivity extends Activity implements OnClickListener {
    //sign in button
    Button btnLoginDialog;
    //sign up button
    Button btnSignupDialog;

    //progress Dialog
    private ProgressDialog pDialog;

    private static String url_create_user = "http://192.168.0.21/karpool/userRegister.php";
    private static String url_login_user = "http://192.168.0.21/karpool/userLogin.php";

    JSONParser jsonParser = new JSONParser();

    //JSON node names
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.activity_main);

        btnLoginDialog = (Button) findViewById(R.id.btnLoginDialog);
        btnLoginDialog.setOnClickListener(this);

        btnSignupDialog = (Button) findViewById(R.id.btnSignupDialog);
        btnSignupDialog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnLoginDialog) {

            // Create Object of Dialog class
            final Dialog login = new Dialog(this);
            // Set GUI of login screen
            login.setContentView(R.layout.activity_login);
            login.setTitle("Sign into KarPool");

            // Init button of login GUI
            Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
            Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
            final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
            final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);





            // Attached listener for login GUI button
            btnLogin.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0)
                    {
                        //validate the login info

                        new userLogin().execute();

                        /*Intent i = new Intent(mainActivity.this, driverAndRider.class);
                        startActivity(i);

                        Toast.makeText(mainActivity.this,
                                "Login Sucessfull", Toast.LENGTH_LONG).show();*/


                        // Redirect to dashboard / home screen.
                        login.dismiss();
                    }
                    else
                    {
                        Toast.makeText(mainActivity.this,
                                "Please enter Username and Password", Toast.LENGTH_LONG).show();

                    }
                }

                class userLogin extends AsyncTask<String, String, String>{
                    @Override
                    protected String doInBackground(String... args) {

                        String username = txtUsername.getText().toString();
                        String password = txtPassword.getText().toString();

                        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                        params1.add(new BasicNameValuePair("user_name", username));

                        //get the same user name's info
                        JSONObject json = jsonParser.makeHttpRequest(url_login_user, "GET", params1);
                        

                        //check the json respones
                        Log.d("Create Response", json.toString());

                        //json success tag
                        try{
                            int success = json.getInt(TAG_SUCCESS);
                            if(success == 1) {
                                //successfully received user info
                                JSONArray userInfoObj = json.getJSONArray("user_name");

                                JSONObject userInfo = userInfoObj.getJSONObject(0);

                                String realPassword = userInfo.getString("password");

                                if (realPassword.equals(password)){

                                    // Validate login credential here than display message
                                    Toast.makeText(mainActivity.this,
                                            "Login Sucessfull", Toast.LENGTH_LONG).show();

                                    Intent i = new Intent(mainActivity.this, driverAndRider.class);
                                    startActivity(i);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        return null;
                    }
                }
            });
            btnCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    login.dismiss();
                }
            });

            // Make dialog box visible.
            login.show();
        }

        if(v == btnSignupDialog){

            // Create Object of Dialog class
            final Dialog signup = new Dialog(this);
            // Set GUI of signup screen
            signup.setContentView(R.layout.activity_signup);
            signup.setTitle("Signup to KarPool");

            // Init button of signup GUI
            Button btnSignup = (Button) signup.findViewById(R.id.btnSignup);
            Button btnCancel = (Button) signup.findViewById(R.id.btnCancel);
            final EditText txtUsername = (EditText)signup.findViewById(R.id.txtUsername);
            final EditText txtPassword = (EditText)signup.findViewById(R.id.txtPassword);
            final EditText txtPassword2 = (EditText)signup.findViewById(R.id.txtPassword2);

            // Attached listener for signup GUI button
            btnSignup.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0) {

                        if (txtPassword.getText().toString().equals(txtPassword2.getText().toString())) {
                            //update user table
                            new RegisterNewUser().execute();

                            Intent i = new Intent(mainActivity.this, driverAndRider.class);
                            startActivity(i);

                            Toast.makeText(mainActivity.this,
                                    "Sign Up Sucessfull", Toast.LENGTH_LONG).show();



                            // Redirect to dashboard / home screen.
                            signup.dismiss();



                        } else {
                            Toast.makeText(mainActivity.this,
                                    "Password does not match the first one", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(mainActivity.this,
                                "Please enter Username and Password", Toast.LENGTH_LONG).show();

                    }
                }

                class RegisterNewUser extends AsyncTask<String, String, String> {
                    @Override
                    protected String doInBackground(String... args) {
                        String username = txtUsername.getText().toString();
                        String password = txtPassword.getText().toString();

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("user_name", username));
                        params.add(new BasicNameValuePair("password", password));

                        JSONObject json = jsonParser.makeHttpRequest(url_create_user, "POST", params);

                        Log.d("Create Response", json.toString());

                        try{
                            int success = json.getInt(TAG_SUCCESS);
                            if(success == 1){

                                /*Intent i = new Intent(mainActivity.this, driverAndRider.class);
                                startActivity(i);*/

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }
            });
            btnCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    signup.dismiss();
                }
            });

            // Make dialog box visible.
            signup.show();
        }

    }



///////////////////////////////////////////////////////////////////////////////
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_with_login, menu);
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
    }*/
}