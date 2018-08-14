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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rivchat.localDatabase.DatabaseHelper;
import com.android.rivchat.localDatabase.Notemark.MyDividerItemDecoration;
import com.android.rivchat.localDatabase.Notemark.RecyclerTouchListener;
import com.android.rivchat.localDatabase.lessonsTime.LessonTime;
import com.android.rivchat.localDatabase.lessonsTime.TimaAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimesActivity extends AppCompatActivity {

    private TimaAdapter mAdapter;
    private List<LessonTime> timesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);

        recyclerView = findViewById(R.id.time_recycler_view);
        noNotesView = findViewById(R.id.empty_timet_view);

        db = new DatabaseHelper(this);

        timesList.addAll(db.getAllTimes());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_time_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog(db.getTimesCount());
            }
        });

        mAdapter = new TimaAdapter(this, timesList);
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
                    openEditDialog(timesList.get(position), position);
                } else {
                    deleteSubject(position);
                }
            }
        });
        builder.show();
    }


    public void openAddDialog(int count) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.add_time_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        int current = count + 1;
        final TextView t_num;
        final EditText t_start, t_end;
        t_num = (TextView) dialoglayout.findViewById(R.id.add_time_number);
        t_start = (EditText) dialoglayout.findViewById(R.id.add_time_start);
        t_end = (EditText) dialoglayout.findViewById(R.id.add_time_end);
        t_num.setText(String.valueOf(current));

        builder.setCancelable(false).setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(t_start.getText().length()!= 5 || t_end.getText().length() != 5){
                    Toast.makeText(getApplicationContext(), "Невірно вказано час!", Toast.LENGTH_SHORT).show();
                }else {
                    createTime(Integer.parseInt(t_num.getText().toString()), t_start.getText().toString(), t_end.getText().toString());
                }
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

    public void openEditDialog(final LessonTime time, final int position) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.edit_time_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        final TextView t_num;
        final EditText t_start, t_end;
        t_num = (TextView) dialoglayout.findViewById(R.id.edit_time_number);
        t_num.setText(String.valueOf(time.getNumber()));
        t_start = (EditText) dialoglayout.findViewById(R.id.edit_time_start);
        t_start.setText(time.getStart());
        t_end = (EditText) dialoglayout.findViewById(R.id.edit_time_end);
        t_end.setText(time.getEnd());


        builder.setCancelable(false).setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateTime(Integer.parseInt(t_num.getText().toString()), t_start.getText().toString(), t_end.getText().toString(), position);
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


    private void createTime(int num, String start, String end) {
        long id = db.insertTime(num, start, end);
        LessonTime n = db.getTime(id);
        if (n != null) {
            timesList.add(timesList.size(), n);
            mAdapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }

    private void updateTime(int num, String start, String end, int position) {
        LessonTime n = timesList.get(position);
        n.setNumber(num);
        n.setStart(start);
        n.setEnd(end);
        db.updateTime(n);
        timesList.set(position, n);
        mAdapter.notifyItemChanged(position);
        toggleEmptyNotes();
    }


    private void deleteSubject(int position) {
        db.deleteTime(timesList.get(position));
        timesList.remove(position);
        mAdapter.notifyItemRemoved(position);
        toggleEmptyNotes();
    }


    private void toggleEmptyNotes() {
        if (db.getTimesCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }
}
