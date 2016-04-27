package com.example.user1.remem_practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by user1 on 2016-04-26.
 */
public class ListviewTest extends Activity {
    ListView mainListView ;
    ArrayAdapter listAdapter ;
    String[] days;
    EditText mEdit;
    Button mSearch,mNext;
    ArrayList<String> medList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_medicine);

        mEdit = (EditText)findViewById(R.id.editText1);
        mSearch = (Button)findViewById(R.id.button1);
        mNext = (Button)findViewById(R.id.button2);
        mainListView = (ListView) findViewById( R.id.listView);



        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionThread connect = new connectionThread(ListviewTest.this);
                medList = new ArrayList<String>();
                String search = mEdit.getText().toString();
                connect.searchMedicine(search);
                days = connect.getMedicine();
                medList.addAll(Arrays.asList(days));
                listAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerow,R.id.rowTextView, medList);
                mainListView.setAdapter(listAdapter);
            }
        });
    }
}

