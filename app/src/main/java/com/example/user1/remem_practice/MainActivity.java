package com.example.user1.remem_practice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        final EditText email,password;
        Button login,signIn;
        Context context;
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.Password);
        login = (Button)findViewById(R.id.login_button);
        signIn = (Button)findViewById(R.id.sign_in_button);
        final connectionThread connection = new connectionThread(MainActivity.this);
        connection.execute();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String E_mail  = email.getText().toString();
                    String Password = password.getText().toString();

                    connection.SignIn(E_mail,Password);
                    Intent intent = new Intent(MainActivity.this,Timetable.class);
                     startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,Register_one.class));

            }
        });
    }


    private void showErrorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Incorrect user details");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

}
