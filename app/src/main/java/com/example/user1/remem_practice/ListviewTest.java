package com.example.user1.remem_practice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by user1 on 2016-04-26.
 */
@EActivity
public class ListviewTest extends Activity {
    ListView mainListView ;
    ArrayAdapter listAdapter ;
    String[] days;
    EditText mEdit;
    Button mSearch,mNext;
    ArrayList<String> medList;
    String tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_medicine);

        mEdit = (EditText)findViewById(R.id.editText1);
        mSearch = (Button)findViewById(R.id.button1);
        mNext = (Button)findViewById(R.id.button2);
        mainListView = (ListView) findViewById( R.id.listView);


        final Context context = this;
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionThread connect = new connectionThread(context);
                medList = new ArrayList<String>();
                String search = mEdit.getText().toString();
                connect.searchMedicine(search, new ClientAnswersToServer.OnSearchMedicineResponse() {
                    @Override
                    public void onResponse(String[] MedicineList) {
                        setMedicineList(MedicineList);
                    }
                });
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),tv+" added in Contact Person",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ListviewTest.this,Premium2.class);
                intent.putExtra("Medicine",tv);
                startActivity(intent);
            }
        });
    }

    @UiThread
    public void setMedicineList(String[] MedicineList) {
        medList.addAll(Arrays.asList(MedicineList));
        listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, R.id.rowTextView, medList);
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(mItemClickListener);

    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long l_position) {

              tv = (String)parent.getAdapter().getItem(position);
              mEdit.setText(tv);
        }
    };
}


class CustomArrayAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public CustomArrayAdapter(Context c, int textViewResourceId,
                              String[] strings) {
        super(c, textViewResourceId, strings);
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View v = convertview;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_medicine, null);
            viewHolder.tv_title = (TextView) v.findViewById(R.id.rowTextView);

            v.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.tv_title.setText(getItem(position));

        return v;
    }
}

class ViewHolder {
    public TextView tv_title;
}


