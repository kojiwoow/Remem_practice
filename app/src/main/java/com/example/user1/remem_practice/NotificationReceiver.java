package com.example.user1.remem_practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by user1 on 2016-04-28.
 */
public class NotificationReceiver extends Activity {
    Button OK;
    ImageButton Cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        Cancel = (ImageButton) findViewById(R.id.cancel_button);
        OK = (Button)findViewById(R.id.ok_button);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
