package com.android.rivchat.localDatabase.mark;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by Andriy on 05.06.2018.
 */

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.MyViewHolder> {

    private Context context;
    private List<Mark> markssList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public TextView mark;
        public TextView date;
        public LinearLayout linearLayoutColor;

        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.mark_note);
            mark = view.findViewById(R.id.mark_mark);
            date = view.findViewById(R.id.mark_date);
            linearLayoutColor = view.findViewById(R.id.mark_item_linear_layout);
        }
    }

    public MarkAdapter(Context context, List<Mark> notesList) {
        this.context = context;
        this.markssList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Mark mark = markssList.get(position);
        holder.note.setText(mark.getNote());
        holder.mark.setText(mark.getMark());
        holder.date.setText(formatDate(mark.getDate()));
        //holder.dot.setText(Html.fromHtml("&#8902;"));
        //Random rnd = new Random();
        //holder.dot.setTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        // Formatting and displaying timestamp
        if(Integer.parseInt(mark.getMark()) < 10 && Integer.parseInt(mark.getMark()) > 4 ){
            holder.linearLayoutColor.setBackgroundColor(Color.parseColor("#babc32"));
            holder.date.setTextColor(Color.parseColor("#babc32"));
        }else if (Integer.parseInt(mark.getMark()) < 5){
            holder.linearLayoutColor.setBackgroundColor(Color.parseColor("#f55959"));
            holder.date.setTextColor(Color.parseColor("#f55959"));
        }

    }


    @Override
    public int getItemCount() {
        return markssList.size();
    }



    /**

     * Formatting timestamp to `MMM d` format

     * Input: 2018-02-21 00:15:42

     * Output: Feb 21

     */

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {
        }

        return "";
    }

}
