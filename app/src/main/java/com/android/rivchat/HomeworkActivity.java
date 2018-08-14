package com.android.rivchat;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rivchat.localDatabase.DatabaseHelper;
import com.android.rivchat.localDatabase.Notemark.MyDividerItemDecoration;
import com.android.rivchat.localDatabase.Notemark.RecyclerTouchListener;
import com.android.rivchat.localDatabase.homework.Homework;
import com.android.rivchat.localDatabase.homework.HomeworkAdapter;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeworkActivity extends AppCompatActivity  {

    private HomeworkAdapter mAdapter;
    private List<Homework> notesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private DatabaseHelper db;



    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());


    private ImageView imageView, day_icon;
    private FloatingActionButton transfer;
    private LinearLayout image, calendar;

    private Calendar mCurrentDate = Calendar.getInstance();
    private Bitmap mGeneratedDataIcon;
    private ImageGenerator mImageGenerator;
    private ImageButton mDateEdit;
    View.OnClickListener setDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // mCurrentDate = Calendar.getInstance();
            int mYear = mCurrentDate.get(Calendar.YEAR);
            int mMonth = mCurrentDate.get(Calendar.MONTH);
            int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker = new DatePickerDialog(HomeworkActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                    Toast.makeText(HomeworkActivity.this, selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear, Toast.LENGTH_SHORT).show();

                    // Set the mCurrentDate to the selected date-month-year
                    mCurrentDate.set(selectedYear, selectedMonth, selectedDay);
                    mGeneratedDataIcon = mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                    mDisplayGeneratedImage.setImageBitmap(mGeneratedDataIcon);

                }
            }, mYear, mMonth, mDay);
            mDatePicker.show();
        }
    };
    ImageView mDisplayGeneratedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        mImageGenerator = new ImageGenerator(this);
        mDateEdit = (ImageButton) findViewById(R.id.select_date_btn1);
        mDisplayGeneratedImage = (ImageView) findViewById(R.id.icon_week_day1);

        mImageGenerator.setIconSize(50, 50);
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);
        mImageGenerator.setDateColor(Color.parseColor("#009688"));
        mImageGenerator.setMonthColor(Color.WHITE);
        mImageGenerator.setStorageToSDCard(true);

        mDateEdit.setOnClickListener(setDate);


        /*
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        //imageView = (ImageView) findViewById(R.id.temp_image);
        transfer = (FloatingActionButton) findViewById(R.id.transfer_calendar_to_image);

        image = (LinearLayout) findViewById(R.id.calendar_image);
        calendar = (LinearLayout) findViewById(R.id.calendar_calendar);


        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swither = !swither;
                showww();
            }
        });

        mImageGenerator = new ImageGenerator(this);
        day_icon = (ImageView) findViewById(R.id.image_day_icon);

        mImageGenerator.setIconSize(50,50);
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(4);
        mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        mImageGenerator.setMonthColor(Color.WHITE);
        mImageGenerator.setStorageToSDCard(true);



        currentCalender.set(2018, 4, 19);
        mGeneratedDataIcon = mImageGenerator.generateDateImage(currentCalender, R.drawable.empty_calendar);
        day_icon.setImageBitmap(mGeneratedDataIcon);

       // compactCalendarView.drawSmallIndicatorForEvents(true);
       // compactCalendarView.setUseThreeLetterAbbreviation(true);

        //set initial title

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if(swither == false) {
                    Toast.makeText(HomeworkActivity.this, "Date : " + dateClicked.getYear() , Toast.LENGTH_SHORT).show();
                }else {}
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }

        });*/

        //  gotoToday();



        /*recyclerView = findViewById(R.id.homework_recycler_view);
        noNotesView = findViewById(R.id.empty_homework_view);

        recyclerView.setVisibility(View.INVISIBLE);

        db = new DatabaseHelper(this);

        notesList.addAll(db.getAllHomeworks());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_homework_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

        mAdapter = new HomeworkAdapter(this, notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));*/
    }




/*
    private void createHomework(String subject, String homework, String date) {
        long id = db.insertHomework(subject, homework, date);
        Homework n = db.getHomework(id);
        if (n != null) {
            notesList.add(0, n);
            mAdapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }



    private void updateHomework(String subject, String homework, String date, int position) {
        Homework n = notesList.get(position);
        n.setSubject(subject);
        n.setHomework(homework);
        n.setDate(date);
        db.updateHomework(n);
        notesList.set(position, n);
        mAdapter.notifyItemChanged(position);
        toggleEmptyNotes();
    }


    private void deleteHomework(int position) {
        db.deleteHomework(notesList.get(position));
        notesList.remove(position);
        mAdapter.notifyItemRemoved(position);
        toggleEmptyNotes();
    }


    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Редагувати", "Видалити"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Виберіть дію: ");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //showNoteDialog(true, notesList.get(position), position);
                } else {
                    deleteHomework(position);
                }
            }
        });
        builder.show();
    }

    private void toggleEmptyNotes() {
        if (db.getHomeworksCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }

*/

}
