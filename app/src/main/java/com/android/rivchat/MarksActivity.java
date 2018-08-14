package com.android.rivchat;

import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rivchat.localDatabase.DatabaseHelper;
import com.android.rivchat.localDatabase.Notemark.MyDividerItemDecoration;
import com.android.rivchat.localDatabase.Notemark.RecyclerTouchListener;
import com.android.rivchat.localDatabase.Subject.Subject;
import com.android.rivchat.localDatabase.Subject.SubjectAdapter;
import com.android.rivchat.localDatabase.mark.Mark;
import com.android.rivchat.localDatabase.mark.MarkAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MarksActivity extends AppCompatActivity {

    private MarkAdapter mAdapter;
    private List<Mark> markList = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView, mainAvgMark;
    private Spinner spinner;

    private DatabaseHelper db;
    private String subject;
    private double marksCount = 0;
    private double marksSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);


        recyclerView = findViewById(R.id.marks_recycler_view);
        noNotesView = findViewById(R.id.empty_marks_view);
        spinner = findViewById(R.id.android_material_design_spinner);
        mainAvgMark = findViewById(R.id.main_marks_avg_mark);



        db = new DatabaseHelper(this);


        subjects = db.getAllSubjects();
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < subjects.size(); i++ ){
            names.add(subjects.get(i).getSubject());
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = spinner.getSelectedItem().toString();
                //Toast.makeText(MarksActivity.this, " " + subject, Toast.LENGTH_LONG).show();
                markList.clear();
                markList.addAll(db.getMarksBySubject(subject));
                mAdapter = new MarkAdapter(getApplicationContext(), markList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new MyDividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL, 16));
                recyclerView.setAdapter(mAdapter);

                marksSum = 0;
                marksCount = 0;
                for (int i = 0; i < markList.size(); i++){
                    marksCount++;
                    marksSum += Integer.parseInt(markList.get(i).getMark());
                }
                double avg = marksSum / marksCount;
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                mainAvgMark.setText(decimalFormat.format(avg));
                //Toast.makeText(MarksActivity.this, marksCount + " - " + marksSum + " - " + marksSum / marksCount, Toast.LENGTH_LONG).show();

                toggleEmptyNotes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //markList.addAll(db.getAllMarks());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_mark_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

        mAdapter = new MarkAdapter(this, markList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
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


    public void openAddDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.add_mark_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        final EditText note, mark, date;
        note = (EditText) dialoglayout.findViewById(R.id.add_mark_note);
        mark = (EditText) dialoglayout.findViewById(R.id.add_mark_mark);
        date = (EditText) dialoglayout.findViewById(R.id.add_mark_date);

        builder.setCancelable(false).setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createMark(subject,note.getText().toString(), mark.getText().toString(), date.getText().toString());
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
                    //deleteSubject(position);
                }
            }
        });
        builder.show();
    }

    private void createMark(String subject, String note, String mark, String date) {
        long id = db.insertMark(subject, note, mark, date);
        Mark n = db.getMark(id);
        if (n != null) {
            markList.add(markList.size(), n);
            mAdapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }

    private void updateMark(String subject, String note, String mark, String date, int position) {
        Mark n = markList.get(position);
        n.setSubject(subject);
        n.setNote(note);
        n.setMark(mark);
        n.setDate(date);
        db.updateMark(n);
        markList.set(position, n);
        mAdapter.notifyItemChanged(position);
        toggleEmptyNotes();
    }


    private void deleteMark(int position) {
        db.deleteMark(markList.get(position));
        markList.remove(position);
        mAdapter.notifyItemRemoved(position);
        toggleEmptyNotes();
    }


    private void toggleEmptyNotes() {
        if (db.getMarksCountBySubject(subject) > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }

}
