package com.example.user1.remem_practice;

/**
 * Created by user1 on 2016-04-12.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.net.Socket;
import java.util.Calendar;

public class Timetable extends Activity {
    connectionThread getInstance;
    Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
  /*      connectionThread connection = new connectionThread(this);
        try {
            socket = connection.getSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

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