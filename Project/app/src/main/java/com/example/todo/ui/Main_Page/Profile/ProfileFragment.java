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



        return root;
    }
}
