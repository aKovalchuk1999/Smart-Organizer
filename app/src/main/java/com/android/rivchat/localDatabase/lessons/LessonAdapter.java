package com.android.rivchat.localDatabase.lessons;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.rivchat.R;

import java.util.List;

/**
 * Created by Andriy on 01.06.2018.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder1> {

    private Context context;
    private List<Lesson> lessonsList;

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        public TextView lllessonNum;
        public TextView lllessonName;
        public TextView lllessonTeacherSymbol;
        public TextView lllessonTeacherName;
        public TextView lllessonAudienceSymbol;
        public TextView lllessonAudienceNum;
        public TextView lllessonWeekSymbol;
        public TextView lllessonWeekWeek;
        public TextView lllessonTimeSymbol;
        public TextView lllessonTimeStart;
        public TextView lllessonTimeEnd;

        public MyViewHolder1(View view) {
            super(view);
            lllessonNum = view.findViewById(R.id.lllesson_number);
            lllessonName = view.findViewById(R.id.lllesson_name);
            lllessonTeacherSymbol = view.findViewById(R.id.lllesson_teacher_symbol);
            lllessonTeacherName = view.findViewById(R.id.lllesson_teacher_name);
            lllessonAudienceSymbol = view.findViewById(R.id.lllesson_audience_symbol);
            lllessonAudienceNum = view.findViewById(R.id.lllesson_audience);
            lllessonWeekSymbol = view.findViewById(R.id.lllesson_week_symbol);
            lllessonWeekWeek = view.findViewById(R.id.lllesson_week);
            lllessonTimeSymbol = view.findViewById(R.id.lllesson_start_end_symbol);
            lllessonTimeStart = view.findViewById(R.id.lllesson_lesson_start);
            lllessonTimeEnd = view.findViewById(R.id.lllesson_lesson_end);
        }
    }

    public LessonAdapter(Context context, List<Lesson> lessonsList) {
        this.context = context;
        this.lessonsList = lessonsList;
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_list_row, parent, false);
        return new MyViewHolder1(itemView);
    }

    @Override

    public void onBindViewHolder(MyViewHolder1 holder, int position) {
        Lesson lesson = lessonsList.get(position);
        holder.lllessonNum.setText(String.valueOf(lesson.getNum()));
        holder.lllessonName.setText(lesson.getName());
        holder.lllessonTeacherName.setText(lesson.getTeacher());
        holder.lllessonAudienceNum.setText(lesson.getAudience());
        holder.lllessonWeekWeek.setText(lesson.getWeek());
        holder.lllessonTimeStart.setText(lesson.getStart());
        holder.lllessonTimeEnd.setText(lesson.getEnd());
        holder.lllessonTeacherSymbol.setText(Html.fromHtml("&#8226;"));
        holder.lllessonAudienceSymbol.setText(Html.fromHtml("&#8226;"));
        holder.lllessonWeekSymbol.setText(Html.fromHtml("&#8226;"));
        holder.lllessonTimeSymbol.setText(Html.fromHtml("&#8226;"));

    }


    @Override
    public int getItemCount() {
        return lessonsList.size();
    }

}
