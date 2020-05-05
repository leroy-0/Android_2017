package com.krisantem.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AdapterRow extends ArrayAdapter<Item> implements Filterable {
    private final Context context;
    private final ArrayList<Item> itemsArrayList;
    private ArrayList<Item> itemsArrayListFiltered;
    private RowFilter rowFilter;

    /**
     * @param context
     * @param itemsArrayList
     */
    AdapterRow(Context context, ArrayList<Item> itemsArrayList) {

        super(context, R.layout.task_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.itemsArrayListFiltered = itemsArrayList;
        getFilter();
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return view of this custom adapter
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.task_row, parent, false);

        TextView titleView = rowView.findViewById(R.id.task_title);
        TextView contentView = rowView.findViewById(R.id.task_content);
        TextView dateView = rowView.findViewById(R.id.task_date);
        TextView timeView = rowView.findViewById(R.id.task_time);
        ProgressBar progressBar = rowView.findViewById(R.id.task_progress);

        try {
            @SuppressLint("SimpleDateFormat") DateFormat format =
                    new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date expected = format.parse(itemsArrayListFiltered.get(position).getDate()
                    + " " + itemsArrayListFiltered.get(position).getTime());
            Date actual = new Date();

            long diff = expected.getTime() - actual.getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            int percentage;
            int color = Color.BLUE;

            if (minutes >= 0)
                percentage = (int)(100 - (long)(100 * ((float)minutes / 10080)));
            else
                percentage = 100;
            percentage = percentage >= 0 ? percentage : 0;

            progressBar.setProgress(percentage);

            progressBar.getProgressDrawable().setColorFilter(color
                    , android.graphics.PorterDuff.Mode.SRC_IN);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        titleView.setText(itemsArrayListFiltered.get(position).getTitle());
        contentView.setText(itemsArrayListFiltered.get(position).getStatus());
        dateView.setText(itemsArrayListFiltered.get(position).getDate());
        timeView.setText(itemsArrayListFiltered.get(position).getTime());

        return rowView;
    }

    /**
     * Get size of task list
     * @return taskList size
     */
    @Override
    public int getCount() {
        return itemsArrayListFiltered.size();
    }

    /**
     * Get specific item from task list
     * @param i item index
     * @return list item
     */
    @Override
    public Item getItem(int i) {
        return itemsArrayListFiltered.get(i);
    }

    /**
     * Get task list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Get custom filter
     * @return filter
     */
    @NonNull
    @Override
    public Filter getFilter() {
        if (rowFilter == null) {
            rowFilter = new RowFilter();
        }

        return rowFilter;
    }

    private class RowFilter extends Filter {

        /**
         * @param constraint
         * @return list of filtered results
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Item> tempList = new ArrayList<>();

                // search content in task list
                for (Item task : itemsArrayList) {
                    if (task.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(task);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = itemsArrayList.size();
                filterResults.values = itemsArrayList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemsArrayListFiltered = (ArrayList<Item>) results.values;
            notifyDataSetChanged();
        }
    }
}
