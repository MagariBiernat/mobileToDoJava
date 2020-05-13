package com.example.todo.ui.Main_Page.Main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.todo.R;

import java.util.ArrayList;

public class AdapterTasks extends ArrayAdapter<Task> {
    private Activity activity;
    private ArrayList<Task> listTask;
    private static LayoutInflater inflater = null;

    //my own adapter for listView

    public AdapterTasks (Activity activity, int textViewResourceId, ArrayList<Task> _listTasks){
        super(activity, textViewResourceId, _listTasks);
        try{
            this.activity = activity;
            this.listTask = _listTasks;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch( Exception e)
        {
            //handle exception ?
        }
    }

    public int getCount() {
        return listTask.size();
    }

    public Task getItem(Task position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                // taks_list_item is my custom layout
                vi = inflater.inflate(R.layout.task_list_item, null);
                holder = new ViewHolder();
                // taskTitle is id for textView where it display title for each task from Task object.getTitle()
                holder.display_name = (TextView) vi.findViewById(R.id.taskTitle);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }
            holder.display_name.setText(listTask.get(position).getTitle());
        } catch (Exception e) {


        }
        return vi;
    }

}
