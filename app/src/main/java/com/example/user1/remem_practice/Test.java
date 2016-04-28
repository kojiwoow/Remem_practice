package com.example.user1.remem_practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by user1 on 2016-04-25.
 */
public class Test extends Activity {
    PersonLocalStore personLocalStore;
    Person person;
    ContactPersonLocalStore contactStore;
    Person ContactPerson;
    MedicineLocalStore medicineLocalStore;
    medicine Medicine;

    Intent intent;
    Button button1,button2,button3,button4,button5;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.logout);
        button5 = (Button)findViewById(R.id.medicine);
        intent = getIntent();
        personLocalStore = new PersonLocalStore(this);
        person = personLocalStore.getLoggedInUser();

        contactStore = new ContactPersonLocalStore(this);
        ContactPerson = contactStore.getLoggedInUser();

        medicineLocalStore = new MedicineLocalStore(this);
        Medicine = medicineLocalStore.getLoggedInUser();



        String email = intent.getStringExtra("email");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Test.this, person.returnName() + " " +person.returnSex() + " " + person.returnSex(), Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Test.this, ContactPerson.returnName() + " " + ContactPerson.returnID() + " " + ContactPerson.returnSex(), Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionThread connect = new connectionThread(Test.this);
                person = connect.getPerson();
               // Toast.makeText(Test.this, person.returnArrayListOfTimetables().get(0).returnActive(),Toast.LENGTH_LONG).show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personLocalStore.clearUserData();
                startActivity(new Intent(Test.this, MainActivity.class));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Test.this,ListviewTest_.class));
            }
        });
    }

}
