package com.android.rivchat.week_days;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.android.rivchat.R;
import com.android.rivchat.ShedulActivity;
import com.android.rivchat.localDatabase.DatabaseHelper;

public class WeekActivity extends AppCompatActivity {


    private CardView monday, tuesday, wednesday, thursday, friday, saturday;
    private TextView mon, tues, wednes, thurs, fri, satur;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        monday = (CardView) findViewById(R.id.monday_shedule_list);
        tuesday = (CardView) findViewById(R.id.tuesday_shedule_list);
        wednesday = (CardView) findViewById(R.id.wednesday_shedule_list);
        thursday = (CardView) findViewById(R.id.thursday_shedule_list);
        friday = (CardView) findViewById(R.id.friday_shedule_list);
        saturday = (CardView) findViewById(R.id.saturday_shedule_list);

        mon = (TextView) findViewById(R.id.sub_header);
        tues = (TextView) findViewById(R.id.sub_header1);
        wednes = (TextView) findViewById(R.id.sub_header2);
        thurs = (TextView) findViewById(R.id.sub_header3);
        fri = (TextView) findViewById(R.id.sub_header4);
        satur = (TextView) findViewById(R.id.sub_header5);

        db = new DatabaseHelper(this);
        int monCount, tueCount, wednesCount, thursCount, friCount, saturCount;
        monCount = db.getLessonsCountByDay("Понеділок");
        tueCount = db.getLessonsCountByDay("Вівторок");
        wednesCount = db.getLessonsCountByDay("Середа");
        thursCount = db.getLessonsCountByDay("Четвер");
        friCount = db.getLessonsCountByDay("Пятниця");
        saturCount = db.getLessonsCountByDay("Субота");

        mon.setText("К-сть занять: " + String.valueOf(monCount));
        tues.setText("К-сть занять: " + String.valueOf(tueCount));
        wednes.setText("К-сть занять: " + String.valueOf(wednesCount));
        thurs.setText("К-сть занять: " + String.valueOf(thursCount));
        fri.setText("К-сть занять: " + String.valueOf(friCount));
        satur.setText("К-сть занять: " + String.valueOf(saturCount));

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentDay(0);
                WeekActivity.this.finish();
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentDay(1);
                WeekActivity.this.finish();
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentDay(2);
                WeekActivity.this.finish();
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentDay(3);
                WeekActivity.this.finish();
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentDay(4);
                WeekActivity.this.finish();
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentDay(5);
                WeekActivity.this.finish();
            }
        });

    }

    public void openCurrentDay(int currentTab){
        Intent intent = new Intent(WeekActivity.this, ShedulActivity.class);
        intent.putExtra("currentTab", currentTab);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}

