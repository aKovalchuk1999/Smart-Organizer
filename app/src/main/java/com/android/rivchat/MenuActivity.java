package com.android.rivchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Calendar;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    RelativeLayout rellay_timeline, rellay_friends, rellay_chat, rellay_music,
            rellay_gallery, rellay_map, rellay_weather, rellay_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        final int currentTab;
        Calendar sCalendar = Calendar.getInstance();
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        if(dayLongName.equals("Monday")){
            currentTab = 0;
        }else if(dayLongName.equals("Tuesday")){
            currentTab = 1;
        }else if(dayLongName.equals("Wednesday")){
            currentTab = 2;
        }else if(dayLongName.equals("Thursday")){
            currentTab = 3;
        }else if(dayLongName.equals("Friday")){
            currentTab = 4;
        }else if(dayLongName.equals("Saturday")){
            currentTab = 5;
        }else { currentTab = 0; }


        rellay_timeline = findViewById(R.id.rellay_timeline);
        rellay_friends = findViewById(R.id.rellay_friends);
        rellay_chat = findViewById(R.id.rellay_chat);
        rellay_music = findViewById(R.id.rellay_music);
        rellay_gallery = findViewById(R.id.rellay_gallery);
        rellay_map = findViewById(R.id.rellay_map);
        rellay_weather = findViewById(R.id.rellay_weather);
        rellay_settings = findViewById(R.id.rellay_settings);

        rellay_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ShedulActivity.class);
                intent.putExtra("currentTab", currentTab);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        rellay_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("currentTab",0);
                startActivity(intent);
            }
        });
        rellay_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("currentTab",1);
                startActivity(intent);
            }
        });
        rellay_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, NotemarkActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, HomeworkkActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MarksActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MenuActivity.this.finish();
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("currentTab", 2);
                startActivity(intent);
            }
        });
    }

}
