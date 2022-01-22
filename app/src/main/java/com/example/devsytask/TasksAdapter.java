package com.example.devsytask;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private final Context mCtx;
    private List<RoomClassPrice> taskList;
    private final Activity activity;

    public TasksAdapter(Context mCtx, Activity act, List<RoomClassPrice> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
        this.activity = act;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.row_layout, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Picasso.get().load(taskList.get(position).getImage()).into(holder.iv_flag);

        holder.name.setText(taskList.get(position).getName());
        holder.symbol.setText("Symbol : " + taskList.get(position).getSymbol());

        String currentPrice = new DecimalFormat("#,###.00").format(taskList.get(position).getCurrentPrice());
        String high = new DecimalFormat("#,###.00").format(taskList.get(position).getHigh());
        String low = new DecimalFormat("#,###.00").format(taskList.get(position).getLow());

        holder.currentPrice.setText("Current price : " + currentPrice + "$");
        holder.high.setText("High : " + high + "$");
        holder.low.setText("Low : " + low + "$");
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView symbol, name, currentPrice, high, low;

        ImageView iv_flag;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            symbol = itemView.findViewById(R.id.tv_symbol);
            name = itemView.findViewById(R.id.tv_name);
            currentPrice = itemView.findViewById(R.id.tv_currentPrice);
            high = itemView.findViewById(R.id.tv_high);
            low = itemView.findViewById(R.id.tv_low);
            iv_flag = itemView.findViewById(R.id.iv_flag);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }

    }

    public void updateList(List<RoomClassPrice> updateList) {
        taskList = updateList;
        notifyDataSetChanged();
    }
}
