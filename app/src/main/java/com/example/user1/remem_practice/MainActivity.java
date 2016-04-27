package com.example.user1.remem_practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    PersonLocalStore personLocalStore;
    Person person;

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
        ClientAnswersToServer.setContext(getApplicationContext());
        connectionThread.setContext(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personLocalStore = new PersonLocalStore(MainActivity.this);
                String E_mail = email.getText().toString();
                String Password = password.getText().toString();
                connection.SignIn(E_mail, Password);
                Intent intent = new Intent(MainActivity.this, Test.class);
                intent.putExtra("email", E_mail);
                person = personLocalStore.getLoggedInUser();
                if(person.returnID() == 30)
                startActivity(intent);
                else
                    Toast.makeText(MainActivity.this,"Account Incorrect",Toast.LENGTH_SHORT).show();

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, Register_one.class));

            }
        });
    }



}
