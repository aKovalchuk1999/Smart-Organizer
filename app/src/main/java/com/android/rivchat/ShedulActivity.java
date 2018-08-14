package com.android.rivchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rivchat.localDatabase.DatabaseHelper;
import com.android.rivchat.localDatabase.Notemark.MyDividerItemDecoration;
import com.android.rivchat.localDatabase.Notemark.Notemark;
import com.android.rivchat.localDatabase.Notemark.NotemarkAdapter;
import com.android.rivchat.localDatabase.Notemark.RecyclerTouchListener;
import com.android.rivchat.localDatabase.Subject.Subject;
import com.android.rivchat.localDatabase.lessons.Lesson;
import com.android.rivchat.localDatabase.lessons.LessonAdapter;
import com.android.rivchat.localDatabase.lessonsTime.LessonTime;
import com.android.rivchat.week_days.WeekActivity;

import java.util.ArrayList;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class ShedulActivity extends AppCompatActivity {


    private LessonAdapter mAdapter;
    private List<Lesson> lessonList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private ImageView foto;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private DatabaseHelper db;
    private String dayName;
    private List<Subject> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedul);


       foto = (ImageView) findViewById(R.id.image_week_day);
       collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_tool_day_name);

        int currentTab;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                currentTab = 0;
            } else {
                currentTab = extras.getInt("currentTab");
            }
        } else {
            currentTab = (int) savedInstanceState.getSerializable("currentTab");
        }
        Drawable image = null;
        if(currentTab == 0){
            //image =(Drawable)getResources().getDrawable(R.drawable.mon_img);
            dayName = "Понеділок";
        }else if(currentTab == 1){
            //image=(Drawable)getResources().getDrawable(R.drawable.tue_img);
            dayName = "Вівторок";
        }else if(currentTab == 2){
            //image=(Drawable)getResources().getDrawable(R.drawable.wed_img);
            dayName = "Середа";
        }else if(currentTab == 3){
            //image=(Drawable)getResources().getDrawable(R.drawable.thurs_img);
            dayName = "Четвер";
        }else if (currentTab == 4){
            //image=(Drawable)getResources().getDrawable(R.drawable.fri_img);
            dayName = "Пятниця";
        }else if(currentTab == 5){
            //image=(Drawable)getResources().getDrawable(R.drawable.satur_img);
            dayName = "Субота";
        }
        //foto.setImageDrawable(image);

        String tempName = "";
        if (dayName.equals("Пятниця")){
            tempName = "П\'ятниця";
        }else {tempName = dayName;}
        collapsingToolbarLayout.setTitle(tempName);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collaps1);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collaps2);



        recyclerView = findViewById(R.id.lllessons_recycler_view);
        noNotesView = findViewById(R.id.empty_lllessons_view);

        db = new DatabaseHelper(this);

        lessonList.addAll(db.getLessonsByDay(dayName));


        mAdapter = new LessonAdapter(this, lessonList);
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
        }));


        FabSpeedDial settingBtn = (FabSpeedDial) findViewById(R.id.ffabSpeedSheduleSetting);
        settingBtn.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if(menuItem.getTitle().equals("Список предметів")){
                    startActivity(new Intent(ShedulActivity.this, SubjectsActivity.class));
                }else if(menuItem.getTitle().equals("Розклад дзвінків")){
                    startActivity(new Intent(ShedulActivity.this, TimesActivity.class));
                }else {
                    openAddDialog();
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });

        FloatingActionButton showWeek = (FloatingActionButton) findViewById(R.id.sshow_week_fab);
        showWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShedulActivity.this, WeekActivity.class));
                ShedulActivity.this.finish();
            }
        });
    }


    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Редагувати", "Видалити"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Виберіть дію: ");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //openEditDialog(subjectsList.get(position), position);
                } else {
                    deleteLesson(position);
                }
            }
        });
        builder.show();
    }
    private void toggleEmptyNotes() {
        if (db.getLessonsCountByDay(dayName) > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }

    public void openAddDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.add_lesson_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        final TextView l_audience, l_teacher, l_start, l_end;
        final Spinner l_num, l_name;
        CheckBox w1, w2, w3, w4;


        l_audience = (TextView) dialoglayout.findViewById(R.id.add_lesson_audience);
        l_teacher = (TextView) dialoglayout.findViewById(R.id.add_lesson_teacher);
        l_start = (TextView) dialoglayout.findViewById(R.id.add_lesson_start);
        l_end = (TextView) dialoglayout.findViewById(R.id.add_lesson_end);

        l_num = (Spinner) dialoglayout.findViewById(R.id.add_lesson_num_spinner);
        l_name = (Spinner) dialoglayout.findViewById(R.id.add_lesson_name);

        w1 = (CheckBox) dialoglayout.findViewById(R.id.week1);
        w2 = (CheckBox) dialoglayout.findViewById(R.id.week2);
        w3 = (CheckBox) dialoglayout.findViewById(R.id.week3);
        w4 = (CheckBox) dialoglayout.findViewById(R.id.week4);


        final String week = "1-4";




        subjects = db.getAllSubjects();

        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < subjects.size(); i++ ){
            names.add(subjects.get(i).getSubject());
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        l_name.setAdapter(adapterSpinner);
        l_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                l_teacher.setText(subjects.get(position).getTeacher());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        List<LessonTime> less = db.getAllTimes();
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < less.size(); i++){
            nums.add(less.get(i).getNumber());
        }
        ArrayAdapter<Integer> adapterNum = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nums);
        l_num.setAdapter(adapterNum);
        l_num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LessonTime lessonTime = db.getTimeByNum(Integer.parseInt(l_num.getSelectedItem().toString()));
                l_start.setText(lessonTime.getStart());
                l_end.setText(lessonTime.getEnd());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setCancelable(false).setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createLesson(Integer.parseInt(l_num.getSelectedItem().toString()), l_name.getSelectedItem().toString(), l_teacher.getText().toString(),
                        l_audience.getText().toString(), week, l_start.getText().toString(), l_end.getText().toString());
            }
        });
        builder.setCancelable(false).setNegativeButton("Скасувати", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
/*
    public void openEditDialog(final Subject subject, final int position) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.edit_subject_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        final TextView sb_name, sb_teacher;
        sb_name = (TextView) dialoglayout.findViewById(R.id.edit_subject_name);
        sb_name.setText(subject.getSubject());
        sb_teacher = (TextView) dialoglayout.findViewById(R.id.edit_subject_teacher);
        sb_teacher.setText(subject.getTeacher());


        builder.setCancelable(false).setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateSubject(sb_name.getText().toString(), sb_teacher.getText().toString(), position);
            }
        });
        builder.setCancelable(false).setNegativeButton("Скасувати", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
*/
    private void createLesson(int num, String name, String teacher, String audience, String week, String start, String end) {
        long id = db.insertLesson(num, name, teacher, audience, week, start, end, dayName);
        Lesson n = db.getLesson(id);
        if (n != null) {
            lessonList.add(lessonList.size(), n);
            mAdapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }

    private void updateLesson(int num, String name, String teacher, String audience, String week, String start, String end, int position) {
        Lesson n = lessonList.get(position);
        n.setNum(num);
        n.setName(name);
        n.setTeacher(teacher);
        n.setAudience(audience);
        n.setWeek(week);
        n.setStart(start);
        n.setEnd(end);
        n.setDay(dayName);
        db.updateLesson(n);
        lessonList.set(position, n);
        mAdapter.notifyItemChanged(position);
        toggleEmptyNotes();
    }


    private void deleteLesson(int position) {
        db.deleteLesson(lessonList.get(position));
        lessonList.remove(position);
        mAdapter.notifyItemRemoved(position);
        toggleEmptyNotes();
    }
    
}
