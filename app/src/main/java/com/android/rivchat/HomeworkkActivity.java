package com.android.rivchat;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rivchat.localDatabase.DatabaseHelper;
import com.android.rivchat.localDatabase.Notemark.MyDividerItemDecoration;
import com.android.rivchat.localDatabase.Notemark.RecyclerTouchListener;
import com.android.rivchat.localDatabase.Subject.Subject;
import com.android.rivchat.localDatabase.homework.Homework;
import com.android.rivchat.localDatabase.homework.HomeworkAdapter;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeworkkActivity extends AppCompatActivity {

    private HomeworkAdapter mAdapter;
    private List<Homework> notesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private DatabaseHelper db;

    private String curDateInString;
    private String selectByDate;
    ImageGenerator mImageGenerator;
    ImageButton mDateEditText;
    Calendar mCurrentDate = Calendar.getInstance();;
    Bitmap mGeneratedDateIcon;
    View.OnClickListener setDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int mYear = mCurrentDate.get(Calendar.YEAR);
            int mMonth = mCurrentDate.get(Calendar.MONTH);
            int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker = new DatePickerDialog(HomeworkkActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                    mCurrentDate.set(selectedYear, selectedMonth, selectedDay);
                    mGeneratedDateIcon = mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                    mDisplayGeneratedImage.setImageBitmap(mGeneratedDateIcon);
                    selectByDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;

                    notesList.addAll(db.getHomeworksByDate(selectByDate));

                    updateList(notesList);

                }
            }, mYear, mMonth, mDay);
            mDatePicker.show();
        }
    };
    ImageView mDisplayGeneratedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeworkk);


        mImageGenerator = new ImageGenerator(this);
        mDateEditText = (ImageButton) findViewById(R.id.select_date_btn);
        mDisplayGeneratedImage = (ImageView) findViewById(R.id.imgGenerated);

        mImageGenerator.setIconSize(40, 45);
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);

        mImageGenerator.setDateColor(Color.parseColor("#009688"));
        mImageGenerator.setMonthColor(Color.WHITE);

        mImageGenerator.setStorageToSDCard(true);
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear = mCurrentDate.get(Calendar.YEAR);
                int mMonth = mCurrentDate.get(Calendar.MONTH);
                int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(HomeworkkActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        mCurrentDate.set(selectedYear, selectedMonth, selectedDay);
                        mGeneratedDateIcon = mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                        mDisplayGeneratedImage.setImageBitmap(mGeneratedDateIcon);
                        selectByDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;

                        notesList.clear();
                        notesList.addAll(db.getHomeworksByDate(selectByDate));
                        updateList(notesList);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();


            }
        });

        int mYear1 = mCurrentDate.get(Calendar.YEAR);
        int mMonth1 = mCurrentDate.get(Calendar.MONTH);
        int mDay1 = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mCurrentDate.set(mYear1, mMonth1, mDay1);
        mGeneratedDateIcon = mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
        mDisplayGeneratedImage.setImageBitmap(mGeneratedDateIcon);

        curDateInString = mYear1 + "-" + mMonth1 + "-" + mDay1;
        selectByDate = curDateInString;

        recyclerView = findViewById(R.id.homework_recycler_view);
        noNotesView = findViewById(R.id.empty_homework_view);

        db = new DatabaseHelper(this);

        notesList.addAll(db.getHomeworksByDate(selectByDate));

        updateList(notesList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_homework_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(HomeworkkActivity.this, mCurrentDate.get(Calendar.YEAR) + " " + mCurrentDate.get(Calendar.MONTH) + " " + mCurrentDate.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_LONG).show();
                //showNoteDialog(false, null, -1);
                openAddDialog();
            }
        });



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

    }


    private void updateList(List<Homework> _list){
        mAdapter = new HomeworkAdapter(this, _list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
    }

    private void createHomework(String subject, String homework, String priority, String date) {
        long id = db.insertHomework(subject, homework, priority, date);
        Homework n = db.getHomework(id);
        if (n != null) {
            notesList.add(notesList.size(), n);
            mAdapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }



    private void updateHomework(String subject, String homework, String priority, String date, int position) {
        Homework n = notesList.get(position);
        n.setSubject(subject);
        n.setHomework(homework);
        n.setPriority(priority);
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

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Редагувати", "Видалити","Позначити як 'ВИКОНАНЕ'"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Виберіть дію: ");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //showNoteDialog(true, notesList.get(position), position);
                } else if(which == 1) {
                    deleteHomework(position);
                }
                else {
                    setCompleted(position);
                }
            }
        });
        builder.show();
    }

    private void setCompleted(int position) {
        Homework n = notesList.get(position);
        n.setCompleted("YES");
        n.setPriority("Виконане завдання");
        db.updateHomework(n);
        notesList.set(position, n);
        mAdapter.notifyItemChanged(position);
        toggleEmptyNotes();
    }


    public void openAddDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.add_homework_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        final EditText homeworkText;
        final Spinner subjectsList;
        final CheckBox isPriority;
        homeworkText = (EditText) dialoglayout.findViewById(R.id.homework_homework_text);
        subjectsList = (Spinner) dialoglayout.findViewById(R.id.homework_subject_spinner);
        isPriority = (CheckBox) dialoglayout.findViewById(R.id.homework_priority);

        List<Subject> subjects = new ArrayList<>();
        subjects = db.getAllSubjects();
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < subjects.size(); i++ ){
            names.add(subjects.get(i).getSubject());
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        subjectsList.setAdapter(adapterSpinner);

        builder.setCancelable(false).setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String prio = "Звичайне завдання";
                if (isPriority.isChecked()){
                    prio = "Важливе завдання";
                }else {prio = "Звичайне завдання";}
                createHomework(subjectsList.getSelectedItem().toString(), homeworkText.getText().toString(), prio, selectByDate);
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

    private void toggleEmptyNotes() {
        if (db.getHomeworksCountByDate(selectByDate) > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }

}
