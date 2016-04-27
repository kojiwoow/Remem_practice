package com.example.user1.remem_practice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by user1 on 2016-03-30.
 */
public class Register_one extends Activity{
    Button Next;
    EditText Email,Password,confirmPassword;
    private Socket client;
    private PrintWriter printWriter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Next = (Button)findViewById(R.id.next);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.Password1);
        confirmPassword = (EditText)findViewById(R.id.Password2);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String first_password = Password.getText().toString();
                String second_password = confirmPassword.getText().toString();
                if(email.isEmpty() || first_password.isEmpty() || second_password.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register_one.this);
                    builder.setMessage("Fill all blank!");
                    builder.setTitle("WARNING!");
                    builder.setIcon(R.drawable.logo);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                else if(!(first_password.equals(second_password))){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register_one.this);
                    builder.setMessage("Not same password!!");
                    builder.setTitle("WARNING!");
                    builder.setIcon(R.drawable.logo);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    String e_mail = Email.getText().toString();
                    String password = Password.getText().toString();
                    connectionThread connection = new connectionThread(Register_one.this);
                    connection.Register(e_mail, password);
                    startActivity(new Intent(getBaseContext(),Register_two.class));
                }
            }
        });
    }
}
