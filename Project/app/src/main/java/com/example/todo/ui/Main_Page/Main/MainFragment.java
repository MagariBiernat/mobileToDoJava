package com.example.todo.ui.Main_Page.Main;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private MainViewModel mainViewModel;
    String _username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                ViewModelProviders.of(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        _username = ((Main_page)getActivity()).getUsername();
        ListView myListView = root.findViewById(R.id.tasksList);
        final Button newTaskButton = root.findViewById(R.id.buttonNewTask);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main_page)getActivity()).newTaskAcitivity();
            }
        });




        // TODO: send database to a viewmodel like with profile fragment, change array list to object type Tasks
        // TODO  create a custom component for listview item...


//        final ArrayList<String> myTasks = new ArrayList<String>();
//
//        myTasks.add("Odkurzyc");
//        myTasks.add("Posprzatac");
//        myTasks.add("Odkurzyc");
//        myTasks.add("Posprzatac");
//        myTasks.add("Odkurzyc");
//        myTasks.add("Posprzatac");
//        myTasks.add("Odkurzyc");
//        myTasks.add("Posprzatac");
//        myTasks.add("Odkurzyc");
//        myTasks.add("Posprzatac");
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, myTasks);
//
//        myListView.setAdapter(arrayAdapter);
//
//        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), myTasks.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });
        
        
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        //TODO: refresh tasks from database
    }
}
