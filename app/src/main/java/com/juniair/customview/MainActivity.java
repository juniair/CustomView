package com.juniair.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juniair.calendarview.custom_view.CalendarView;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
    }
}
