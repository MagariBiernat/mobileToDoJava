package com.example.todo.ui.Main_Page.Notes;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main.Task;
import com.example.todo.ui.Main_Page.Main_page;

import java.util.ArrayList;

public class NotesFragment extends Fragment {


    private NotesViewModel notesViewModel;
    SQLiteDatabase myDatabase;
    private ListView myListView;
    private ArrayList<String> myNotes = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        //get username from parent, set to VM

        String _username = ((Main_page)getActivity()).getUsername();
        notesViewModel.setUsername(_username);

        //database
        myDatabase = ((Main_page)getActivity()).myDatabase;
        notesViewModel.setMyDatabase(myDatabase);

        myListView = root.findViewById(R.id.notesList);

        Button newNote = root.findViewById(R.id.buttonNewNote);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main_page)getActivity()).newNoteActivity();
            }
        });


        notesViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                myNotes.clear();
                myNotes = (ArrayList<String>)strings.clone();
                fillTasks();
            }
        });

        return root;
    }

    private void fillTasks(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, myNotes);

        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), myNotes.get(position), Toast.LENGTH_SHORT).show();
                //TODO: show notes on new notes activity
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        myNotes.clear();
        notesViewModel.setNotes();
    }

}
