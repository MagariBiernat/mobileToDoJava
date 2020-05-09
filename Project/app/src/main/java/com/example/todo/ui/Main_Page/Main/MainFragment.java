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
import java.util.ArrayList;


public class MainFragment extends Fragment {

    private MainViewModel mainViewModel;
    private ListView myListView;
    private ArrayList<Task> myTasks = new ArrayList<Task>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                ViewModelProviders.of(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //get username from parent and set it to viewmodel

        String _username = ((Main_page) getActivity()).getUsername();
        mainViewModel.setUsername(_username);

        //db from parent and to viewmodel
        SQLiteDatabase myDatabase = ((Main_page) getActivity()).myDatabase;
        mainViewModel.setMyDatabase(myDatabase);

        myListView = root.findViewById(R.id.tasksList);

        mainViewModel.setTasks();
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

        // TODO  create a custom component for listview item...
        return root;
    }

    private void fillTasks(){

        ArrayAdapter<Task> arrayAdapter = new ArrayAdapter<Task>(getContext(), android.R.layout.simple_list_item_1, myTasks);
        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: create an activity for each task !
                ((Main_page)getActivity()).showTaskActivity(myTasks.get(position).getID(), myTasks.get(position).getTitle());
                 Toast.makeText(getActivity(), myTasks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mainViewModel.setTasks();
        this.fillTasks();
        //TODO: refresh tasks from database
    }
}
