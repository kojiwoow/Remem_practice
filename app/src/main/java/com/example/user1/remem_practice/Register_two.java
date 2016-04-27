package com.example.user1.remem_practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user1 on 2016-03-30.
 */
public class Register_two extends Activity implements View.OnClickListener {
    EditText Name, Surname, PhoneNumber;
    Button mButton;

    private String sex = "Male";
    private String name;
    private String surname;
    private String phoneNumber;
//    CommandsForServerCommunication commands = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        Name = (EditText) findViewById(R.id.editText);
        Surname = (EditText) findViewById(R.id.editText2);
        PhoneNumber = (EditText) findViewById(R.id.editText3);
        mButton = (Button) findViewById(R.id.buttonNext);



        mButton.setOnClickListener(this);
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonNext:
                name = Name.getText().toString();
                surname = Surname.getText().toString();
                phoneNumber = PhoneNumber.getText().toString();
                connectionThread connection = new connectionThread(Register_two.this);
                connection.Register_two(name,surname,sex,phoneNumber);
                Toast.makeText(Register_two.this,"LogIn Again",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                break;
        }
    }
}