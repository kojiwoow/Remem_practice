package com.example.user1.remem_practice;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by Domik on 15.4.2016.
 */
public class TimetableView extends ActionBarActivity {
    TextView showEvents;
    Toolbar toolbar;
    PersonLocalStore personLocalStore;
    Person person;
    ContactPersonLocalStore contactStore;
    Person ContactPerson;
    MedicineLocalStore medicineLocalStore;
    medicine Medicine;
    String  email;
    Intent intent;

    public GregorianCalendar month, itemmonth;// calendar instances.

    public TimetableAdapter adapter;// adapter instance
    public Handler handler;// for grabbing some event values for showing the dot marker.
    public ArrayList<String> items; // container to store calendar items which needs showing the event marker


    ArrayList<String> event;
    LinearLayout rLayout;
    ArrayList<String> date;
    ArrayList<String> desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
//        Locale.setDefault(Locale.ENGLISH);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        intent = getIntent();
        personLocalStore = new PersonLocalStore(this);
        person = personLocalStore.getLoggedInUser();

        contactStore = new ContactPersonLocalStore(this);
        ContactPerson = contactStore.getLoggedInUser();

        medicineLocalStore = new MedicineLocalStore(this);
        Medicine = medicineLocalStore.getLoggedInUser();

        connectionThread connect = new connectionThread(TimetableView.this);
        person = connect.getPerson();
        connect.MonitorNotifications(person);

       email = intent.getStringExtra("email");

        rLayout = (LinearLayout) findViewById(R.id.text);
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        itemmonth = (GregorianCalendar) month.clone();

        items = new ArrayList<String>();

        adapter = new TimetableAdapter(this, month);


        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        handler = new Handler();
        handler.post(calendarUpdater);


        TextView title = (TextView) findViewById(R.id.title);
        showEvents = (TextView) findViewById(R.id.showEvents);

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));


        RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });


        RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });


        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // removing the previous view if added
                if (((LinearLayout) rLayout).getChildCount() > 0) {
                    ((LinearLayout) rLayout).removeAllViews();
                }
                desc = new ArrayList<String>();
                date = new ArrayList<String>();

                //date.add("Event");
                ((TimetableAdapter) parent.getAdapter()).setSelected(v);
                String selectedGridDate = TimetableAdapter.dayString.get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*", "");
                // taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);

                // navigate to next or previous month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((TimetableAdapter) parent.getAdapter()).setSelected(v);

                for (int i = 0; i < Timetable.startDates.size(); i++) {
                    if (Timetable.startDates.get(i).equals(selectedGridDate)) {
                        desc.add(Timetable.nameOfEvent.get(i));
                    }
                }

                if (desc.size() > 0) {
                    for (int i = 0; i < desc.size(); i++) {
                        TextView rowTextView = new TextView(TimetableView.this);

                        // set some properties of rowTextView or something
                        rowTextView.setText("Event:" + desc.get(i));
                        rowTextView.setTextColor(Color.BLACK);

                        // add the textview to the linearlayout
                        rLayout.addView(rowTextView);

                    }

                }

                desc = null;

            }

        });
    }


    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar() {
        TextView title = (TextView) findViewById(R.id.title);

        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }


    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String itemvalue;
            event = Timetable.readCalendarEvent(TimetableView.this);
            // showEvents.setText("=====Event===="+event.toString()+"\n"+"=====Date ARRAY===="+Timetable.startDates.toString()+"\n");
            Log.d("=====Event====", event.toString());
            Log.d("=====Date ARRAY====", Timetable.startDates.toString());

            for (int i = 0; i < Timetable.startDates.size(); i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                items.add(Timetable.startDates.get(i).toString());
            }
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    };


   /* public String addEvent(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();
        long endTime = cal.getTimeInMillis() + 60 + 60 + 1000;

        intent.putExtra(EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(EXTRA_EVENT_ALL_DAY, true);

        intent.putExtra(Events.TITLE, "Title");
        intent.putExtra(Events.DESCRIPTION, "Description");
        intent.putExtra(Events.EVENT_LOCATION, "Location");
        intent.putExtra(Events.RRULE, "FREQ=YEARLY");

        intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
        intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);

        startActivity(intent);
        return Events.TITLE;

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timetableAB:
                startActivity(new Intent(TimetableView.this, TimetableView.class));
                return true;

            case R.id.logout:
                Toast.makeText(TimetableView.this,"Log out safely",Toast.LENGTH_LONG).show();
                personLocalStore.clearUserData();
                startActivity(new Intent(TimetableView.this, MainActivity.class));
                return true;

            case R.id.profile:
                Intent intent = new Intent(TimetableView.this, Profile.class);
                intent.putExtra("email",email);
                startActivity(intent);
                return true;

            case R.id.addmedicine:
                startActivity(new Intent(TimetableView.this,ListviewTest_.class));

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void createNotification() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Time to take a pill!! ")
                .setContentText("Pill Alert").setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }

}
