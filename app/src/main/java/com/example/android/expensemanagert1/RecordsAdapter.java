package com.example.android.expensemanagert1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Database.model.Tracker;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.MyViewHolder> {
    private Context context;
    private List<Tracker> recordsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amountdisplay;
        public TextView categorydisplay;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            amountdisplay = view.findViewById(R.id.amountdisplay);
            categorydisplay = view.findViewById(R.id.categorydisplay);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }

    public RecordsAdapter(Context context, List<Tracker> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tracker record = recordsList.get(position);

        holder.amountdisplay.setText(Integer.toString(record.gAmount()));

        holder.categorydisplay.setText(record.gPurpose());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(record.gTimestamp()));
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }



}
