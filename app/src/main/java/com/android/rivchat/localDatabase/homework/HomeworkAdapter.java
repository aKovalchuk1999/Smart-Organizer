package com.android.rivchat.localDatabase.homework;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.rivchat.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Andriy on 03.06.2018.
 */

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.MyViewHolder> {

    private Context context;
    private List<Homework> notesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subject;
        public TextView homework;
        public TextView priority;
        public TextView color;
        public TextView symbol;
        public LinearLayout linearLayout, linearLayout1;

        public MyViewHolder(View view) {
            super(view);

            subject = view.findViewById(R.id.homework_subject_name);
            homework = view.findViewById(R.id.homework_homework);
            priority = view.findViewById(R.id.homework_priority);
            color = view.findViewById(R.id.homework_item_left_color);
            symbol = view.findViewById(R.id.priority_symbol);
            linearLayout = view.findViewById(R.id.color_linear_layout);
            linearLayout1 = view.findViewById(R.id.color_linear_layout1);
        }
    }

    public HomeworkAdapter(Context context, List<Homework> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Homework note = notesList.get(position);
        holder.subject.setText(note.getSubject());
        holder.homework.setText(note.getHomework());
        holder.priority.setText(note.getPriority());
        holder.symbol.setText(Html.fromHtml("&#8226;"));
        //Random rnd = new Random();
        //holder.color.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        if(note.getPriority().equals("Важливе завдання")){
            holder.color.setBackgroundColor(Color.RED);
            holder.subject.setBackgroundColor(Color.parseColor("#f55959"));
            holder.linearLayout.setBackgroundColor(Color.parseColor("#f55959"));
            holder.linearLayout1.setBackgroundColor(Color.parseColor("#f55959"));
        }
        if(note.getCompleted().equals("YES")){
            holder.color.setBackgroundColor(Color.parseColor("#447832"));
            holder.subject.setBackgroundColor(Color.parseColor("#90ce7b"));
            holder.linearLayout.setBackgroundColor(Color.parseColor("#90ce7b"));
            holder.linearLayout1.setBackgroundColor(Color.parseColor("#90ce7b"));
        }
    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }



}
