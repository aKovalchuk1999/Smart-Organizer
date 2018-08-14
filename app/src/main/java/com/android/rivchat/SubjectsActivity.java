package com.android.rivchat;

import android.content.DialogInterface;
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
import android.widget.TextView;

import com.android.rivchat.localDatabase.DatabaseHelper;
import com.android.rivchat.localDatabase.Notemark.MyDividerItemDecoration;
import com.android.rivchat.localDatabase.Notemark.RecyclerTouchListener;
import com.android.rivchat.localDatabase.Subject.Subject;
import com.android.rivchat.localDatabase.Subject.SubjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity {

    private SubjectAdapter mAdapter;
    private List<Subject> subjectsList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);


        recyclerView = findViewById(R.id.subject_recycler_view);
        noNotesView = findViewById(R.id.empty_subject_view);

        db = new DatabaseHelper(this);

        subjectsList.addAll(db.getAllSubjects());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_subject_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

        mAdapter = new SubjectAdapter(this, subjectsList);
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

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Редагувати", "Видалити"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Виберіть дію: ");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    openEditDialog(subjectsList.get(position), position);
                } else {
                    deleteSubject(position);
                }
            }
        });
        builder.show();
    }

    public void openAddDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.add_subject_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        final TextView sb_name, sb_teacher;
        sb_name = (TextView) dialoglayout.findViewById(R.id.add_subject_name);
        sb_teacher = (TextView) dialoglayout.findViewById(R.id.add_subject_teacher);


        builder.setCancelable(false).setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createSubject(sb_name.getText().toString(), sb_teacher.getText().toString());
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




    private void createSubject(String subject, String teacher) {
        long id = db.insertSubject(subject, teacher);
        Subject n = db.getSubject(id);
        if (n != null) {
            subjectsList.add(0, n);
            mAdapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }

    private void updateSubject(String subject, String teacher, int position) {
        Subject n = subjectsList.get(position);
        n.setSubject(subject);
        n.setTeacher(teacher);
        db.updateSubject(n);
        subjectsList.set(position, n);
        mAdapter.notifyItemChanged(position);
        toggleEmptyNotes();
    }


    private void deleteSubject(int position) {
        db.deleteSubject(subjectsList.get(position));
        subjectsList.remove(position);
        mAdapter.notifyItemRemoved(position);
        toggleEmptyNotes();
    }


    private void toggleEmptyNotes() {
        if (db.getSubjectsCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }
}
