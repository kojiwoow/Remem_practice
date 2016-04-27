package com.example.user1.remem_practice;

/**
 * Created by user1 on 2016-04-12.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class Timetable extends Activity {

    PersonLocalStore personLocalStore;
    Person person;
    ContactPersonLocalStore contactStore;
    Person ContactPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        personLocalStore = new PersonLocalStore(this);
        person = personLocalStore.getLoggedInUser();

        contactStore = new ContactPersonLocalStore(this);
        ContactPerson = contactStore.getLoggedInUser();
        Toast.makeText(this,person.returnName()+" "+this.person.returnID()+" "+this.person.returnSex(),Toast.LENGTH_SHORT).show();
       // returnUser = new PersonLocalStore(this);

    }

    public void onAddEventClicked(View view) {
        Calendar onAddEventClicked = Calendar.getInstance();
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", onAddEventClicked.getTimeInMillis());
        i.putExtra("allDay", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra("endTime", onAddEventClicked.getTimeInMillis() + 60 * 60 * 1000);
        i.putExtra("title", "Sample Calender Event Android Application");
        startActivity(i);
    }
}