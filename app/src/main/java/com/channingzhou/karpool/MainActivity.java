package com.channingzhou.karpool;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class mainActivity extends Activity implements OnClickListener {
    //sign in button
    Button btnLoginDialog;
    //sign up button
    Button btnSignupDialog;
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

                        // Validate Your login credential here than display message
                        Toast.makeText(mainActivity.this,
                                "Login Sucessfull", Toast.LENGTH_LONG).show();

                        // Redirect to dashboard / home screen.
                        login.dismiss();

                        Intent i = new Intent(mainActivity.this, driverAndRider.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(mainActivity.this,
                                "Please enter Username and Password", Toast.LENGTH_LONG).show();

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
                            // Validate Your signup credential here than display message
                            Toast.makeText(mainActivity.this,
                                    "Sign Up Sucessfull", Toast.LENGTH_LONG).show();

                            // Redirect to dashboard / home screen.
                            signup.dismiss();

                            Intent i = new Intent(mainActivity.this, driverAndRider.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(mainActivity.this,
                                    "Password does not match the first one", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(mainActivity.this,
                                "Please enter Username and Password", Toast.LENGTH_LONG).show();

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