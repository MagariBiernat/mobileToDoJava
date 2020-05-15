package com.example.todo.ui.Main_Page.Main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;


public class MainFragment extends Fragment {

    private MainViewModel mainViewModel;
    SQLiteDatabase myDatabase;
    private ListView myListView;
    LinearLayout layout;
    private ArrayList<Task> myTasks = new ArrayList<Task>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                ViewModelProviders.of(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //get username from parent and set it to viewmodel

        layout = root.findViewById(R.id.tasksNone);

        String _username = ((Main_page) getActivity()).getUsername();
        mainViewModel.setUsername(_username);

        //db from parent and to viewmodel
        myDatabase = ((Main_page) getActivity()).myDatabase;
        mainViewModel.setMyDatabase(myDatabase);

        myListView = root.findViewById(R.id.tasksList);

        try {
            mainViewModel.setTasks();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Button newTaskButton = root.findViewById(R.id.buttonNewTask);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main_page)getActivity()).newTaskAcitivity();
            }
        });

        //observer for new tasks from view model
        mainViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Task>>() {
            @Override
            public void onChanged(ArrayList<Task> tasks) {
                myTasks.clear();
                myTasks = (ArrayList<Task>)tasks.clone();
                fillTasks();
            }
        });

        return root;
    }

    private void fillTasks(){

        AdapterTasks adapterTasks;

        //TODO if msg is to make it red make it red lol
        int count = myTasks.size();

        if(count == 0){
            layout.setVisibility(View.VISIBLE);
        } else
            layout.setVisibility(View.GONE);


        adapterTasks = new AdapterTasks(getActivity(), 0, myTasks);
        myListView.setAdapter(adapterTasks);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((Main_page)getActivity()).showTaskActivity(myTasks.get(position).getID(), myTasks.get(position).getTitle());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        myTasks.clear();
        try {
            mainViewModel.setTasks();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
