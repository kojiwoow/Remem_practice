package com.example.user1.remem_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Domik on 12.4.2016.
 */
public class Premium1 extends ActionBarActivity {

    TextView textView2, textView3, textView4, textView5;
    RadioButton radioButton1, radioButton2;
    EditText editText, editText2, editText3;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        buttonSave = (Button) findViewById(R.id.buttonNext);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Premium1.this,ContactPerson.class));
            }
        });
    }


    public void eventClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton1:
                editText.setBackgroundResource(R.color.Blue);
                editText2.setBackgroundResource(R.color.Blue);
                editText3.setBackgroundResource(R.color.Blue);
                break;
            case R.id.radioButton2:
                editText.setBackgroundResource(R.color.Red);
                editText2.setBackgroundResource(R.color.Red);
                editText3.setBackgroundResource(R.color.Red);
                break;
        }
    }
}
