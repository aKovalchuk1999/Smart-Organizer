package com.android.rivchat.localDatabase.Subject;

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
 * Created by Andriy on 31.05.2018.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder>{

    private Context context;
    private List<Subject> subjectsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView symbol;
        public TextView name;
        public TextView teacher;

        public MyViewHolder(View view) {
            super(view);
            symbol = view.findViewById(R.id.subject_symbol);
            name = view.findViewById(R.id.subject_name);
            teacher = view.findViewById(R.id.subject_teacher);
        }
    }

    public SubjectAdapter(Context context, List<Subject> subjectsList) {
        this.context = context;
        this.subjectsList = subjectsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Subject subject = subjectsList.get(position);
        holder.name.setText(subject.getSubject());
        // Displaying dot from HTML character code
        holder.symbol.setText(Html.fromHtml("&#8226;"));
        //Random rnd = new Random();
        //holder.symbol.setTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        // Formatting and displaying timestamp
        holder.teacher.setText(subject.getTeacher());
    }


    @Override
    public int getItemCount() {
        return subjectsList.size();
    }



}
