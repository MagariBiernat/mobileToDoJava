package com.example.todo.ui.Main_Page.Profile;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private SQLiteDatabase myDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView usernameText = root.findViewById(R.id.userName);
        final TextView dateCreated = root.findViewById(R.id.date_created);
        final TextView tasksSuccess = root.findViewById(R.id.tasksSuccessful);
        final TextView tasksFailed = root.findViewById(R.id.tasksFailed);
        final TextView tasksOnGoing = root.findViewById(R.id.tasksOnGoing);
        final String username = ((Main_page)getActivity()).getUsername();
        myDatabase = ((Main_page)getActivity()).myDatabase;
        profileViewModel.setUsername(username);
        profileViewModel.setDatabase(myDatabase);
        usernameText.setText(username);



        //observe for date created return
        profileViewModel.getDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dateCreated.setText(s);
            }
        });

        //observe for tasks susccessful and failed

        profileViewModel.getSuccess().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tasksSuccess.setText("Tasks Successful done : "+integer.toString());
            }
        });

        profileViewModel.getFailed().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tasksFailed.setText("Tasks failed : "+ integer.toString());
            }
        });

        profileViewModel.getOnGoing().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tasksOnGoing.setText("Tasks On Going now : "+ integer.toString());
            }
        });


        return root;
    }
}
