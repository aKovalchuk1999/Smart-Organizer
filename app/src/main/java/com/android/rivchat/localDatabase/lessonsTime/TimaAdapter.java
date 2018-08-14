package com.android.rivchat.localDatabase.lessonsTime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.rivchat.R;
import java.util.List;

/**
 * Created by Andriy on 02.06.2018.
 */

public class TimaAdapter extends RecyclerView.Adapter<TimaAdapter.MyViewHolder> {

    private Context context;
    private List<LessonTime> timeLessons;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView num;
        public TextView start;
        public TextView end;

        public MyViewHolder(View view) {
            super(view);
            num = view.findViewById(R.id.time_num);
            start = view.findViewById(R.id.time_start);
            end = view.findViewById(R.id.time_end);
        }
    }

    public TimaAdapter(Context context, List<LessonTime> timesList) {
        this.context = context;
        this.timeLessons = timesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override

    public void onBindViewHolder(MyViewHolder holder, int position) {
        LessonTime time = timeLessons.get(position);
        holder.num.setText(String.valueOf(time.getNumber()));
        holder.start.setText(time.getStart());
        holder.end.setText(time.getEnd());
    }


    @Override
    public int getItemCount() {
        return timeLessons.size();
    }

}
