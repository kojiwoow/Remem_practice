package com.example.user1.remem_practice;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Domik on 12.4.2016.
 */
public class Premium2 extends ActionBarActivity {
 //   Intent intent;
    TextView textView2, textView3, textView4, textView5;
    EditText editText, editText2, editText3, editText4,often,many,Time;
    Button buttonSave, button;
    DatePicker datePicker;
    Intent intent;
    TimePicker tp;
    String time;
    public void onStart() {
        super.onStart();
        EditText txtDate = (EditText) findViewById(R.id.editText4);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                 //   date = ft.toString();
                    dialog.show(ft, "DatePicker");

                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium2);
        tp = (TimePicker)findViewById(R.id.timePicker1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        often = (EditText)findViewById(R.id.often);
        many = (EditText)findViewById(R.id.many);
      //  Time = (EditText)findViewById(R.id.time);
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time = String.valueOf(hourOfDay)+":"+String.valueOf(minute);
            }
        });
      //  OurDateClass now = new OurDateClass(new Date());
      // Time.setText(now.returnTime());

      //  Toast.makeText(Premium2.this,date,Toast.LENGTH_SHORT).show();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = editText4.getText().toString(); //date
                int howOften  = Integer.parseInt(often.getText().toString());
                int numberOfpill = Integer.parseInt(many.getText().toString());


               // String time = Time.getText().toString();
                intent = getIntent();
                String medicine = intent.getStringExtra("Medicine");
                connectionThread connect = new connectionThread(Premium2.this);
                Person person = connect.getPerson();
                boolean check = connect.setNewTimetable(howOften,numberOfpill,Date,time,medicine);
                connect.add(person,check);

                Toast.makeText(Premium2.this, "Medicine added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Premium2.this, TimetableView.class));

            }
        });


    }


}
