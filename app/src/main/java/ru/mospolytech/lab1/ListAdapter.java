package ru.mospolytech.lab1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    List<JobDetail> list;
    List<Metro> metroList;

    public ListAdapter(Context context, List<JobDetail> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobDetail jobs = list.get(position);
        metroList = new ArrayList<>();
        metroList.clear();
        holder.titleJob.setText(jobs.title);
        String[] words = jobs.description.split("\\r?\\n");
        holder.description.setText(words[0]);
       // holder.sourceView.setText("Просмотров: "+ news.views);
        holder.salary.setText("Зарплата: от " + jobs.salary_from + " ₽" );
        Log.d(TAG, "Список станций метро: " + metroList.addAll(jobs.metro));
        if (!metroList.isEmpty()) {
            if (metroList.get(0).color == "" || metroList.get(0).color == null || metroList.get(0).color.isEmpty()) {
                holder.metroPic.setTextColor(Color.parseColor("#ffc0cb"));
                holder.address.setText("Метро: " + metroList.get(0).name);
            } else {
                Log.d(TAG, "Цвет: " + metroList.get(0).color);
                holder.metroPic.setTextColor(Color.parseColor(metroList.get(0).color));
                holder.address.setText("Метро: " + metroList.get(0).name);
            }
        } else  {
            holder.metroPic.setText("");
            holder.address.setText(jobs.address);
        }
        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, JobActivity.class);
            intent.putExtra("hashid", jobs.hashid);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView salary;
        TextView titleJob;
        TextView address;
        TextView metroPic;
        LinearLayout item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.description);
            salary = itemView.findViewById(R.id.salary);
            address = itemView.findViewById(R.id.nameStation);
            metroPic = itemView.findViewById(R.id.metroPin);
            titleJob = itemView.findViewById(R.id.titleJob);
            item = itemView.findViewById(R.id.item);
        }
    }
}
